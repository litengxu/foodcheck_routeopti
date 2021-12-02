package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.utils.BaiduApiTool;
import com.bjfu.fcro.common.utils.CoordinateToDistance;
import com.bjfu.fcro.dao.SpendBetweenInPointsDao;
import com.bjfu.fcro.dao.SpendBetweenInPointsFlowDao;
import com.bjfu.fcro.entity.SysSamplingLibrary;
import com.bjfu.fcro.entity.SysSpendBetweenInPoints;
import com.bjfu.fcro.service.SysSpendBetweenInPointsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysSpendBetweenInPointsServiceimpl implements SysSpendBetweenInPointsService {

    static String period[][] = {
            {"8","10"},
            {"10","17"},
            {"17","20"},
            {"20","24"},
            {"0","8"}
    };
    protected static final Logger logger = LoggerFactory.getLogger(SysSpendBetweenInPointsServiceimpl.class);

    @Autowired
    private SpendBetweenInPointsDao spendBetweenInPointsDao;
    @Autowired
    private SpendBetweenInPointsFlowDao spendBetweenInPointsFlowDao;


    @Override
    public Object selectAll() {
        return spendBetweenInPointsDao.selectAll();
    }
    /**
     * 插入新的点到库内所有点的映射
     *
     * 异步去做
     * */
//    @Async("generallyTaskExecutor")
    public void InsertNewPoints(List<SysSamplingLibrary> oldList,SysSamplingLibrary newPoint,int adminid){
        for (int i = 0; i < oldList.size(); i++) {
            String startPoint = newPoint.getAddress();
            int startPointId = newPoint.getId();
            String endPoint = oldList.get(i).getAddress();
            int endPointId = oldList.get(i).getId();
            String startLongitude = newPoint.getLongitude();
            String startLatitude = newPoint.getLatitude();
            String endLongitude = oldList.get(i).getLongitude();
            String endLatitude = oldList.get(i).getLatitude();
            //欧式距离
            String euclideanDistance = Double.toString(CoordinateToDistance.coordinateToDistance(Double.parseDouble(startLongitude),Double.parseDouble(startLatitude),Double.parseDouble(endLongitude),Double.parseDouble(endLatitude)));
            //获取两地间距离和时间
            Map<String,String> origins = new HashMap<>();
            origins.put("lat",startLatitude);
            origins.put("lng",startLongitude);
            Map<String,String> destinations = new HashMap<>();
            destinations.put("lat",endLatitude);
            destinations.put("lng",endLongitude);
            Double pathAndTime[] = BaiduApiTool.getDrivingdistanceAndTime(origins,destinations);
            if(pathAndTime[0] == 0.0 && pathAndTime[1] == 0.0){
                logger.debug(startPointId+"至"+endPointId+"获取距离和时间失败");
            }
            String drivingTime = Double.toString(pathAndTime[1]);
            String drivingPath = Double.toString(pathAndTime[0]);

            int countedTimes = 0;
            for (int j = 0; j < period.length; j++) {
                String startTime = period[j][0];
                String endTime = period[j][1];
                spendBetweenInPointsDao.insertNewData(adminid,startPoint,startPointId,endPoint,endPointId,startLongitude,
                        startLatitude,endLongitude,endLatitude,euclideanDistance,drivingTime,drivingPath,startTime,endTime,
                        countedTimes);

            }
        }
    }


    /**
     * 定时任务需要调用的方法
     * */

    public void scheduleTask(int hash,String startTime,String endTime) {

        List<SysSpendBetweenInPoints> List = spendBetweenInPointsDao.selectAll();
        for (int i = 0; i < List.size(); i++) {
            SysSpendBetweenInPoints sysSpendBetweenInPoints = List.get(i);

            if(sysSpendBetweenInPoints.getId() % 3 == hash && startTime.equals(sysSpendBetweenInPoints.getStart_time()) && endTime.equals(sysSpendBetweenInPoints.getEnd_time())){
                String startLongitude = sysSpendBetweenInPoints.getStart_longitude();
                String startLatitude = sysSpendBetweenInPoints.getStart_latitude();
                String endLongitude = sysSpendBetweenInPoints.getEnd_longitude();
                String endLatitude = sysSpendBetweenInPoints.getEnd_latitude();
                Map<String,String> origins = new HashMap<>();
                origins.put("lat",startLatitude);
                origins.put("lng",startLongitude);
                Map<String,String> destinations = new HashMap<>();
                destinations.put("lat",endLatitude);
                destinations.put("lng",endLongitude);
                Double pathAndTime[] = BaiduApiTool.getDrivingdistanceAndTime(origins,destinations);
                if(pathAndTime[0] == 0.0 && pathAndTime[1] == 0.0){
                    logger.debug(sysSpendBetweenInPoints.getStart_point_id()+"至"+ sysSpendBetweenInPoints.getEnd_point_id()+"获取距离和时间失败");
                    continue;
                }
                String newDrivingTime = Double.toString(pathAndTime[1]);
                String newDrivingPath = Double.toString(pathAndTime[0]);
                int oldCountedTimes = sysSpendBetweenInPoints.getCounted_times();
                String oldDrivingTime = sysSpendBetweenInPoints.getDriving_time();
                String oldDrivingPath = sysSpendBetweenInPoints.getDriving_path();
                int newCountedTimes = oldCountedTimes+1;
                String drivingTime = String.valueOf((Double.parseDouble(oldDrivingTime)*oldCountedTimes+Double.parseDouble(newDrivingTime))/newCountedTimes);
                String drivingPath = String.valueOf((Double.parseDouble(oldDrivingPath)*oldCountedTimes+Double.parseDouble(newDrivingPath))/newCountedTimes);
                try{
                    /**设置休眠，因为百度api有并发限制*/
                    Thread.sleep(500);
                    //插入新数据到映射表
                    spendBetweenInPointsDao.updateData(sysSpendBetweenInPoints.getId(), drivingTime,drivingPath,newCountedTimes);
                    //插入数据到liushuibiao
                    spendBetweenInPointsFlowDao.insertNewData(sysSpendBetweenInPoints.getAdmin_id(), sysSpendBetweenInPoints.getId(), sysSpendBetweenInPoints.getStart_point(),
                            sysSpendBetweenInPoints.getStart_point_id(),sysSpendBetweenInPoints.getEnd_point(), sysSpendBetweenInPoints.getEnd_point_id(),
                            sysSpendBetweenInPoints.getStart_longitude(),sysSpendBetweenInPoints.getStart_latitude(),sysSpendBetweenInPoints.getEnd_longitude(),
                            sysSpendBetweenInPoints.getEnd_latitude(),sysSpendBetweenInPoints.getEuclidean_distance(),newDrivingTime,newDrivingPath,
                            sysSpendBetweenInPoints.getStart_time(),sysSpendBetweenInPoints.getEnd_time());
                }catch (Exception e){
                    logger.error(sysSpendBetweenInPoints.getId()+"更新数据出错");
                }

            }
        }
    }

}
