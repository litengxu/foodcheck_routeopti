package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.dao.RoleDao;
import com.bjfu.fcro.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SysRoleServiceimpl implements SysRoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public String selectroleByAccount(String account) {

        return roleDao.findroleByAccount(account);
    }
}
