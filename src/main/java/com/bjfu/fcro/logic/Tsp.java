package com.bjfu.fcro.logic;

/*
 * �����������ۺ��ֱ࣬�ӵ������еķ������ɡ�
 */
public class Tsp {
	/*
	 * �ѷ����ۺ�����������ѡ��
	 */
	public static int[] tspSyn(double[][] dists){
		if (dists.length < 8){ //С����������ʹ�ø÷���
			int[] permutatePath = PermutateSearch.permutateSearch(dists);
			return permutatePath;
		}
		// ��1�෽���ۺϣ�
		int[] greedyPath = GreedySearch.greedySearch(dists);
		int[] greedySwapTunePath = SwapSearch.swapSearch(greedyPath, dists);
		int[] greedyTunePath = TuneSearch.tuneSearch(greedySwapTunePath, dists);

		double greedyTuneDist = BasicOperation.getDistFromPath(greedyTunePath, dists);
		
		// ��2�෽���ۺϣ�
		Chromosome ch = GaSearch.gaSearch(dists);
		int[] gaPath = ch.genes;
		int[] gaTunePath = TuneSearch.tuneSearch(gaPath, dists);
		int[] gaTuneSwapPath = SwapSearch.swapSearch(gaTunePath, dists);
		double gaTuneSwapDist = BasicOperation.getDistFromPath(gaTuneSwapPath, dists);
		
		// ���ţ�
		if (greedyTuneDist < gaTuneSwapDist)
			return greedyTunePath;
		else
			return gaTuneSwapPath;
		
	}
}