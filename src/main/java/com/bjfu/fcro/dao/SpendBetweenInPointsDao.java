package com.bjfu.fcro.dao;

import com.bjfu.fcro.entity.SysSpendBetweenInPoints;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SpendBetweenInPointsDao {


    @Select("select * from sys_spend_between_in_points")
    List<SysSpendBetweenInPoints> selectAll();

    @Insert("insert into sys_spend_between_in_points (admin_id,start_point,start_point_id,end_point,end_point_id," +
            "start_longitude,start_latitude,end_longitude,end_latitude,euclidean_distance,start_time,end_time," +
            "driving_time,driving_path,counted_times)" +
            "values(#{adminid},#{startPoint},#{startPointId},#{endPoint}," +
            "#{endPointId},#{startLongitude},#{startLatitude},#{endLongitude},#{endLatitude},#{euclideanDistance}" +
            ",#{startTime},#{endTime},#{drivingTime},#{drivingPath},#{countedTimes});")
    boolean insertNewData(@Param("adminid") int adminid,
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
                          @Param("endTime") String endTime,
                          @Param("countedTimes") int countedTimes);

    /**根据id更新*/
    @Update("UPDATE sys_spend_between_in_points set driving_time = #{drivingTime},driving_path = #{drivingPath},counted_times = #{countedTimes} where id = #{id}")
    Integer updateData(
            @Param("id") int id,
            @Param("drivingTime") String drivingTime,
            @Param("drivingPath") String drivingPath,
            @Param("countedTimes") int countedTimes);

}
