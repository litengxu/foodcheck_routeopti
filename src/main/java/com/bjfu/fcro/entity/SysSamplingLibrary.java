package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_sampling_library")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingLibrary {

    @Id
    private  int id;

    /*抽检点名称*/
    private String ssl_name;
    /*类别*/
    private String category;
    /*地址*/
    private String address;
    /*管理员id*/
    private int admin_id;
    /*包含的抽检食品类型id*/
    private  String foodtype_ids;
    /*辖区*/
    private  String jurisdiction;
    /*经度*/
    private String longitude;
    /*纬度*/
    private String latitude;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String ssl_reserved_field1;
    private String ssl_reserved_field2;
    private String ssl_reserved_field3;
}

