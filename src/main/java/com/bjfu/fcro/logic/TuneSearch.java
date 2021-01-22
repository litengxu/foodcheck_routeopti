package com.bjfu.fcro.logic;

/*
 * �ֲ���������
 */
public class TuneSearch {
    public static int[] tuneSearch(int[] initPath, double[][] dists) {
        int n = dists.length;
        double initValue = BasicOperation.getDistFromPath(initPath, dists);
        double bestValue = initValue;
        int[] bestPath = initPath.clone();
        boolean flagImproved = true;
        while (flagImproved) {
            flagImproved = false;
            for (int i = 1; i < n - 1; i++) {
                int[] tempPath = bestPath.clone();
                int next = (i + 1) % n;
                int temp = tempPath[i];
                tempPath[i] = tempPath[next];
                tempPath[next] = temp;
                double tempValue = BasicOperation.getDistFromPath(tempPath, dists);
                if (tempValue < bestValue) {
                    bestValue = tempValue;
                    bestPath = tempPath;
                    flagImproved = true;
                    break;
                }
            }
        }
        return bestPath;
    }
}