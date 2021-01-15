package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.BaiduApiTool;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.service.SysSamplingPlanService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("ssplan")
public class SysSamplingPlanController {


    @Autowired
    private SysSamplingPlanService sysSamplingPlanService;


    @PostMapping("/generateplan")
    @ResponseBody
    public  Object generateplan(
            @RequestParam String selectedsamplingaccountid[],
            @RequestParam String typeoffoodselectedid[],
            @RequestParam String numbers,
            @RequestParam String starting_point
    ){
        if(selectedsamplingaccountid.length == 0 || typeoffoodselectedid.length == 0 ||  numbers == null || starting_point == null){
            return ResultTool.fail(ResultCode.PlLEASE_FILL_IN_THE_REQUIRED_OPTIONS);
        }
        int number = Integer.parseInt(numbers);
        if(selectedsamplingaccountid.length > number){
            return ResultTool.fail();
        }
        String coordinate[] = BaiduApiTool.getCoordinate(starting_point);
        if(coordinate == null){
            return  ResultTool.fail(ResultCode.IRREGULAR_ADDRESS);
        }
        return sysSamplingPlanService.generateplan(selectedsamplingaccountid,typeoffoodselectedid,number,coordinate);


    }
}
