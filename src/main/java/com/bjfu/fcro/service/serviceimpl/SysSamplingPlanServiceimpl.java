package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.algorithm.Divide;
import com.bjfu.fcro.algorithm.Grouping;
import com.bjfu.fcro.common.enums.ResultCode;
import com.bjfu.fcro.common.utils.CoordinateToDistance;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingFoodTypeDao;
import com.bjfu.fcro.dao.SamplingLibraryDao;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.entity.temporary.Temp_SampleFoodTable;
import com.bjfu.fcro.entity.temporary.Temp_SamplePlanInfoTable;
import com.bjfu.fcro.service.SysSamplingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SysSamplingPlanServiceimpl implements SysSamplingPlanService {

    /**设置随机值的种子*/
    public static java.util.Random rand = new java.util.Random(47);
    /**设置分组时循环的阈值*/
    public static int exitThreshold = 500;

    @Autowired
    private SamplingFoodTypeDao samplingFoodTypeDao;
    @Autowired
    private SamplingLibraryDao samplingLibraryDao;


    /**
     * selectedsamplingaccountid 抽检账号的id
     * quantityvalue 每种抽检食品要抽检的数量
     * typeoffoodselectedid 抽检食品类型的id
     * numbers 抽检总数量
     * coordinate 出发点经纬度 index= 0为lng
     *selectedsamplingaccountid一定小于等于抽检数量
     *
     * 返回分组结果的字符串
     * */
    @Override
    public Object generateplan(String[] selectedsamplingaccountid, String[] typeoffoodselectedid,String quantityvalue[], int numbers,String coordinate[],String starting_point,int admin_id) {

        /**
         * 0. 每个种类的数量相加为总的基本数量，风险数量为总的数量减去总的基本数量
         * 1. 根据风险数量，风险权重数组，基础数量数组，通过按风险抽检算法，获得每种抽检类型的基本数量
         * 2. 对于某种食品类型，找到含有这种食品类型的商店集合，从中随机选取这种抽检类型对应的基本数量个，存入A=Map<String,List>
         * 3. 遍历一遍A，找到不同list中相同的商店，合并为一个，且抽检数量相加，得到最终的抽检点集合
         * 4. 得到一个各个坐标点间的距离矩阵 dist[][],不利用百度api，只利用原有的坐标点
         * ===============================================================2. 如果list的大小大于numbers则随机选出numbers个，否则不随机，将结果重新存入list
         * 5. 将结果分为selectedsamplingaccountid.length份，每一份交给一个账号抽检（使用分组算法进行分组，保证分组的总的时间花费最少）
         * 4. 获得每一份中各个点之间的交通成本，并存入矩阵
         * 6. 使用算法排序各个点之间的顺序，linitial_position排在第一个和最后一个
         * 7. 将抽检计划存入数据库，修改抽检账号状态为不参与抽检，待抽检完成后改回
         * 8. 将抽检计划发送到每一个参与的抽检账号表的app端
         * 9. 将抽检计划返回给前台并展示
         *
         * */


        /**        1. 根据风险数量，风险权重数组，基础数量数组，通过按风险抽检算法，获得每种抽检类型的基本数量*/

        int typelength = typeoffoodselectedid.length;
        double ratios[] = new double[typelength];
        int basicAmounts[] = new int[typelength];
//              获取风险权重数组
        for(int i=0;i<typelength;i++){
            ratios[i] = Double.parseDouble(samplingFoodTypeDao.findriskvalue(Integer.parseInt(typeoffoodselectedid[i])));
            basicAmounts[i] = Integer.parseInt(quantityvalue[i]);
        }
        System.out.println(Arrays.toString(selectedsamplingaccountid));
        System.out.println(Arrays.toString(typeoffoodselectedid));
        System.out.println(Arrays.toString(quantityvalue));
        System.out.println(Arrays.toString(ratios));

//        最终抽检数量
        int[] finalAmounts = Divide.divideByBasicAndRatios(ratios,numbers,basicAmounts);
        System.out.println(Arrays.toString(finalAmounts));


     /*** 2. 对于某种食品类型，找到含有这种食品类型的商店集合，从中随机选取这种抽检类型对应的基本数量个，存入A=List<List>*/

        /*根据食品类型id找到对应的食品类型名字*/
        String typeoffoodselected[]  = new String[typelength];
        for (int i = 0; i < typeoffoodselectedid.length ; i++) {
            typeoffoodselected[i] = samplingFoodTypeDao.findtypename(Integer.parseInt(typeoffoodselectedid[i]));
        }
        //        对于每个食品类型id，找到含有它的抽检点，注意对应的基本抽检数量要大于1
        Map<String,List<Temp_SamplePlanInfoTable>> map = new HashMap<>();
        /*查询管理员名下所有的抽检点*/
        List<SysSamplingLibrary> alllibrary = samplingLibraryDao.selectallByAdminidnopage(admin_id);
        /*此循环的目的是找到含有某种食品类型的多个或者某个抽检点，存入同一个map中，key为食品类型id*/
        for (int i = 0; i < typeoffoodselectedid.length ; i++) {
            /*只有当抽检数量大于等于1才执行查询*/
                if(finalAmounts[i] >= 1){

                    /*遍历所有的抽检点*/
                    for(int j=0;j<alllibrary.size();j++){
                        /*按-拆分，获得抽检点中含有的所有食品类型id*/
                        String typefoodids[] = alllibrary.get(j).getFoodtype_ids().split("-");
                        /*遍历食品类型数组*/
                        for(int k=0;k< typefoodids.length;k++){
                            /*当数组中含有要抽检的食品时*/
                            if(typefoodids[k].equals(typeoffoodselectedid[i])){
                                /*新建抽检食品类型实体，传入当前抽检的食品类型和数量*/
                                Temp_SampleFoodTable temp_sampleFoodTable = new Temp_SampleFoodTable(typeoffoodselected[i],1);
                                /*新建临时抽检计划实体，传入抽检点的id，状态，地址，经纬度，以及临时抽检实体的list*/
                                Temp_SamplePlanInfoTable temp_samplePlanInfoTable = new Temp_SamplePlanInfoTable(alllibrary.get(j).getId(),false,
                                        alllibrary.get(j).getAddress(),Double.parseDouble(alllibrary.get(j).getLongitude()),Double.parseDouble(alllibrary.get(j).getLatitude()),new ArrayList<>());
                                temp_samplePlanInfoTable.getSampleofoodlist().add(temp_sampleFoodTable);
                                /*当map中不存在当前抽检食品类型时*/
                                if(map.get(typeoffoodselectedid[i]) == null){
                                    /*新建list并存入*/
                                    List<Temp_SamplePlanInfoTable> list = new ArrayList<>();
                                    list.add(temp_samplePlanInfoTable);
                                    map.put(typeoffoodselectedid[i],list);
                                }else{
                                    /*如果存在，则直接加在list中*/
                                    map.get(typeoffoodselectedid[i]).add(temp_samplePlanInfoTable);
                                }
                                break;
                            }
                        }
                    }
                }

        }
        /*当map为空时，说明抽检库为空或者抽检库中都没有该抽检类型，直接返回错误*/
        if(map == null ||  map.size() == 0){
            return ResultTool.fail(ResultCode.NO_FOOD_FOUND_IN_THE_LIBRARY);
        }
        /*为每个食品类型随机找到最终抽捡数目个抽检点*/
        for(int i=0;i<typeoffoodselectedid.length;i++){
            int finalAmount = finalAmounts[i];
            String key = typeoffoodselectedid[i];
            /*当key不存在或者 抽检点数量小于等于最终抽检数量时 跳过*/
            if(map.get(key) == null || map.get(key).size() <= finalAmount){
                continue;
            }else{
                int size = map.get(key).size();
                /*要从list中移除的数量*/
                int remaining = size - finalAmount;
                for(int j=0;j<remaining;j++){
                    int index = (int) (rand.nextDouble() * map.get(key).size());
                    map.get(key).remove(index);
                }
            }

        }

        /**
         3. 遍历一遍A，找到不同list中相同的商店，合并为一个，且抽检数量相加，得到最终的抽检点集合
           */
        for (int i = 0; i < typeoffoodselectedid.length-1; i++) {
            String key1 = typeoffoodselectedid[i];
            if(map.get(key1) == null){
                continue;
            }
            for (int j = i+1; j <typeoffoodselectedid.length ; j++) {
                String key2 = typeoffoodselectedid[j];
                if(map.get(key2) == null){
                    continue;
                }
                List<Temp_SamplePlanInfoTable> list1 = map.get(key1);
                List<Temp_SamplePlanInfoTable> list2 = map.get(key2);
                for (int k = 0; k < list1.size() ; k++) {
                    for (int l = 0; l < list2.size() ; l++) {
                        if(list1.get(k).getSamplingpointid() == list2.get(l).getSamplingpointid()){
                            list1.get(k).getSampleofoodlist().add(list2.get(l).getSampleofoodlist().get(0));
                            list2.remove(l);
                            if(list2.size() == 0){
                                map.remove(key2);
                            }
                        }
                    }
                }
            }
        }
        for (Map.Entry<String, List<Temp_SamplePlanInfoTable>> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue().toString());
        }
    /**4. 得到一个各个坐标点间的距离矩阵 dist[][]（不利用百度api，只利用原有的经纬度）*/

    /*从map中取出参与抽检的抽检点，存入新的List中*/
    List<Temp_SamplePlanInfoTable> samplingPoints = new ArrayList<>();
    /*先加入出发点到抽检计划中*/
    Temp_SamplePlanInfoTable start = new Temp_SamplePlanInfoTable(0,true,starting_point,Double.parseDouble(coordinate[0]),Double.parseDouble(coordinate[1]),null);
    samplingPoints.add(start);
    /*再加入要抽检的抽检点*/
    for(Map.Entry<String,List<Temp_SamplePlanInfoTable>> entry:map.entrySet()){
        for(int i=0;i<entry.getValue().size();i++){
            samplingPoints.add(entry.getValue().get(i));
        }
    }
    if(samplingPoints.size() <= selectedsamplingaccountid.length ){
        return ResultTool.fail(ResultCode.TOO_MANY_SAMPLING_ACCOUNTS);
    }
    double [][]dists = getdists(samplingPoints);
    /**     5. 将结果分为selectedsamplingaccountid.length份，每一份交给一个账号抽检（使用分组算法进行分组，保证分组的总的时间花费最少)*/
    int [][]groups = Grouping.grouping(dists,selectedsamplingaccountid.length,exitThreshold);
    for (int i = 0; i < groups.length; i++) {
            for (int j = 0; j < groups[i].length; j++)
                System.out.print("" + groups[i][j] + ", ");
            System.out.println();
        }
        return null;
    }







    /**根据抽检点的list获取距离矩阵*/
    public static double[][] getdists(List<Temp_SamplePlanInfoTable> samplingPoints){
        int len = samplingPoints.size();
        double dists[][] = new double[len][len];
        for (int i = 0; i < len ; i++) {
            double fromlng = samplingPoints.get(i).getLng();
            double fromlat = samplingPoints.get(i).getLat();
            for (int j = 0; j < len ; j++) {
                double tolng = samplingPoints.get(i).getLng();
                double tolat = samplingPoints.get(j).getLat();

                dists[i][j] = CoordinateToDistance.coordinateToDistance(fromlng,fromlat,tolng,tolat);
            }
        }
        return dists;
    }
}
