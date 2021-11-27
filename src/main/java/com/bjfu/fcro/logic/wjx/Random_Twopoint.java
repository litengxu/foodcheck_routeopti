package com.bjfu.fcro.logic.wjx;

/**
 * @author LTX
 * Tsp 
 * random_twopoint     
 * 时间:2020年5月17日 上午11:05:42
 * 随机排列两个数中间的城市   
 */
public class Random_Twopoint {

	public static int[] ran_twopoint(int[]path) {
		int n = path.length;
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
		
		int []temp = new int[(second-first+1)];//存下来要重排的城市
		int k = 0;
		for(int i = first;i<=second;i++) {
			temp[k++] = path[i];
		}
		Tools.randomizeArr(temp);//打乱数组顺序
		k = 0;
		for(int i = first;i<=second;i++) {
			path[i] = temp[k++];
			
		}
		
		return path;
	}
	
}
