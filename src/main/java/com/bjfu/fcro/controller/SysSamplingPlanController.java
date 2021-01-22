package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.BaiduApiTool;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingPlanService;
import com.bjfu.fcro.service.SysUserService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("ssplan")
public class SysSamplingPlanController {


    @Autowired
    private SysSamplingPlanService sysSamplingPlanService;
    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/generateplan")
    @ResponseBody
    public  Object generateplan(
            @RequestParam String selectedsamplingaccountid[],
            @RequestParam String typeoffoodselectedid[],
            @RequestParam String quantityvalue[],
            @RequestParam String numbers,
            @RequestParam String starting_point
    ){

        if(selectedsamplingaccountid.length == 0 || typeoffoodselectedid.length == 0 ||  numbers == null || starting_point == null){
            return ResultTool.fail(ResultCode.PlLEASE_FILL_IN_THE_REQUIRED_OPTIONS);
        }

        int number = Integer.parseInt(numbers);
        int basicSum = 0;
        // 从基本数量数组中遍历计算总和
        for (int i = 0; i < quantityvalue.length;i++)
            basicSum += Integer.parseInt(quantityvalue[i]);
        if(basicSum > number){
            return ResultTool.fail(ResultCode.TOTAL_NUMBER_TOO_SMALL);
        }
        String coordinate[] = BaiduApiTool.getCoordinate(starting_point);
        if(coordinate == null){
            return  ResultTool.fail(ResultCode.IRREGULAR_ADDRESS);
        }
        String adminaccount = sysCommonMethodService.findadminaccount();
        int admin_id = sysUserService.selectbyaccount(adminaccount);
        return sysSamplingPlanService.generateplan(selectedsamplingaccountid,typeoffoodselectedid,quantityvalue,number,coordinate,starting_point,admin_id);


    }
}
