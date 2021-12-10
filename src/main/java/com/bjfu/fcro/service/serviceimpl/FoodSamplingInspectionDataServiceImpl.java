package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.utils.BaiduApiTool;
import com.bjfu.fcro.dao.FoodSamplingInspectionDataDao;
import com.bjfu.fcro.dao.FoodSamplingInspectionDataStatisticalDao;
import com.bjfu.fcro.service.FoodSamplingInspectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FoodSamplingInspectionDataServiceImpl implements FoodSamplingInspectionDataService {



    static String foods[] = {
        "蔬菜","畜禽肉及副产品","水产品","水果类","鲜蛋","豆类","生干坚果与籽类食品"
    };
    static String province[] = {
        "福建","云南","湖南","浙江","河南","吉林","广东","湖北","河北","陕西","上海","广西","黑龙江","重庆","贵州","辽宁","山东",
            "新疆","海南","天津","北京","山西","江苏","内蒙古","安徽","宁夏","青海","四川","江西","甘肃"
    };

    static String filePath = "F:\\Python\\riskmodel\\files";
    @Autowired
    private FoodSamplingInspectionDataDao samplingInspectionDataDao;
    @Autowired
    private FoodSamplingInspectionDataStatisticalDao foodSamplingInspectionDataStatisticalDao;
    @Override
    public void pretreatmentData() {

        /*食品亚类数据处理*/
//        for (int i = 1; i < foods.length; i++) {
//            String newFood = foods[i];
//            String oldFood = newFood+"\t";
//            System.out.println(oldFood);
//            System.out.println(samplingInspectionDataDao.updateFood(newFood,oldFood));
//        }
        //      System.out.println(samplingInspectionDataDao.selectFood(oldFood));

        /*省份数据处理*/
        for (int i = 0; i < province.length; i++) {
            String newProvince = province[i];
            String oldProvince = newProvince+"\t";
            System.out.println(oldProvince);
            System.out.println(samplingInspectionDataDao.updateProvince(oldProvince,newProvince));
//            System.out.println(samplingInspectionDataDao.selectProvince(oldProvince));
        }


    }

    public void samplingStatistics(){
        String sql = "";
        for (int i = 0; i < foods.length ; i++) {
            for (int j = 0; j < province.length; j++) {
                for (int k = 1; k <= 12; k++) {
                    String the_sampling_time;
                    if(k<10){
                         the_sampling_time = "%-0"+k+"-%";
                    }else{
                         the_sampling_time = "%-"+k+"-%";
                    }
                    String food = foods[i];
                    String nowProvince = province[j];
                    int samplingNumber = samplingInspectionDataDao.selectSamplingNumber(nowProvince,food,the_sampling_time);
                    int qualifiedSamplingNumber = samplingInspectionDataDao.selectQualifiedSamplingNumber(nowProvince,food,the_sampling_time);
                    StringBuilder sql_content = new StringBuilder();
                    sql_content.append("SELECT count(1) FROM food_sampling_inspection_data WHERE province = '");
                    sql_content.append(nowProvince);
                    sql_content.append("' and food = '");
                    sql_content.append(food);
                    sql_content.append("' and the_sampling_time  like '");
                    sql_content.append(the_sampling_time);
                    sql_content.append("';");
                    StringBuilder sql_content_qualified = new StringBuilder();
                    sql_content_qualified.append("SELECT count(1) FROM food_sampling_inspection_data WHERE province = '");
                    sql_content_qualified.append(nowProvince);
                    sql_content_qualified.append("' and food = '");
                    sql_content_qualified.append(food);
                    sql_content_qualified.append("'and conclusion = '纯抽检合格样品' and the_sampling_time  like '");
                    sql_content_qualified.append(the_sampling_time);
                    sql_content_qualified.append("';");
                    double old_pass_rate =  1;
                    if(samplingNumber != 0){
                        old_pass_rate = (double) qualifiedSamplingNumber / samplingNumber;
                    }
                    String pass_rate = String.format("%.3f",old_pass_rate);
                    System.out.println(food+" "+nowProvince+" "+k);
                    foodSamplingInspectionDataStatisticalDao.insertNewData(nowProvince,food,Integer.toString(k),qualifiedSamplingNumber,samplingNumber,pass_rate,sql_content.toString(),sql_content_qualified.toString());
                }
            }
        }
    }

    public void createFile() throws IOException {

        File dir = new File(filePath);
        // 一、检查放置文件的文件夹路径是否存在，不存在则创建
        if (!dir.exists()) {
            dir.mkdirs();// mkdirs创建多级目录
        }
        Map<String,Integer> provincemap = new HashMap<>();
        for (int i = 0; i < province.length; i++) {
            provincemap.put(province[i],i+1);
        }

        for (int k = 0; k < foods.length; k++) {
            String  food = foods[k];
            String fileName = "/"+food+".txt";

            File checkFile = new File(filePath + fileName);
            FileWriter writer = null;
            try {
                checkFile.delete();
                // 二、检查目标文件是否存在，不存在则创建
                if (!checkFile.exists()) {
                    checkFile.createNewFile();// 创建目标文件
                }
                // 三、向目标文件中写入内容
                // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
                writer = new FileWriter(checkFile, true);
                for (int i = 0; i < province.length; i++) {
                    String nowProvince = province[i];
                    int provinceIndex = provincemap.get(nowProvince);
                    for (int j = 1; j <= 12; j++) {

                        String sampling_month = Integer.toString(j);
                        int totalSum = foodSamplingInspectionDataStatisticalDao.selectTotalSum(nowProvince,sampling_month,food);
                        /**不算哪些没有抽检的*/
                        if(totalSum == 0) continue;
                        String pass_rate = foodSamplingInspectionDataStatisticalDao.selectPassRate(nowProvince,sampling_month,food);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(provinceIndex+" ");
                        stringBuilder.append(sampling_month+" ");
                        stringBuilder.append(pass_rate);
                        writer.append(stringBuilder.toString());
                        if(i!=province.length-1 || j!=12){
                            writer.append("\r\n");
                        }
                    }
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != writer)
                    writer.close();
            }
        }





    }

    @Override
    public String getImgs() {
        String path = "F:\\Java\\foodcheck_routeopti\\files\\images\\modelImages\\";
//        String path = "F:\\Python\\riskmodel\\images\\";
//        String path = "F:\\Java\\foodcheck_routeopti\\files\\modelImages\\";
        String files[] = getFile(path,0);
        StringBuilder stringBuilder =new StringBuilder();
        stringBuilder.append("{\"url\":[");
        for (int i=0;i<files.length;i++){

            stringBuilder.append("\""+files[i]+"\"");
            if(i!=files.length-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    @Override
    public String doPredict() {
        String url = "http://127.0.0.1:5000/predict?food=%E6%B0%B4%E4%BA%A7%E5%93%81&province=5&month=30";
        String json = BaiduApiTool.loadJSON(url);
        System.out.println(json);
        return "";
    }

    private static String[] getFile(String path,int deep){
        // 获得指定文件对象
        File file = new File(path);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        String res[] = new String[array.length];
        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");
                // 只输出文件名字
                res[i] = array[i].getName();
                // 输出当前文件的完整路径
                // System.out.println("#####" + array[i]);
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
                // System.out.println(array[i].getPath());
            }
            else if(array[i].isDirectory())//如果是文件夹
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");

                System.out.println( array[i].getName());
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(),deep+1);
            }
        }
        return res;
    }
}

