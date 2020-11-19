package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.entity.SysPermission;
import com.bjfu.fcro.dao.PermissionDao;
import com.bjfu.fcro.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Program: ltx_web
 * @ClassName: SysPermissionServiceimpl
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:38
 * @Description: ${todo}
 * @Version: V1.0
 */
@Repository
public class SysPermissionServiceimpl implements SysPermissionService{

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 根据id查出用户权限
     * @param userId
     * @return
     */
    @Override
    public List<SysPermission> selectListByUser(Integer userId) {
        return permissionDao.selectListByUserId(userId);
    }

    @Override
    public List<SysPermission> selectListByPath(String path) {
        return permissionDao.selectListByPath(path);
    }
}
