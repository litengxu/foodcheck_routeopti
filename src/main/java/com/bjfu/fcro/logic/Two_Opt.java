package com.bjfu.fcro.logic;
/**
 * @author LTX
 * Tsp 
 * two_opt     
 * 时间:2020年5月11日 上午10:39:33   
 */
//2-opt优化算法
public class Two_Opt {

	public static int[] Two_opt(int[] initPath,double [][]dists,int optcount) {
		
		int n = initPath.length;
		double initValue = BasicOperation.getDistFromPath(initPath, dists);
		double bestValue = initValue;
		//选取两个点(1-n-1)不交换0
		int count=1;
		while(count<=optcount) {
			int first = (int)(Math.random() * (n-1)+1);
			int second = (int)(Math.random() * (n-1)+1);
			while(first == second) {
				second = (int)(Math.random() * (n-1)+1);
			}
			
			if(second < first) {
				int temp = second;
				second = first;
				first = temp;
			}
			int left = first;
			int right = second;
			while(left<right) {//逆置两个点之间的路径
				swap(initPath,left,right);
				left++;
				right--;
			}
			initValue = BasicOperation.getDistFromPath(initPath, dists);
			if(initValue < bestValue) {
				bestValue = initValue;
				count=1;
				
			}else {
				left = first;
				right = second;
				while(left<right) {
					swap(initPath,left,right);
					left++;
					right--;
				}
				count++;
			}
			
			
		}
		return initPath;
		
	}
public static int[] Two_opt_sa(int[] initPath,double [][]dists,int optcount) {//随机两个数，并逆置
	
		
		int n = initPath.length;
		double initValue = BasicOperation.getDistFromPath(initPath, dists);
		
		//选取两个点(1-n-1)不交换0
		int count=1;
		while(count<=optcount) {
			int first = (int)(Math.random() * (n-1)+1);
			int second = (int)(Math.random() * (n-1)+1);
			while(first == second) {
				second = (int)(Math.random() * (n-1)+1);
			}
			
			if(second < first) {
				int temp = second;
				second = first;
				first = temp;
			}
			int left = first;
			int right = second;
			while(left<right) {//逆置两个点之间的路径
				swap(initPath,left,right);
				left++;
				right--;
			}
			
				count++;
			
		
			
		}
		return initPath;
		
	}
	public static void swap(int[] initPath,int i,int j) {
		int temp = initPath[i];
		initPath[i] = initPath[j];
		initPath[j] = temp;
	}

}
