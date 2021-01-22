package com.bjfu.fcro.service.serviceimpl;


import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingAccountDao;
import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.SamplingInspectorInformationDao;
import com.bjfu.fcro.dao.UserDao;
import com.bjfu.fcro.entity.SysSamplingAccount;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysCommonService;
import com.bjfu.fcro.service.SysSamplingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository("SysSamplingAccountServiceimpl")
public class SysSamplingAccountServiceimpl implements SysSamplingAccountService, SysCommonService<SysSamplingAccount> {

    @Autowired
    private SamplingAccountDao samplingAccountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SamplingInspectorInformationDao samplingInspectorInformationDao;
    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;
    Random random = new Random();
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
    public int  selectcountlbyadminaccount(String adminaccount) {
        return samplingAccountDao.selectcountAllbyadmincount(adminaccount);
    }

    @Override
    public List<SysSamplingAccount> selectpagebyadminaccount(String adminaccount,int pagesize,int pageindex) {
        return samplingAccountDao.selectpageByAdminAccount(adminaccount,pagesize,pageindex);
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

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public boolean admininspectortoaccount(String adminaccount, String[] distributenames, Integer insaccountid) throws Exception {
        /**
         *     1. 修改选中抽检员为已分配
         *     2. 把抽检员名字插入到抽检账号中,并把是否修改过改为0（已修改过），该行不会被前台查到
         *     3. 再复制该行数据插入到抽检账号表中，是否修改过字段为1，目的在于保留账号分配的历史
         *
         *     事务控制
         *     */
        /**1. 修改选中抽检员为已分配*/

            StringBuilder ids = new StringBuilder();
            StringBuilder names = new StringBuilder();

            for (int i = 0; i < distributenames.length; i++) {
                int id = samplingInspectorInformationDao.selectidbynameandaccount(distributenames[i], adminaccount);
                ids.append(id);
                names.append(distributenames[i]);
                if (i != distributenames.length - 1) {
                    ids.append('-');
                    names.append('-');
                }
                int res = samplingInspectorInformationDao.updateassignedbyid(id);
            }
            /**如果存在值，追加在已有值的后面*/
            SysSamplingAccount sysSamplingAccount = samplingAccountDao.selectnameandidsbyid(insaccountid);
            if(sysSamplingAccount !=null) {
                String strids = sysSamplingAccount.getSampling_inspector_ids();
                if (strids != null &&! "".equals(strids)) {
                    ids.append('-');
                    ids.append(strids);
                    names.append('-');
                    names.append(sysSamplingAccount.getSampling_inspector_names());

                }
            }
            /**2. 把抽检员名字插入到抽检账号中,并把是否修改过改为0（已修改过），该行不会被前台查到   */
            int a = samplingAccountDao.updateidsandnamesbyid(ids.toString(), names.toString(), insaccountid);
            /**3. 再复制该行数据插入到抽检账号表中，是否修改过字段为1，目的在于保留账号分配的历史*/
            samplingAccountDao.deletetemp();
            samplingAccountDao.create_sys_sampling_account_temp(insaccountid);
            samplingAccountDao.updatetempidandids();
            samplingAccountDao.insertssacountbetemp();
            samplingAccountDao.deletetemp();
            return true;

    }

    @Override
    public String selectnamesbyid(Integer id) {
        String res = samplingAccountDao.selectnamesbyid(id);
        if("".equals(res)){
            return null;
        }
        return res;
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public int resetsamplingaccount(String adminaccount) throws  Exception {
        samplingAccountDao.resetsamplingaccount(adminaccount);
        samplingInspectorInformationDao.resetsamplingaccount(adminaccount);
        return 1;
    }

    @Override
    @Transactional( rollbackFor = {Exception.class})
    public boolean randomlyassigned(String adminaccount, int size, int accountsize, int personsize) throws Exception{
        resetsamplingaccount(adminaccount);
        int accounts[] = new int[accountsize];
        int persons[] = new int[personsize];
        accounts = randomsort(accounts,accountsize);
        persons = randomsort(persons,personsize);
        int admin_id = userDao.selectbyaccount(adminaccount);
        List<SysSamplingAccount> listaccount = samplingAccountDao.selectAllbyadminid(admin_id);
        List<SysSamplingInspectorInformation> listperson = samplingInspectorInformationDao.selectAllByAdminAccount(adminaccount);
        int accoutindex = 0;
        int personindex = 0;
        for(int i=0;i<listaccount.size();i++){
            int insaccountid = listaccount.get(accounts[accoutindex]).getId();
            for(int j=0;j<size;j++){
                /**1. 修改选中抽检员为已分配*/
                String distributename = listperson.get(persons[personindex]).getSii_name();
                int distributeid = listperson.get(persons[personindex]).getId();
                StringBuilder ids = new StringBuilder();
                StringBuilder names = new StringBuilder();
                ids.append(distributeid);
                names.append(distributename);
                samplingInspectorInformationDao.updateassignedbyid(distributeid);
                /**如果存在值，追加在已有值的后面*/
                SysSamplingAccount sysSamplingAccount = samplingAccountDao.selectnameandidsbyid(insaccountid);
                if(sysSamplingAccount !=null) {
                    String strids = sysSamplingAccount.getSampling_inspector_ids();
                    if (strids != null &&! "".equals(strids)) {
                        ids.append('-');
                        ids.append(strids);
                        names.append('-');
                        names.append(sysSamplingAccount.getSampling_inspector_names());
                    }
                }
                /**2. 把抽检员名字插入到抽检账号中,并把是否修改过改为0（已修改过），该行不会被前台查到   */
                samplingAccountDao.updateidsandnamesbyid(ids.toString(), names.toString(), insaccountid);
                personindex++;
            }
            /**3. 再复制该行数据插入到抽检账号表中，是否修改过字段为1，目的在于保留账号分配的历史*/
            samplingAccountDao.deletetemp();
            samplingAccountDao.create_sys_sampling_account_temp(insaccountid);
            samplingAccountDao.updatetempidandids();
            samplingAccountDao.insertssacountbetemp();
            samplingAccountDao.deletetemp();
            accoutindex++;
        }
        return true;
    }

    @Override
    public Object selectAllCanParticipatebyadminid(int adminid) {
        List<SysSamplingAccount>  listaccount = samplingAccountDao.selectAllCanParticipatebyadminid(adminid);
        List<SysSamplingFoodType> listtype = samplingFoodTypeDao.findallbyadminid(adminid);
        Map<String,Object> res = new HashMap<>();
        res.put("listaccount",listaccount);
        res.put("listtype",listtype);
        return ResultTool.success(res);
    }


    public int[] randomsort(int[] arr,int size){

        for(int i=0;i<size;i++){
            arr[i]  = i;
        }
        for(int i=0;i<size;i++){
            int p = random.nextInt(i+1);
            int tmp = arr[i];
            arr[i] = arr[p];
            arr[p] = tmp;
        }
        return arr;
    }
}
