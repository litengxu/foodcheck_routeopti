package com.bjfu.fcro.algorithm;

import java.util.Arrays;

/**
 *
 * 不同时间段下的距离矩阵
 * */
public class DistInDifTime {

    double dists[][]; //距离花费矩阵 = 实际路上的距离

    double times[][]; //时间花费矩阵
    double speeds[][]; //速度矩阵
    double startTime;
    double endTime;
    public DistInDifTime(double [][]times,double startTime,double endTime){
        this.times = times;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public DistInDifTime(double [][]dists,double times[][],double startTime,double endTime){
        this.dists = dists;

        this.times = times;
        this.speeds = new double[dists.length][dists[0].length];
        this.endTime = endTime;
        this.startTime = startTime;
        for (int i = 0; i < dists.length; i++) {
            for (int j = 0; j < dists[i].length; j++) {
                if (times[i][j] == 0) {
                    this.speeds[i][j] = 0;
                } else {
                    this.speeds[i][j] = dists[i][j] / times[i][j];

                }
            }
        }
    }
    public double[][] getDists(){
        return dists;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public double[][] getTimes() {
        return times;
    }

    public double[][] getSpeeds() {
        return speeds;
    }

    @Override
    public String toString() {
        return "DistInDifTime{" +
                "dists=" + Arrays.toString(dists) +
                ", times=" + Arrays.toString(times) +
                ", speeds=" + Arrays.toString(speeds) +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }



}
