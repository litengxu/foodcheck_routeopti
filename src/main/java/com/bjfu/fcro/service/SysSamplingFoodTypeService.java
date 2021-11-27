package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingFoodType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingFoodTypeService {

    String selecttypename( int id);

    List<SysSamplingFoodType> findalltypebyadminid(String adminaccount);

    /**查找31大类食品类型   adminid = adminid*/
    List<SysSamplingFoodType> findsixteencategories(int pagesize_true,int pageindex_true,String adminaccount);
    /**查找31大类食品类型   adminid = adminid*/
    int findcountsixteencategories(String adminaccount);

    /**查找自定义食品类型   adminid = admin_id*/
    List<SysSamplingFoodType> findcustomizecategories(int pagesize_true,int pageindex_true,String adminaccount);

    int updatesixteencategories(Integer id,String value_at_risk);
    /**查找自定义食品类型的数量*/
    int findcountcustomizecategories(String adminaccount);

    /**返回某个自定义食品类型的数量*/
    int findcountcustomizecategorie(int adminid, String food_type);

    /**插入新的自定义抽检类型*/
    boolean insertnewcustomizecategorie( int admin_id,String type_name,int value_at_risk);

    /**添加自定义食品类型*/

    Object addcustomizecategories(int admin_id,String adminaccount,String type_name,int value_at_risk);
    /**删除食品类型*/
    Object deletecustomizecategories(int id,int adminid);
    /**添加大类食品类型*/
    Object addsixteencategories(int admin_id,String adminaccount,String type_name,int value_at_risk);

}
