package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.BaiduApiTool;
import com.bjfu.fcro.common.utils.OfficeTool;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.ExcelProcessingProgressDao;
import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.SamplingLibraryDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.service.SysSamplingLibraryService;
import com.bjfu.fcro.service.SysSpendBetweenInPointsService;
import lombok.Synchronized;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static com.bjfu.fcro.common.utils.BaiduApiTool.getCoordinate;


@Repository
public class SysSamplingLibraryServiceimpl implements SysSamplingLibraryService {


    protected  static  final Logger logger = LoggerFactory.getLogger(SysSamplingLibraryServiceimpl.class) ;
    @Autowired
    private SamplingLibraryDao   samplingLibraryDao;
    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;
    @Autowired
    private ExcelProcessingProgressDao excelProcessingProgressDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SysSpendBetweenInPointsService sysSpendBetweenInPointsService;

    @Override
    public List<SysSamplingLibrary> selectAll( int pagesize, int pageIndex) {
        return samplingLibraryDao.selectAll(pagesize,pageIndex);
    }

    @Override
    public List<SysSamplingLibrary> selectpageByAdminid(int admin_id, int pagesize, int pageIndex) {
        return samplingLibraryDao.selectpageByAdminid(admin_id,pagesize,pageIndex);
    }

    public  List getalllibrary(
            Integer admin_id,
            Integer pageSize,
            Integer pageIndex,
            String searchname){

        List<SysSamplingLibrary> reslist = new ArrayList<>();
        if(admin_id == 1){
            reslist = selectAll(pageSize,pageIndex);
        }else{
            if(searchname.equals("") || searchname == null){
                reslist = selectpageByAdminid(admin_id,pageSize,pageIndex);
            }else{
                reslist=  searchpageByAdminid(admin_id,pageSize,pageIndex,searchname);
            }
        }
        /**把表中的类型id转换为类型名称*/
        for(int i=0;i<reslist.size();i++){
            StringBuilder foodtypes = new StringBuilder();
            String foodtypeids = reslist.get(i).getFoodtype_ids();
            if(foodtypeids == null || foodtypeids.equals("")){
                reslist.get(i).setFoodtype_ids(null);
                continue;
            }
            String typefoodids[] = reslist.get(i).getFoodtype_ids().split("-");

            for(int j=0;j<typefoodids.length;j++){
                String typename = samplingFoodTypeDao.selecttypename(Integer.valueOf(typefoodids[j]));
                if(typename!=null){
                    foodtypes.append(typename);
                }
                if(j!=typefoodids.length-1){
                    foodtypes.append("-");
                }
            }
            reslist.get(i).setFoodtype_ids(foodtypes.toString());
        }

        return reslist;
    }

    @Override
    public List<SysSamplingLibrary> searchpageByAdminid(int admin_id, int pagesize, int pageIndex, String searchname) {
        return samplingLibraryDao.searchpageByAdminid(admin_id, pagesize, pageIndex, searchname);
    }

    @Override
    public int selectcountbyadminid(int admin_id,String searchname) {
        if(searchname.equals("") || searchname == null){
            if(admin_id == 1){
                return samplingLibraryDao.selectcount();
            }
            return samplingLibraryDao.selectcountByAdminid(admin_id);
        }else{
            if(admin_id == 1){
                return samplingLibraryDao.searchcount(searchname);
            }
            return samplingLibraryDao.searchcountByAdminid(admin_id,searchname);
        }
    }

    @Override
    public int updatesamplinglibrarybyid(int id, String jurisdiction, String category, String ssl_name, String address) {
        return samplingLibraryDao.updatesamplinglibrarybyid(id, jurisdiction, category, ssl_name, address);
    }

