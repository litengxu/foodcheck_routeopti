package com.bjfu.fcro.logic;

import java.text.DecimalFormat;
import java.util.Scanner;

public class TestDivide {
	public static void main(String[] args) {
		System.out.println("请输入食品种类数量：");

		int foodAmount = new Scanner(System.in).nextInt();

		int[] basicAmounts = new int[foodAmount];

		int amount = 0;

		for (int i = 0; i < basicAmounts.length; i++) {
			basicAmounts[i] = 1;

			amount += basicAmounts[i];
		}

		System.out.println("抽检食品的基础数量：" + java.util.Arrays.toString(basicAmounts));

		double[] ratios = new double[foodAmount];

		DecimalFormat decimalFormat = new DecimalFormat("#.00000");

		for (int i = 0; i < ratios.length; i++) {
			ratios[i] = Double.parseDouble(decimalFormat.format(Math.random()));
		}

		System.out.println("抽检食品的风险因子：" + java.util.Arrays.toString(ratios));

		System.out.println("请输入抽检总个数（需>" + amount + "):");

		int total = 0;

		while ((total = new Scanner(System.in).nextInt()) <= amount) {
			System.out.println("请输入抽检总个数（需>" + amount + "):");
		}

		int[] finalAmounts = divideByBasicAndRatios(ratios, total, basicAmounts);

		System.out.println("抽检食品的最终数量：" + java.util.Arrays.toString(finalAmounts));
	}

	/*
	 * ratios: array for risk values amount: total amount of inspections
	 * basicAmounts: the basic amount array prescribed return: the final amount
	 * array for inspections return null if the amount cannot be divided
	 */
	public static int[] divideByBasicAndRatios(double[] ratios, int amount, int[] basicAmounts) {
		// 标识风险因子个数
		int n = ratios.length;
		// 求得基本数量总和
		int basicSum = 0;
		// 从基本数量数组中遍历计算总和
		for (int i = 0; i < n; i++)
			basicSum += basicAmounts[i];
		// 抽检总数减去基本数量总和，得到剩余数量
		int residueAmount = amount - basicSum;
		// 如果无剩余数量，直接返回基本数量数组作为最终抽检个数结果
		if (residueAmount == 0)
			return basicAmounts;
		// 如果预设总数量小于基本数量总和，直接返回null
		if (residueAmount < 0)
			return null;
		// 根据风险因子分配剩余数量
		int[] ratioAmounts = divideByRatios(ratios, residueAmount);
		// 最终的每种食品抽检个数，基本数量+根据风险因子分配的个数
		int[] finalAmounts = new int[n];
		for (int i = 0; i < n; i++)
			finalAmounts[i] = basicAmounts[i] + ratioAmounts[i];
		// 最终食品种类抽检个数的结果
		return finalAmounts;
	}

	private static int[] divideByRatios(double[] ratios, int amount) {
		// 用来比较两个值的大小
		class Pair implements Comparable<Pair> {
			int mark;
			double value;

			Pair(int mark, double value) {
				this.mark = mark;
				this.value = value;
			}

			public int compareTo(Pair that) {
				if (this.value > that.value)
					return -1;
				if (this.value < that.value)
					return 1;
				return 1;
			}
		}

		// 风险因子的个数
		int len = ratios.length;
		// 标识风险总值（即各项风险因子相加之和）
		double ratioSum = 0.0;
		// 得到风险总值
		for (int i = 0; i < len; i++)
			ratioSum += ratios[i];
		// 用来标识每类食品根据风险因子分配的个数
		int[] counts = new int[len];
		int countSum = 0;
		Pair[] pairs = new Pair[len];
		for (int i = 0; i < len; i++) {
			// 得到理论上的分配数量（剩余数量*该类食品风险因子/风险因子总和）
			int count = (int) (amount * ratios[i] / ratioSum);
			// 累加个数
			countSum += count;
			//
			counts[i] = count;
			// 该类食品风险因子 - 理论分配数量/总剩余数量
			double value = ratios[i] - (double) count / amount;
			// 存入比较的数组
			pairs[i] = new Pair(i, value);
		}
		// 按照value的值从大到小排序Pair数组
		java.util.Arrays.sort(pairs);
		// 得到剩余数量
		int residue = amount - countSum;
		// 将剩余数量按照Pairs顺序依次增加
		for (int i = 0; i < residue; i++) {
			int which = pairs[i].mark;
			counts[which]++;
		}
		return counts;
	}
}