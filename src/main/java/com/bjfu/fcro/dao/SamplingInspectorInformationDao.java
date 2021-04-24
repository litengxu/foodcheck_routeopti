package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingAccount;
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

    /**查找是否由该账号名和密码 的对应*/
    @Select("SELECT count(id) from sys_sampling_inspector_information WHERE sii_account =#{username} and sii_password =#{password} and whether_deleted = 0")
    int selectaccountandpassword(@Param("username") String username,@Param("password") String password);

    /**根据账号查找对应的抽检员姓名*/
    @Select("SELECT sii_name from sys_sampling_inspector_information WHERE sii_account =#{account}")
    String selectnamebyaccount(@Param("account") String account);
    /**根据账号查找对应的数据*/
    @Select("SELECT * from sys_sampling_inspector_information WHERE sii_account =#{account}")
    SysSamplingInspectorInformation  selectbyaccount(@Param("account") String account);
    /**根据管理员账号查询未删除的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectAllByAdminAccount(@Param("adminaccount") String adminaccount);
    /**根据管理员账号查询未删除的未请假的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and leave_status != 2 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectnoleaveAllByAdminAccount(@Param("adminaccount") String adminaccount);
    /**根据管理员账号查询未删除的抽检员信息数量*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectcountAllByAdminAccount(@Param("adminaccount") String adminaccount);

    /**查询未删除未请假的抽检员信息数量*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and leave_status != 2 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectnoleavecountAllByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号查询正在请求假期的抽检员信息数量*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and leave_status = 1 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selectAskForLeaveCountAllByAdminAccount(@Param("adminaccount") String adminaccount);


    /**根据管理员账号查询未删除的抽检员信息 按分页*/
    @Select("select * from sys_sampling_inspector_information where id >= (SELECT id from sys_sampling_inspector_information WHERE whether_deleted = 0 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and whether_deleted = 0 and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingInspectorInformation> selectpageByAdminAccount(@Param("adminaccount") String adminaccount,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);

    /**根据管理员账号查询申请假期的未删除的抽检员信息 按分页*/
    @Select("select * from sys_sampling_inspector_information where id >= (SELECT id from sys_sampling_inspector_information WHERE whether_deleted = 0 and leave_status = 1 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and whether_deleted = 0 and leave_status = 1 and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingInspectorInformation> selectAskForLeavePageByAdminAccount(@Param("adminaccount") String adminaccount,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);


    /**根据管理员账号查询未删除的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and whether_assigned = 1  and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectunassignedByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号查询未删除的且未被分配的抽检员信息*/
    @Select("select * from sys_sampling_inspector_information where whether_deleted = 0 and whether_assigned = 1 and leave_status != 2 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    List<SysSamplingInspectorInformation> selectunassignednoleaveByAdminAccount(@Param("adminaccount") String adminaccount);

    /**根据管理员账号和姓名，查询同意账号下是否存在同一抽检员信息*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and sii_name = #{siiname} and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int  selectcountByAdminAccountandname(@Param("adminaccount") String adminaccount,@Param("siiname") String siiname);
    /**根据管理员账号和姓名，查询同意账号下是否存在同一抽检员账号*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and sii_account = #{sii_account} and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int  selectcountByAdminAccountandsiiaccount(@Param("adminaccount") String adminaccount,@Param("sii_account") String sii_account);

    /**根据管理员账号和姓名，查询所有账号下是否存在同一抽检员信息*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and sii_name = #{siiname}")
    int  selectallcountByAdminAccountandname(@Param("siiname") String siiname);
    /**根据管理员账号和姓名，查询所有账号下是否存在同一抽检员账号*/
    @Select("select count(id) from sys_sampling_inspector_information where whether_deleted = 0 and sii_account = #{sii_account}")
    int  selectallcountByAdminAccountandsiiaccount(@Param("sii_account") String sii_account);

    @Update("UPDATE sys_sampling_inspector_information set sii_name = #{sii_name},sii_sex = #{sii_sex},sii_phone = #{sii_phone}," +
            "sampling_agency = #{sampling_agency},leave_status = #{int_leave_status} where id = #{id};")
    int updatebyid(@Param("id")  Integer id,@Param("sii_name")  String sii_name,
                   @Param("sii_sex") String sii_sex,@Param("sii_phone")  String sii_phone,@Param("sampling_agency")  String sampling_agency,@Param("int_leave_status") int int_leave_status);
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
    @Insert("INSERT into sys_sampling_inspector_information (sii_name,sii_phone,sii_sex,sampling_agency,admin_id,sii_account,sii_password,leave_status) VALUES(#{sii_name},#{sii_phone},#{sii_sex},#{sampling_agency},#{admin_id},#{sii_account},#{sii_password},0);")
    boolean insertnewsiiinformaion (@Param("admin_id") Integer admin_id,
                                       @Param("sii_name")  String sii_name,
                                       @Param("sii_sex")  String sii_sex,
                                       @Param("sii_phone")  String sii_phone,
                                       @Param("sampling_agency")  String sampling_agency,
                                       @Param("sii_account")  String sii_account,
                                        @Param("sii_password")  String sii_password
    );

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


    /**根据账号更新密码*/
    @Update("update sys_sampling_inspector_information set sii_password = #{password} where sii_account =#{account}")
    int updatePasswordByAccount(@Param("account") String account, @Param("password") String password );

    @Update("UPDATE sys_sampling_inspector_information set leave_status = #{state} where id = #{id}")
    int  updateleavestatusbyid(@Param("id") Integer id,@Param("state") Integer state);
}
