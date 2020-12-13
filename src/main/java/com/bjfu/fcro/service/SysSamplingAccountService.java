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
}
