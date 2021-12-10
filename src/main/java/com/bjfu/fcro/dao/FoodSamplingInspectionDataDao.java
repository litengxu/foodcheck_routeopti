package com.bjfu.fcro.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface FoodSamplingInspectionDataDao {
    @Update("UPDATE food_sampling_inspection_data SET province = #{newProvince} WHERE province = #{oldProvince};")
    int updateProvince( @Param("oldProvince") String oldProvince,
                        @Param("newProvince") String newProvince);

    @Update("UPDATE food_sampling_inspection_data SET food = #{newFood} WHERE food = #{oldFood};")
    int updateFood( @Param("newFood") String newFood,
                        @Param("oldFood") String oldFood);

    @Select("SELECT COUNT(id) FROM food_sampling_inspection_data  WHERE food = #{oldFood};")
    int selectFood(@Param("oldFood") String oldFood);

    @Select("SELECT COUNT(id) FROM food_sampling_inspection_data  WHERE province = #{oldProvince};")
    int selectProvince(@Param("oldProvince") String oldProvince);


    @Select("SELECT count(1) FROM food_sampling_inspection_data WHERE province = #{province} and food = #{food} and the_sampling_time  like #{the_sampling_time};")
    int selectSamplingNumber(@Param("province") String province,
                             @Param("food") String food,
                             @Param("the_sampling_time") String the_sampling_time);
    @Select("SELECT count(1) FROM food_sampling_inspection_data WHERE province = #{province} and food = #{food}and conclusion = '纯抽检合格样品' and the_sampling_time  like #{the_sampling_time};")
    int selectQualifiedSamplingNumber(@Param("province") String province,
                             @Param("food") String food,
                             @Param("the_sampling_time") String the_sampling_time);

}
