package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/superuser")
public class SysSuperUserController {

    @Autowired
    private SysUserService sysUserService;


    //（超级管理员）获取所有管理员账号的信息
    @GetMapping("/getallaccountmessage")
    @ResponseBody
    public Object getallcountmessage(){
        List<SysUser> list =  new ArrayList<>();
        list = sysUserService.selectAllAccount();
        return ResultTool.success(list);
    }


    // （超级管理员）修改账户权限
    @PostMapping("/editaccountpower")
    @ResponseBody
    public Object editaccountpower(

            @RequestParam Integer id,
            @RequestParam Boolean ifenabled,
            @RequestParam Boolean iflocked
    ){

        SysUser sysUser = sysUserService.queryById(id);
        sysUser.setEnabled(ifenabled);
        sysUser.setAccount_not_locked(iflocked);
        sysUser.setUpdate_time(new Date());
        sysUserService.update(sysUser);
        return ResultTool.success();
    }

    // (超级管理员)新增管理员信息
    @PostMapping("/addnewaccountmessage")
    @ResponseBody
    public Object addnewaccountmessage(
            @RequestParam  String name,
            @RequestParam  String username,
            @RequestParam String password,
            @RequestParam Boolean ifenabled,
            @RequestParam Boolean iflocked
    ){
        if(name.equals("") || name.contains(" ") || username .equals("") || username.contains(" ") || password.equals("") || password.contains(" ")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        int count = sysUserService.selectacountnum(name);
        if(count >=1){
            return ResultTool.fail(ResultCode.USER_ALREADY_EXISTS);
        }else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);
            SysUser sysUser = new SysUser();
            sysUser.setAccount(name);
            sysUser.setUser_name(username);
            sysUser.setPassword(password);
            sysUser.setLast_login_time(new Date());
            sysUser.setNot_expired(true);
            sysUser.setCredentials_not_expired(true);
            sysUser.setEnabled(ifenabled);
            sysUser.setAccount_not_locked(iflocked);
            sysUser.setUpdate_time(new Date());
            sysUser.setCreate_time(new Date());
            sysUser.setCreate_user(1);
            sysUser.setUpdate_user(1);
            sysUserService.insertnewaccount(sysUser);
            return ResultTool.success();
        }

    }
}
