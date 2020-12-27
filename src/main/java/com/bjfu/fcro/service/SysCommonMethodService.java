package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

@Service
public interface SysCommonMethodService {

    /**获取登录的账号用户名*/
    String findadminaccount();

}
