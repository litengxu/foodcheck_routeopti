package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingLibrary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingLibraryService {

    List<SysSamplingLibrary> selectAll( int pagesize, int pageIndex);

    /**根据管理员账号查询抽检库信息 按分页*/
    List<SysSamplingLibrary> selectpageByAdminid( int  admin_id,  int pagesize, int pageIndex);
    /**根据管理员账号查询抽检库信息 按分页*/
    List getalllibrary(
            Integer admin_id,
            Integer pageSize,
            Integer pageIndex
            );

    /**修改抽检库的数据*/
    int updatesamplinglibrarybyid( int id,String jurisdiction,String category, String ssl_name,String address);



    /**删除抽检库的数据*/
    boolean deletesamplinglibrarybyid( int id);

    /**保存抽检类型到抽检库*/
    boolean savesamplingidstosslibrary(String adminaccount,String distributenames[], String againhavedistributenames[], int insaccountid) throws  Exception;

    /**根据抽检点名称查询共有多少个*/
    int slelectcountbysslname(String ssl_name);

    /**保存新的抽检点数据到表中*/
    boolean insertallsamplinglibrary(
            String ssl_name,
             String category,
             String address,
             int admin_id,
            String jurisdiction,
            String selectedfoodtypes[],
            String  adminaccount
    ) throws  Exception;
}
