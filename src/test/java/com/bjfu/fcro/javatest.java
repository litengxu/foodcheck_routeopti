package com.bjfu.fcro;




import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.*;

public class javatest {
    static String AK = "afzx0PGdTM3wlK9WrLqY3QhOdEhWr3Iz"; // 百度地图密钥
    static class A {
        public void print(){
            System.out.println("a");
        }
    }
    static  class B extends A{
        public void print(){
            System.out.println("b");
        }
    }
    public static void main(String[] args) {
//        String dom = "北京王府井";
//        String coordinate[] = getCoordinate(dom);
//        System.out.println("'" + dom + "'的经纬度为：" + coordinate[0]+"  "+coordinate[1]);
//        int groups[] = {3,5,4};
//        int path[] =  {0,1,3,2};
//
//        int temp[] = Arrays.copyOf(groups,groups.length);
//        for (int j = 1; j < path.length; j++) {
//            groups[j-1] = temp[path[j] - 1];
//        }
//        System.out.println(Arrays.toString(groups));
        Integer a = 127; Integer b = 127;
        Integer a2 = 128; Integer b2 = 128;
        System.out.println(a == b);
        System.out.println(a2 == b2);
    }
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
            System.out.println(json);
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
