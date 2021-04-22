package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import com.bjfu.fcro.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sstype")
public class SysSamplingTypeController {

    @Autowired
    private SysSamplingFoodTypeService sysSamplingFoodTypeService;
    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SysUserService sysUserService;

    /**抽检类型管理，查找不属于当前抽检点的抽检类型
     *
     * （查询出所有的抽抽检类型，传回前端排除掉已有的）
     *
     * （adminid =1）  + （adminid = admin_id）的抽检类型
     * */
    @PostMapping("/findalltypebyadminid")
    @ResponseBody
    public  Object findalltypebyadminid(){

        String  adminaccount = null;
        adminaccount = sysCommonMethodService.findadminaccount();
        List<SysSamplingFoodType> list = sysSamplingFoodTypeService.findalltypebyadminid(adminaccount);
        return ResultTool.success(list);
    }
    /**查看十六大类食品类型*/
    @PostMapping("/findsixteencategories")
    @ResponseBody
    public  Object findsixteencategories(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        List<SysSamplingFoodType> list = sysSamplingFoodTypeService.findsixteencategories(pagesize_true,pageindex_true);
        return ResultTool.success(list);
    }
    /**修改食品类型风险值*/
    @PostMapping("/updatesixteencategories")
    @ResponseBody
    public  Object updatesixteencategories(
            @RequestParam Integer id,
            @RequestParam String value_at_risk
    ){

        sysSamplingFoodTypeService.updatesixteencategories(id,value_at_risk);
        return ResultTool.success();
    }
    /**查看自定义食品类型*/
    @PostMapping("/findcustomizecategories")
    @ResponseBody
    public  Object findcustomizecategories(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        String  adminaccount = null;
        adminaccount = sysCommonMethodService.findadminaccount();
        List<SysSamplingFoodType> list = sysSamplingFoodTypeService.findcustomizecategories(pagesize_true,pageindex_true,adminaccount);
        int total = sysSamplingFoodTypeService.findcountcustomizecategories(adminaccount);
        Map<String,Object> res = new HashMap<>();
        res.put("list",list);
        res.put("total",total);
        return ResultTool.success(res);
    }

    /**添加自定义食品类型*/
    @PostMapping("/addcustomizecategories")
    @ResponseBody
    public  Object addcustomizecategories(
            @RequestParam String food_type
    ){
        String  adminaccount = null;
        adminaccount = sysCommonMethodService.findadminaccount();
        int adminid = sysUserService.selectbyaccount(adminaccount);
        /**
         * 1.判断是否有重复的自定义食品类型
         *
         * 2. 插入新的自定义食品类型 风险值为0
         * */
        return sysSamplingFoodTypeService.addcustomizecategories(adminid,food_type,0);
    }
    /**删除自定义食品类型*/
    @PostMapping("/deletecustomizecategories")
    @ResponseBody
    public  Object deletecustomizecategories(
            @RequestParam int id
    ){
        String  adminaccount = null;
        adminaccount = sysCommonMethodService.findadminaccount();
        int adminid = sysUserService.selectbyaccount(adminaccount);
        /**
         * 1.找到抽检库中包含该id的抽检点，删除该食品类型id 例(食品类型id 2   抽检点包含id （1-2-3） 删除后为1-3)（这里不新建数据用于保留抽检库类型历史）
         *
         * 2. 删除自定义食品类型
         *
         * 以上操作执行事务
         * */
        return sysSamplingFoodTypeService.deletecustomizecategories(id,adminid);
    }

}
