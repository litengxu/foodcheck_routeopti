package com.bjfu.fcro.algorithm;

/**
 * dists 各个间的花费
 *
 * 贪心算法获取路径
 */
public class GreedySearch {
	public static int[] greedySearch(double[][] dists){
		int n = dists.length;
		boolean[] flags = new boolean[n];
		flags[0] = true;
		int[] marks = new int[n];
		marks[0] = 0;
		for (int i=1; i<n; i++){
			int to = -1;
			int from = marks[i-1];
			double min = Double.POSITIVE_INFINITY;
			for (int j=0; j<n; j++){
				if (flags[j])
					continue;
				if (j == from)
					continue;
				if (dists[from][j] < min){
					min = dists[from][j];
					to = j;
				}
			}
			flags[to] = true;
			marks[i] = to;
		}
		return marks;
	}
}