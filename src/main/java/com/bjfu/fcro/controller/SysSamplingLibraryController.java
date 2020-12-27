package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import com.bjfu.fcro.service.SysSamplingLibraryService;
import com.bjfu.fcro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sslibrary")
public class SysSamplingLibraryController {

    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSamplingLibraryService sysSamplingLibraryService;
    @Autowired
    private SysSamplingFoodTypeService sysSamplingFoodTypeService;

    /**查询所有的抽检点信息，分页展示*/
    @PostMapping("/getalllibrary")
    @ResponseBody
    public Object getalllibrary(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        String admincount = sysCommonMethodService.findadminaccount();
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        int admin_id = sysUserService.selectbyaccount(admincount);
        List<SysSamplingFoodType> foodTypes = sysSamplingFoodTypeService.findalltypebyadminid(admincount);
        List<SysSamplingLibrary> sysSamplingLibraries=  sysSamplingLibraryService.getalllibrary(admin_id,pagesize_true,pageindex_true);
        Map<String,List> res = new HashMap<>();
        res.put("foodTypes",foodTypes);
        res.put("sysSamplingLibraries",sysSamplingLibraries);
        return ResultTool.success(res);
    }

    /**编辑抽检库的信息*/
    @PostMapping("/updatesamplinglibrarybyid")
    @ResponseBody
    public Object updatesamplinglibrarybyid(
            @RequestParam int id,
            @RequestParam String jurisdiction,
            @RequestParam String category,
            @RequestParam String ssl_name,
            @RequestParam String address
    ) {
            if(sysSamplingLibraryService.updatesamplinglibrarybyid(id, jurisdiction, category, ssl_name, address) > 0){
                return ResultTool.success();
            }else{
                return ResultTool.fail();
            }
    }



    /**删除抽检库的数据*/
    @PostMapping("/deletesamplinglibrarybyid")
    @ResponseBody
    public Object deletesamplinglibrarybyid(
            @RequestParam int id){
        if(sysSamplingLibraryService.deletesamplinglibrarybyid(id)){
            return ResultTool.success();
        }else {
            return ResultTool.fail();
        }
    }

    /**保存抽检类型到抽检库*/
    @PostMapping("/savesamplingtype")
    @ResponseBody
    public Object savesamplingtype(
            @RequestParam String distributenames[],
            @RequestParam String againhavedistributenames[],
            @RequestParam int insaccountid
            ) throws Exception{
        String adminaccount = sysCommonMethodService.findadminaccount();
        if(sysSamplingLibraryService.savesamplingidstosslibrary(adminaccount,distributenames,againhavedistributenames,insaccountid)){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }


    /**添加新的抽检点*/
    @PostMapping("/addnewsamplinglibrary")
    @ResponseBody
    public Object addnewsamplinglibrary(
            @RequestParam String jurisdiction,
            @RequestParam String category,
            @RequestParam String ssl_name,
            @RequestParam String address,
            @RequestParam String selectedfoodtypes[]
    )throws  Exception{
        if(jurisdiction.equals("") || jurisdiction.contains(" ") || category .equals("") || category.contains(" ") || ssl_name.equals("") || ssl_name.contains(" ") || address.equals("") || address.contains(" ")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        if(sysSamplingLibraryService.slelectcountbysslname(ssl_name) >0){
            return ResultTool.fail(ResultCode.USER_ALREADY_EXISTS);
        }
        String adminaccount = sysCommonMethodService.findadminaccount();
        int admin_id = sysUserService.selectbyaccount(adminaccount);
        sysSamplingLibraryService.insertallsamplinglibrary(ssl_name,category,address,admin_id,jurisdiction,selectedfoodtypes,adminaccount);
        return ResultTool.success();
    }

}
