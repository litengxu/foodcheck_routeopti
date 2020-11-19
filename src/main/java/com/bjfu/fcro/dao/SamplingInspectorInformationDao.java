package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

//抽检员信息dao层
@Component
public interface SamplingInspectorInformationDao {
    @Select("select * from sys_sampling_inspector_information")
    SysSamplingInspectorInformation selectAll();
}
