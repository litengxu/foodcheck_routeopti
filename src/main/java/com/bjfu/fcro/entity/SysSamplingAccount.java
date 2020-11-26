package com.bjfu.fcro.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="sys_sampling_account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysSamplingAccount {

        @Id
        private  int id;
        //抽检账号
        private  String s_account;
        //密码
        private  String s_password;
        //用户名
        private String  s_username;
        //管理员id（外键）
        private int admin_id;
        //分配到的抽检员id，用-分开
        private String sampling_inspector_ids;
        //是否参与抽检，默认为1，参与抽检
        private boolean whether_participate;
        //adminid是否修改过（默认1，未修改）
        private boolean whether_modify_adminid;
        //创建时间
        private Date create_time;
        //修改时间
        private Date last_update_time;
        //保留字段
        private String ssa_reserved_field1;
        private String ssa_reserved_field2;
        private String ssa_reserved_field3;
}
