package com.bjfu.fcro.common.utils;

import com.bjfu.fcro.controller.SysSamplingLibraryController;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaiduApiTool {
    static String AK = "svq1mQqPx1vrCaI5r6GiId5pHj9bQ03G"; // 百度地图密钥
    protected static final Logger logger = LoggerFactory.getLogger(BaiduApiTool.class);

    /*根据url 通过http获取返回值*/
    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {} catch (IOException e) {}
        return json.toString();
    }

    // 调用百度地图API根据地址，获取坐标
    public static String[] getCoordinate(String address) {
        if (address != null && !"".equals(address)) {
//            http://api.map.baidu.com/geocoding/v3/?address=北京市海淀区上地十街10号&output=json&ak=您的ak&callback=showLocation
            address = address.replaceAll("\\s*", "").replace("#", "栋");
            String url = " http://api.map.baidu.com/geocoding/v3/?address=" + address + "&output=json&ak=" + AK;
            String json = loadJSON(url);
            String res[]  = new String[2];
            if (json != null && !"".equals(json)) {
                JSONObject obj = JSONObject.fromObject(json);
                if ("0".equals(obj.getString("status"))) {
                    double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng"); // 经度
                    double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat"); // 纬度
                    DecimalFormat df = new DecimalFormat("#.######");
                    res[0] = df.format(lng);
                    res[1] = df.format(lat);
                    return res;
                }
            }
        }
        return null;
    }

    //根据经纬度获取两地间的行车距离和时间
    public static Double[] getDrivingdistanceAndTime(Map<String, String> origins, Map<String, String> destinations){
        String url = "https://api.map.baidu.com/routematrix/v2/driving?output=json&origins="+origins.get("lat")
                +","+origins.get("lng")+"&destinations="+destinations.get("lat")+","+destinations.get("lng")+
                "&ak="+AK+"&tactics=12";
        String json = loadJSON(url);
        System.out.println("return json:" + json);
        Double res[]  = new Double[2];
        if (json != null && !"".equals(json)) {
            JSONObject obj = JSONObject.fromObject(json);
            if ("0".equals(obj.getString("status"))) {
                double distance = obj.getJSONArray("result").getJSONObject(0).
                        getJSONObject("distance").getDouble("value"); // 经度
                double time = obj.getJSONArray("result").getJSONObject(0).
                        getJSONObject("duration").getDouble("value"); // 纬度
                res[0] = distance;
                res[1] = time;
                return res;
            }else{
                logger.info(origins.toString()+destinations.toString()+"获取行车距离和时间失败");
            }
        }
        return new Double[]{0.0,0.0};

    }

    public static void main(String[] args) {
        Map<String,String> origins = new HashMap<>();
        Map<String,String> destinations = new HashMap<>();
        origins.put("lat","30.223564");
        origins.put("lng","120.17427");
        destinations.put("lat","30.32432");
        destinations.put("lng","120.20212");
        System.out.println(origins.toString());
        System.out.println(Arrays.toString(getDrivingdistanceAndTime(origins,destinations)));
    }

}
