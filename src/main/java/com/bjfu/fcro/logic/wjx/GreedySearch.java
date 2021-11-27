package com.bjfu.fcro.logic.wjx;

/*
 * 贪心搜索，高效且解的质量比较高
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
				if(dists[from][j] == min && dists[j][0]>dists[to][0]) {//相等时，贪心留下一个离初始点近的
					to = j;
				}
			}
			flags[to] = true;
			marks[i] = to;
		}
		return marks;
	}
}