package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "sys_excel_processing_progress")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysExcelProcessingProgress {

    @Id
    private Integer id;

    private Integer adminid;

    private String excel_name;
    /*状态，0代表未处理完成*/
    private boolean state;
    /*总数目*/
    private Integer total;
    /*处理成功数目*/
    private Integer successtotal;
    /*处理失败数目*/
    private Integer failtotal;
    /*重复数据*/
    private Integer repeattotal;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String sepp_reserved_field1;
    private String sepp_reserved_field2;
    private String sepp_reserved_field3;

}
