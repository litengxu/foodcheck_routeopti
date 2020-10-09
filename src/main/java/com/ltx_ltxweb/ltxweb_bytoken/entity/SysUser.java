package com.ltx_ltxweb.ltxweb_bytoken.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
//import javax.persistence.Id;
import java.util.Date;

@Entity(name = "sys_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    @Id
    private Integer id;
    //账号
    private String account;
    //用户名
    private String user_name;
    //用户密码
    private String password;
    //上一次登录时间
    private Date last_login_time;
    //账号是否可用。默认为1（可用）
    private Boolean enabled;
    //是否过期。默认为1（没有过期）
    private Boolean not_expired;
    //账号是否锁定。默认为1（没有锁定）
    private Boolean account_not_locked;
    //证书（密码）是否过期。默认为1（没有过期）
    private Boolean credentials_not_expired;
    //创建时间
    private Date create_time;
    //修改时间
    private Date update_time;
    //创建人
    private Integer create_user;
    //修改人
    private Integer update_user;
}
