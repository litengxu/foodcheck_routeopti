package com.ltx_ltxweb.ltxweb_bytoken.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.ltx_ltxweb.ltxweb_bytoken.common.entity.JsonResult;
import com.ltx_ltxweb.ltxweb_bytoken.common.utils.ResultTool;
import com.ltx_ltxweb.ltxweb_bytoken.entity.SysUser;
import com.ltx_ltxweb.ltxweb_bytoken.dao.UserDao;
import com.ltx_ltxweb.ltxweb_bytoken.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysUser queryById(Integer id) {
        return this.userDao.queryById(id);
//        return null;
    }

    @Override
    public SysUser selectByAccount(String account) {
        return this.userDao.selectByAccount(account);
    }

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 实例对象
     */
    @Override
    public SysUser update(SysUser sysUser) {
        this.userDao.update(sysUser);
        return this.queryById(sysUser.getId());
    }

    /**
     * 个性化验证登录
     * @param username 账号
     * @param rawPassword 原始密码
     * @return
     */
    @Override
    public boolean checkLogin(String username,String rawPassword) throws Exception {
        SysUser sysuser = this.selectByAccount(username);
        System.out.println("userEntity = " + sysuser);
        if (sysuser == null) {
            //System.out.println("checkLogin--------->账号不存在，请重新尝试！");
            //设置友好提示

            throw  new Exception("账号不存在，请重新尝试！");

        }else {
            //加密的密码
            String encodedPassword = sysuser.getPassword();
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            //和加密后的密码进行比配
            if(!bcryptPasswordEncoder.matches(rawPassword,encodedPassword)) {

                //System.out.println("checkLogin--------->密码不正确！");
                //设置友好提示
                throw new Exception("密码不正确！");
            }else{
                return  true;
            }
        }

    }
}
