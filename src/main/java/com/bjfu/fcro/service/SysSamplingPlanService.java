package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingPlan;
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
    List<SysSamplingPlan> findallplan();
    /**根据id更新抽检计划表*/
    int updateplan(
           String taskjson,
            Integer id,
           boolean status
    );
}