    @Override
    @Async
    public Map<Integer, Map<Integer, Object>> uploadbyexcel(String adminaccount,int admin_id,String []paths) {
        logger.info("开始处理excel");
        Map<Integer, Map<Integer,Object>> map = new HashMap<>();
        int superadminid = userDao.selectsuperadminidbyaccount(adminaccount);
        List<SysSamplingFoodType> listtype = samplingFoodTypeDao.findallidbyadminid(superadminid);
        int ids[] = new int[listtype.size()];
        for (int i=0;i<listtype.size();i++){
            ids[i] = listtype.get(i).getId();
        }
        String idstring = toids(ids);
        for(int f = 0;f<paths.length;f++){
            //数据总数
            int total = 0 ;
            //成功条数
            int successtotal = 0;
            //失败条数
            int failtotal = 0;
            //重复条数
            int repeattotal = 0;
            //当前状态
            int state = 0;
            try {
                File file = ResourceUtils.getFile(paths[f]);
//                String filename = files.get(f).getOriginalFilename();
                String filename = file.getName();
                excelProcessingProgressDao.insertnewexceldata(admin_id,filename,state,total,successtotal,failtotal,repeattotal);
                //最新插入的数据id
                int excelprogressid = excelProcessingProgressDao.selectmaxidbyexcelname(filename);
                map = OfficeTool.readExcelContentz(file);
                total = map.size();
//            map.forEach((key,value)->{
//
//            });
                for (Map.Entry<Integer, Map<Integer,Object>> entry1:map.entrySet()){
                    String ssl_name = null;
                    String category = null;
                    String address = null;
                    String jurisdiction = null;
                    String longitude = "0";
                    String latitude = "0";
                    int whether_to_repeat = 0;
                    for(Map.Entry<Integer,Object> entry2:entry1.getValue().entrySet()){

                        if(entry2.getKey() == 1){//辖区
                            jurisdiction = entry2.getValue().toString();
                        }
                        if(entry2.getKey() == 2){//类别
                            category = entry2.getValue().toString();
                        }
                        if(entry2.getKey() == 4){//地址
                            address = entry2.getValue().toString();
                        }
                        if(entry2.getKey() == 3){//抽检点
                            ssl_name = entry2.getValue().toString();
                            if(slelectcountbysslname(ssl_name) >=1){//存在重复抽检点，直接跳过
                                whether_to_repeat = 1;
                                repeattotal ++;
                            }
                        }
                    }
                    if(whether_to_repeat == 0){
                        /*获取经纬度*/
                        String coorstr[] = BaiduApiTool.getCoordinate(address);
                        if(coorstr !=null){
                            longitude = coorstr[0];
                            latitude = coorstr[1];
                              /*插入excel数据   非空时才能插入数据*/
                            if(insertexcel(ssl_name, category, address, admin_id, jurisdiction, idstring,longitude,latitude)){
                                successtotal ++;
                            }
                        }

                    }
                    excelProcessingProgressDao.updatestate(excelprogressid,state,total,successtotal,repeattotal);
                }
                state = 1;
                excelProcessingProgressDao.updatestate(excelprogressid,state,total,successtotal,repeattotal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("处理完成");
        return map;
    }
    @Transactional( rollbackFor = {Exception.class})
    public boolean insertexcel(String ssl_name,String category,String address,int admin_id,String jurisdiction,String ids,String longitude,String latitude){
       synchronized (this){
           List<SysSamplingLibrary> oldList = samplingLibraryDao.selectallByAdminidnopage(admin_id);
           samplingLibraryDao.insertallnewsamplinglibrary(ssl_name, category, address, admin_id, jurisdiction, ids,longitude,latitude);
           int id = samplingLibraryDao.selectidbysllname(ssl_name,admin_id);
           SysSamplingLibrary newPoint = samplingLibraryDao.selectByid(id);
           sysSpendBetweenInPointsService.InsertNewPoints(oldList,newPoint,admin_id);
           return true;
       }


    }
    @Override
    public boolean deletesamplinglibrarybyid(int id) {
        return samplingLibraryDao.deletesamplinglibrarybyid(id);
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public boolean savesamplingidstosslibrary(String adminaccount,String[] distributenames, String[] againhavedistributenames, int insaccountid) throws  Exception {
        int dislen = distributenames.length;
        int adislen = 0;
        if(againhavedistributenames !=null){
            adislen = againhavedistributenames.length;
        }
        int []ids = new int[dislen+adislen];
        int index=0;
        int superadminid = userDao.selectsuperadminidbyaccount(adminaccount);
        for(int i=0;i<dislen;i++ ){
            ids[index++] = samplingFoodTypeDao.selectidbytypenameandadminaccount(distributenames[i],adminaccount,superadminid);
        }
        for(int i=0;i<adislen;i++){


            ids[index++] = samplingFoodTypeDao.selectidbytypenameandadminaccount(againhavedistributenames[i],adminaccount,superadminid);
        }
        samplingLibraryDao.updateidsbyid(toids(ids),insaccountid);
        return true;
    }

    public String toids(int []ids){
        Arrays.sort(ids);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<ids.length ;i++){
            stringBuilder.append(ids[i]);
            if(i!=ids.length-1){
                stringBuilder.append("-");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public int slelectcountbysslname(String ssl_name) {
        return samplingLibraryDao.slelectcountbysslname(ssl_name);
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public   Object  insertallsamplinglibrary(String ssl_name, String category, String address, int admin_id, String jurisdiction, String[] selectedfoodtypes,String adminaccount) throws Exception {

        /**需要加锁*/
        synchronized(this){
            List<SysSamplingLibrary> oldList = samplingLibraryDao.selectallByAdminidnopage(admin_id);
            String str[] = BaiduApiTool.getCoordinate(address);
            if(str == null){
                return ResultTool.fail(ResultCode.IRREGULAR_ADDRESS);
            }
            String longitude = str[0];
            String latitude  = str[1];
            samplingLibraryDao.insertnewsamplinglibrary(ssl_name,category,address,admin_id,jurisdiction,longitude,latitude);
            int id = samplingLibraryDao.selectidbysllname(ssl_name,admin_id);
            this.savesamplingidstosslibrary(adminaccount,selectedfoodtypes,null,id);

            /*插入新的数据到映射表*/
            SysSamplingLibrary newPoint = samplingLibraryDao.selectByid(id);
            sysSpendBetweenInPointsService.InsertNewPoints(oldList,newPoint,admin_id);
            return ResultTool.success();
        }


    }

    /**处理一些遗留的数据 记得关掉异步方法*/
    public void dosome(){
        int adminid = 2;
        List<SysSamplingLibrary> oldList = samplingLibraryDao.selectallByAdminidnopage(adminid);
        System.out.println(oldList.size());
        for (int i = 0; i < oldList.size(); i++) {
            SysSamplingLibrary sysSamplingLibrary = oldList.get(i);
            List<SysSamplingLibrary> temp = new ArrayList<>();
            for (int j = i+1; j < oldList.size(); j++) {
                temp.add(oldList.get(j));
            }
            System.out.println(temp.size());
            sysSpendBetweenInPointsService.InsertNewPoints(temp,sysSamplingLibrary,adminid);
        }
    }

}
