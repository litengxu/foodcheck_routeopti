package com.bjfu.fcro.logic.wjx;

import java.util.Arrays;

/*
 * 这是用于旅行商问题的遗传算法
 */
public class GaSearch {
	public static final int PoolSize = 200;
	public static final int SeaSize = 400;
	public static final int EliteSize = 10;
	public static final double MutateRate = 0.05; //突变率
	public static final int GenerationNum = 500;
	public static final double PowerRate = 1.25; //为了让更优秀的个体产生更多的后代，用这个幂值产生倾向性
	public static final double gen = 0.10; //移民概率，避免陷入局部最值

	/**
	 * @param dists
	 * @param Path
	 * @return
	 */
	public static Chromosome gaSearch(double[][] dists,int[] Path){
		int n = dists.length;
		Chromosome[] pool = new Chromosome[PoolSize];
		Chromosome[] sea = new Chromosome[SeaSize];
		for (int i=0; i<PoolSize; i++) {
			pool[i] = new Chromosome(n,Path);
		}
		double []everygenerationbest = new double[GenerationNum];
		int []everygeneration = new int[GenerationNum];
		for (int i=0; i<GenerationNum; i++){
			for (int j=0; j<EliteSize; j++) {
				sea[j] = pool[j].clone();
				
			}
			for (int j=EliteSize; j<SeaSize; j++){
				double rand = Math.random();
				double randPowered = Math.pow(rand, PowerRate);
				int pos = (int)(randPowered * PoolSize);
				sea[j] = pool[pos].clone();
				
			}
			int changeSize = SeaSize - EliteSize;
			int pairSize = changeSize / 2;
			int count=0;
			for (int j=0; j<pairSize; j++){
				int posPaternal = EliteSize + j * 2;
				int posMaternal = posPaternal + 1;
				sea[posPaternal].cross(sea[posMaternal]);
				
//				//贪心构建遗传染色体，并且择优选()ltx)
//				int []gen = new int[n];
//				boolean []flag = new boolean[n];
//				gen[0] = sea[posPaternal].genes[0];
//				flag[sea[posPaternal].genes[0]] = true;
//				for(int k=1;k<n;k++) {
//					double d1 = dists[gen[k-1]][sea[posPaternal].genes[k]];//到两条染色体中下一位置的距离
//					double d2 = dists[gen[k-1]][sea[posMaternal].genes[k]];
//					if(flag[sea[posMaternal].genes[k]] == true && flag[sea[posPaternal].genes[k]] == true) {//都被选择过，随机生成未选择的
//						int ran;
//						while(true) {
//						
//							ran = (int)(Math.random()*n);
//							if(flag[ran] == false) {
//								break;
//							}
//						
//						}
//						gen[k] = ran;
//					
//					}
//					else if(d1<=d2) {
//						if(flag[sea[posPaternal].genes[k]] == false) {//贪心选择较小的，且未被选择的
//							gen[k] = sea[posPaternal].genes[k];
//							
//						}
//						if(flag[sea[posPaternal].genes[k]] == true && flag[sea[posMaternal].genes[k]] == false) {//如果已被选择，则选择另一个
//							gen[k] = sea[posMaternal].genes[k];
//							
//						}
//						
//					}
//				
//					else if(d1>d2) {
//						if(flag[sea[posMaternal].genes[k]] == false) {//贪心选择较小的，且未被选择的
//							gen[k] = sea[posMaternal].genes[k];
//						}
//						if(flag[sea[posMaternal].genes[k]] == true && flag[sea[posPaternal].genes[k]] == false) {//如果已被选择，则选择另一个
//							gen[k] = sea[posPaternal].genes[k];
//						}
//						
//					}else {}
//				
//					flag[gen[k]] = true;
//					
//				}
//				double genfitness = BasicOperation.getDistFromPath(gen, dists);//种群的适应度
//				double pfitness = BasicOperation.getDistFromPath(sea[posPaternal].genes, dists);
//				double Mfitness = BasicOperation.getDistFromPath(sea[posMaternal].genes, dists);
//				
//				//留下两个路径较短的
//				if(pfitness < Mfitness && genfitness <  Mfitness) {
//
//					sea[posMaternal].genes = gen;
//				}
//				else if(Mfitness < pfitness && genfitness <  pfitness) {
//					sea[posPaternal].genes = gen;
//				}else {}
////			//贪心构建染色体结束
			}
			int mutateSize = (int)(changeSize * MutateRate);
			for (int j=0; j<mutateSize; j++){
				int pos = (int)(Math.random() * changeSize) + EliteSize;
				sea[pos].mutate();
			}
			for (int j=EliteSize; j<SeaSize; j++)
				sea[j].getFitness(dists);
			Arrays.sort(sea);
			//移民
			//if((i+1) % 30000 == 0) {
			if(i >200) {
				if(i%10==0) {
					int k = (int) (sea.length*gen);
					
			
					for(int p=0;p<k;p++) {
						
						Tools.randomizeArr(sea[sea.length-p-1].genes);
	//					sea[sea.length-i] = new Chromosome(n);
						sea[sea.length-p-1].getFitness(dists);
						
					}
			
				}
			}
			Arrays.sort(sea);
			for (int j=0; j<PoolSize; j++)
				pool[j] = sea[j].clone();
			int []temp = Arrays.copyOf(pool[0].genes, n);
			double fitness1 = pool[0].fitness;			
			temp = Two_Opt.Two_opt(temp,dists,200);
			double fitness2 = BasicOperation.getDistFromPath(temp, dists);
			
			if(fitness2<fitness1) {
				pool[0].genes = Arrays.copyOf(temp, n);
			}
			pool[0].getFitness(dists);
			everygenerationbest[i] = pool[0].fitness;
			everygeneration[i] = i;
		}
		System.out.println(Arrays.toString(everygenerationbest));
		System.out.println((Arrays.toString(everygeneration)));
		return pool[0];
	}
}