package com.bjfu.fcro.logic.wjx;


/**
 * @author LTX
 * Tsp 
 * Simulated_Annealing     
 * 时间:2020年5月14日 上午10:50:18   
 * 模拟退火算法
 */
public class Simulated_Annealing {
	
	public 	static int[] sa(int[] path1,double fitness1,int[] path2,double fitness2,double T) {
		
		if(fitness1 > fitness2) {//当新的路径比旧的路径短时，返回新的
			return path2;
		}else {//新的比旧的长
			System.out.println("fitness1"+fitness1);
			System.out.println("fitness2"+fitness2);
			System.out.println("T"+T);
//			System.out.printf("%.3f",Math.pow(Math.E, (fitness1-fitness2))/T);
			if(Math.random() < (Math.pow(Math.E, (fitness1-fitness2))/T)) {//满足条件，返回新的
				return path2;
			}
		}
		return path1;
	}
}
