package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingAccountDao;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysSamplingAccountHistoryService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysSamplingAccountHistoryServiceimpl implements SysSamplingAccountHistoryService {

    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SamplingAccountDao samplingAccountDao;

    public Object getallsamplingaccounthistory(int pagesize_true, int pageindex_true){
            String account_name = sysCommonMethodService.findadminaccount();
            int total = samplingAccountDao.selecthistorycountAllbyadmincount(account_name);
            List<SysSamplingAccount> list = samplingAccountDao.selecthistorypageByAdminAccount(account_name,pagesize_true,pageindex_true);
            Map<String,Object> map = new HashMap<>();
            map.put("tableData",list);
            map.put("pageTotal",total);
            return ResultTool.success(map);
    }
}
