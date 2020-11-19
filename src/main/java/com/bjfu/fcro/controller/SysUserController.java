package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysUserService;
import com.bjfu.fcro.service.serviceimpl.SysPermissionServiceimpl;
import com.bjfu.fcro.common.utils.ResultTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class SysUserController {
    private final Logger log= LoggerFactory.getLogger(SysUserController.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysPermissionServiceimpl sysPermissionService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    //根据单个账号名称，获取该账号的信息
    @GetMapping("/getaccountmessage")
    @ResponseBody
    public Object getcountmessage(@RequestParam  String accountname){
        // 检查是否是自己对账号的操作
        if(sysUserService.checkacountorid(accountname)){
            SysUser sysUser = sysUserService.selectByAccount(accountname);
            return ResultTool.success(sysUser);
        }else{
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
    }

    // 编辑账户信息 whether_change_password代表前端是否修改了密码
    @PostMapping("/editaccountmessage")
    @ResponseBody
    public Object editcountmessage(
            @RequestParam  String username,
            @RequestParam Integer id,
            @RequestParam String password,
            @RequestParam Integer whether_change_password
                                   ){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        SysUser sysUser = sysUserService.queryById(id);
        //要查询是否是自己修改
        if(sysUser == null || !sysUserService.checkacountorid(sysUser.getAccount())){
            return ResultTool.fail(ResultCode.NO_PERMISSION);
        }
        if(whether_change_password == 1){
            sysUser.setPassword(password);
        }
        sysUser.setUser_name(username);
        sysUser.setUpdate_time(new Date());
        sysUserService.update(sysUser);
        return ResultTool.success();
    }


    @GetMapping("getUser")
    @ResponseBody
    public Object findAll(){

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
