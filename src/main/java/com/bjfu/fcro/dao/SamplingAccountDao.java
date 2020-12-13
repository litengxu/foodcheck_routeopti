package com.bjfu.fcro.dao;



import com.bjfu.fcro.entity.SysSamplingAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SamplingAccountDao {

    @Select("select * from sys_sampling_account")
    SysSamplingAccount selectAll();

    @Select("select * from sys_sampling_account where admin_id = #{admin_id} and whether_modify_ids = 1")
    List<SysSamplingAccount> selectAllbyadminid(@Param("admin_id") int admin_id);

    @Update("UPDATE sys_sampling_account set s_password = #{s_password},s_username = #{s_username}," +
            "whether_participate = #{whether_participate} where id = #{id};")
    int updatebyid(@Param("id")  Integer id, @Param("s_password") String s_password,@Param("s_username")  String s_username,@Param("whether_participate")  Integer whether_participate);

    /**修改是否修改过id分配字段为0   删除时使用*/
    @Update("UPDATE sys_sampling_account set whether_modify_ids = 0 where id = #{id};")
    int update_whether_modify_idsbyid(@Param("id")  Integer id);

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
}
