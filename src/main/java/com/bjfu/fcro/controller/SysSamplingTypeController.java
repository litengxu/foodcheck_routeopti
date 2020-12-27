package com.bjfu.fcro.controller;


import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/sstype")
public class SysSamplingTypeController {

    @Autowired
    private SysSamplingFoodTypeService sysSamplingFoodTypeService;
    @Autowired
    private SysCommonMethodService sysCommonMethodService;


    /**抽检类型管理，查找不属于当前抽检点的抽检类型  （查询出所有的抽抽检类型，传回前端排除掉已有的）*/
    @PostMapping("/findalltypebyadminid")
    @ResponseBody
    public  Object findalltypebyadminid(){

        String  adminaccount = null;
        adminaccount = sysCommonMethodService.findadminaccount();
        List<SysSamplingFoodType> list = sysSamplingFoodTypeService.findalltypebyadminid(adminaccount);
        return ResultTool.success(list);

    }

}
