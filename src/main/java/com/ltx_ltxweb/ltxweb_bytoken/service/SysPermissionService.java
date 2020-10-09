package com.ltx_ltxweb.ltxweb_bytoken.service;

import com.ltx_ltxweb.ltxweb_bytoken.entity.SysPermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Program: ltx_web
 * @ClassName: SysPermissionService
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:37
 * @Description: ${todo}
 * @Version: V1.0
 */
@Service
public interface SysPermissionService {

    /**
     * 查询用户的权限列表
     *
     * @param userId
     * @return
     */
    List<SysPermission> selectListByUser(Integer userId);
    /**
     * 查询路径需要具有的权限列表
     *
     * @param path
     * @return
     */
    List<SysPermission> selectListByPath(String path);
}
