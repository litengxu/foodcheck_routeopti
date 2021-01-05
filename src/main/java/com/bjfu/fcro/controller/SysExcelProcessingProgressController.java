package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysExcelProcessingProgress;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysExcelProcessingProgressService;
import com.bjfu.fcro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sepp")
public class SysExcelProcessingProgressController {
    @Autowired
    private SysExcelProcessingProgressService sysExcelProcessingProgressService;
    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/getall")
    @ResponseBody
    public  Object getall(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        String adminacount = sysCommonMethodService.findadminaccount();
        int admin_id = sysUserService.selectbyaccount(adminacount);
        List<SysExcelProcessingProgress> list = sysExcelProcessingProgressService.selectall(admin_id,pagesize_true,pageindex_true);
        int pagetotal= sysExcelProcessingProgressService.selectcountByAdminid(admin_id);
        Map<String,Object> res = new HashMap<>();
        res.put("pageTotal",pagetotal);
        res.put("ExcelProcessingProgress",list);
        return ResultTool.success(res);
    }

    /**删除某条数据*/
    @PostMapping("/deleteexcel")
    @ResponseBody
    public  Object deleteexcel(
            @RequestParam Integer id
            ){
            if(sysExcelProcessingProgressService.deleteexcelprocessbyid(id)){
                return ResultTool.success();
            }else{
                return ResultTool.fail();
            }
    }
}
