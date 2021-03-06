package com.bjfu.fcro.logic;

/*
 * �Ի�����
 */
public class SwapSearch {
	public static final int SwapNum = 100000;

	public static int[] swapSearch(int[] initPath, double[][] dists) {
		int n = dists.length;
		double initValue = BasicOperation.getDistFromPath(initPath, dists);
		double bestValue = initValue;
		int[] bestPath = initPath.clone();
		for (int i = 0; i < SwapNum; i++) {
			int[] tempPath = bestPath.clone();
			int pos1 = (int) (Math.random() * (n - 1) + 1);
			int pos2 = (int) (Math.random() * (n - 1) + 1);
			if (pos1 == pos2)
				continue;
			int temp = tempPath[pos1];
			tempPath[pos1] = tempPath[pos2];
			tempPath[pos2] = temp;
			double tempValue = BasicOperation.getDistFromPath(tempPath, dists);
			if (tempValue >= bestValue)
				continue;
			bestValue = tempValue;
			bestPath = tempPath;
		}

		return bestPath;
	}
}