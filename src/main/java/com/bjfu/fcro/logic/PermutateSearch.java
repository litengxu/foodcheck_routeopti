package com.bjfu.fcro.logic;

/*
 * �������
 */
public class PermutateSearch {
	public static int[] permutateSearch(double[][] dists){
		int n = dists.length;
		Permutate permutate = new Permutate(n, n);
		int[] bestPath = new int[n];
		double minDistSum = Double.POSITIVE_INFINITY;
		while( permutate.step() ){
			int[] marks = permutate.getCurrMarkRef();
			double currDistSum = BasicOperation.getDistFromPath(marks, dists);
			if (currDistSum < minDistSum){
				minDistSum = currDistSum;
				for (int j=0; j<n; j++)
					bestPath[j] = marks[j];
			}
		}
		return bestPath;
	}
}