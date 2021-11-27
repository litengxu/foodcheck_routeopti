package com.bjfu.fcro.logic.wjx;

import java.util.Arrays;

/**
 * @author LTX
 * Tsp 
 * SSA     
 * 时间:2020年5月11日 上午10:40:54   
 * 
 * 麻雀搜索算法的离散化
 */

public class SSA {
	public static final int PoolSize = 200;//种群数量,城市数目多时可以尝试调高，运行时间会长
	public static final double discover = 0.2;//发现者比例
	public static final double danger = 0.1;//感知到到危险的种群比例
	public static final double max_early_waring = 0.8;//最大预警值
	public static final double min_early_waring = 0.2;//最小预警值
	
	public static final int GenerationNum = 500;//代数
	
	public static Chromosome ssa(double[][] dists,int[] Path) {
		int n = dists.length;
		int discovervalue =(int) (PoolSize * discover);
		int dangervalue = (int)(PoolSize * danger);
		Chromosome[] pool = new Chromosome[PoolSize];
		double []everygenerationbest = new double[GenerationNum];
		int []everygeneration = new int[GenerationNum];

		for (int i=0; i<PoolSize; i++) {
			pool[i] = new Chromosome(n,Path);//利用路径
//			pool[i] = new Chromosome(n);//随机产生
			pool[i].getFitness(dists);
			
		}
//		double T = 200 * dists.length; //模拟退火初始温度
//		double c = 0.85; //温度衰减因子
		double value;
		Arrays.sort(pool);
		int[]bestpath = new int[dists.length];
		bestpath = Path.clone();
		double bestvalue = BasicOperation.getDistFromPath(bestpath, dists);
		for(int i = 0;i<GenerationNum;i++) {
			double randomvalue = Math.random();
			//发现者的变化
			double early = max_early_waring - (i/GenerationNum) * min_early_waring;//自适应预警值
			if(randomvalue > early) {//大于预警值说明安全
				
				for(int j = 0;j<discovervalue;j++) {
					
					int []temp = Arrays.copyOf(pool[j].genes, n);
					double fitness1 = pool[j].fitness;
//					temp = Random_Twopoint.ran_twopoint(temp); //随机调换两个点之间 的值
					temp = k_opt.kopt(temp,dists,200,2);
					temp = PartitionSwap.partionswap(temp,dists,200,1);
//					temp = two_opt.Two_opt_sa(temp,dists,2);//进行一次2-opt 不管适应度是否进步
//					double fitness2 = BasicOperation.getDistFromPath(temp, dists);
//					temp = Simulated_Annealing.sa(pool[j].genes, fitness1, temp, fitness2, T);//模拟退火选择接受与否
					
//					if(fitness2<fitness1) {
//						pool[j].genes = Arrays.copyOf(temp, n);
//					}
					pool[j].genes = Arrays.copyOf(temp, n);
					pool[j].getFitness(dists);
					
				}
			}else {//如果小于预警值，表明有危险
				
				for(int j = 0;j<PoolSize;j++) {
				
					int []temp = Arrays.copyOf(pool[j].genes, n);
					double fitness1 = pool[j].fitness;
//					temp = Random_Twopoint.ran_twopoint(temp); //随机调换两个点之间 的值
					
					temp = Two_Opt.Two_opt(temp,dists,200);
					temp = PartitionSwap.partionswap(temp,dists,200,2);//分段交换，原则上加快收敛，局部优化，避免陷入局部最优。改变较少，不会破坏大的路径

				
					
//					temp = two_opt.Two_opt_sa(temp,dists,2);//进行一次2-opt 不管适应度是否进步
					double fitness2 = BasicOperation.getDistFromPath(temp, dists);
//					temp = Simulated_Annealing.sa(pool[j].genes, fitness1, temp, fitness2, T);//模拟退火选择接受与否
					
					if(fitness2<fitness1) {
						pool[j].genes = Arrays.copyOf(temp, n);
					}
					pool[j].getFitness(dists);
				}
			}
			
			Arrays.sort(pool);
			
			
			//追随者的变化
			int k = (PoolSize-discovervalue)/2;
			for(int j = discovervalue;j<PoolSize;j++) {
				if(j<k) {
					int m =(int) (Math.random()*discovervalue);//随机与某个发现者交叉
					pool[j].singlecross(pool[m]);
				}else {//效果较差，重新随机
					pool[j] = new Chromosome(n,Path);
//					pool[j] = new Chromosome(n);
			
				}
				pool[j].getFitness(dists);
			}
			
			Arrays.sort(pool);
			
			//危险预警
			for(int j = 0;j<dangervalue;j++) {
				int m = (int)(Math.random()*(PoolSize-1)+1);
				if(m<(PoolSize/2)) {
					int ran = (int)(Math.random()*(PoolSize-1)+1);
					pool[m].singlecross(pool[ran]);
				}else {
					int ran = (int)(Math.random()*(discovervalue));
					pool[m].singlecross(pool[ran]);
				}
				pool[j].getFitness(dists);
			}
			Arrays.sort(pool);
			//T = T * c;//温度衰减
//			if(pool[0].fitness < bestvalue) {
//				bestvalue = pool[0].fitness;
//				for(int j=0;i<pool[0].genes.length;i++) {
//					bestpath[j] = pool[0].genes[j];
//				}
//			}
			
			everygenerationbest[i] = pool[0].fitness;
			everygeneration[i] = i;
		}

		System.out.println(Arrays.toString(everygenerationbest));
		System.out.println((Arrays.toString(everygeneration)));


		return pool[0];
		
	}
}
