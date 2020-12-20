package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.SysUser;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysSamplingAccountService;
import com.bjfu.fcro.service.SysSamplingInspectorInformationService;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private SysSamplingInspectorInformationService sysSamplingInspectorInformationService;


    /**
     * 根据admin账号获取该账号下所有的抽检账号信息*/
    @PostMapping("/getallssaccountbyadminaccount")
    @ResponseBody
    public Object getallssaccountbyadminaccount(
            @RequestParam String adminaccount,
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
//        List<SysSamplingAccount> list = sysCommonService.selectallbyadminaccount(adminaccount);
        List<SysSamplingAccount> list = sysCommonService.selectpagebyadminaccount(adminaccount,pagesize_true,pageindex_true);
        int total = sysCommonService.selectcountlbyadminaccount(adminaccount);

        Map<String,Object> map = new HashMap<>();
        map.put("tableData",list);
        map.put("pageTotal",total);
        return ResultTool.success(map);
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

    /**插入新的抽检账号*/
    @PostMapping("/insertnewssaccount")
    @ResponseBody
    public Object insertnewssaccount(
            @RequestParam String adminaccount,
            @RequestParam String s_password,
            @RequestParam String s_username,
            @RequestParam String s_account,
            @RequestParam String whether_participate
          ) {
        if(adminaccount.equals("") || adminaccount.contains(" ") || s_password .equals("") || s_password.contains(" ") || s_username.equals("") || s_username.contains(" ") || s_account.equals("") || s_account.contains(" ")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
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

    /**
     *     1. 修改选中抽检员为已分配
     *     2. 把抽检员名字插入到抽检账号中,并把是否修改过改为0（已修改过），该行不会被前台查到
     *     3. 再复制该行数据插入到抽检账号表中，是否修改过字段为1，目的在于保留账号分配的历史
     *
     *     事务控制
     */
    @PostMapping("/distributetossaccount")
    @ResponseBody
    public Object distributetossaccount (
            @RequestParam String adminaccount,
            @RequestParam String distributenames[],
            @RequestParam Integer insaccountid) throws Exception {

        boolean res = sysSamplingAccountService.admininspectortoaccount(adminaccount,distributenames,insaccountid);

        if(res == true){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }

    }

    /**重置抽检员到抽检账号的分配*/
    @PostMapping("/resetsamplingaccount")
    @ResponseBody
    public  Object  resetsamplingaccount(
            @RequestParam String adminaccount
    )throws  Exception{
        if(sysSamplingAccountService.resetsamplingaccount(adminaccount) == 1){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }

    /**随机分配抽检员到抽检账号*/
    @PostMapping("/randomlyassigned")
    @ResponseBody
    public Object randomlyassigned(
            @RequestParam String adminaccount,
            @RequestParam int size
    ) throws  Exception{

        int accountsize = sysCommonService.selectcountlbyadminaccount(adminaccount);
        int personsize = sysSamplingInspectorInformationService.selectcountAllByAdminAccount(adminaccount);
        if(personsize < accountsize*size || personsize==0){
            return ResultTool.fail(ResultCode.TOO_FEW_INSPECTORS);
        }
        if(sysSamplingAccountService.randomlyassigned(adminaccount,size,accountsize,personsize)){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }

    }
}
