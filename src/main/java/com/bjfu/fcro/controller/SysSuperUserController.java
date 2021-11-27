package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysCommonMethodService;
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
    @Autowired
    private SysCommonMethodService sysCommonMethodService;

    //（超级管理员）获取所有管理员账号的信息
    @GetMapping("/getallaccountmessage")
    @ResponseBody
    public Object getallcountmessage(){
        List<SysUser> list =  new ArrayList<>();
        String   adminaccount = sysCommonMethodService.findadminaccount();
        list = sysUserService.selectAllAccount(adminaccount);
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
        if(sysUser.getCity() == null || sysUser.getCity().equals("")  ){
            sysUser.setCity("北京市");
        }
        sysUserService.update(sysUser);
        return ResultTool.success();
    }

    // (超级管理员)新增管理员信息
    @PostMapping("/addnewaccountmessage")
    @ResponseBody
    public Object addnewaccountmessage(
            @RequestParam String supername,
            @RequestParam  String name,
            @RequestParam  String username,
            @RequestParam String password,
            @RequestParam String city,
            @RequestParam Boolean ifenabled,
            @RequestParam Boolean iflocked
    ){
        if(name.equals("") || name.contains(" ") || username .equals("") || username.contains(" ") || password.equals("") || password.contains(" ") || city.equals("") || city.contains(" ") ){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        int count = sysUserService.selectacountnum(name);
        if(count >=1){
            return ResultTool.fail(ResultCode.USER_ALREADY_EXISTS);
        }else{
            Integer superid = sysUserService.selectbyaccount(supername);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);
            SysUser sysUser = new SysUser();
            sysUser.setAccount(name);
            sysUser.setUser_name(username);
            sysUser.setPassword(password);
            sysUser.setCity(city);
            sysUser.setLast_login_time(new Date());
            sysUser.setNot_expired(true);
            sysUser.setCredentials_not_expired(true);
            sysUser.setEnabled(ifenabled);
            sysUser.setAccount_not_locked(iflocked);
            sysUser.setUpdate_time(new Date());
            sysUser.setCreate_time(new Date());
            sysUser.setCreate_user(superid);
            sysUser.setUpdate_user(superid);
            sysUserService.insertnewaccount(sysUser);
            return ResultTool.success();
        }

    }
}
