package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingFoodType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SamplingFoodTypeDao {

    /**超级管理员，按分页查询所有的抽检库数据*/
    @Select("select type_name from sys_sampling_food_type where id =#{id}")
    String selecttypename( @Param("id") int  id);

    /**根据管理员账号名查询此账号下所有的抽检类型*/
    @Select("select type_name from sys_sampling_food_type where admin_id = #{adminid} or admin_id = 1")
    List<SysSamplingFoodType> findalltypebyadminid2(@Param("adminid")  int  adminid);

    /**根据管理员账号名查询此账号下所有的抽检类型*/
    @Select("select * from sys_sampling_food_type where admin_id = #{adminid} or admin_id = 1")
    List<SysSamplingFoodType> findallbyadminid(@Param("adminid")  int  adminid);


    /**根据管理员账号名查询此账号下所有的抽检类型*/
    @Select("select type_name from sys_sampling_food_type where admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) or admin_id = 1")
    List<SysSamplingFoodType> findalltypebyadminid(@Param("adminaccount")  String adminaccount);

    /**查找十六大类食品id   adminid = 1 id*/
    @Select("select id from sys_sampling_food_type where admin_id = 1 ")
    List<SysSamplingFoodType> findallidbyadminid(@Param("adminid")  int adminid);

    /**根据管理员账号和食品类型查询该类型的id*/
    @Select("select id from sys_sampling_food_type where type_name = #{typename} and (admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) or admin_id = 1)")
    int selectidbytypenameandadminaccount(@Param("typename") String typename,@Param("adminaccount") String adminaccount);

    /**查找十六大类食品类型   adminid = 1*/
    @Select("select * from sys_sampling_food_type where id>=(select id from  sys_sampling_food_type where admin_id = 1  ORDER BY id LIMIT #{pageIndex}, 1) and admin_id = 1 " +
            " ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingFoodType> findsixteencategories(@Param("pagesize")  int pagesize_true,@Param("pageIndex") int pageindex_true);

    /**查找自定义食品类型   adminid = admin_id*/
    @Select("select * from sys_sampling_food_type where id>=(select id from  sys_sampling_food_type where admin_id =( SELECT id from sys_user WHERE account = #{adminaccount})  ORDER BY id LIMIT #{pageIndex}, 1) and  admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) " +
            " ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingFoodType> findcustomizecategories(@Param("pagesize")  int pagesize_true,@Param("pageIndex") int pageindex_true,@Param("adminaccount") String adminaccount);

    /**查找自定义食品类型的数量*/
    @Select("select count(id) from sys_sampling_food_type where admin_id =( SELECT id from sys_user WHERE account = #{adminaccount})")
    int findcountcustomizecategories(@Param("adminaccount") String adminaccount);


    /**返回某个自定义食品类型的数量*/
    @Select("select count(id) from sys_sampling_food_type where (admin_id = #{adminid} or admin_id = 1) and type_name = #{food_type}")
    int findcountcustomizecategorie(@Param("adminid") int adminid,@Param("food_type") String food_type);

    /**插入新的自定义抽检类型*/
    @Insert("insert into sys_sampling_food_type (type_name,value_at_risk,admin_id) values (#{type_name},#{value_at_risk},#{admin_id})")
    boolean insertnewcustomizecategorie(@Param("admin_id") int admin_id,@Param("type_name") String type_name,@Param("value_at_risk") int value_at_risk);


    /**删除新的自定义抽检类型*/
    @Delete("delete from sys_sampling_food_type where id= #{id}")
    boolean deletecustomizecategorie(@Param("id") int id);

    /**根据id找到对应的风险值*/
    @Select("select value_at_risk from sys_sampling_food_type where id =#{id}")
    String findriskvalue (@Param("id") int id);

    /**根据id找到对应的风险值*/
    @Select("select type_name from sys_sampling_food_type where id =#{id}")
    String findtypename (@Param("id") int id);

    /**根据id修改对应的风险值*/
    @Update("update sys_sampling_food_type set value_at_risk = #{value_at_risk} where id =#{id}")
    int updatesixteencategories (@Param("id") int id,@Param("value_at_risk") String value_at_risk);
}
