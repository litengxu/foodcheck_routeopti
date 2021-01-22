package com.bjfu.fcro.logic;

//修改使用的算法
public class MakePlan {

	public static int[] main(double[][] args) {
		double[][] dists;

		if (args != null) {
			dists = args;
		} else
			return null;

		System.out.println(
				MakePlan.class.getName().toString() + " | Length of received distance matrix is " + dists.length);

		// 穷举
		if (dists.length < 8) {
			int[] permutatePath = PermutateSearch.permutateSearch(dists);

			System.out.println(MakePlan.class.getName().toString() + " | PermutateSearch => "
					+ java.util.Arrays.toString(permutatePath));

			return permutatePath;
		}

		int[] greedyCombinePath = greedy(dists);
//
//		int[] gaCombinePath = genetic(dists);
//		
//		int[] ssaPath = ssa(dists,greedyCombinePath);
//		
//
//		double greedyTuneDist = BasicOperation.getDistFromPath(greedy(dists), dists);
//
//		double gaTuneSwapDist = BasicOperation.getDistFromPath(gaCombinePath, dists);
//		
//		double ssaDist = BasicOperation.getDistFromPath(ssaPath, dists);
		
//		System.out.println("贪心："+greedyTuneDist+",遗传:"+gaTuneSwapDist+",ssa:"+ssaDist);
		// TODO:遗传算法需调整至从0出发
		return greedyCombinePath;

		// 返回结果更优的解
		// return greedyTuneDist < gaTuneSwapDist ? greedyCombinePath :
		// gaCombinePath;

	}

	private static int[] greedy(double[][] dists) {

		// 得到贪婪算法后的路径
		int[] greedyPath = GreedySearch.greedySearch(dists);

		System.out.println(
				MakePlan.class.getName().toString() + " | GreedySearch => " + java.util.Arrays.toString(greedyPath));

		System.out.println();

		int[] greedyTunePath = TuneSearch.tuneSearch(greedyPath, dists);

		System.out.println(MakePlan.class.getName().toString() + " | GreedyTuneSearch => "
				+ java.util.Arrays.toString(greedyTunePath));

		System.out.println();

		int[] greedySwapTunePath = SwapSearch.swapSearch(greedyTunePath, dists);

		System.out.println(MakePlan.class.getName().toString() + " | GreedySwapTuneSearch => "
				+ java.util.Arrays.toString(greedySwapTunePath));

		System.out.println();

		return greedySwapTunePath;
	}

	//遗传算法
	private static int[] genetic(double[][] dists) {

		Chromosome ch = GaSearch.gaSearch(dists);

		int[] gaPath = ch.genes;

		int[] gaTunePath = TuneSearch.tuneSearch(gaPath, dists);

		int[] gaTuneSwapPath = SwapSearch.swapSearch(gaTunePath, dists);

		System.out.println(MakePlan.class.getName().toString() + " | GaTuneSwapSearch => "
				+ java.util.Arrays.toString(gaTuneSwapPath));

		System.out.println();

		return gaTuneSwapPath;
	}
	//麻雀算法
		private static int[] ssa(double[][] dists,int[]path) {

			Chromosome ssa = SSA.ssa(dists,path);
			int[] ssaPath = ssa.genes;
			System.out.println(MakePlan.class.getName().toString() + " |SSASearch => "
					+ java.util.Arrays.toString(ssaPath));

			System.out.println();

			return ssaPath;
		}
}