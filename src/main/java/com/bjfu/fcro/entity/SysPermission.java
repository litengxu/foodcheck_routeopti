package com.bjfu.fcro.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Program: ltx_web
 * @ClassName: SysPermission
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:30
 * @Description: ${todo}
 * @Version: V1.0
 */

@Entity(name = "sys_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission {


    //主键id
    @Id

    private Integer id;
    //权限code
    private String permission_Code;
    //权限名
    private String permission_Name;


}
