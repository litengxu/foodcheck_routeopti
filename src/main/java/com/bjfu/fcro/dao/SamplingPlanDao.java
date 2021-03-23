package com.bjfu.fcro.dao;


import com.bjfu.fcro.entity.SysSamplingPlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

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
    /**查找已生成的抽检计划   adminid = admin_id*/
    @Select("select * from sys_sampling_plan where id>=(select id from  sys_sampling_plan where admin_id = #{adminid}  ORDER BY id LIMIT #{pageIndex}, 1) and  admin_id = #{adminid} ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingPlan> findplan(@Param("pagesize")  int pagesize_true, @Param("pageIndex") int pageindex_true, @Param("adminid") int  adminid);

    /**查找计划的数量*/
    @Select("select count(id) from sys_sampling_plan where admin_id = #{adminid}")
    int findcount(@Param("adminid") int adminid);
}
