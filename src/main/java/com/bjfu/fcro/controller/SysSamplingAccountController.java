package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysSamplingAccountService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Observer;

@Controller
@RequestMapping("/ssaccount")
public class SysSamplingAccountController {

//    @Autowired
//    private SysCommonService<SysSamplingAccount> sysCommonService;
    @Resource(name = "SysSamplingAccountServiceimpl")
    private SysCommonService  sysCommonService;
    @Autowired
    private SysSamplingAccountService sysSamplingAccountService;


    /**
     * 根据admin账号获取该账号下所有的抽检账号信息*/
    @PostMapping("/getallssaccountbyadminaccount")
    @ResponseBody
    public Object getallssaccountbyadminaccount(@RequestParam String adminaccount){

        List<SysSamplingAccount> list = sysCommonService.selectallbyadminaccount(adminaccount);
        return ResultTool.success(list);
    }
    /**根据id修改抽检账号的某些值*/
    @PostMapping("/updatessaccountbyid")
    @ResponseBody
    public Object updatessaccountbyid(
            @RequestParam Integer id,
            @RequestParam String s_password,
            @RequestParam String s_username,
            @RequestParam String whether_participate
    ){
        int int_whether_participate = 0;
        if(whether_participate.equals("true")){
            int_whether_participate = 1;
        }
        if(sysSamplingAccountService.updatebyid(id,s_password,s_username,int_whether_participate ) > 0){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }

    /**根据id删除某条信息,实际是把某个字段改为0*/
    @PostMapping("deletessaccountbyid")
    @ResponseBody
    public Object deletessaccountbyid(@RequestParam Integer id){
        if(sysSamplingAccountService.update_whether_modify_idsbyid(id) > 0){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }

    @PostMapping("/insertnewssaccount")
    @ResponseBody
    public Object insertnewssaccount(
            @RequestParam String adminaccount,
            @RequestParam String s_password,
            @RequestParam String s_username,
            @RequestParam String s_account,
            @RequestParam String whether_participate
          ) {
        if(sysSamplingAccountService.selectssaccountbyaccount(s_account) >0){
            return ResultTool.fail(ResultCode.USER_ALREADY_EXISTS);
        }
        int int_whether_participate = 0;
        if(whether_participate.equals("true")){
            int_whether_participate = 1;
        }
        //先判断账号是否已存在
        if ( sysSamplingAccountService.insertnewssaccount(adminaccount,s_password, s_username, s_account,  int_whether_participate)) {
            return ResultTool.success();
        }else{
            return  ResultTool.fail();
        }

    }
}
