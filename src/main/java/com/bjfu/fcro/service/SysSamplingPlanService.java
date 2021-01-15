package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

@Service
public interface SysSamplingPlanService {

    Object generateplan(  String selectedsamplingaccountid[],
                          String typeoffoodselectedid[],
                           int numbers,
                          String coordinate[]);
}
