package com.bjfu.fcro.controller;


import ch.qos.logback.core.util.FileUtil;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.OfficeTool;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.entity.SysSamplingFoodType;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingFoodTypeService;
import com.bjfu.fcro.service.SysSamplingLibraryService;
import com.bjfu.fcro.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sslibrary")
public class SysSamplingLibraryController {

    protected static final Logger logger = LoggerFactory.getLogger(SysSamplingLibraryController.class);

    @Autowired
    private SysCommonMethodService sysCommonMethodService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysSamplingLibraryService sysSamplingLibraryService;
    @Autowired
    private SysSamplingFoodTypeService sysSamplingFoodTypeService;

    /**查询所有的抽检点信息，分页展示*/
    @PostMapping("/getalllibrary")
    @ResponseBody
    public Object getalllibrary(
            @RequestParam  String searchname,
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        String admincount = sysCommonMethodService.findadminaccount();
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        int admin_id = sysUserService.selectbyaccount(admincount);
        List<SysSamplingFoodType> foodTypes = sysSamplingFoodTypeService.findalltypebyadminid(admincount);
        List<SysSamplingLibrary> sysSamplingLibraries= new ArrayList<>();
        sysSamplingLibraries=  sysSamplingLibraryService.getalllibrary(admin_id,pagesize_true,pageindex_true,searchname);
        int pageTotal = sysSamplingLibraryService.selectcountbyadminid(admin_id,searchname);
        Map<String,Object> res = new HashMap<>();
        res.put("foodTypes",foodTypes);
        res.put("sysSamplingLibraries",sysSamplingLibraries);
        res.put("pageTotal",pageTotal);
        return ResultTool.success(res);
    }

    /**编辑抽检库的信息*/
    @PostMapping("/updatesamplinglibrarybyid")
    @ResponseBody
    public Object updatesamplinglibrarybyid(
            @RequestParam int id,
            @RequestParam String jurisdiction,
            @RequestParam String category,
            @RequestParam String ssl_name,
            @RequestParam String address
    ) {
            if(sysSamplingLibraryService.updatesamplinglibrarybyid(id, jurisdiction, category, ssl_name, address) > 0){
                return ResultTool.success();
            }else{
                return ResultTool.fail();
            }
    }



    /**删除抽检库的数据*/
    @PostMapping("/deletesamplinglibrarybyid")
    @ResponseBody
    public Object deletesamplinglibrarybyid(
            @RequestParam int id){

        if(sysSamplingLibraryService.deletesamplinglibrarybyid(id)){

            return ResultTool.success();
        }else {
            return ResultTool.fail();
        }
    }

    /**保存抽检类型到抽检库*/
    @PostMapping("/savesamplingtype")
    @ResponseBody
    public Object savesamplingtype(
            @RequestParam String distributenames[],
            @RequestParam String againhavedistributenames[],
            @RequestParam int insaccountid
            ) throws Exception{
        String adminaccount = sysCommonMethodService.findadminaccount();
        if(sysSamplingLibraryService.savesamplingidstosslibrary(adminaccount,distributenames,againhavedistributenames,insaccountid)){
            return ResultTool.success();
        }else{
            return ResultTool.fail();
        }
    }


    /**添加新的抽检点*/
    @PostMapping("/addnewsamplinglibrary")
    @ResponseBody
    public Object addnewsamplinglibrary(
            @RequestParam String jurisdiction,
            @RequestParam String category,
            @RequestParam String ssl_name,
            @RequestParam String address,
            @RequestParam String selectedfoodtypes[]
    )throws  Exception{
        if(jurisdiction.equals("") || jurisdiction.contains(" ") || category .equals("") || category.contains(" ") || ssl_name.equals("") || ssl_name.contains(" ") || address.equals("") || address.contains(" ")){
            return ResultTool.fail(ResultCode.CONTAINS_UNKNOWN_CHARACTERSS);
        }
        if(sysSamplingLibraryService.slelectcountbysslname(ssl_name) >0){
            return ResultTool.fail(ResultCode.USER_ALREADY_EXISTS);
        }

        String adminaccount = sysCommonMethodService.findadminaccount();
        int admin_id = sysUserService.selectbyaccount(adminaccount);
        return sysSamplingLibraryService.insertallsamplinglibrary(ssl_name,category,address,admin_id,jurisdiction,selectedfoodtypes,adminaccount);

    }


    /**利用excel上传抽检库*/
    @PostMapping("/uploadbyexcel")
    @ResponseBody
    public Object uploadbyexcel(
            HttpServletRequest request
                                ) throws Exception {
//        MultipartHttpServletRequest multipartRequest =null;
//        if (request instanceof MultipartHttpServletRequest) {
//            multipartRequest = (MultipartHttpServletRequest)(request);
//        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        String paths[] = new String[files.size()];
        String adminacount = sysCommonMethodService.findadminaccount();

        /*把文件存到磁盘上*/
        for (int i = 0; i <files.size() ; i++) {
            paths[i] =  OfficeTool.uploadAndReturnPath(files.get(i),adminacount,files.get(i).getOriginalFilename());
        }
        int admin_id = sysUserService.selectbyaccount(adminacount);
        sysSamplingLibraryService.uploadbyexcel(adminacount,admin_id,paths);
        return ResultTool.success();
    }
    @RequestMapping(value = "/download")
    @ResponseBody
    public void download(

    ) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        String filename = "templateexcel.xlsx";
        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String bianma = new String(filename.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + bianma);
        OfficeTool.download(filename, response);
    }
}
