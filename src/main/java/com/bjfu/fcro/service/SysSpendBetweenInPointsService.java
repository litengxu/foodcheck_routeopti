package com.bjfu.fcro.service;

import com.bjfu.fcro.entity.SysSamplingLibrary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSpendBetweenInPointsService {

    Object selectAll();

    void InsertNewPoints(List<SysSamplingLibrary> oldList, SysSamplingLibrary newPoint, int adminid);

    public void scheduleTask(int hash,String startTime,String endTime);
}
