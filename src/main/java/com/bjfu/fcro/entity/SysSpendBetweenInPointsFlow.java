package com.bjfu.fcro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_spend_between_in_points_flow")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSpendBetweenInPointsFlow {

    @Id
    private int id;

    private int admin_id;

    private int flow_id;

    private String start_point;

    private int start_point_id;

    private String end_point;

    private int end_point_id;

    private String start_longitude;

    private String start_latitude;

    private String end_longitude;

    private String end_latitude;

    private String euclidean_distance;

    private String start_time;

    private String end_time;

    private String driving_time;

    private String driving_path;

    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String ssbipf_reserved_field1;
    private String ssbipf_reserved_field2;
    private String ssbipf_reserved_field3;
}
