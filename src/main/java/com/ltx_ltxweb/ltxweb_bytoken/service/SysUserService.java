package com.ltx_ltxweb.ltxweb_bytoken.service;

import com.ltx_ltxweb.ltxweb_bytoken.entity.SysUser;
import org.springframework.stereotype.Service;

@Service
public interface SysUserService {

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
