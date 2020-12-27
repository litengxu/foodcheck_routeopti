package com.bjfu.fcro.service;


import com.bjfu.fcro.entity.SysSamplingFoodType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysSamplingFoodTypeService {

    String selecttypename( int id);

    List<SysSamplingFoodType> findalltypebyadminid(String adminaccount);

}
