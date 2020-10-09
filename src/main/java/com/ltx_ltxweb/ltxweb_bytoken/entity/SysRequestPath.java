package com.ltx_ltxweb.ltxweb_bytoken.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Program: ltx_web
 * @ClassName: SysRequestPath
 * @Author: 李腾旭
 * @Date: 2020-06-12 15:32
 * @Description: ${todo}
 * @Version: V1.0
 */
@Entity(name = "sys_request_path")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRequestPath {
    //主键id
    @Id
    private Integer id;
    //请求路径
    private String url;
    //路径描述
    private String description;
}
