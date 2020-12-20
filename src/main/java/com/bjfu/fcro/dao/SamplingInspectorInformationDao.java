package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

//抽检员信息dao层
@Component
public interface SamplingInspectorInformationDao {
    @Select("select * from sys_sampling_inspector_information")
    List<SysSamplingInspectorInformation> selectAll();

    /**根据管理员账号查询未删除的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectAllByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号查询未删除的抽检员信息数量*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectcountAllByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号查询未删除的抽检员信息 按分页*/
    @Select("select * from sys_sampling_inspector_information where id >= (SELECT id from sys_sampling_inspector_information WHERE whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and whether_deleted = 0 and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingInspectorInformation> selectpageByAdminAccount(@Param("adminaccount") String adminaccount,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);

    /**根据管理员账号查询未删除的且未被分配的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and whether_assigned = 1 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectunassignedByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号和姓名，查询同意账号下是否存在同一抽检员信息*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and sii_name = #{siiname} and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int  selectcountByAdminAccountandname(@Param("adminaccount") String adminaccount,@Param("siiname") String siiname);

    @Update("UPDATE sys_sampling_inspector_information set sii_name = #{sii_name},sii_sex = #{sii_sex},sii_phone = #{sii_phone}," +
            "sampling_agency = #{sampling_agency} where id = #{id};")
    int updatebyid(@Param("id")  Integer id,@Param("sii_name")  String sii_name,
                   @Param("sii_sex") String sii_sex,@Param("sii_phone")  String sii_phone,@Param("sampling_agency")  String sampling_agency);
    /*根据id查询出whether_assigned*/
    @Select("select whether_assigned from sys_sampling_inspector_information where id =#{id}")
    int selectwhetherassignedbyid(@Param("id") Integer id);


    @Delete("DELETE FROM sys_sampling_inspector_information WHERE id=#{id};")
    int deletebyid(@Param("id") Integer id);

    /**
     * 插入新数据到抽检员信息表
     *
     * @param
     * @return
     */
    @Insert("INSERT into sys_sampling_inspector_information (sii_name,sii_phone,sii_sex,sampling_agency,admin_id) VALUES(#{sii_name},#{sii_phone},#{sii_sex},#{sampling_agency},#{admin_id});")
    boolean insertnewsiiinformaion (@Param("admin_id") Integer admin_id,
                                       @Param("sii_name")  String sii_name,
                                       @Param("sii_sex")  String sii_sex,
                                       @Param("sii_phone")  String sii_phone,
                                       @Param("sampling_agency")  String sampling_agency);

    /**修改是否修改过id分配字段为0   删除时使用*/
    @Update("UPDATE sys_sampling_inspector_information set whether_deleted = 1 where id = #{id};")
    int update_whether_deleted_byid(@Param("id")  Integer id);

    /**
     * 下面几条语句全是 分配抽检员给抽检账号要一起执行的
     * */
    /**根据名字和管理员名字修改抽检员为已分配*/
    @Update("UPDATE sys_sampling_inspector_information set whether_assigned = 0 where id = #{id}")
    int  updateassignedbyid(@Param("id") Integer id);

    /**根据名字和管理员名字找到要修改的抽检员的id*/
    @Select("select id from sys_sampling_inspector_information where sii_name = #{sii_name} and  admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectidbynameandaccount(@Param("sii_name") String sii_name,@Param("adminaccount") String adminaccount);

    /**重置抽检员到抽检账号的分配*/
    @Update("update sys_sampling_inspector_information set whether_assigned = 1 WHERE whether_deleted = 0 and admin_id = (SELECT id from sys_user WHERE account = #{adminacount})")
    int resetsamplingaccount(@Param("adminacount") String adminaccount);
}
