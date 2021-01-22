package com.bjfu.fcro.logic;

/*
 * 这是用于旅行商问题的遗传算法
 */
public class GaSearch {
	public static int PoolSize = 100; // 群体大小
	public static int SeaSize = 200; // 样本容量
	public static int EliteSize = 10; // 顶层个体数量
	public static final double MutateRate = 0.05; // 突变率
	public static int GenerationNum = 500; // 执行代数
	public static final double PowerRate = 1.25; // 为了让更优秀的个体产生更多的后代，用这个幂值产生倾向性

	public static Chromosome gaSearch(double[][] dists) {
		// 获取矩阵长度（即基因序列长度）
		int n = dists.length;
		// 声明群体
		Chromosome[] pool = new Chromosome[PoolSize];
		// 声明样本
		Chromosome[] sea = new Chromosome[SeaSize];
		// 初始化群体基因序列
		for (int i = 0; i < PoolSize; i++)
			pool[i] = new Chromosome(n);
		// 设置停止条件（即循环繁殖GenerationNum代以后停止）

		GenerationNum = GenerationNum < 500 ? 500 : GenerationNum;

		for (int i = 0; i < GenerationNum; i++) {
			// 从群体中获取顶层个体作为优胜个体
			for (int j = 0; j < EliteSize; j++)
				sea[j] = pool[j].clone();
			// 根据再生算子的比例进行繁殖
			for (int j = EliteSize; j < SeaSize; j++) {
				// 为了让更优秀的个体产生更多的后代，用这个幂值产生倾向性
				double rand = Math.random();
				double randPowered = Math.pow(rand, PowerRate);
				int pos = (int) (randPowered * PoolSize);
				sea[j] = pool[pos].clone();
			}
			// 改变数量
			int changeSize = SeaSize - EliteSize;
			// 需要进行交叉的数量
			int pairSize = changeSize / 2;
			// 开始进行染色体交叉
			for (int j = 0; j < pairSize; j++) {
				// 按照顺序除去顶层外的每俩个体一组依次进行交叉
				int posPaternal = EliteSize + j * 2;
				int posMaternal = posPaternal + 1;
				// 进行交叉
				sea[posPaternal].cross(sea[posMaternal]);
			}
			// 根据突变率计算该种群中的突变数量
			int mutateSize = (int) (changeSize * MutateRate);
			// 进行突变
			for (int j = 0; j < mutateSize; j++) {
				// 除去顶层的个体随机进行突变
				int pos = (int) (Math.random() * changeSize) + EliteSize;
				// 染色体突变
				sea[pos].mutate();
			}
			// 计算个体适应度
			for (int j = EliteSize; j < SeaSize; j++)
				sea[j].getFitness(dists);
			// 根据个体适应度进行排序
			java.util.Arrays.sort(sea);
			// 取前PoolSize的个体作为下一代种群
			for (int j = 0; j < PoolSize; j++)
				pool[j] = sea[j].clone();
		}
		// 返回最优个体
		return pool[0];
	}
}