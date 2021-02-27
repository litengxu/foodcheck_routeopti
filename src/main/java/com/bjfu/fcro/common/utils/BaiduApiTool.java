package com.bjfu.fcro.common.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class BaiduApiTool {
    static String AK = "svq1mQqPx1vrCaI5r6GiId5pHj9bQ03G"; // 百度地图密钥

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

}
