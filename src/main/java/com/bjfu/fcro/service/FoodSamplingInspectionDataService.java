package com.bjfu.fcro.service;


import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FoodSamplingInspectionDataService {


    /**预处理下数据库中的数据*/
    void pretreatmentData();
    /**统计数据*/
    void  samplingStatistics();
    /**创建文件*/
    void createFile() throws IOException;
    /**返回图片路径*/
    String getImgs();

    String doPredict();
}
