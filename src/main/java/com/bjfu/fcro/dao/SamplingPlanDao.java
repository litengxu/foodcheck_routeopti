package com.bjfu.fcro.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface SamplingPlanDao {
    /**
     * 插入新数据到抽检计划表
     *
     * @param
     * @return
     */
    @Insert("INSERT into sys_sampling_plan (admin_id,task_json,sampling_status) VALUES(#{admin_id},#{task_json},#{sampling_status});")
    boolean insertnewsamplingplan (@Param("admin_id") Integer admin_id,
                                @Param("task_json")  String task_json,
                                @Param("sampling_status")  boolean sampling_status
                                );
}
