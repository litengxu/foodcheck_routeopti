package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

@Service
public interface SysSamplingAccountService {

        /**
         * 根据id更新抽检账号中的某些值
         *
         * */
        int updatebyid(Integer id,
                    String s_password,  String s_username, Integer whether_participate);
        /**
        * 修改whether_modify_id字段为0
        * */
        int update_whether_modify_idsbyid(Integer id);


        /**
         * 插入新数据到抽检账号表
         *
         * @param
         * @return
         */
        boolean insertnewssaccount ( String adminaccount,
                                     String s_password,
                                     String s_username,
                                     String s_account,
                                     int whether_participate);

        /**根据抽检账号查询表中是否有数据*/
        int selectssaccountbyaccount( String s_account);

        /**管理抽检员到抽检账号的分配*/
        boolean admininspectortoaccount( String adminaccount,
                                            String distributenames[],
                                            Integer insaccountid) throws  Exception;

        /**根据id查找分配到某抽检账号的抽检员*/
        String selectnamesbyid( Integer id);

        /**重置抽检员到抽检账号的分配*/
        int resetsamplingaccount(String adminaccount) throws Exception;

        /**随机分配*/
        boolean randomlyassigned(String adminaccount,int size,int accountsize,int personsize) throws Exception;

        /**查询所有可以参与抽检的账号*/

        Object selectAllCanParticipatebyadminid(int adminid,String admincount);

        /**查找是否由该账号名和密码 的对应*/
        int selectaccountandpassword(String username, String password);
}
