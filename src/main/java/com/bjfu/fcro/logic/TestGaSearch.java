package com.bjfu.fcro.logic;

import java.util.Scanner;

/*
 * 这是用于旅行商问题的遗传算法
 */
public class TestGaSearch {
	public static int PoolSize = 100; // 群体大小
	public static int SeaSize = 200; // 样本容量
	public static int EliteSize = 10; // 顶层个体数量
	public static double MutateRate = 0.05; // 突变率
	public static int GenerationNum = 500; // 执行代数
	public static double PowerRate = 1.25; // 为了让更优秀的个体产生更多的后代，用这个幂值产生倾向性

	public static void main(String[] path) {

		boolean isCountinue = true;

		double[][] points = readPoints(path[0]);
		double[][] dists = getDists(points);

		System.out.println("遗传算法实验：");

		Scanner scanner = new Scanner(System.in);

		while (isCountinue) {

			System.out.println("请输入门店数量：");

			PoolSize = scanner.nextInt();

			double[][] testDists;

			if (PoolSize == dists.length)
				testDists = dists;
			else
				testDists = new double[PoolSize][PoolSize];

			for (int i = 0; i < PoolSize; i++)
				for (int j = 0; j < PoolSize; j++)
					testDists[i][j] = dists[i][j];

			System.out.println("请输入种群规模：");

			SeaSize = scanner.nextInt();

			System.out.println("请输入运行代数：");

			GenerationNum = scanner.nextInt();

			System.out.println("请输入交叉概率:");

			PowerRate = scanner.nextDouble();

			System.out.println("请输入突变概率:");

			MutateRate = scanner.nextDouble();

			// 开始计时
			long executeDuration = System.nanoTime();

			EliteSize = PoolSize <= EliteSize ? PoolSize : 10;

			int[] bestPath = gaSearch(testDists).genes;
			
			executeDuration = System.nanoTime() - executeDuration;

			System.out.println("\n测试耗时 ===> " + executeDuration + "ns | " + executeDuration / Math.pow(10, 6) + "ms");

			System.out.println("\n最优路径 ===> " + java.util.Arrays.toString(bestPath));

			System.out.println("\n路程总长 ===> " + BasicOperation.getDistFromPath(bestPath, testDists));

			if (testDists.length < 9){
				
				Dynamic dynamic = new Dynamic(testDists);
				
				System.out.println("\n动态规划算法  ===> " + dynamic.getMin());
				
				System.out.println("\n耗时 ===> " + dynamic.getGuihuaTime());
			}
		}

		scanner.close();
	}

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

	public static double[][] readPoints(String path) {
		String content = Tools.readFileText(path);
		content = content.trim();
		String[] strLines = content.split("\n");
		int n = strLines.length;
		double[][] points = new double[n][2];
		for (int i = 0; i < n; i++) {
			String[] subs = strLines[i].trim().split(" ");
			points[i][0] = Double.parseDouble(subs[0]);
			points[i][1] = Double.parseDouble(subs[1]);
		}
		return points;
	}

	public static double[][] getDists(double[][] points) {
		int n = points.length;
		double[] x = new double[n];
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			x[i] = points[i][0];
			y[i] = points[i][1];
		}
		double[][] dists = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double xDiff = x[i] - x[j];
				double yDiff = y[i] - y[j];
				dists[i][j] = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			}
		}
		return dists;
	}

}