package com.bjfu.fcro.service;

import com.bjfu.fcro.entity.SysSamplingFoodList;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Service
public interface SysSamplingFoodListService {

    Object selectAllBySspid(int pagesize_true, int pageindex_trueint ,int ssp_id);

    Object   addSamplingFood(
                                    String sampling_food_type,
                                    String food_specific_name,
                                    Integer food_counts,
                                    String sampler,
                                    String spot_check_location,
                                    String sampling_time,
                                    Integer ssp_id,
                                    Integer ssl_id,
                                    String remarks,
                                    MultipartFile[] uploadfiles) throws ParseException;
    Object selectImages(int id);
}
