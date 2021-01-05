package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingLibrary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public interface SysSamplingLibraryService {

    List<SysSamplingLibrary> selectAll( int pagesize, int pageIndex);

    /**根据管理员账号查询抽检库信息 按分页*/
    List<SysSamplingLibrary> selectpageByAdminid( int  admin_id,  int pagesize, int pageIndex);
    /**根据管理员账号查询抽检库信息 按分页*/
    List getalllibrary(
            Integer admin_id,
            Integer pageSize,
            Integer pageIndex,
            String searchname
            );
    /**根据管理员账号查询抽检库信息 按分页*/
    List<SysSamplingLibrary> searchpageByAdminid( int  admin_id,int pagesize, int pageIndex, String searchname);


    /**获取管理员id下的所有数据数量，分页用*/
    int selectcountbyadminid(int admin_id,String searchname);
    /**修改抽检库的数据*/
    int updatesamplinglibrarybyid( int id,String jurisdiction,String category, String ssl_name,String address);
    /**上传excel表*/
    Map<Integer, Map<Integer,Object>> uploadbyexcel(String adminacount,int admin_d,String []paths);

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
