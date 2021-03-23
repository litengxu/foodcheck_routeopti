package com.bjfu.fcro.entity.temporary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.json.JSONObject;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temp_SampleFoodTable {

        private  String foodtype;

        private int count;
        public String toString(){
                return JSONObject.fromObject(this).toString();
        }
}
