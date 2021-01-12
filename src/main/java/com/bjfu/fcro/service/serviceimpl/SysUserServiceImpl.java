package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public boolean checkacountorid(String account) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails  = (UserDetails)principal;
            return userDetails.getUsername().equals(account);
        }else{
            return false;
        }
    }

    @Override
    public boolean insertnewroleuserrelation(int userid, int roleid) {
        return this.userDao.insertnewroleuserrelation(userid,roleid);
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public boolean insertnewaccount(SysUser sysUser) {
        boolean res = true;
        try {
           this.userDao.insertnewaccount(sysUser);
           int userid = userDao.selectByAccount(sysUser.getAccount()).getId();
           res = this.userDao.insertnewroleuserrelation(userid,2);
       }catch (Exception e){
           e.printStackTrace();
       }
       return res;
    }

    /**
     * 查询某账户在用户表中的数量
     *
     * @param
     * @return 实例对象
     */
    @Override
    public int selectacountnum(String account) {
        return this.userDao.selectacountnum(account);
    }

    @Override
    public List<SysUser> selectAllAccount() {
        return this.userDao.selectAllAccount();
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

    @Override
    public int selectbyaccount(String adminaccount) {
        return userDao.selectbyaccount(adminaccount);
    }

    @Override
    public String seleccity(String adminaccount) {
        return userDao.selectcity(adminaccount);
    }

}
