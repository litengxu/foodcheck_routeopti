package com.bjfu.fcro.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface RoleDao {
    @Select("SELECT role_name from sys_role WHERE id = \n" +
            "\t(SELECT role_id from sys_user_role_relation WHERE user_id =\n" +
            "\t\t(SELECT id from sys_user WHERE account = #{account}));")
    String findroleByAccount(String  account);
}
