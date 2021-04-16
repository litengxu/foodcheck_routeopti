package com.bjfu.fcro.service;

import com.bjfu.fcro.entity.SysSamplingAccount;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingAccountHistoryService {

    /**查询账号的所有历史数据*/
    Object getallsamplingaccounthistory(int pagesize_true, int pageindex_true);

}
