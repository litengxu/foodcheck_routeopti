package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_sampling_food_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingFoodType {

    @Id
    private  int id;
    /*食品类型名称*/
    private  String type_name;
    /*风险值*/
    private String value_at_risk;
    /*是否被删除*/
    private boolean whether_deleted;
    /*管理员id*/
    private int admin_id;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String ssl_reserved_field1;
    private String ssl_reserved_field2;
    private String ssl_reserved_field3;
}
