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
import com.bjfu.fcro.service.*;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/samplingApp")
public class    SysSamplingAppController {


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


    @Autowired
    private SysSamplingFoodListService sysSamplingFoodListService;
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
            int leave_state = sysSamplingInspectorInformationService.selectLeaveStateByAaccount(username);
            Map<String,String> res  = new HashMap<>();
            res.put("name",name);
            res.put("leave_state",String.valueOf(leave_state));
            return ResultTool.success(res);
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

    /*更新密码*/
    @PostMapping("/updatepassword")
    @ResponseBody
    public Object updatepassword(
            @RequestBody String str
    ){
        String account = JSON.parseObject(str).getString("username");
        String newpassward = JSON.parseObject(str).getString("password");
        return sysSamplingInspectorInformationService.updatepassword(account,newpassward);
    }

    /*请假*/
    @PostMapping("/asktoleave")
    @ResponseBody
    public Object asktoleave(
            @RequestBody String str
    ){
        String account = JSON.parseObject(str).getString("username");

        return sysSamplingInspectorInformationService.asktoleave(account);
    }

    /*上传商品食物数据*/

//
    /**
     * 接口：http://47.94.240.186:7070/samplingApp/addSamplingFood
     *
     * 提交方式说明：某个抽检点的每一大类食品都需要提交count次
     *
     * 例如：
     *          某个抽检点的数据为："sampleofoodlist":[{"count":2,"foodtype":"调味品"},{"count":3,"foodtype":"乳制品"}],
     *          则需要提交2次调味品 + 3次乳制品的表单，共提交5次数据到接口
     *
     * 参数说明
     * String sampling_food_type  食品大类（例如：乳制品）
       String food_specific_name   具体抽检食品（例如：蒙牛纯牛奶）
     Integer food_counts,具体抽检食品的数量 （例如：要抽检几瓶蒙牛纯牛奶）
      String sampler, 抽检员（当前抽检员的名字）
      String spot_check_location,（抽检商家名字，例如：嘉兴市兴业精细化工厂）
      String sampling_time,（抽检时间，例如：2021-05-14 16:03:30）
      Integer ssp_id, 抽检计划的id（返回值中有）
      Integer ssl_id, 抽检商家的id（例如：嘉兴市兴业精细化工厂对应的id  为 :"samplingpointid":4188,）
     File file  (提交的图片，后台使用@RequestParam(value = "file")  MultipartFile[] uploadfiles，故可以一次传多张图片)
    *
     * 另外传参的形式为form-data ,且要设置headers信息 Content-Type=  multipart/form-data
     *        之前传参的形式是json串形式
     * 例如
     *      之前的形式：{"username":"4","password":"123456"}
     *      现在的形式：见下张postman图片
    *
    *
    * */
    @RequestMapping("/addSamplingFood")
    @ResponseBody
    public Object addSamplingFood(
            @RequestParam   String sampling_food_type,
            @RequestParam   String food_specific_name,
            @RequestParam   Integer food_counts,
            @RequestParam   String sampler,
            @RequestParam   String spot_check_location,
            @RequestParam   String sampling_time,
            @RequestParam   Integer ssp_id,
            @RequestParam   Integer ssl_id,
            @RequestParam   String remarks,
            @RequestParam(value = "file")  MultipartFile[] uploadfiles
    ) throws ParseException {

        return sysSamplingFoodListService.addSamplingFood(sampling_food_type,food_specific_name,food_counts,sampler,spot_check_location,sampling_time,ssp_id,ssl_id,remarks,uploadfiles);
    }


}
