package com.bjfu.fcro.service;

import com.bjfu.fcro.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysUserService {
     /**
      *   与登录的用户进行对比，防止其他用户利用url修改不属于自己账号的信息
      *
      * @param
      * @return 实例对象
      */
     boolean checkacountorid(String account);
     /**
      * 插入新数据到用户角色关系表
      *
      * @param
      * @return 实例对象
      */
     boolean insertnewroleuserrelation(int userid,int roleid);
     /**
      * 插入新账户到用户表中
      *
      * @param
      * @return 实例对象
      */
     boolean insertnewaccount(SysUser sysUser);
     /**
      * 查询某账户在用户表中的数量
      *
      * @param
      * @return 实例对象
      */
     int selectacountnum(String account);

     //获取所有账号的信息
     List<SysUser> selectAllAccount();

     //根据账户获取账号的信息
     SysUser selectByAccount(String account);

     /**
      * 修改数据
      *
      * @param sysUser 实例对象
      * @return 实例对象
      */
     SysUser update(SysUser sysUser);
     /**
      * 通过ID查询单条数据
      *
      * @param id 主键
      * @return 实例对象
      */
     SysUser queryById(Integer id);

     /**
      * 个性化验证登录
      * @param username 账号
      * @param rawPassword 原始密码
      * @return
      */
     boolean checkLogin(String username,String rawPassword) throws Exception;
}
