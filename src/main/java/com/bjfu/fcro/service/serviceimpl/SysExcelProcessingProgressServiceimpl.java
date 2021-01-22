package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.dao.ExcelProcessingProgressDao;
import com.bjfu.fcro.entity.SysExcelProcessingProgress;
import com.bjfu.fcro.service.SysExcelProcessingProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysExcelProcessingProgressServiceimpl implements SysExcelProcessingProgressService {

    @Autowired
    private ExcelProcessingProgressDao excelProcessingProgressDao;

    @Override
    public List<SysExcelProcessingProgress> selectall(int admin_id,int pagesize, int pageIndex ) {
        return excelProcessingProgressDao.selectall(admin_id,pagesize,pageIndex);
    }

    @Override
    public int selectcountByAdminid(int admin_id) {
        return excelProcessingProgressDao.selectcountByAdminid(admin_id);
    }

    @Override
    public boolean deleteexcelprocessbyid(int id) {
        return excelProcessingProgressDao.deleteexcelprocessbyid(id);
    }
}
