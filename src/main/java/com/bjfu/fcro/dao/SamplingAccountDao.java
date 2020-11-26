package com.bjfu.fcro.dao;



import com.bjfu.fcro.entity.SysSamplingAccount;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface SamplingAccountDao {

    @Select("select * from sys_sampling_account")
    SysSamplingAccount selectAll();
}
