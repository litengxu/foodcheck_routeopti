package com.bjfu.fcro.controller;


import com.bjfu.fcro.entity.SysSamplingFoodList;
import com.bjfu.fcro.service.SysSamplingFoodListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.soap.SAAJMetaFactory;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("samplingFoodList")
public class SysSamplingFoodListController {

    @Autowired
    private SysSamplingFoodListService sysSamplingFoodListService;

    @RequestMapping("/findAllBySspId")
    @ResponseBody
    public Object findAllBySspId(
            @RequestParam Integer id,
            @RequestParam Integer pageIndex,
            @RequestParam Integer pageSize
    ){
        int pageindex_true = (pageIndex-1)*pageSize;
        int pagesize_true = pageSize;
        return sysSamplingFoodListService.selectAllBySspid(pagesize_true,pageindex_true,id);
    }
    /*查看图片*/
    @RequestMapping("/selectImages")
    @ResponseBody
    public Object selectImages(
            @RequestParam   Integer id
    ) throws ParseException {
        return sysSamplingFoodListService.selectImages(id);
    }

}
