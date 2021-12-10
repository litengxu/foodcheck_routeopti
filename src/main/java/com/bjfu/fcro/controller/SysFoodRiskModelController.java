package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.service.FoodSamplingInspectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sfrmc")
public class SysFoodRiskModelController {

    @Autowired
    private FoodSamplingInspectionDataService foodSamplingInspectionDataService;

    @PostMapping("/getimgs")
    @ResponseBody
    public  Object getall(
          ){

        return ResultTool.success(foodSamplingInspectionDataService.getImgs());

    }
}
