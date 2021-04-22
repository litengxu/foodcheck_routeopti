package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.SamplingLibraryDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class SysSamplingFoodTypeServiceimpl  implements SysSamplingFoodTypeService{

    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SamplingLibraryDao samplingLibraryDao;

    @Override
    public String selecttypename(int id) {
        return samplingFoodTypeDao.selecttypename(id);
    }

    @Override
    public List<SysSamplingFoodType> findalltypebyadminid(String adminaccount) {

        List<SysSamplingFoodType> list = samplingFoodTypeDao.findalltypebyadminid(adminaccount);
        return list;
    }

    @Override
    public List<SysSamplingFoodType> findsixteencategories(int pagesize_true,int pageindex_true) {
        return samplingFoodTypeDao.findsixteencategories(pagesize_true,pageindex_true);
    }

    @Override
    public List<SysSamplingFoodType> findcustomizecategories(int pagesize_true, int pageindex_true, String adminaccount) {
        return samplingFoodTypeDao.findcustomizecategories(pagesize_true, pageindex_true, adminaccount);
    }

    @Override
    public int updatesixteencategories(Integer id, String value_at_risk) {
        return samplingFoodTypeDao.updatesixteencategories(id,value_at_risk);
    }

    @Override
    public int findcountcustomizecategories(String adminaccount) {
        return samplingFoodTypeDao.findcountcustomizecategories(adminaccount);
    }

    @Override
    public int findcountcustomizecategorie(int adminid, String food_type) {
        return samplingFoodTypeDao.findcountcustomizecategorie(adminid,food_type);
    }

    @Override
    public boolean insertnewcustomizecategorie(int admin_id, String type_name, int value_at_risk) {
        return samplingFoodTypeDao.insertnewcustomizecategorie(admin_id,type_name,value_at_risk);
    }

    @Override
    public Object addcustomizecategories(int admin_id, String type_name, int value_at_risk) {
        if(findcountcustomizecategorie(admin_id,type_name) >= 1){
            return ResultTool.fail(ResultCode.FOOD_TYPE_EXISTS);
        }
        if(insertnewcustomizecategorie(admin_id,type_name,value_at_risk)){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public Object deletecustomizecategories(int id,int adminid) {
        /**
         * 1.找到抽检库中包含该id的抽检点，删除该食品类型id 例(食品类型id 2   抽检点包含id （1-2-3） 删除后为1-3)（这里不新建数据用于保留抽检库类型历史）
         *
         * 2. 删除自定义食品类型
         *
         * 以上操作执行事务
         * */

        List<SysSamplingLibrary> list  = samplingLibraryDao.selectallByAdminidnopage(adminid);
        for (int i = 0; i <list.size() ; i++) {
            String foodtypeids = list.get(i).getFoodtype_ids();
            if(foodtypeids == null || foodtypeids.equals("")){//判空，直接跳过
                continue;
            }
            String ids[] = foodtypeids.split("-");//获得食品类型id的数组
            StringBuilder stringBuilder = new StringBuilder();
            for(int j=0;j<ids.length;j++){
                if(!ids[j].equals(String.valueOf(id))){//如果不等于要删除的id，则加入字符串
                    stringBuilder.append(ids[j]);
                    if(j!=ids.length-1){
                        stringBuilder.append('-');
                    }
                }
            }
            /*把新的字符串插入数据库中*/
            samplingLibraryDao.updateidsbyid(stringBuilder.toString(),list.get(i).getId());

        }
        samplingFoodTypeDao.deletecustomizecategorie(id);

        return ResultTool.success();
    }
}
