package com.bjfu.fcro.common.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**输入两个坐标的经纬度，输出距离*/
public class CoordinateToDistance {

    public static void main(String[] args) {
        coordinateToDistance(121.717594,31.12055,121.817629,31.090867);
    }

    public static double coordinateToDistance(double fromlng,double fromlat,double tolng,double tolat){
        GlobalCoordinates source = new GlobalCoordinates(fromlat, fromlng);
        GlobalCoordinates target = new GlobalCoordinates(tolat, tolng);
        double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
//        double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);

        return meter1;
    }

    public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
