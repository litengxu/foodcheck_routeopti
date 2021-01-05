package com.bjfu.fcro.service;

import com.bjfu.fcro.entity.SysExcelProcessingProgress;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysExcelProcessingProgressService {

    List<SysExcelProcessingProgress> selectall(int admin_id,int pagesize, int pageIndex);

    /**根据管理员账号获取数据总数*/
    int selectcountByAdminid(int  admin_id);

    /**删除数据*/
    boolean deleteexcelprocessbyid( int id);
}
