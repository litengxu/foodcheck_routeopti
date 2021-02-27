package com.bjfu.fcro.entity.temporary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
/**
 * @Program: foodcheck_routeopti
 * @ClassName: Temp_Group
 * @Author: 李腾旭
 * @Date: 2021-01-28 15:10
 * @Description: 抽检分组用的临时表
 * @Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp_Group {
//   分组id
    private int id;
//    任务接收时间
    private String acceptTimeStamp;
//    任务完成时间
    private String finishTimeStamp;
//    分组任务完成状态
    private boolean state;
//    参与该抽检分组id
    private int accountid;
//    参与该抽年分组账号名
    private String account;
//    参与的抽检员姓名
    private String names;
//    抽检顺序，按Task中地址的索引排序
    private int samplingPointIndex[];
//    抽检顺序，里面是抽检点的ids
    private int samplingPointIds[];
//    所包含的抽检计划List
    private List<Temp_SamplePlanInfoTable> samplePlanInfoTableList;

}
