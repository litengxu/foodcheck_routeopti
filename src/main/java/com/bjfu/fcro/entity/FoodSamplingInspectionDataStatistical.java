package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "food_sampling_inspection_data_statistical")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSamplingInspectionDataStatistical {

    @Id
    private int id;

    private  String food_categories;
    private  String food;

    private  String sampling_month;

    private  String province;

    private  String city;
    private  String county;

    private  String qualified_number;

    private  String total_sum;

    private  String pass_rate;
    private  String sql_content;

    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String sql_content_qualified;
    private String sepp_reserved_field2;
    private String sepp_reserved_field3;






}
