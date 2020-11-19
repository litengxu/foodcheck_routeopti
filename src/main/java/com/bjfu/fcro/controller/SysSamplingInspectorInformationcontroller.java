package com.bjfu.fcro.controller;

import com.bjfu.fcro.dao.SamplingInspectorInformationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/siinformation")
public class SysSamplingInspectorInformationcontroller {

    @Autowired
    private SamplingInspectorInformationDao samplingInspectorInformationDao;

    @GetMapping("/test")
    public void index() {
        System.out.println("时间是：==========="+samplingInspectorInformationDao.selectAll().getLast_update_time());
    }
}
