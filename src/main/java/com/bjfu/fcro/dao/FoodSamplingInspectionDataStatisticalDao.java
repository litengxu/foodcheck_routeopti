package com.bjfu.fcro.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
public interface FoodSamplingInspectionDataStatisticalDao {

    @Insert("INSERT INTO food_sampling_inspection_data_statistical SET province = #{province},food = #{food},sampling_month = #{sampling_month}," +
            "qualified_number = #{qualified_number} , total_sum = #{total_sum} , pass_rate = #{pass_rate},sql_content = #{sql_content},sql_content_qualified = #{sql_content_qualified};")
    boolean insertNewData(
            @Param("province") String province,
            @Param("food") String food,
            @Param("sampling_month") String sampling_month,
            @Param("qualified_number") int qualified_number,
            @Param("total_sum") int total_sum,
            @Param("pass_rate") String pass_rate,
            @Param("sql_content") String sql_content,
            @Param("sql_content_qualified") String sql_content_qualified
    );

    @Select("SELECT pass_rate from food_sampling_inspection_data_statistical WHERE province = #{province} AND food = #{food} and sampling_month = #{sampling_month};")
    String selectPassRate(
            @Param("province") String province,
            @Param("sampling_month") String sampling_month,
            @Param("food") String food
    );

    @Select("SELECT total_sum from food_sampling_inspection_data_statistical WHERE province = #{province} AND food = #{food} and sampling_month = #{sampling_month};")
    int selectTotalSum(
            @Param("province") String province,
            @Param("sampling_month") String sampling_month,
            @Param("food") String food
    );
}
