package com.bjfu.fcro.service.serviceimpl;


import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingAccountDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysSamplingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("SysSamplingAccountServiceimpl")
public class SysSamplingAccountServiceimpl implements SysSamplingAccountService, SysCommonService<SysSamplingAccount> {

    @Autowired
    private SamplingAccountDao samplingAccountDao;
    @Autowired
    private UserDao userDao;
    /**
     * 根据管理员账号查出抽检账号的信息
     * */
    @Override
    public List<SysSamplingAccount> selectallbyadminaccount(String adminaccount) {


        int admin_id = userDao.selectbyaccount(adminaccount);
        if(admin_id!=0){
            List<SysSamplingAccount> res = samplingAccountDao.selectAllbyadminid(admin_id);
            return res;
        }else{
            return null;
        }

    }

    @Override
    public int updatebyid(Integer id,  String s_password, String s_username, Integer whether_participate) {
        return samplingAccountDao.updatebyid(id,s_password,s_username,whether_participate);
    }

    @Override
    public int update_whether_modify_idsbyid(Integer id) {
        return samplingAccountDao.update_whether_modify_idsbyid(id);
    }

    @Override
    public boolean insertnewssaccount(String  adminaccount, String s_password, String s_username, String s_account, int whether_participate) {
        int adminid = userDao.selectbyaccount(adminaccount);
        if(adminid != 0 ) {
            return samplingAccountDao.insertnewssaccount( adminid,s_password, s_username, s_account,  whether_participate);
        }else{
            return false;
        }

    }

    @Override
    public int selectssaccountbyaccount(String s_account) {
        return samplingAccountDao.selectssaccountbyaccount(s_account);
    }
}
