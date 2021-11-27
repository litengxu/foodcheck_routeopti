package com.bjfu.fcro.logic.wjx;

/*
 * 旅行商问题综合类，直接调用其中的方法即可。
 */
public class Tsp {
	/*
	 * 把方法综合起来，并优选。
	 */
	public static int[] tspSyn(double[][] dists){
		if (dists.length < 8){ //小数据量优先使用该方法
			int[] permutatePath = PermutateSearch.permutateSearch(dists);
			return permutatePath;
		}
		// 第1类方法综合：
		int[] greedyPath = GreedySearch.greedySearch(dists);
		int[] greedyTunePath = TuneSearch.tuneSearch(greedyPath, dists);
		int[] greedySwapTunePath = SwapSearch.swapSearch(greedyTunePath, dists);
		double greedyTuneSwapDist = BasicOperation.getDistFromPath(greedySwapTunePath, dists);
		

		// 第2类方法综合：
		Chromosome ch = GaSearch.gaSearch(dists,greedyPath);
		int[] gaPath = ch.genes;
		int[] gaTunePath = TuneSearch.tuneSearch(gaPath, dists);
		int[] gaTuneSwapPath = SwapSearch.swapSearch(gaTunePath, dists);
		double gaTuneSwapDist = BasicOperation.getDistFromPath(gaTuneSwapPath, dists);
		
		// 择优：
		if (greedyTuneSwapDist < gaTuneSwapDist) {
			System.out.println("贪心");
			return greedySwapTunePath;
		}
		else {
			System.out.println("遗传");
			return gaTuneSwapPath;
		}
		
	}
}