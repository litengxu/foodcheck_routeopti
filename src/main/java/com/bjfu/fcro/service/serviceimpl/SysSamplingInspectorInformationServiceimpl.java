package com.bjfu.fcro.service.serviceimpl;


import com.bjfu.fcro.dao.SamplingInspectorInformationDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysSamplingInspectorInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SysSamplingInspectorInformationServiceimpl  implements SysSamplingInspectorInformationService{


    @Autowired
    private SamplingInspectorInformationDao samplingInspectorInformationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public List<SysSamplingInspectorInformation> selectall() {
        return samplingInspectorInformationDao.selectAll();
    }

    @Override
    public List<SysSamplingInspectorInformation> selectAllByAdminAccount(String adminiaccount) {
        return samplingInspectorInformationDao.selectAllByAdminAccount(adminiaccount);
    }

    @Override
    public int updatebyid(Integer id,String sii_name,String sii_sex,String sii_phone,String sampling_agency) {
        return samplingInspectorInformationDao.updatebyid(id,sii_name,sii_sex,sii_phone,sampling_agency);
    }

    @Override
    public int selectwhetherassignedbyid(Integer id) {
        return samplingInspectorInformationDao.selectwhetherassignedbyid(id);
    }

    @Override
    public int deletebyid(Integer id) {
        return samplingInspectorInformationDao.deletebyid(id);
    }

    @Override
    public int insertbyaccount(String adminaccount, String sii_name, String sii_sex, String sii_phone, String sampling_agency) {
        int adminid = userDao.selectbyaccount(adminaccount);
        if(adminid != 0 ) {
            samplingInspectorInformationDao.insertnewsiiinformaion(adminid, sii_name, sii_sex, sii_phone, sampling_agency);
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int update_whether_deleted_byid(Integer id) {
        return samplingInspectorInformationDao.update_whether_deleted_byid(id);
    }

    @Override
    public List<SysSamplingInspectorInformation> selectunassignedByAdminAccount(String adminaccount) {
        return samplingInspectorInformationDao.selectunassignedByAdminAccount(adminaccount);
    }


}
