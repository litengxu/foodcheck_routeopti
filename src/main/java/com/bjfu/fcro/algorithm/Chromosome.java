package com.bjfu.fcro.algorithm;


/*
 * 这是用于遗传算法的染色体类：
 */

import com.bjfu.fcro.algorithm.BasicOperation;
import com.bjfu.fcro.algorithm.Tools;

import java.util.Arrays;
import java.util.List;

/**
 * @author LTX
 * Tsp 
 * Chromosome     
 * 时间:2020年5月11日 上午11:31:33   
 */
public class Chromosome implements Comparable<Chromosome>{
	double fitness = Double.POSITIVE_INFINITY; //��Ӧ��ֵ
	int[] genes = null; //��������
	double [][]dists;
	
	public Chromosome(){
	}
	
	public Chromosome(int[] genes){
		this.genes = genes.clone();
	}
	
	public Chromosome(int n){
		genes = new int[n];
		
		for (int i=0; i<n; i++)
			genes[i] = i;
		com.bjfu.fcro.algorithm.Tools.randomizeArr(genes);
		for(int i=0;i<n;i++) {//修改第一个城市为0
			if(genes[i] == 0) {
				genes[i] = genes[0];
				genes[0] = 0;
			}
		}
	}
	//ltx
	public Chromosome(int n, int []path){
		
		genes = new int[n];
		for(int i =0 ;i<n;i++) {
			genes[i] = path[i];
		}
		//this.genes = path;这样会把当前的genes的引用指向path
		for(int i=0;i<100;i++) {
			int pos1 = (int)(Math.random() * (n-1)+1);//修改第一个城市为0
			int pos2 = (int)(Math.random() * (n-1)+1);
			if (pos1 == pos2)
				return;
				//continue;
			int temp =this.genes[pos1];
			this.genes[pos1] = this.genes[pos2];
			this.genes[pos2] = temp;
		}

//		for (int i=0; i<n; i++)
//			genes[i] = i;
//		Tools.randomizeArr(genes);
//		for(int i=0;i<n;i++) {//修改第一个城市为0
//			if(genes[i] == 0) {
//				genes[i] = genes[0];
//				genes[0] = 0;
//			}
//		}
	}
	public Chromosome(int n, int []path,double [][]dists){
		this.dists = dists;
		genes = new int[n];
		for(int i =0 ;i<n;i++) {
			genes[i] = path[i];
		}
		//this.genes = path;这样会把当前的genes的引用指向path
		for(int i=0;i<100;i++) {
			int pos1 = (int)(Math.random() * (n-1)+1);//修改第一个城市为0
			int pos2 = (int)(Math.random() * (n-1)+1);
			if (pos1 == pos2)
				return;
			//continue;
			int temp =this.genes[pos1];
			this.genes[pos1] = this.genes[pos2];
			this.genes[pos2] = temp;
		}

//		for (int i=0; i<n; i++)
//			genes[i] = i;
//		Tools.randomizeArr(genes);
//		for(int i=0;i<n;i++) {//修改第一个城市为0
//			if(genes[i] == 0) {
//				genes[i] = genes[0];
//				genes[0] = 0;
//			}
//		}
	}
	public String toString(){
		return "" + fitness + "; " + java.util.Arrays.toString(this.genes);
	}
	
	public Chromosome clone(){
		Chromosome copy = new Chromosome(this.genes);
		copy.fitness = this.fitness;
		return copy;
	}
	
	public int compareTo(Chromosome that){
		if (this.fitness < that.fitness)
			return -1;
		if (this.fitness > that.fitness)
			return 1;
		return 0;
	}
	
