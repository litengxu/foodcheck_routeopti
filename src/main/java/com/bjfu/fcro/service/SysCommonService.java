package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysCommonService<T> {

    /**
     * 根据抽检员账号查出所有的信息
     * */
    List<T>  selectallbyadminaccount(String adminaccount);

    /**
     *
     *
     * */
}
