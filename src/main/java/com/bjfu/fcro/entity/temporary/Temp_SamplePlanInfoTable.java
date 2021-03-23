package com.bjfu.fcro.entity.temporary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp_SamplePlanInfoTable {

    /*抽检点在抽检库表中的id*/
    private int  samplingpointid;
    /*抽检点名字*/
    private  String samplingpoint;
    /*状态 true = 完成 false = 未完成*/
    private String state;
    /*地址*/
    private String address;
    /*经度*/
    private double lng;
    /*纬度*/
    private double lat;
    /*此抽检点要抽检的食品类型list*/
    private List<Temp_SampleFoodTable> sampleofoodlist;
    public String toString(){
        return JSONObject.fromObject(this).toString();
    }



    public static void main(String[] args) {
        Temp_SampleFoodTable temp_sampleFoodTable = new Temp_SampleFoodTable("1",1);
        Temp_SampleFoodTable temp_sampleFoodTable2 = new Temp_SampleFoodTable("2",2);
        Temp_SamplePlanInfoTable temp_samplePlanInfoTable = new Temp_SamplePlanInfoTable(1,"1","未完成","1",12.0,21.0,new ArrayList<>());
        temp_samplePlanInfoTable.getSampleofoodlist().add(temp_sampleFoodTable);
        temp_samplePlanInfoTable.getSampleofoodlist().add(temp_sampleFoodTable2);
        System.out.println(temp_samplePlanInfoTable.getSampleofoodlist().get(0).getFoodtype());
        System.out.println(temp_samplePlanInfoTable.getSampleofoodlist().get(1).getFoodtype());
    }
}
