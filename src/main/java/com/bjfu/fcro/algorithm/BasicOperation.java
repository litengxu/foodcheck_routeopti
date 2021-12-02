package com.bjfu.fcro.algorithm;


import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicOperation {
	/**
	 * path 路径顺序
	 * dists 各个点间的花费矩阵
	 *
	 * 用于获得该路径的时间花费
	 */
	public static double getDistFromPath(int[] path, double[][] dists){
		int n = path.length;
		double distSum = 0.0;
		for (int i=0; i<n-1; i++){
			distSum += dists[path[i]][path[i+1]];
		}
		distSum += dists[path[n-1]][path[0]];
		return distSum;
	}
	/**
	 * 这是用来计算时变算法的适应度函数,当出现跨越时间段的情况时，只按照出发时间点开始算
	 *
	 * path 路径顺序
	 *
	 * List<DistInDifTime>
	 * */
	public static double getDistFormPathByDifTime(double curTime,int[] path, List<DistInDifTime> Dlist){
		double distSum = 0.0;
		int n = path.length;
		for (int i = 0; i < n-1; i++) {
			for (int j = 0; j < Dlist.size(); j++) {
				DistInDifTime distInDifTime = Dlist.get(j);
				double startTime = distInDifTime.getStartTime();
				double endTime = distInDifTime.getEndTime();
				double times[][] = distInDifTime.getTimes();
				//如果目前的时间处于设定的时间段中，则使用此时间段的时间花费
				if(curTime >= startTime && curTime < endTime){
					distSum += times[path[i]][path[i+1]];
					curTime += times[path[i]][path[i+1]];
					curTime %= 24;
					System.out.println(distSum);
					break;
				}
			}
		}
		for (int j = 0; j < Dlist.size(); j++) {
			DistInDifTime distInDifTime = Dlist.get(j);
			double startTime = distInDifTime.getStartTime();
			double endTime = distInDifTime.getEndTime();
			double times[][] = distInDifTime.getTimes();
			//如果目前的时间处于设定的时间段中，则使用此时间段的时间花费
			if(curTime >= startTime && curTime < endTime){
				distSum += times[path[n-1]][path[0]];
				curTime += times[path[n-1]][path[0]];
				curTime %= 24;
				break;
			}
		}

		return distSum;
	}

	/**
	 *
	 * 用来计算时变算法的适应度函数，出现跨越时间段的情况是按比例算
	 *
	 * 例如：
	 * 		时间段  8_____v=3____10_____v=4______12____v=5_____14
	 *		当前时间 9 总路程 20(取坐标之间的距离，可以固定)
	 *		则时间花费为  (10-9) + (12-10) + 9/5
	 * 		路程			1*3=3     2*4=8    20-8-3=9
	 *
	 * */
	public static double getDistFormPathByDifTime2(double curTime,int[] path,double euddists[][], List<DistInDifTime> Dlist){

		int n = path.length;
		double res = 0.0;
		double tempTime = curTime;//记录开始时的时间
		for (int i = 0; i < n-1; i++) {
 			double remaindist = euddists[path[i]][path[i+1]];//当前点到下一点的剩余未行驶距离

			while(remaindist > 0.0){
				for (int j = 0; j < Dlist.size(); j++) {
					DistInDifTime distInDifTime = Dlist.get(j);
					double startTime = distInDifTime.getStartTime();
					double endTime = distInDifTime.getEndTime();

					double speeds[][]  = distInDifTime.getSpeeds();
					if(tempTime >= startTime && tempTime < endTime){

						double speed = speeds[path[i]][path[i+1]];
						double remaintime = endTime - tempTime;//当前时间段内剩余时间
						double tempdist = remaintime * speed;//当前时间段内剩余时间可行使距离
//						System.out.println("remaindist:= "+remaindist);
//						System.out.println("tempdist:="+tempdist);
//						System.out.println("remaintime: "+remaintime);
						if(remaindist <= tempdist){//当前剩余为行驶距离小于当前时间段内可行使距离，说明在当前时段内可以到达目的地
							res += remaindist/speed;
							tempTime += remaindist/speed;
							remaindist = 0;
							break;
						}else{
							res += remaintime;
							tempTime += remaintime;
							remaindist -= tempdist;
						}
//						System.out.print("speed= "+speed);
//						System.out.print(" tempTime = "+tempTime);
//						System.out.print(" remaindist = "+remaindist);
//						System.out.print(" res ="+res);
//						System.out.println();
					}
					tempTime %= 24;
				}
			}

		}
//		double remaindist = euddists[path[n-1]][path[0]];//当前点到第一个点的剩余未行驶距离
		double remaindist = euddists[path[n-1]][0];//因为最后一个点一定为0，可以改写成这个，方便计算抽检途中path首点不是0的
		while(remaindist > 0){
			for (int j = 0; j < Dlist.size(); j++) {
				DistInDifTime distInDifTime = Dlist.get(j);
				double startTime = distInDifTime.getStartTime();
				double endTime = distInDifTime.getEndTime();
				double speeds[][] = distInDifTime.getSpeeds();
				if(tempTime >= startTime && tempTime < endTime){
					double speed = speeds[path[n-1]][0];//最后一点一定为0
					double remaintime = endTime - tempTime;//当前时间段内剩余时间
					double tempdist = remaintime * speed;//当前时间段内剩余时间可行使距离
					if(remaindist <= tempdist){//当前剩余为行驶距离小于当前时间段内可行使距离，说明在当前时段内可以到达目的地
						res += remaindist/speed;
						tempTime += remaindist/speed;
						remaindist = 0;
					}else{
						res += remaintime;
						tempTime += remaintime;
						remaindist -= tempdist;
					}
//					System.out.print("speed= "+speed);
//					System.out.print(" tempTime = "+tempTime);
//					System.out.print(" remaindist = "+remaindist);
//					System.out.print(" res ="+res);
//					System.out.println();
				}
				tempTime %= 24;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		//测试时变算法的适应度函数
		double times1[][] = {
				{0,4,5,8},
				{4,0,6,7},
				{5,6,0,4},
				{8,7,4,0}
		};
		double times2[][] = {
				{0,2,4,12},
				{2,0,3,6},
				{4,3,0,10},
				{12,6,10,0}
		};
		int path[] = {0,1,2,3};
//		List<DistInDifTime> Dlist = new ArrayList<>();
//		DistInDifTime distInDifTime1 = new DistInDifTime(times1,0,5);
//		DistInDifTime distInDifTime2 = new DistInDifTime(times2,5,50);
//		Dlist.add(distInDifTime1);
//		Dlist.add(distInDifTime2);
//		System.out.println(getDistFormPathByDifTime(0,path,Dlist));

		//测试分段的
		double times3[][] = {
				{0,6,4,3},
				{6,0,5,4},
				{4,5,0,7},
				{3,4,7,0}
		};
		double dists1[][] = {
				{0,3,4,5},
				{3,0,6,2},
				{4,6,0,1},
				{5,2,1,0}
		};
		double dists2[][] = {
				{0,8,4,5},
				{8,0,2,2},
				{4,2,0,3},
				{5,2,3,0}
		};
		double dists3[][] = {
				{0,1,5,5},
				{1,0,7,2},
				{5,7,0,2},
				{5,2,2,0}
		};
		double euddists[][] = {
				{0,25,12,10},
				{25,0,8, 9},
				{12,8,0, 6},
				{10,9,6, 0}
		};
		DistInDifTime distInDifTime1 = new DistInDifTime(dists1,times1,0,5);
		DistInDifTime distInDifTime2 = new DistInDifTime(dists2,times2,5,15);
		DistInDifTime distInDifTime3 = new DistInDifTime(dists3,times3,15,24);
		List<DistInDifTime> list = new ArrayList<>();
		list.add(distInDifTime1);
		list.add(distInDifTime2);
		list.add(distInDifTime3);
		System.out.println(getDistFormPathByDifTime2(0,path,euddists,list));
	}
	public static  void print(double a[][]){
		for (int i = 0; i < a.length; i++) {

				System.out.println(Arrays.toString(a[i]));

		}
		System.out.println();
	}
}