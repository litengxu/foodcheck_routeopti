package com.bjfu.fcro.logic;

/**
 * @author LTX
 * Tsp 
 * k_opt     
 * 时间:2020年5月14日 下午4:46:41   
 * K_OPT算法
 */
public class k_opt {

	public static int[] kopt(int[] initPath,double [][]dists,int optcount,int k) {//分为k段，每段选两个数，并逆置中间的数，择优
		int n = dists.length;//长度
		int s = (n-1)/k;//分为k段,每段s.0点不参与
		double initValue = BasicOperation.getDistFromPath(initPath, dists);
		double bestValue = initValue;
		int count=1;
	
		while(count<=optcount) {
			//保留数据
			//int []temppath = initPath.clone();
			
			for(int i = 0;i<k;i++) {
				
				int start = i * s+1;//从1开始
				int end = start + s;
			
				int []temppath = initPath.clone();
				initPath = twooptswap(initPath,start,end);
				initValue = BasicOperation.getDistFromPath(initPath, dists);
				if(initValue < bestValue) {
					
					bestValue = initValue;
					count=1;
					
				}else {
				
					initPath = temppath.clone();
					
				}
				
			}
		
			if((n-1)%k >= 2) {//如果剩余的数目大于2，说明也可以进行调整 S++
				
				int start = k*s+1;
				int end = n-1;
				
				int []temppath = initPath.clone();
				initPath = twooptswap(initPath,start,end);
				initValue = BasicOperation.getDistFromPath(initPath, dists);

				if(initValue < bestValue) {
					
					bestValue = initValue;
					count=1;
					
				}else {
				
					initPath = temppath.clone();
					
				}

			}
			
			count++;
//			initValue = BasicOperation.getDistFromPath(initPath, dists);
//			if(initValue < bestValue) {
//				bestValue = initValue;
//				count=1;
//				
//			}else {
//			
//				initPath = temppath.clone();
//				count++;
//			}
			
		}
		return initPath;
		
		
	}
	public static int[] twooptswap(int[]initpath,int start,int end) {//逆置两个数之间的路径，不择优
		int first = (int)(Math.random() * (end-start)+start);
		int second = (int)(Math.random() * (end-start)+start);
		if(end-start == 1) {
			first = start;
			second = end ;
		}
		while(first == second) {
			second = (int)(Math.random() * (end-start)+start);
		}
		
		if(second < first) {
			int temp = second;
			second = first;
			first = temp;
		}
		int left = first;
		int right = second;
		while(left<right) {//逆置两个点之间的路径
			swap(initpath,left,right);
			left++;
			right--;
		}
		return initpath;
	}
	public static void swap(int[] initPath,int i,int j) {
		int temp = initPath[i];
		initPath[i] = initPath[j];
		initPath[j] = temp;
	}

}
