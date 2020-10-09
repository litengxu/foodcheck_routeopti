package com.ltx_ltxweb.ltxweb_bytoken.dao;

import com.ltx_ltxweb.ltxweb_bytoken.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Program: ltx_web
 * @ClassName: PermissionMapper
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:22
 * @Description: ${todo}
 * @Version: V1.0
 */

@Component
@Repository
public interface PermissionDao {

    @Select("SELECT p.* FROM sys_user AS u " +
        "LEFT JOIN sys_user_role_relation AS ur ON u.id = ur.user_id " +
        "LEFT JOIN sys_role AS r ON r.id = ur.role_id " +
        "LEFT JOIN sys_role_permission_relation AS rp ON r.id = rp.role_id" +
        " LEFT JOIN sys_permission AS p ON p.id = rp.permission_id" +
        " WHERE u.id = #{userid}"
    )
    List<SysPermission> selectListByUserId(Integer userid);

    @Select("SELECT pe.* FROM sys_permission pe " +
            " LEFT JOIN sys_request_path_permission_relation re ON re.permission_id = pe.id " +
            " LEFT JOIN sys_request_path pa ON pa.id = re.url_id WHERE pa.url = #{path}")
    List<SysPermission> selectListByPath(String path);
}
