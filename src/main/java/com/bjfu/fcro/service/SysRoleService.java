package com.bjfu.fcro.service;

import org.springframework.stereotype.Service;

@Service
public interface SysRoleService {

    /**
     * 根据账户名称查询出账户所具有的角色
     *
     * @param
     * @return 实例对象
     */
    String selectroleByAccount(String account );
}
