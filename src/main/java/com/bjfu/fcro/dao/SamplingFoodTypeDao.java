package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingFoodType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SamplingFoodTypeDao {

    /**超级管理员，按分页查询所有的抽检库数据*/
    @Select("select type_name from sys_sampling_food_type where id =#{id}")
    String selecttypename( @Param("id") int  id);

    /**根据管理员账号名查询次账号下所有的抽检类型*/
    @Select("select type_name from sys_sampling_food_type where admin_id =( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingFoodType> findalltypebyadminid(@Param("adminaccount")  String adminaccount);

    /**根据管理员账号和食品类型查询该类型的id*/
    @Select("select id from sys_sampling_food_type where type_name = #{typename} and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectidbytypenameandadminaccount(@Param("typename") String typename,@Param("adminaccount") String adminaccount);
}
