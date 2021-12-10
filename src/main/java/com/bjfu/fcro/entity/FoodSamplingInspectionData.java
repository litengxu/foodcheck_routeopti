package com.bjfu.fcro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "food_sampling_inspection_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodSamplingInspectionData {

    @Id
    private Integer id;

    String name_of_unit_to_be_sampled;
    String sampling_locations2;
    String province;
    String city;
    String county;
    String regional_types;
    String sample_name;
    String sampling_number;
    String food;
    String food_subcategory;
    String fine_food_class;
    String the_sample_size;
    String logo_production_enterprise_name;
    String logo_production_enterprise_provinces;
    String whether_to_mport;
    String country_of_origin;
    String the_sampling_time;
    String inspection_agency_name;
    String inspection_agency_province;
    String conclusion;
    String inspection_items;
    String the_inspection_results;
    String results_the_unit;
    String the_results_determine;
    String maximum_permissible_limit;
    String maximum_allowable_unit;
    String sampling_locations1;
    String logo_production_enterprises;
    String logo_production_enterprises_county;
    String net_platform_home_province;
    String network_name;

    //创建时间
    private Date create_time;
    //修改时间
    private Date last_update_time;
    //保留字段
    private String sepp_reserved_field1;
    private String sepp_reserved_field2;
    private String sepp_reserved_field3;





}
