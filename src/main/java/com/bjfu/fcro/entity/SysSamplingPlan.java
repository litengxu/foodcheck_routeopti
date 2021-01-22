package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_sampling_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingPlan {

    @Id
    private  int id;

    /*外键 管理员id*/
    private int admin_id;
  /*外籍 抽检账号id*/
    private int ssa_id;
    /*抽检员姓名*/
    private String inspector_names;
    /*抽检计划 json串
    *{id,state,timetamp,current_path_sort,
    *       planinfolist:[{
    *              planid,state,address,lng,lat,samplefoodlist:[{foodtype,count}]
    *        }]
    * }
    *
    * */
    private String task_json;
    /*已被调查的点*/
    private  String point_has_been_investigated;
    /*目前抽检点的排序*/
    private  String current_path_sort;
    /*抽检状态 true代表以抽检完成*/
    private boolean sampling_state;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String ssp_reserved_field1;
    private String ssp_reserved_field2;
    private String ssp_reserved_field3;
}
