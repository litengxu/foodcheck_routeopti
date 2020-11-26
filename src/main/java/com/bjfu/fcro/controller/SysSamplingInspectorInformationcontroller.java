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
        System.out.println(sii_name);
        samplingInspectorInformationService.updatebyid(id,sii_name,sii_sex,sii_phone,sampling_agency);
        return ResultTool.success();
    }

    @PostMapping("/deletesiinformationbyid")
    @ResponseBody
    public Object deletesiinformationbyid(
            @RequestParam Integer id){
        System.out.println(id);
        int ws = samplingInspectorInformationService.selectwhetherassignedbyid(id);
        if(ws == 0){//要删除的抽检员信息已被分配，不允许删除
            return ResultTool.fail(ResultCode.Spot_Inspectors_Have_Been_Assigned);
        }
        int res = samplingInspectorInformationService.deletebyid(id);

        return ResultTool.success();
    }
}
