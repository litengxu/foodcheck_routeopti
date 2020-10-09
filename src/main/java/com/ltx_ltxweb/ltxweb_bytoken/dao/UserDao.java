package com.ltx_ltxweb.ltxweb_bytoken.dao;

import com.ltx_ltxweb.ltxweb_bytoken.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {

    @Select("select * from sys_user where account = #{account} ")
    SysUser selectByAccount(String account);

    @Select("select id, account, user_name, password, last_login_time, enabled, not_expired, account_not_locked, credentials_not_expired, create_time, update_time, create_user, update_user" +
            "  from sys_user where id = #{id};")
    SysUser queryById(Integer id);

    @Update("update sys_user SET account = #{account},user_name=#{user_name}, password=#{password}, last_login_time=#{last_login_time}, enabled=#{enabled}, not_expired=#{not_expired}, account_not_locked=#{account_not_locked}," +
            " credentials_not_expired=#{credentials_not_expired}, create_time=#{create_time}, update_time=#{update_time}, " +
            "create_user=#{create_user}, update_user=#{update_user}" +
            " where id= #{id}")
    int update(SysUser sysUser);



}
