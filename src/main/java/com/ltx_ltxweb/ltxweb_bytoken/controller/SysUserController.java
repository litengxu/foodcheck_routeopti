package com.ltx_ltxweb.ltxweb_bytoken.controller;

import com.ltx_ltxweb.ltxweb_bytoken.common.utils.ResultTool;
import com.ltx_ltxweb.ltxweb_bytoken.entity.SysUser;
import com.ltx_ltxweb.ltxweb_bytoken.service.SysUserService;
import com.ltx_ltxweb.ltxweb_bytoken.service.serviceimpl.SysPermissionServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionServiceimpl sysPermissionService;



    @GetMapping("getUser/{account}")
    @ResponseBody
    public Object findAll(@PathVariable String account){

//        System.out.println(account);
//        System.out.println(sysUserService.selectByAccount(account));
//        return sysUserService.selectByAccount(account);

        //可以通过这个接口获取一登陆的用户信息，以sessionid信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object credentials = authentication.getCredentials();

            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails){
                UserDetails userDetails  = (UserDetails)principal;
                System.out.println(userDetails.getUsername());
            }else{
                System.out.println(principal.toString());
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object details = authentication.getDetails();
        System.out.println(credentials);
        System.out.println(authorities);
        System.out.println(details);
        return 1;

    }
    @GetMapping("/index")
//    @ResponseBody
    public Object find(){

        return "test";
    }

    @GetMapping("/logintest")
//    @ResponseBody
    public Object logintest(){

        return "login";
    }
    @GetMapping("/deleteUser")
    @ResponseBody
    public Object find2(){
        SysUser users = sysUserService.selectByAccount("user1");
        System.out.println("88888888888888888888888888888888888888888888888888");
        return ResultTool.success(users);
    }
    @GetMapping("/deleteUser/a")
//    @ResponseBody
    public Object find3(){
        SysUser users = sysUserService.selectByAccount("user1");
        return ResultTool.success(users);
    }
    @GetMapping("a/{id}")
    @ResponseBody
    public Object findlist(@PathVariable int  id){
        SysUser sysUser = sysUserService.queryById(id);
        sysUser.setUser_name("用户3");
        return sysUserService.update(sysUser);
    }
}
