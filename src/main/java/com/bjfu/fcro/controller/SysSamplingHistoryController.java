package com.bjfu.fcro.controller;

import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.service.SysSamplingAccountHistoryService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/samplingAccountHistory")
public class SysSamplingHistoryController {

    @Autowired
    private SysSamplingAccountHistoryService sysSamplingAccountHistoryService;

    @PostMapping("/getallsamplingaccounthistory")
    @ResponseBody
    public Object getallsamplingaccounthistory(
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        return sysSamplingAccountHistoryService.getallsamplingaccounthistory(pagesize_true,pageindex_true);


    }
}
