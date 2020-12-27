package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SysSamplingFoodTypeServiceimpl  implements SysSamplingFoodTypeService{

    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public String selecttypename(int id) {
        return samplingFoodTypeDao.selecttypename(id);
    }

    @Override
    public List<SysSamplingFoodType> findalltypebyadminid(String adminaccount) {

        List<SysSamplingFoodType> list = samplingFoodTypeDao.findalltypebyadminid(adminaccount);
        return list;
    }
}
