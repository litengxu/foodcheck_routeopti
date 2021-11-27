package com.bjfu.fcro.algorithm;

import java.util.ArrayList;

/**
 * 这个类的主旨是把若干个任务分成大致相等的几份，使得每一份由一个或一队抽检员完成。
 * 目标是所有抽检员的费用消耗之和最小。
 */
public class Grouping {
	public static java.util.Random rand = new java.util.Random(47);

	public static void main(String[] args) {
//		testGrouping();
/*
10*
9 *	11 	8	9	  5
8 *            12
7 *
6 * 		4
5 *       6
4 * 	3     7
3 *     2
2 * 1     10
1 *
0 * * * * * * * * * * * * *
  0 1 2 3 4 5 6 7 8 9 10
* */
		ArrayList<double[]> dataSet=new ArrayList<double[]>();
		dataSet.add(new double[]{1,2});//1
		dataSet.add(new double[]{3,3});//2
		dataSet.add(new double[]{3,4});//3
		dataSet.add(new double[]{5,6});//4
		dataSet.add(new double[]{8,9});//5
		dataSet.add(new double[]{4,5});//6
		dataSet.add(new double[]{6,4});//7
		dataSet.add(new double[]{3,9});//8
		dataSet.add(new double[]{5,9});//9
		dataSet.add(new double[]{4,2});//10
		dataSet.add(new double[]{1,9});//11
		dataSet.add(new double[]{7,8});//12
		int [][]groups = grouping_kmeans(dataSet,5);
		for (int i = 0; i < groups.length; i++) {
			for (int j = 0; j < groups[i].length; j++)
				System.out.print("" + groups[i][j] + ", ");
			System.out.println();
		}

	}

