package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

@Service
public interface SysSamplingPlanService {

    Object generateplan(  String selectedsamplingaccountid[],
                          String typeoffoodselectedid[],
                           String quantityvalue[],
                           int numbers,
                          String coordinate[],
                          String starting_point,
                          int admin_id);

}
