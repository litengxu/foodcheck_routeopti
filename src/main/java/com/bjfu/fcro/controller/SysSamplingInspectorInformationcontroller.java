package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysSamplingInspectorInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/siinformation")
public class SysSamplingInspectorInformationcontroller {

    @Autowired
    private SysSamplingInspectorInformationService samplingInspectorInformationService;


    //
    @GetMapping("/getallsiinformation")
    @ResponseBody
    public Object getallsiinformation(){

        List<SysSamplingInspectorInformation> list =  new ArrayList<>();
        list = samplingInspectorInformationService.selectall();
        return ResultTool.success(list);
    }

    @PostMapping("/getallsiinformationbyadminaccount")
    @ResponseBody
    public Object getallsiinformationbyadminaccount(@RequestParam String adminaccount){

        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectAllByAdminAccount(adminaccount);
        System.out.println(list);
        return ResultTool.success(list);
    }
    @PostMapping("/enditsiinformationbyid")
    @ResponseBody
    public Object getallsiinformationbyadminaccount(
            @RequestParam Integer id,
            @RequestParam String sii_name,
            @RequestParam String sii_sex,
            @RequestParam String sii_phone,
            @RequestParam String sampling_agency){
        samplingInspectorInformationService.updatebyid(id,sii_name,sii_sex,sii_phone,sampling_agency);
        return ResultTool.success();
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
            @RequestParam String sii_sex,
            @RequestParam String sii_phone,
            @RequestParam String sampling_agency) {
        int res = samplingInspectorInformationService.insertbyaccount(adminaccount, sii_name, sii_sex, sii_phone, sampling_agency);
        if (res == 1) {
            return ResultTool.success();
        }else{
            return  ResultTool.fail();
        }

    }


    /**根据管理员账号查询未删除的且未被分配的抽检员信息*/
    @PostMapping("/selectunassignedByAdminAccount")
    @ResponseBody
    public  Object  selectunassignedByAdminAccount( @RequestParam String accountname){

        List<SysSamplingInspectorInformation> list = samplingInspectorInformationService.selectunassignedByAdminAccount(accountname);
        return ResultTool.success(list);
    }
}
