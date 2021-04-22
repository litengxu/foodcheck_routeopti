package com.bjfu.fcro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.entity.SysSamplingPlan;
import com.bjfu.fcro.entity.temporary.Temp_SamplePlanInfoTable;
import com.bjfu.fcro.entity.temporary.Temp_Task;
import com.bjfu.fcro.service.SysSamplingAccountService;
import com.bjfu.fcro.service.SysSamplingInspectorInformationService;
import com.bjfu.fcro.service.SysSamplingPlanService;
import com.bjfu.fcro.service.SysUserService;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/samplingApp")
public class SysSamplingAppController {


    /**
     * 1.登录接口 /samplingApp/login
     *      请求参数 用户名，密码
     *      返回值     状态码
     * 2. 请求计划 /samplingApp/getplan
     *       请求参数 用户名
     *       返回值 抽检计划库中的一条数据，根据账号定位到一个最新的未完成计划
     *3. 提交计划完成情况 /samplingApp/updateplan
     *       请求参数 提交id和修改后的task_json
     *       返回值 响应码 成功或者失败等
     * */
    @Autowired
    private SysSamplingPlanService sysSamplingPlanService;
    @Autowired
    private SysSamplingAccountService sysSamplingAccountService;
    @Autowired
    private SysSamplingInspectorInformationService sysSamplingInspectorInformationService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //根据单个账号名称，获取该账号的信息
    @PostMapping("/login")
    @ResponseBody
    public Object login(
            @RequestBody JSONObject jsonParam
    ){
        String  username = jsonParam.get("username").toString();
        String passward = jsonParam.get("password").toString();

//        if(sysSamplingAccountService.selectaccountandpassword(username,passward)>0){
//            return ResultTool.success();
//        }else{
//            return ResultTool.fail();
//        }
        if(sysSamplingInspectorInformationService.selectaccountandpassword(username,passward)>0){
            String name = sysSamplingInspectorInformationService.selectnamebyaccount(username);
            return ResultTool.success(name);
        }else{
            return ResultTool.fail();
        }

    }

    //根据单个账号名称，获取该账号的抽检计划
    @PostMapping("/getplan")
    @ResponseBody
    public Object getplan(
            @RequestBody String str
    ){
/*改*/
        String account = JSON.parseObject(str).getString("username");
        return sysSamplingPlanService.getplan(account);

    }

    //根据id 更新抽检计划
    @PostMapping("/updateplan")
    @ResponseBody
    public Object updateplan (
            @RequestBody String str
    ) throws Exception{
        SysSamplingPlan sysSamplingPlan =  JSONObject.parseObject(str,SysSamplingPlan.class);
        String task_json = sysSamplingPlan.getTask_json();
        Temp_Task temp_task = JSONObject.parseObject(task_json,Temp_Task.class);
        /*要考虑到A组接收了抽检计划，B组接收了抽检计划，A组提交修改，B提交修改，这时要避免B组提交的数组覆盖A，
            如果先拿到A修改后的数据和B修改后的数据合并，在提交，要避免此期间C组提交了新的数据会丢失，
             解决思路： 1 锁住某行，让C阻塞，修改完成后再放行，这里只加需要加读写锁，故查询时会阻塞，不合适
                               2  由于是单机应用，故这里直接加JVM锁即可，使用synchronized

        以及重复判断导致的抽检完成时间的重复赋值
        *
        *
        * */

        int id = sysSamplingPlan.getId();
        if(id==0){
            return ResultTool.fail();
        }

        return sysSamplingPlanService.updateplan(temp_task,sysSamplingPlan);
    }
}
