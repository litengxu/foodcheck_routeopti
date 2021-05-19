package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingFoodList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SamplingFoodListDao {

    /**超级管理员，按分页查询所有的抽检库数据*/
    @Select("select * from sys_sampling_food_list where id >= (select id from sys_sampling_food_list where ssp_id=#{ssp_id}  ORDER BY id LIMIT #{pageIndex}, 1) and  ssp_id =#{ssp_id} ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingFoodList> selectAllBySspid(@Param("pagesize")  int pagesize_true,@Param("pageIndex") int pageindex_true,@Param("ssp_id") int  ssp_id);

    @Select("select count(id) from sys_sampling_food_list where ssp_id=#{ssp_id} ")
    Integer selectCountId(@Param("ssp_id") int ssp_id);

    @Insert("Insert into sys_sampling_food_list(sampling_food_type,food_specific_name,food_counts,sampler,spot_check_location,sampling_time,ssp_id,ssl_id,sampling_status,image_link,remarks) values(#{sampling_food_type}," +
            "#{food_specific_name},#{food_counts},#{sampler},#{spot_check_location},#{sampling_time},#{ssp_id},#{ssl_id},#{sampling_status},#{image_link},#{remarks})")
    boolean insertNewSamplingListDao(SysSamplingFoodList sysSamplingFoodList);

    @Select("select * from sys_sampling_food_list where id = #{id}")
    SysSamplingFoodList selectById(@Param("id") int id);
}
