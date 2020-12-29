package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.SamplingLibraryDao;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.service.SysSamplingLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Repository
public class SysSamplingLibraryServiceimpl implements SysSamplingLibraryService {

    @Autowired
    private SamplingLibraryDao   samplingLibraryDao;
    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;

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
            return samplingLibraryDao.selectcountByAdminid(admin_id);
        }else{
            return samplingLibraryDao.searchcountByAdminid(admin_id,searchname);
        }
    }

    @Override
    public int updatesamplinglibrarybyid(int id, String jurisdiction, String category, String ssl_name, String address) {
        return samplingLibraryDao.updatesamplinglibrarybyid(id, jurisdiction, category, ssl_name, address);
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
        for(int i=0;i<dislen;i++ ){
            ids[index++] = samplingFoodTypeDao.selectidbytypenameandadminaccount(distributenames[i],adminaccount);
        }
        for(int i=0;i<adislen;i++){
            ids[index++] = samplingFoodTypeDao.selectidbytypenameandadminaccount(againhavedistributenames[i],adminaccount);
        }
        Arrays.sort(ids);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<(dislen+adislen);i++){
            stringBuilder.append(ids[i]);
            if(i!=(dislen+adislen)-1){
                stringBuilder.append("-");
            }
        }
        samplingLibraryDao.updateidsbyid(stringBuilder.toString(),insaccountid);
        return true;
    }

    @Override
    public int slelectcountbysslname(String ssl_name) {
        return samplingLibraryDao.slelectcountbysslname(ssl_name);
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public boolean insertallsamplinglibrary(String ssl_name, String category, String address, int admin_id, String jurisdiction, String[] selectedfoodtypes,String adminaccount) throws Exception {
        samplingLibraryDao.insertnewsamplinglibrary(ssl_name,category,address,admin_id,jurisdiction);
        int id = samplingLibraryDao.selectidbysllname(ssl_name);
        this.savesamplingidstosslibrary(adminaccount,selectedfoodtypes,null,id);
        return true;

    }
}
