package com.bjfu.fcro.service;


import com.alibaba.druid.sql.dialect.oracle.ast.OracleDataTypeIntervalYear;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingInspectorInformationService {

    /**
     * 查询出表中的抽检员信息表中的所有信息
     *
     * @param
     * @return 实例对象
     */
    List<SysSamplingInspectorInformation> selectall();
    /*
    * 根据adminid查询当前管理员id下的抽检员的信息
    * */
    List<SysSamplingInspectorInformation> selectAllByAdminAccount(String adminaccount);
    /**查询数量*/
    int selectcountAllByAdminAccount(String adminaccount);
    int selectnoleavecountAllByAdminAccount(String adminaccount);
    /**查询数量*/
    int selectAskForLeaveCountAllByAdminAccount(String adminaccount);
    /**分页查询所有信息*/
    List<SysSamplingInspectorInformation> selectpageByAdminAccount(String adminaccount,int pagesize, int pageIndex);
    /**申请中抽检员*/
    List<SysSamplingInspectorInformation> selectAskForLeavePageByAdminAccount(String adminaccount,int pagesize, int pageIndex);
    /*
    * 根据id，更新抽检员信息表中的数据
    * */
    int updatebyid(Integer id,String sii_name,String sii_sex,String sii_phone,String sampling_agency,int int_leave_status);


    /*根据id查询出whether_assigned*/
    int selectwhetherassignedbyid( Integer id);

    /*根据id删除数据*/
    int  deletebyid(Integer id);

    /*插入新的抽检员数据*/
     int insertbyaccount(String adminaccount,
                          String sii_name,
                         String sii_sex,
                          String sii_phone,
                          String sampling_agency,
                         String sii_account,
                         String sii_password);
     /*修改删除状态，删除时置字段为0*/
     int update_whether_deleted_byid(Integer id);

    /**根据管理员账号查询未删除的且未被分配的抽检员信息*/
    List<SysSamplingInspectorInformation> selectunassignedByAdminAccount( String adminaccount);

    /**查找是否由该账号名和密码 的对应*/
    int selectaccountandpassword(String username, String password);

    /**根据账号查询对应的抽检员名字*/
    String  selectnamebyaccount(String account);

    Object updatepassword(String account,String password);

   Object handleleave(Integer id,int state);

    Object asktoleave(String account);

    int selectLeaveStateByAaccount(String account);

}
