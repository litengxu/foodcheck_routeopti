package com.bjfu.fcro.dao;


import com.bjfu.fcro.entity.SysExcelProcessingProgress;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ExcelProcessingProgressDao {

    /**插入新数据到生成过程表*/
    @Insert("Insert into sys_excel_processing_progress(admin_id,excel_name,state,total,successtotal,failtotal,repeattotal) VALUES (#{admin_id},#{excel_name},#{state},#{total},#{successtotal},#{failtotal},#{repeattotal})")
    boolean insertnewexceldata(
            @Param("admin_id") Integer admin_id,
            @Param("excel_name") String excel_name,
            @Param("state") Integer state,
            @Param("total") Integer total,
            @Param("successtotal") Integer successtotal,
            @Param("failtotal") Integer failtotal,
            @Param("repeattotal") Integer repeattotal
            );
    /**根据excelname 获得id*/
    @Select("select max(id) from sys_excel_processing_progress where excel_name = #{excel_name}")
    int selectmaxidbyexcelname(@Param("excel_name") String excel_name);

    /**更新excel生成状态*/
    @Update("update sys_excel_processing_progress set state = #{state},total = #{total},successtotal = #{successtotal},repeattotal = #{repeattotal} where id  = #{id}")
    int updatestate(
            @Param("id") int id,
            @Param("state") int state,
            @Param("total") Integer total,
            @Param("successtotal") Integer successtotal,
            @Param("repeattotal") Integer repeattotal
            );
    /**根据adminid查询所有数据*/
    @Select("select * from sys_excel_processing_progress where id >= (SELECT id from sys_excel_processing_progress WHERE  admin_id  = #{admin_id} ORDER BY id LIMIT #{pageIndex}, 1)" +
            " and   admin_id  = #{admin_id} ORDER BY  id LIMIT #{pagesize}")
    List<SysExcelProcessingProgress> selectall(@Param("admin_id") int admin_id,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);

    /**根据管理员账号获取数据总数*/
    @Select("select count(id) from sys_excel_processing_progress where  admin_id  = #{admin_id}")
    int selectcountByAdminid(@Param("admin_id") int  admin_id);

    /**删除数据*/
    @Delete("DELETE FROM sys_excel_processing_progress WHERE id=#{id}")
    boolean deleteexcelprocessbyid(@Param("id") int id);
}
