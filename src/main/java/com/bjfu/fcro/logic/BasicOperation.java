package com.bjfu.fcro.logic;

public class BasicOperation {
	/*
	 * ������������dists���һ��·��path�������··���ܳ���
	 */
	public static double getDistFromPath(int[] path, double[][] dists){
		int n = path.length;
		double distSum = 0.0;
		for (int i=0; i<n-1; i++){
			distSum += dists[path[i]][path[i+1]];
		}
		distSum += dists[path[n-1]][path[0]];
		return distSum;
	}
}