package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingLibrary;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface SamplingLibraryDao {



    /**超级管理员，按分页查询所有的抽检库数据*/
    @Select("select * from sys_sampling_library where id >= (SELECT id from sys_sampling_library  ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and  ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingLibrary> selectAll(@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);

    /**根据管理员账号查询抽检库信息 按分页*/
    @Select("select * from sys_sampling_library where id >= (SELECT id from sys_sampling_library WHERE  admin_id  = #{admin_id} ORDER BY id LIMIT #{pageIndex}, 1)" +
            " and   admin_id  = #{admin_id} ORDER BY  id LIMIT #{pagesize}")
    List<SysSamplingLibrary> selectpageByAdminid(@Param("admin_id") int  admin_id,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);


    /**修改抽检库的数据*/
    @Update("Update sys_sampling_library set jurisdiction = #{jurisdiction},category=#{category},ssl_name=#{ssl_name},address=#{address} where id = #{id}")
    int updatesamplinglibrarybyid(@Param("id") int id,@Param("jurisdiction") String jurisdiction,@Param("category") String category,@Param("ssl_name") String ssl_name,@Param("address") String address);

    /**删除抽检库的数据*/
    @Delete("DELETE FROM sys_sampling_library WHERE id=#{id}")
    boolean deletesamplinglibrarybyid(@Param("id") int id);

    /**根据id插入新的抽检类型的ids*/
    @Update("update sys_sampling_library set foodtype_ids = #{foodtypeids} where id = #{id}")
    int updateidsbyid(@Param("foodtypeids") String foodtypeids,@Param("id") int id);

    /**根据抽检点名称查询共有多少个*/
    @Select("select count(id)  from sys_sampling_library where ssl_name = #{ssl_name}")
    int slelectcountbysslname(@Param("ssl_name") String ssl_name);

    /**插入新的抽检点数据*/
    @Insert("INSERT into sys_sampling_library (ssl_name,category,address,admin_id,jurisdiction) VALUES(#{ssl_name},#{category},#{address},#{admin_id},#{jurisdiction});")
    boolean insertnewsamplinglibrary(
            @Param("ssl_name") String ssl_name,
            @Param("category") String category,
            @Param("address") String address,
            @Param("admin_id") int admin_id,
            @Param("jurisdiction") String jurisdiction
    );

    /**根据抽检点名称查询id*/
    @Select("select id from sys_sampling_library where ssl_name = #{ssl_name}")
    int selectidbysllname(@Param("ssl_name") String ssl_name);
}
