package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingPlan;
import com.bjfu.fcro.entity.temporary.Temp_Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingPlanService {

    Object generateplan(  String selectedsamplingaccountid[],
                          String typeoffoodselectedid[],
                           String quantityvalue[],
                           int numbers,
                          String coordinate[],
                          String starting_point,
                          int admin_id);
    Object findplan(int val,int pagesize_true,int pageindex_true,String adminaccount);
    Object getplan(String account);
    List<SysSamplingPlan> findallplan(int adminid);
    /**根据id更新抽检计划表*/
    Object updateplan(
            Temp_Task temp_task, SysSamplingPlan sysSamplingPlan
    ) throws Exception;

    Object deleteplan(int id);


}
