package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    /**
     * 插入新数据到用户角色关系表
     *
     * @param
     * @return
     */
    @Insert("INSERT into sys_user_role_relation VALUES(0,#{userid},#{roleid});")
    boolean insertnewroleuserrelation(@Param("userid") Integer userid, @Param("roleid") Integer roleid);
    /**
     * 判断用户中是否已有要注册的用户名
     *
     * @param
     * @return
     */
    @Insert("INSERT into sys_user VALUES(0,#{account},#{user_name},#{password},#{last_login_time},#{enabled},#{not_expired},#{account_not_locked}," +
            "#{credentials_not_expired},#{create_time},#{update_time},#{create_user},#{update_user},#{city});")
    boolean insertnewaccount(SysUser sysUser);
    /**
     * 判断用户中是否已有要注册的用户名
     *
     * @param
     * @return
     */
    @Select("SELECT COUNT(id) from sys_user WHERE account=#{account};")
    int selectacountnum(String account);
    /**
     * 查询用户表中所有数据，排除superadmin
     *
     * @param
     * @return
     */
    @Select("select * from sys_user where id!=1")
    List<SysUser> selectAllAccount();
    /**
    * 根据超级管理员用户名，查出所有的管理员员账号信息
    * */
    @Select("select * from sys_user where create_user = #{id}")
    List<SysUser> selectAllAccountByAdminAccount(Integer id);
    /**
     * 根据账号名查询用户信息
     *
     * @param
     * @return
     */
    @Select("select * from sys_user where account = #{account} ")
    SysUser selectByAccount(String account);
    /**
     * 根据id查询用户信息
     *
     * @param
     * @return
     */
    @Select("select id, account, user_name, password, last_login_time, enabled, not_expired, account_not_locked, credentials_not_expired, create_time, update_time, create_user, update_user" +
            "  from sys_user where id = #{id};")
    SysUser queryById(Integer id);
    /**
     * 更新已有的用户信息
     *
     * @param
     * @return
     */
    @Update("update sys_user SET account = #{account},user_name=#{user_name}, password=#{password}, last_login_time=#{last_login_time}, enabled=#{enabled}, not_expired=#{not_expired}, account_not_locked=#{account_not_locked}," +
            " credentials_not_expired=#{credentials_not_expired}, create_time=#{create_time}, update_time=#{update_time}, " +
            "create_user=#{create_user}, update_user=#{update_user},city=#{city}" +
            " where id= #{id}")
    int update(SysUser sysUser);

    @Select("select id from sys_user where account =#{adminaccount}")
    int selectbyaccount(@Param("adminaccount") String adminaccount);

    @Select("select city from sys_user where account =#{adminaccount}")
    String selectcity(@Param("adminaccount") String adminaccount);

    @Select("select create_user from sys_user where account=#{adminaccount}")
    int selectsuperadminidbyaccount(@Param("adminaccount") String adminaccount);
}