	public static void testGrouping() {
		/*
		 * 构造一个二维平面上的序列：(0) 在坐标原点，(1)的坐标是(1, 0)，...... (8)的坐标是(1, -1)
		 * 
		 * (6) (2) (5)
		 * 
		 * 
		 * 
		 * (3) (0) (1)
		 * 
		 * 
		 * 
		 * 
		 * (7) (4) (8)
		 * 
		 */
		// 以下是9个点的横坐标和纵坐标值：
		double[] X = { 0, 1, 0, -1, 0, 1, -1, -1, 1 };
		double[] Y = { 0, 0, 1, 0, -1, 1, 1, -1, -1 };
		// 计算距离矩阵dists:
		double[][] dists = new double[X.length][X.length];
		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < X.length; j++) {
				dists[i][j] = Math.sqrt((X[i] - X[j]) * (X[i] - X[j]) + (Y[i] - Y[j]) * (Y[i] - Y[j]));
			}
		}
		// 打印距离矩阵dists:
		for (int i = 0; i < X.length; i++) {
			for (int j = 0; j < Y.length; j++) {
				System.out.print("" + dists[i][j] + ",  ");
			}
			System.out.println();
		}
		System.out.println();

		// 调用函数grouping(double[][], int, int)，查看分组结果是否合理：
		int[][] groups = grouping(dists, 6, X.length*X.length/2);
		for (int i = 0; i < groups.length; i++) {
			for (int j = 0; j < groups[i].length; j++)
				System.out.print("" + groups[i][j] + ", ");
			System.out.println();
		}
		/*
		 * 分组结果是： 1, 2, 4, 8 3, 5, 6, 7 分组结果合理。
		 */
	}

	/*
	 * 本方法要把所有要遍历的地点（共有siteAmount个）分为groupAmount份，每一份是一组。
	 * 分割之后，每一组的序号构成一个数组，所有的数组合成一个二维数组返回。
	 * 需要注意的是：一共siteAmount个地点中，序号从1开始，而不是从0开始，因为0预留给了共同的出发点。 要求：每一组的数量尽量均匀。
	 */
	private static int[][] splitInitSites(int siteAmount, int groupAmount) {
		// 标识每组的地点个数
		int[] counts = new int[groupAmount];
		// 每组平均地点个数（取整）
		int quotient = siteAmount / groupAmount;
		// 得到未被均分的剩余地点个数
		int residue = siteAmount % groupAmount;
		// 按照分组顺序依次加入剩余地点，直至所有地点全部分完
		for (int i = 0; i < groupAmount; i++) {
			if (i < residue)
				counts[i] = quotient + 1;
			else
				counts[i] = quotient;
		}
		// 标识每组路线
		int[][] groups = new int[groupAmount][];
		// 初始化每组路线长度
		for (int i = 0; i < groupAmount; i++)
			groups[i] = new int[counts[i]];
		int innerPointer = 0;
		int outerPointer = 0;
		// 下标从1开始，因为0是默认的共同出发点
		for (int i = 1; i <= siteAmount; i++) {
			// 根据预设好的每组个数，将地点按顺序填入
			groups[outerPointer][innerPointer] = i;
			innerPointer++;
			if (innerPointer == counts[outerPointer]) {
				outerPointer++;
				innerPointer = 0;
			}
		}
		return groups;
	}

	/*
	 * 这个方法把singleGroup中的序号与序号0结合，形成一个新的组， 目的是为了进行旅行商问题的计算
	 */
	public static int[] addInit(int[] singleGroup) {
		int len = singleGroup.length;
		int[] combinedGroup = new int[len + 1];
		combinedGroup[0] = 0;
		for (int i = 0; i < len; i++)
			combinedGroup[1 + i] = singleGroup[i];
		return combinedGroup;
	}

	/*
	 * 这个方法根据所有点两两之间的距离，以及一组地点的序号，确定地点子集两两之间的距离
	 * 也就是得到dists的一个部分。其中singleGroup表示子集的序号集合：
	 */
	public static double[][] getPartDists(double[][] dists, int[] singleGroup) {
		// 该组路线的地点个数
		int len = singleGroup.length;
		// 声明该组地点集合的距离矩阵
		double[][] partDists = new double[len][len];
		// 从总的距离矩阵中摘取该组路线所对应的距离
		for (int i = 0; i < len; i++) {
			int markI = singleGroup[i];
			for (int j = 0; j < len; j++) {
				int markJ = singleGroup[j];
				partDists[i][j] = dists[markI][markJ];
			}
		}
		// 返回该路线的距离矩阵
		return partDists;
	}

	/*
	 * 这个方法试图交换两组中的某个地点，如果变得更好，则保留；如果没有变得更好，则保持不变。
	 * 如果变得更好，返回true；如果没有变得更好，则返回false
	 * 其中dists是距离矩阵；groups是分组情况，并没有包含原点；allSums是各组的耗费值；
	 * whichA是选择用于交换地点的甲组，whichInA是甲组中的哪个地点用于交换；
	 * whichB是选择用于交换地点的乙组，whichInB是乙组中的哪个地点用于交换。
	 */
	private static boolean exchange(double[][] dists, int[][] groups, double[] allSums, int whichA, int whichInA,
			int whichB, int whichInB) {
		// 交换两组中的两个地点
		int temp = groups[whichA][whichInA];
		groups[whichA][whichInA] = groups[whichB][whichInB];
		groups[whichB][whichInB] = temp;

		// 向交换后的路线中加入出发点
		int[] groupA = addInit(groups[whichA]);
		// 得到局部距离矩阵
		double[][] distsA = getPartDists(dists, groupA);
		// 调用贪心算法得到交换后A组的最优路线
		int[] solutionA = GreedySearch.greedySearch(distsA);
		// 得到最优路线的总长度
		double sumA = BasicOperation.getDistFromPath(solutionA, distsA);

		// 同理
		int[] groupB = addInit(groups[whichB]);
		double[][] distsB = getPartDists(dists, groupB);
		int[] solutionB = GreedySearch.greedySearch(distsB);
		double sumB = BasicOperation.getDistFromPath(solutionB, distsB);

		// 计算交换后两组的路线总长是否优于交换前
		double increase = sumA + sumB - allSums[whichA] - allSums[whichB];
		// 如果优于的话，更新路径长度数组，进行交换
		if (increase < 0) {
			allSums[whichA] = sumA;
			allSums[whichB] = sumB;
			return true;
		}
		// 否则，交换为原先状态，不做交换
		else {
			temp = groups[whichA][whichInA];
			groups[whichA][whichInA] = groups[whichB][whichInB];
			groups[whichB][whichInB] = temp;
			return false;
		}
	}

	/*
	 * 这个方法用于通过交换实现优化分组。 其中dists是距离矩阵，groupAmount是分组的数量；
	 * 变量exitThreshold是一个阈值，当连续没有改进的次数不超过这个值时，交换优化的过程继续；
	 * 反之，当连续没有改进的次数达到这个阈值的时候，整个的交换优化的过程终止。
	 */
	public static int[][] grouping(double[][] dists, int groupAmount, int exitThreshold) {
		// 地点个数，距离矩阵长度
		int total = dists.length;
		// 如果分组数为1
		if (groupAmount == 1) {
			// 直接设置该组路线（按下标顺序填入）
			int[][] groups = new int[1][total - 1];
			for (int i = 1; i < total; i++)
				groups[0][i - 1] = i;
			return groups;
		}
		// 分组  toal-1是去掉的长度
		int[][] groups = splitInitSites(total - 1, groupAmount);
		// 标识每组路线总长度
		double[] allSums = new double[groupAmount];
		// 计算每组路线长度
		for (int i = 0; i < groupAmount; i++) {
			// 将出发点插入路线首位
			int[] tempGroup = addInit(groups[i]);
			// 得到该组路线的距离矩阵
			double[][] partDists = getPartDists(dists, tempGroup);
			// 调用贪心算法得到最优路径
			int[] path = GreedySearch.greedySearch(partDists);
			// 计算该路线总长
			allSums[i] = BasicOperation.getDistFromPath(path, partDists);
		}
		while (true) {
			boolean flagImproved = false;
			// 当循环次数小于阈值时，一直循环
			for (int i = 0; i < exitThreshold; i++) {
				// 随机选出两组
				// 从当前组数中得到随机值
				int whichA = (int) (rand.nextDouble() * groupAmount);
				int whichB = -1;
				// 确保A和B两个随机值不同
				while (true) {
					whichB = (int) (rand.nextDouble() * groupAmount);
					if (whichB != whichA)
						break;
				}
				// 从选取的两组中随机选取两个点
				int whichInA = (int) (rand.nextDouble() * groups[whichA].length);
				int whichInB = (int) (rand.nextDouble() * groups[whichB].length);
				// 标识是否需要进行交换
				boolean stepImproved = exchange(dists, groups, allSums, whichA, whichInA, whichB, whichInB);
				// 交换后退出循环
				if (stepImproved) {
					flagImproved = true;
					break;
				}
			}
			if (!flagImproved)
				break;
		}
		for (int i = 0; i < groupAmount; i++)
			java.util.Arrays.sort(groups[i]);
		return groups;
	}

	/**
	 * 使用 kmeans 进行分组
	* */

	public static int[][] grouping_kmeans(ArrayList<double[]> dataSet, int groupAmount){

		int total = dataSet.size();
//		/ 如果分组数为1
		if (groupAmount == 1) {
			// 直接设置该组路线（按下标顺序填入）
			int[][] groups = new int[1][total];
			for (int i = 0 ; i < total; i++)
				groups[0][i] = i+1;
			return groups;
		}
		Kmeans k=new Kmeans(groupAmount);
		//设置原始数据集
		k.setDataSet(dataSet);
		//执行算法
		k.execute();
		//得到聚类结果
		ArrayList<ArrayList<double[]>> cluster=k.getCluster();
		for(int i=0;i<cluster.size();i++)
		{
			k.printDataArray(cluster.get(i), "cluster["+i+"]");
		}
//		根据分组的结果，生成索引的数组
		int[][] groups = new int[groupAmount][];
		int  flag[] = new int[dataSet.size()];
		for (int i = 0; i < cluster.size(); i++) {
			groups[i] = new int[cluster.get(i).size()];

			for (int j = 0; j < cluster.get(i).size() ; j++) {
				double a = cluster.get(i).get(j)[0];
				double b = cluster.get(i).get(j)[1];
				for (int l = 0; l < dataSet.size(); l++) {
					if(a == dataSet.get(l)[0] && b == dataSet.get(l)[1] && flag [l] == 0){
						groups[i][j] = l+1;
						flag[l] = 1;
						break;
					}
				}
			}
		}

		return groups;
	}


}

