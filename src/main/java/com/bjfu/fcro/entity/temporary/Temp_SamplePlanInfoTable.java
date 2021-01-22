package com.bjfu.fcro.entity.temporary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp_SamplePlanInfoTable {

    private int  samplingpointid;

    private boolean state;

    private String address;

    private double lng;

    private double lat;

    private List<Temp_SampleFoodTable> sampleofoodlist;





    public static void main(String[] args) {
        Temp_SampleFoodTable temp_sampleFoodTable = new Temp_SampleFoodTable("1",1);
        Temp_SampleFoodTable temp_sampleFoodTable2 = new Temp_SampleFoodTable("2",2);
        Temp_SamplePlanInfoTable temp_samplePlanInfoTable = new Temp_SamplePlanInfoTable(1,true,"1",12.0,21.0,new ArrayList<>());
        temp_samplePlanInfoTable.getSampleofoodlist().add(temp_sampleFoodTable);
        temp_samplePlanInfoTable.getSampleofoodlist().add(temp_sampleFoodTable2);
        System.out.println(temp_samplePlanInfoTable.getSampleofoodlist().get(0).getFoodtype());
        System.out.println(temp_samplePlanInfoTable.getSampleofoodlist().get(1).getFoodtype());
    }
}