	/*
	 * 此方法用于序列调整。如果两个染色体交叉后，则有可能两条染色体已经不是完整路径了
	 * 用此方法调整
	 */
	public void modify(){
		int n = this.genes.length;
		/*应对分组时的情况*/

		boolean[] isRepeated = new boolean[n];
		int[] counts = new int[n];

		int repeatNum = 0;
		for (int i=0; i<genes.length; i++){
			counts[genes[i]]++;
			if (counts[genes[i]] > 1){
				isRepeated[i] = true;
				repeatNum++;
			}
		}
		if (repeatNum == 0)
			return;
		int[] repeats = new int[repeatNum];
		int pointer = 0;
		for (int i=0; i<n; i++){
			if (counts[i] == 0){
				repeats[pointer] = i;
				pointer++;
			}
		}
		Tools.randomizeArr(repeats);
		pointer = 0;
		for (int i=0; i<n; i++){
			if (!isRepeated[i])
				continue;
			genes[i] = repeats[pointer];
			pointer++;
		}
	}
	public void modify2(int pre[]){
		/*应对分组时的情况*/

		int n = dists.length;

		boolean[] isRepeated = new boolean[n];
		int[] counts = new int[n];
		for (int i = 0; i < n; i++) {
			counts[i] = Integer.MIN_VALUE;
		}
		for (int i = 0; i < pre.length; i++) {
			counts[pre[i]] = 0;
		}
		counts[0] = 0;

		int repeatNum = 0;
		for (int i=0; i<genes.length; i++){
			counts[genes[i]]++;
			if (counts[genes[i]] > 1){
				isRepeated[i] = true;
				repeatNum++;
			}
		}

		if (repeatNum == 0)
			return;
		int[] repeats = new int[repeatNum];
		int pointer = 0;
		for (int i=0; i<n; i++){
			if (counts[i] == 0){
				repeats[pointer] = i;
				pointer++;
			}
		}

		Tools.randomizeArr(repeats);
		pointer = 0;
		for (int i=0; i<n; i++){
			if (!isRepeated[i])
				continue;
			genes[i] = repeats[pointer];
			pointer++;
		}

	}
	
	/*
	 * 染色体交叉：
	 */
	public void cross(Chromosome that){
		int n = this.genes.length;
		int thispath[] = this.genes.clone();
		int thatpath[] = that.genes.clone();
		if (n <= 1)
			return;
		int leftLen = (int)((n-1) * Math.random()) + 1;
		for (int i=1; i<leftLen; i++){//修改第一个城市为0
			int temp = this.genes[i];
			this.genes[i] = that.genes[i];
			that.genes[i] = temp;
		}
		this.modify2(thispath);
		that.modify2(thatpath);
//		this.modify();
//		that.modify();
	}
	//只改变this染色体的交叉
	public void singlecross(Chromosome that) {
		int n = this.genes.length;
		int thispath[] = this.genes.clone();
		if(n<=1) {
			return;
		}
//		int leftLen = (int)(((n-1)/2) * Math.random()) + (n-1)/2;
//		int low =  (int)(((n-1)/2) * Math.random())+1;
//		for(;low<leftLen;low++) {
//			this.genes[low] = that.genes[low];
//		}
		int leftLen = (int)((n-1) * Math.random()) + 1;
		for (int i=1; i<leftLen; i++){//修改第一个城市为0
			this.genes[i] = that.genes[i];
		}
		this.modify2(thispath);
//		this.modify();
	}
	/*
	 * 染色体突变：
	 */
	public void mutate(){
		int n = this.genes.length;
		if (n == 1)
			return;
		int pos1 = (int)(Math.random() * (n-1)+1);//修改第一个城市为0
		int pos2 = (int)(Math.random() * (n-1)+1);
	
		if (pos1 == pos2)
			return;
		int temp = genes[pos1];
		genes[pos1] = genes[pos2];
		genes[pos2] = temp;
	}
	
	/*
	 * 获取适应度函数值。这个值就是路径对应的总路程。
	 */
	public void getFitness(double[][] dists){
		this.fitness = BasicOperation.getDistFromPath(genes, dists);
	}

	public void getFitness2(double curTime,double[][] dists,List<DistInDifTime> Dlist){
		this.fitness = BasicOperation.getDistFormPathByDifTime2(curTime,genes,dists,Dlist);
	}

	public static void main(String[] args) {
		double dists[][] = new double[10][10];
		Chromosome chromosome = new Chromosome(6,new int[]{0,1,3,9,4,2},dists);
		Chromosome that = new Chromosome(6,new int[]{0,2,4,3,9,1},dists);
		chromosome.cross(that);
//		chromosome.modify2(new int[]{0,1,2,4,9,3});
	}
}
