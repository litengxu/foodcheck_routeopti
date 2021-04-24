package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysSamplingAccountService;
import com.bjfu.fcro.service.SysSamplingInspectorInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/siinformation")
public class SysSamplingInspectorInformationcontroller {

    @Autowired
    private SysSamplingInspectorInformationService samplingInspectorInformationService;
    @Autowired
    private SysSamplingAccountService sysSamplingAccountService;

    //
    @GetMapping("/getallsiinformation")
    @ResponseBody
    public Object getallsiinformation() throws InterruptedException {

        List<SysSamplingInspectorInformation> list =  new ArrayList<>();
        list = samplingInspectorInformationService.selectall();
        return ResultTool.success(list);
    }

    @PostMapping("/getallsiinformationbyadminaccount")
    @ResponseBody
    public Object getallsiinformationbyadminaccount(
            @RequestParam String adminaccount,
             @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){

//        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectAllByAdminAccount(adminaccount);
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectpageByAdminAccount(adminaccount,pagesize_true,pageindex_true);
        int total = samplingInspectorInformationService.selectcountAllByAdminAccount(adminaccount);
        Map<String,Object> map = new HashMap<>();
        map.put("tableData",list);
        map.put("pageTotal",total);
        return ResultTool.success(map);
    }
    @PostMapping("/enditsiinformationbyid")
    @ResponseBody
    public Object getallsiinformationbyadminaccount(
            @RequestParam Integer id,
            @RequestParam String sii_name,
            @RequestParam String sii_sex,
            @RequestParam String sii_phone,
            @RequestParam String sampling_agency,
            @RequestParam String leave_status){
        int int_leave_status= 2;
        if(leave_status.equals("0")){
            int_leave_status = 0;
        }
        if(leave_status.equals("1")){
            int_leave_status = 1;
        }
        if(samplingInspectorInformationService.updatebyid(id,sii_name,sii_sex,sii_phone,sampling_agency,int_leave_status) >0){
            return  ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }

    @PostMapping("/deletesiinformationbyid")
    @ResponseBody
    public Object deletesiinformationbyid(
            @RequestParam Integer id){
        int ws = samplingInspectorInformationService.selectwhetherassignedbyid(id);
        if(ws == 0){//要删除的抽检员信息已被分配，不允许删除
            return ResultTool.fail(ResultCode.Spot_Inspectors_Have_Been_Assigned);
        }
//        int res = samplingInspectorInformationService.deletebyid(id);
        int res = samplingInspectorInformationService.update_whether_deleted_byid(id);
        if(res!=0){
            return ResultTool.success();
        }
       return ResultTool.fail();
    }
    @PostMapping("/addnewsiinformation")
    @ResponseBody
    public Object addnewsiinformation(
            @RequestParam String adminaccount,
            @RequestParam String sii_name,
            @RequestParam String sii_account,
            @RequestParam String sii_password,
            @RequestParam String sii_sex,
            @RequestParam String sii_phone,
            @RequestParam String sampling_agency) {

        if(sii_account.equals("") || sii_account.contains(" ")|| sii_password.equals("") || sii_password.contains(" ") || sii_name.equals("") || sii_name.contains(" ") || sii_sex .equals("") || sii_sex.contains(" ") || sii_name.equals("") || sii_name.contains(" ") || sii_phone.equals("") || sii_phone.contains(" ")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        if(sii_name.contains("-")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        int res = samplingInspectorInformationService.insertbyaccount(adminaccount, sii_name, sii_sex, sii_phone, sampling_agency,sii_account,sii_password);
        if (res == 1) {
            return ResultTool.success();
        }else if (res == 0){
            return  ResultTool.fail();
        }else{
            return ResultTool.fail(ResultCode.NAM_EALREADY_EXISTS);
        }
    }


    /**根据管理员账号查询未删除的且未被分配的且未请假的抽检员信息*/
    @PostMapping("/selectunassignedByAdminAccount")
    @ResponseBody
    public  Object  selectunassignedByAdminAccount( @RequestParam String accountname,@RequestParam Integer siiaccountid){

        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectunassignedByAdminAccount(accountname);
        String res = sysSamplingAccountService.selectnamesbyid(siiaccountid);
        Map<String,Object> map= new HashMap<>();
        map.put("undes",list);
        map.put("des",res);
        return ResultTool.success(map);
    }

    /**查询所有正在请假的抽检员*/

    @PostMapping("/getaskforleave")
    @ResponseBody
    public Object getaskforleave(
            @RequestParam String adminaccount,
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){

//        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectAllByAdminAccount(adminaccount);
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectAskForLeavePageByAdminAccount(adminaccount,pagesize_true,pageindex_true);
        int total = samplingInspectorInformationService.selectAskForLeaveCountAllByAdminAccount(adminaccount);

        Map<String,Object> map = new HashMap<>();
        map.put("tableData",list);
        map.put("pageTotal",total);
        return ResultTool.success(map);
    }

    @PostMapping("/handlerefuseleave")
    @ResponseBody
    public Object handlerefuseleave(
            @RequestParam Integer id){
        return  samplingInspectorInformationService.handleleave(id,0);

    }

    @PostMapping("/handleagreeleave")
    @ResponseBody
    public Object handleagreeleave(
            @RequestParam Integer id){

        return  samplingInspectorInformationService.handleleave(id,2);

    }

}
