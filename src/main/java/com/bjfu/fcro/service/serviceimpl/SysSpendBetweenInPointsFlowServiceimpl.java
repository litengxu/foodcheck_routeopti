package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.dao.SpendBetweenInPointsDao;
import com.bjfu.fcro.dao.SpendBetweenInPointsFlowDao;
import com.bjfu.fcro.service.SysSpendBetweenInPointsFlowService;
import com.bjfu.fcro.service.SysSpendBetweenInPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysSpendBetweenInPointsFlowServiceimpl implements SysSpendBetweenInPointsFlowService {

    @Autowired
    private SpendBetweenInPointsFlowDao spendBetweenInPointsFlowDao;


    @Override
    public Object selectAll() {
        return spendBetweenInPointsFlowDao.selectAll();
    }
}
