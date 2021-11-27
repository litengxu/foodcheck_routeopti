package com.bjfu.fcro.algorithm;

import com.bjfu.fcro.algorithm.BasicOperation;
import com.bjfu.fcro.algorithm.GreedySearch;
import com.bjfu.fcro.algorithm.Tools;
import com.bjfu.fcro.algorithm.*;

import java.nio.file.Path;
import java.util.Arrays;

public class Test {
	public static void main(String[] args){
//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/Points.txt";
//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/burma14.txt"; 
//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/oliver30.txt"; 
//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/bays29.txt";//最优 9074.15

//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/att48.txt";//最优 33 523.71
		
		String filePath = "E:/文件/论文/毕设/双随机+路径优化算法/eil51.txt";  // 428.87
//		String filePath = "E:/文件/论文/毕设/双随机+路径优化算法/berlin52.txt";  //7544.37

//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/st70.txt"; //677.10

//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/kioa100.txt";//21307.422459603575

//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/ch130.txt";  // 6175.61

//		String filePath = "E:/研究生课程文档/双随机+路径优化算法/A280.txt";
		double[][] points = readPoints(filePath);
		double[][] dists = getDists(points);
		System.out.println(dists[0][1]);
		int[] path = com.bjfu.fcro.logic.wjx.GreedySearch.greedySearch(dists);
		System.out.println(Arrays.toString(path));
		System.out.println("贪心算法路径和");
		double sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(path, dists);
		System.out.println(sum);
		System.out.println();

//		int[] tunePath = TuneSearch.tuneSearch(path, dists);
//		System.out.println(Arrays.toString(tunePath));
//		System.out.println("贪心算法局部优化路径和");
//		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(tunePath, dists);
//		System.out.println(sum);
//		System.out.println();
//
//		int[] GreedyswapPath = SwapSearch.swapSearch(tunePath, dists);
//		System.out.println(Arrays.toString(GreedyswapPath));
//		System.out.println("贪心算法交换优化路径和");
//		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(GreedyswapPath, dists);
//		System.out.println(sum);
//		System.out.println();
//
//		int[] TwooptPath = Two_Opt.Two_opt(GreedyswapPath, dists,10000);
//		System.out.println(Arrays.toString(TwooptPath));
//		System.out.println("2_opt交换优化路径和");
//		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(TwooptPath, dists);
//		System.out.println(sum);
//		System.out.println();
		
//		double s = 0;
//		double bestvalue = 9999999;
//		for(int i = 0;i<20;i++) {
//			Chromosome ch = GaSearch.gaSearch(dists,path);
//			int[] gaPath = ch.genes;
//			System.out.println(java.util.Arrays.toString(gaPath));
//			System.out.println("遗传算法路径和");
//			sum = BasicOperation.getDistFromPath(gaPath, dists);
//			System.out.println(sum);
//			if(sum<bestvalue) {
//				bestvalue = sum;
//			}
//			s += sum;
//		}
//		System.out.println(bestvalue);
//		System.out.println(s/20);
		double start = System.currentTimeMillis();
		Chromosome ch = GaSearch.gaSearch(dists,path);
		int[] gaPath = ch.genes;
		System.out.println(Arrays.toString(gaPath));
		System.out.println("遗传算法路径和");
		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(gaPath, dists);
		System.out.println(sum);
		System.out.print(System.currentTimeMillis()-start);
		System.out.println();
		
		int[] gatunePath = TuneSearch.tuneSearch(gaPath, dists);
		System.out.println(Arrays.toString(gatunePath));
		System.out.println("遗传算法局部优化优化路径和");
		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(gatunePath, dists);
		System.out.println(sum);
		System.out.println();
		
		int[] swapPath = SwapSearch.swapSearch(gatunePath, dists);
		System.out.println(Arrays.toString(swapPath));
		System.out.println("遗传算法交换优化路径和");
		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(swapPath, dists);
		System.out.println(sum);
		System.out.println();
		
		int[] koptssaPath = Kopt.kopt(swapPath, dists,1000,2);
		System.out.println(Arrays.toString(koptssaPath));
		System.out.println("k_opt交换优化路径和");
		sum = com.bjfu.fcro.logic.wjx.BasicOperation.getDistFromPath(koptssaPath, dists);
		System.out.println(sum);
		System.out.println();
		
		int[] TwooptGaPath = Two_Opt.Two_opt(koptssaPath, dists,10000);
		System.out.println(Arrays.toString(TwooptGaPath));
		System.out.println("2-opt交换优化路径和");
		sum = BasicOperation.getDistFromPath(TwooptGaPath, dists);
		System.out.println(sum);
		System.out.println();

		start = System.currentTimeMillis();
		int[] DynaSearchPath = DynaSearch.dynaSearch(path,dists,500,1,false,null);
		System.out.println(Arrays.toString(DynaSearchPath));
		System.out.println("DynaSearch交换优化路径和");
		sum = BasicOperation.getDistFromPath(DynaSearchPath, dists);
		System.out.println(sum);
		System.out.print(System.currentTimeMillis()-start);
		System.out.println();
//		
//		int[] PaPath = PartitionSwap.partionswap(TwooptGaPath, dists,10000,2);
//		System.out.println(java.util.Arrays.toString(PaPath));
//		System.out.println("分段交换优化路径和");
//		sum = BasicOperation.getDistFromPath(PaPath, dists);
//		System.out.println(sum);
//		System.out.println();
		
	
		
//		sum = 0;
//		for(int i = 0;i<20;i++) {
//			Chromosome ssa = SSA.ssa(dists,path);
//			int[] ssaPath = ssa.genes;
//			System.out.println(Arrays.toString(ssaPath));
//			System.out.println("麻雀算法路径和");
//
//			System.out.println(ssa.fitness);
//			sum += ssa.fitness;
//			System.out.println();
//		}
//		System.out.println("20次平均路径和:"+sum/20);

		
//		Arrays.sort(gaPath);
//		for(int i=0;i<gaPath.length;i++) {
//			System.out.println(gaPath[i]);
//		}
//		int[] Tsppath = Tsp.tspSyn(dists);
//		System.out.println(java.util.Arrays.toString(Tsppath));
//		System.out.println("综合交换优化路径和");
//		sum = BasicOperation.getDistFromPath(Tsppath, dists);
//		System.out.println(sum);
//		Chromosome ch = GaSearch.gaSearch(dists);
//		int[] path = ch.genes;
//		int n = points.length;
//		double[] x = new double[n];
//		double[] y = new double[n];
//		for (int i=0; i<n; i++){
//			x[i] = points[i][0];
//			y[i] = points[i][1];
//		}
//		rangePointsByPath(x, y, path);
//		sum = BasicOperation.getDistFromPath(swapPath, dists);
//		System.out.println(sum);
	}
	
