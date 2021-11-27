package com.bjfu.fcro.dao;


import com.bjfu.fcro.entity.SysSamplingPlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.relational.core.sql.In;
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

    /**查找已完成的已生成的抽检计划   adminid = admin_id*/
    @Select("select * from sys_sampling_plan where id>=(select id from  sys_sampling_plan where admin_id = #{adminid} and sampling_status = 1 ORDER BY id LIMIT #{pageIndex}, 1) and  admin_id = #{adminid} and sampling_status = 1 ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingPlan> findcompletedplan(@Param("pagesize")  int pagesize_true, @Param("pageIndex") int pageindex_true, @Param("adminid") int  adminid);

    /**查找未完成的生成的抽检计划   adminid = admin_id*/
    @Select("select * from sys_sampling_plan where id>=(select id from  sys_sampling_plan where admin_id = #{adminid} and sampling_status = 0 ORDER BY id LIMIT #{pageIndex}, 1) and  admin_id = #{adminid} and sampling_status = 0 ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingPlan> findundoplan(@Param("pagesize")  int pagesize_true, @Param("pageIndex") int pageindex_true, @Param("adminid") int  adminid);

    /**查找计划的数量*/
    @Select("select count(id) from sys_sampling_plan where admin_id = #{adminid}")
    int findcount(@Param("adminid") int adminid);

    /**查找已完成计划的数量*/
    @Select("select count(id) from sys_sampling_plan where admin_id = #{adminid} and sampling_status = 1")
    int findcompletedcount(@Param("adminid") int adminid);

    /**查找未完成计划的数量*/
    @Select("select count(id) from sys_sampling_plan where admin_id = #{adminid} and sampling_status = 0")
    int findundocount(@Param("adminid") int adminid);


    /**查找管理员下的所有抽检计划*/
    @Select("select * from sys_sampling_plan where admin_id = #{adminid} ORDER BY create_time DESC")
    List<SysSamplingPlan> findallplanbyadminid(@Param("adminid") int adminid);

    /**根据id更新抽检计划表*/
    @Update("UPDATE sys_sampling_plan set task_json = #{taskjson},sampling_status = #{status} where id = #{id}")
    Integer updateplan(
                @Param("taskjson") String taskjson,
                @Param("id") int id,
                @Param("status") boolean status);

    /**根据id查找计划*/
    @Select("select * from sys_sampling_plan where id = #{id}")
    List<SysSamplingPlan> findPlanByid(
            @Param("id") int id);
    /**根据id查找完成计划的数量*/
    @Select("select count(id) from sys_sampling_plan where id = #{id} and sampling_status = 1")
    int  findCompletePlanByid(
            @Param("id") int id);
    /**根据id删除计划*/
    @Select("delete from sys_sampling_food_list where ssp_id = #{id}")
    List<SysSamplingPlan> deleteFoodlistByid(
            @Param("id") int id);
    @Select("delete from sys_sampling_plan where id = #{id}")
    List<SysSamplingPlan> deletePlanByid(
            @Param("id") int id);

}
