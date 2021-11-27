package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSpendBetweenInPointsFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SpendBetweenInPointsFlowDao {


    @Select("select * from sys_spend_between_in_points_flow")
    List<SysSpendBetweenInPointsFlow> selectAll();

    @Insert("insert into sys_spend_between_in_points_flow (admin_id,flow_id,start_point,start_point_id,end_point,end_point_id," +
            "start_longitude,start_latitude,end_longitude,end_latitude,euclidean_distance,start_time,end_time," +
            "driving_time,driving_path)" +
            "values(#{adminid},#{flowid},#{startPoint},#{startPointId},#{endPoint}," +
            "#{endPointId},#{startLongitude},#{startLatitude},#{endLongitude},#{endLatitude},#{euclideanDistance}" +
            ",#{startTime},#{endTime},#{drivingTime},#{drivingPath});")
    boolean insertNewData(@Param("adminid") int adminid,
                          @Param("flowid") int flowid,
                          @Param("startPoint") String startPoint,
                          @Param("startPointId") int startPointId,
                          @Param("endPoint") String endPoint,
                          @Param("endPointId") int endPointId,
                          @Param("startLongitude") String startLongitude,
                          @Param("startLatitude") String startLatitude,
                          @Param("endLongitude") String endLongitude,
                          @Param("endLatitude") String endLatitude,
                          @Param("euclideanDistance") String euclideanDistance,
                          @Param("drivingTime") String drivingTime,
                          @Param("drivingPath") String drivingPath,
                          @Param("startTime") String startTime,
                          @Param("endTime") String endTime
                          );
}
