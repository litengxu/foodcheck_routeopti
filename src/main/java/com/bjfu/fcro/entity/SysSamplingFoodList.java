package com.bjfu.fcro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_sampling_food_list")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingFoodList {

    @Id
    private int id;

//   委托单登记号
    private  String order_registration_number;
//    抽检时间
    private Date sampling_time;
//    抽检员
    private String sampler;
//    抽检点名称
    private String spot_check_location;
//    抽检点id
    private int ssl_id;
//    抽检食品大类
    private String sampling_food_type;
//    抽检食品具体名称
    private String food_specific_name;
//    数量
    private int food_counts;
//    图片链接地址
    private String image_link;
//    抽检状态
    private int sampling_status;
//    所属抽检计划id
    private int ssp_id;
//    备注信息
    private String remarks;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String ssfl_reserved_field1;
    private String ssfl_reserved_field2;
    private String ssfl_reserved_field3;
//
}
