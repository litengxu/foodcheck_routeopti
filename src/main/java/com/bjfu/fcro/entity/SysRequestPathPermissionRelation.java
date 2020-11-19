package com.bjfu.fcro.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

/**
 * @Program: ltx_web
 * @ClassName: SysRequestPathPermissionRelation
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:34
 * @Description: ${todo}
 * @Version: V1.0
 */
@Entity(name = "sys_request_path_permission_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRequestPathPermissionRelation {
    //主键id
    @Id
    private Integer id;
    //请求路径id
    private Integer url_Id;
    //权限id
    private Integer permission_Id;
}
