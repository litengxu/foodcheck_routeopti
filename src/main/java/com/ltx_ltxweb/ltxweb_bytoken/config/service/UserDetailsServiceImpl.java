package com.ltx_ltxweb.ltxweb_bytoken.config.service;

import com.ltx_ltxweb.ltxweb_bytoken.entity.SysPermission;
import com.ltx_ltxweb.ltxweb_bytoken.entity.SysUser;
import com.ltx_ltxweb.ltxweb_bytoken.service.SysPermissionService;
import com.ltx_ltxweb.ltxweb_bytoken.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * @Program: ltx_web
 * @ClassName: UserDetailsServiceImpl
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:46
 * @Description: ${todo}
 * @Version: V1.0
 *
 * 连接jdbc，率先检查是否符合规定，并按规定封装好User传出去
 */

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        SysUser sysUser = sysUserService.selectByAccount(username);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (sysUser != null) {

            //获取该用户所拥有的权限
            List<SysPermission> sysPermissions = sysPermissionService.selectListByUser(sysUser.getId());
            // 声明用户授权
            sysPermissions.forEach(sysPermission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(sysPermission.getPermission_Code());
                grantedAuthorities.add(grantedAuthority);
            });
        }
//        User user = new User(sysUser.getAccount(), passwordEncoder.encode(sysUser.getPassword()), grantedAuthorities);
        return  new User(sysUser.getAccount(),sysUser.getPassword(), sysUser.getEnabled(), sysUser.getNot_expired(), sysUser.getCredentials_not_expired(), sysUser.getAccount_not_locked(), grantedAuthorities);

    }
}
