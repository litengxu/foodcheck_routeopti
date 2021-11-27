package com.bjfu.fcro.logic.wjx;


/*
 * 用于穷举搜索的全排列类
 */
class Permutate{
	private int total = 0;
	private int[] marks = null;
	private boolean[] flagUsed = null;

	public Permutate(int total, int elite){
		this.total = total;
		marks = new int[elite];
		marks[0] = -1;
		flagUsed = new boolean[total];
	}
	
	public boolean step(){
		int elite = marks.length;
		if (marks[0] < 0){
			for (int i=0; i<elite; i++){
				marks[i] = i;
				flagUsed[i] = true;
			}
			return true;
		}
		int lastNotFull = -1;
		int nextMark = -1;
		outer:
		for (int i=elite-1; i>=0; i--){
			for (int j=marks[i]+1; j<total; j++){
				if (!flagUsed[j]){
					lastNotFull = i;
					nextMark = j;
					break outer;
				}
			}
			flagUsed[marks[i]] = false;
		}
		if (lastNotFull < 0)
			return false;
		flagUsed[marks[lastNotFull]] = false;
		marks[lastNotFull] = nextMark;
		flagUsed[marks[lastNotFull]] = true;
		int pointer = lastNotFull;
		for (int i=0; i<total; i++){
			if (!flagUsed[i]){
				pointer++;
				if (pointer >= elite)
					break;
				marks[pointer] = i;
				flagUsed[i] = true;
			}
		}
		return true;
	}

	public int[] getCurrMarkRef(){
		return marks;
	}
}

class DealDist {
	public static double getDistFromPath(int[] path, double[][] dists){
		int n = path.length;
		double distSum = 0.0;
		for (int i=0; i<n-1; i++){
			distSum += dists[path[i]][path[i+1]];
		}
		distSum += dists[path[n-1]][path[0]];
		return distSum;
	}
	private static int[] greedySearch(double[][] dists){
		int n = dists.length;
		boolean[] flags = new boolean[n];
		flags[0] = true;
		int[] marks = new int[n];
		marks[0] = 0;
		for (int i=1; i<n; i++){
			int to = -1;
			int from = marks[i-1];
			double min = Double.POSITIVE_INFINITY;
			for (int j=0; j<n; j++){
				if (flags[j])
					continue;
				if (j == from)
					continue;
				if (dists[from][j] < min){
					min = dists[from][j];
					to = j;
				}
			}
			flags[to] = true;
			marks[i] = to;
		}
		return marks;
	}
	private static int[] permutateSearch(double[][] dists){
		int n = dists.length;
		Permutate permutate = new Permutate(n, n);
		int[] bestPath = new int[n];
		double minDistSum = Double.POSITIVE_INFINITY;
		while( permutate.step() ){
			int[] marks = permutate.getCurrMarkRef();
			double currDistSum = getDistFromPath(marks, dists);
			if (currDistSum < minDistSum){
				minDistSum = currDistSum;
				for (int j=0; j<n; j++)
					bestPath[j] = marks[j];
			}
		}
		return bestPath;
	}
	public static int[] optimalPath(double[][] dists){
		int n = dists.length;
		if (n <= 8)
			return permutateSearch(dists);
		else
			return greedySearch(dists);
	}
	public static void main(String[] args){
		double[][] dists = { {1, 3, 2}, {5, 1, 4}, {3, 6, 5}};
		int[] path = optimalPath(dists);
		double distSum = getDistFromPath(path, dists);
		System.out.println(distSum);
		System.out.println(java.util.Arrays.toString(path));
	}
}