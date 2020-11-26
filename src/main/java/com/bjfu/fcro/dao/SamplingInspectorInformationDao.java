package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

//抽检员信息dao层
@Component
public interface SamplingInspectorInformationDao {
    @Select("select * from sys_sampling_inspector_information")
    List<SysSamplingInspectorInformation> selectAll();

    @Select("select * from sys_sampling_inspector_information where admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectAllByAdminAccount(@Param("adminaccount") String adminaccount);

    @Update("UPDATE sys_sampling_inspector_information set sii_name = #{sii_name},sii_sex = #{sii_sex},sii_phone = #{sii_phone}," +
            "sampling_agency = #{sampling_agency} where id = #{id};")
    int updatebyid(@Param("id")  Integer id,@Param("sii_name")  String sii_name,
                   @Param("sii_sex") String sii_sex,@Param("sii_phone")  String sii_phone,@Param("sampling_agency")  String sampling_agency);
    /*根据id查询出whether_assigned*/
    @Select("select whether_assigned from sys_sampling_inspector_information where id =#{id}")
    int selectwhetherassignedbyid(@Param("id") Integer id);

    @Delete("DELETE FROM sys_sampling_inspector_information WHERE id=#{id];")
    int deletebyid(@Param("id") Integer id);
}
