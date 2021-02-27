package com.bjfu.fcro.entity.temporary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
/**
 * @Program: foodcheck_routeopti
 * @ClassName: Temp_Task
 * @Author: 李腾旭
 * @Date: 2021-01-28 15:20
 * @Description: ${todo}
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp_Task {
////    任务id，同表中的id相同
//    private int id;
//    出发点
    private String startPoint;
//  出发点经度
    private String startPoint_lng;
//  出发点纬度
    private String startPoint_lat;
//    任务生成时间
    private String createTimeStamp;
//    任务完成时间
    private String finishTimeStamp;
//    要抽检的所有地址信息
    private String address[];
//    要抽检的所有食品种类
    private String Foodtypes[];
//    该抽检计划中的所有分组
    private List<Temp_Group> groupList;
//    状态是否完成
    private boolean state;
}