	public static double[][] readPoints(String path){
		String content = Tools.readFileText(path);
		content = content.trim();
		String[] strLines = content.split("\n");
		int n = strLines.length;
		double[][] points = new double[n][2];
		for (int i=0; i<n; i++){
			String[] subs = strLines[i].trim().split(" ");
			points[i][0] = Double.parseDouble(subs[1]);
			points[i][1] = Double.parseDouble(subs[2]);
		}
		return points;
	}
	
	public static double[][] getDists(double[][] points){
		int n = points.length;
		double[] x = new double[n];
		double[] y = new double[n];
		for (int i=0; i<n; i++){
			x[i] = points[i][0];
			y[i] = points[i][1];
		}
		double[][] dists = new double[n][n];
		for (int i=0; i<n; i++){
			for (int j=0; j<n; j++){
				double xDiff = x[i] - x[j];
				double yDiff = y[i] - y[j];
				dists[i][j] = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
			}
		}
		return dists;
	}
	
	public static void rangePointsByPath(double[] x, double[] y, int[] path) {
		int n = x.length;
		for (int i=0; i<n; i++){
			int which = path[i];
			System.out.println("" + x[which] + " " + y[which]);
		}
	}
	
	public static void testGreedySearch(){
		double[][] points = readPoints("D:/Points.txt");
		double[][] dists = getDists(points);
		int[] path = GreedySearch.greedySearch(dists);
		System.out.println(Arrays.toString(path));
	}
	
	
//	public static void testGaSearch(){
//		int n = 100;
//		double[] x = new double[n];
//		double[] y = new double[n];
//		for (int i=0; i<n; i++){
//			x[i] = Math.random();
//			y[i] = Math.random();
//		}
//		double[][] dists = new double[n][n];
//		for (int i=0; i<n; i++){
//			for (int j=0; j<n; j++){
//				double xDiff = x[i] - x[j];
//				double yDiff = y[i] - y[j];
//				dists[i][j] = Math.sqrt(xDiff*xDiff + yDiff*yDiff);
//			}
//		}
//		Chromosome ch = GaSearch.gaSearch(dists);
//		System.out.println(ch.fitness);
//	}
	
	public static void generatePoints(int n){
		for (int i=0; i<n; i++){
			double x = Math.random();
			double y = Math.random();
			System.out.println("" + x + "," + y);
		}
	}
}