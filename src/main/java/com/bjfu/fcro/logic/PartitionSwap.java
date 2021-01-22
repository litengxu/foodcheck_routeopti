package com.bjfu.fcro.logic;

/**
 * @author LTX
 * Tsp 
 * PartitionSwap     
 * 时间:2020年5月18日 上午11:01:23   
 * 分为k部分，每部分随机产生两个数，i,j.若i,i+1挪到j后比原来适应度高，则替换
 */
public class PartitionSwap {

	public static int[] partionswap(int[] initPath,double dists[][],int optcount,int k) {
		int n = dists.length;//长度
	
		int s = (n-1)/k;//分为k段,每段s.0点不参与
		if(s<5) {
			k = 1;
			s = n-1;
		}
		double initValue = BasicOperation.getDistFromPath(initPath, dists);
		double bestValue = initValue;
		int count=1;
		while(count<=optcount) {
			//保留数据
			
			for(int i = 0;i<k;i++) {
				
				int start = i * s+1;//从1开始
				int end = start + s;
				int []temppath = initPath.clone();
				
				if(end-start <= 1) {
					continue;
				}
				initPath = pswap(initPath,start,end);
				
				initValue = BasicOperation.getDistFromPath(initPath, dists);

				if(initValue < bestValue) {
					
					bestValue = initValue;
				
					
				}else {
				
					initPath = temppath.clone();
					
				}
			}
			if((n-1)%k >= 5) {//如果剩余的数目大于5，说明也可以进行调整 S++
				
				int start = k*s+1;
				int end = n-1;
				
				int []temppath = initPath.clone();
				initPath = pswap(initPath,start,end);
				initValue = BasicOperation.getDistFromPath(initPath, dists);

				if(initValue < bestValue) {
					
					bestValue = initValue;
					
					
				}else {
				
					initPath = temppath.clone();
					
				}

			}
			
			count++;

			
		}
	
		return initPath;
	}
	public static int[] pswap(int []path,int start,int end) {
		int first = (int)(Math.random() * (end-start)+start);
		int second = (int)(Math.random() * (end-start)+start);
		
		if(second < first) {
			int temp = second;
			second = first;
			first = temp;
		}
		while(Math.abs((first-second))<2 || second == (end-1) || second == (end-2) ){
			first = (int)(Math.random() * (end-start)+start);
			second = (int)(Math.random() * (end-start)+start);
			if(second < first) {
				int temp = second;
				second = first;
				first = temp;
			}
		}
		
		swap(path,first,second+1);
		swap(path,first+1,second+2);
		return path;
	}
	public static void swap(int[] initPath,int i,int j) {
		int temp = initPath[i];
		initPath[i] = initPath[j];
		initPath[j] = temp;
	}
	
	
}
