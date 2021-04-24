package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "sys_sampling_inspector_information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingInspectorInformation {
    //id
    @Id
    private Integer id;
    //姓名
    private String sii_name;
    //性别
    private String sii_sex;
    //手机号
    private String sii_phone;
    //所属抽检机构
    private String sampling_agency;
    //    是否已被分配到抽检账号（默认1，未分配到账号）
    private boolean whether_assigned;
    //管理员id（外键）
    private int admin_id;
    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;

    /*是否已删除（默认0，代表未删除*/
    private boolean whether_deleted;
    //账号密码
    private String sii_account;
    private String sii_password;
    /*请假状态 */
    private int leave_status;
}
