package com.bjfu.fcro.dao;



import com.bjfu.fcro.entity.SysSamplingAccount;
import org.apache.ibatis.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SamplingAccountDao {

    @Select("select * from sys_sampling_account")
    SysSamplingAccount selectAll();

    @Select("select * from sys_sampling_account where admin_id = #{admin_id} and whether_modify_ids = 1 and whether_participate = 1")
    List<SysSamplingAccount> selectAllbyadminid(@Param("admin_id") int admin_id);

    /**可以参与抽检的账号 参与抽检=1，未被修改过，分配的抽检员id不为null*/
    @Select("select * from sys_sampling_account where admin_id = #{admin_id} and whether_modify_ids = 1 and whether_participate = 1 and sampling_inspector_ids is not NULL")
    List<SysSamplingAccount> selectAllCanParticipatebyadminid(@Param("admin_id") int admin_id);

    @Select("select count(id) from sys_sampling_account where admin_id =  ( SELECT id from sys_user WHERE account = #{adminaccount})  and whether_modify_ids = 1 and whether_participate = 1")
    int selectcountAllbyadmincount(@Param("adminaccount") String  adminaccount);

    @Select("select count(id) from sys_sampling_account where admin_id =  ( SELECT id from sys_user WHERE account = #{adminaccount})")
    int selecthistorycountAllbyadmincount(@Param("adminaccount") String  adminaccount);
    /**根据管理员账号查询未删除的抽检员信息 按分页*/
    @Select("select * from sys_sampling_account where id >= (SELECT id from sys_sampling_account WHERE whether_modify_ids = 1 and admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and whether_modify_ids = 1 and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingAccount> selectpageByAdminAccount(@Param("adminaccount") String adminaccount,@Param("pagesize") int pagesize,@Param("pageIndex") int pageIndex);

    @Update("UPDATE sys_sampling_account set s_password = #{s_password},s_username = #{s_username}," +
            "whether_participate = #{whether_participate} where id = #{id};")
    int updatebyid(@Param("id")  Integer id, @Param("s_password") String s_password,@Param("s_username")  String s_username,@Param("whether_participate")  Integer whether_participate);

    /**修改是否修改过id分配字段为0   删除时使用*/
    @Update("UPDATE sys_sampling_account set whether_modify_ids = 0 where id = #{id};")
    int update_whether_modify_idsbyid(@Param("id")  Integer id);
    /**修改是否参与抽检字段为0 */
    @Update("UPDATE sys_sampling_account set whether_participate = 0 where id = #{id};")
    int update_whether_participate_idsbyid(@Param("id")  Integer id);

    /**
     * 插入新数据到抽检账号表
     *
     * @param
     * @return
     */
    @Insert("INSERT into sys_sampling_account (s_username,s_password,s_account,whether_participate,admin_id) VALUES(#{s_username},#{s_password},#{s_account},#{whether_participate},#{admin_id});")
    boolean insertnewssaccount (@Param("admin_id") Integer admin_id,
                                    @Param("s_password")  String s_password,
                                    @Param("s_username")  String s_username,
                                    @Param("s_account")  String s_account,
                                    @Param("whether_participate")  int whether_participate);

    /**根据抽检账号查询表中是否有数据*/
    @Select("select count(id) from sys_sampling_account where s_account =#{s_account} ")
    int selectssaccountbyaccount(@Param("s_account") String s_account);

    /**根据id查询出当前账号中已被分配的抽检员姓名*/
    @Select("select sampling_inspector_names from sys_sampling_account where id  =#{id}")
    String selectnamesbyid(@Param("id") Integer id);

    /**
     * 下面几条语句全是分配抽检员给抽检账号要一起执行的
     * */

    /**根据id更新抽检账号表的抽检员id和抽检员姓名*/
    @Update("UPDATE sys_sampling_account set sampling_inspector_ids= #{sampling_inspector_ids},sampling_inspector_names =#{sampling_inspector_names},whether_modify_ids = 0 where id =#{id};")
    int updateidsandnamesbyid(@Param("sampling_inspector_ids") String sampling_inspector_ids,
                                                @Param("sampling_inspector_names") String sampling_inspector_names,
                                                @Param("id") Integer id);

    /**查找当前抽检账号下，已分配的抽检员姓名和id*/
    @Select("select * from sys_sampling_account where id = #{id}")
    SysSamplingAccount selectnameandidsbyid(@Param("id") Integer id);

    /**为了复制表中的一条数据创建临时表*/
    @Select("CREATE TEMPORARY TABLE sys_sampling_account_temp SELECT * FROM sys_sampling_account WHERE id = #{id}")
    void create_sys_sampling_account_temp(@Param("id") Integer id);

    /**更新临时表中的字段值*/
    @Update("UPDATE sys_sampling_account_temp SET id = 0,whether_modify_ids = 1")
    int updatetempidandids();

    /**把临时表中的数据插入到抽检账号表中*/
    @Insert("INSERT INTO sys_sampling_account SELECT * FROM sys_sampling_account_temp")
    boolean insertssacountbetemp();

    /**删除临时表*/
    @Delete("DROP TEMPORARY TABLE IF EXISTS sys_sampling_account_temp")
    boolean deletetemp();
    /**
     * 结束
     * */

    /**重置抽检员到抽检账号的分配*/
    @Update("update sys_sampling_account set sampling_inspector_ids = NULL,sampling_inspector_names = NULL WHERE whether_modify_ids = 1 and admin_id = (SELECT id from sys_user WHERE account = #{adminacount})")
    int resetsamplingaccount(@Param("adminacount") String adminaccount);

    /**查找是否由该账号名和密码 的对应*/
    @Select("SELECT count(id) from sys_sampling_account WHERE s_account =#{username} and s_password=#{password} and whether_modify_ids = 1;")
    int selectaccountandpassword(@Param("username") String username,@Param("password") String password);

    /**查找抽检账号的历史信息，*/
    /**根据管理员账号查询未删除的抽检员信息 按分页*/
    @Select("select * from sys_sampling_account where id >= (SELECT id from sys_sampling_account WHERE admin_id  = ( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pageIndex}, 1)" +
            "and admin_id =( SELECT id from sys_user WHERE account = #{adminaccount}) ORDER BY id LIMIT #{pagesize}")
    List<SysSamplingAccount> selecthistorypageByAdminAccount(@Param("adminaccount") String adminaccount, @Param("pagesize") int pagesize, @Param("pageIndex") int pageIndex);


    /**根据抽检账号更新是否参与抽检为   参与抽检*/
    @Update("update sys_sampling_account set whether_participate = 1 where s_account = #{saccount}")
    int updateWhetherParticipateBySaccount(@Param("saccount") String saccount);
}
