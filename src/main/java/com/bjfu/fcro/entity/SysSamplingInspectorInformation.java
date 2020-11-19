package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "sys_sampling_inspector_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingInspectorInformation {
    //id
    @Id
    private Integer id;
    //姓名
    private String sii_name;
    //性别
    private String sii_sex;
    //手机号
    private String sii_phone;
    //所属抽检机构
    private String sampling_agency;
    //创建时间
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String sii_reserved_field1;
    private String sii_reserved_field2;
    private String sii_reserved_field3;
}
