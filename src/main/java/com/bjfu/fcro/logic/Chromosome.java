package com.bjfu.fcro.logic;

/*
 * 这是用于遗传算法的染色体类：
 */
public class Chromosome implements Comparable<Chromosome> {
	double fitness = Double.POSITIVE_INFINITY; // 适应度值ֵ
	int[] genes = null; // 基因序列

	public Chromosome() {
	}

	/**
	 * int[]构造方法
	 */
	public Chromosome(int[] genes) {
		this.genes = genes.clone();
	}

	/**
	 * int构造方法
	 */
	public Chromosome(int n) {
		genes = new int[n];
		for (int i = 0; i < n; i++)
			genes[i] = i;

		Tools.randomizeArr(genes);
	}
	//ltx
	public Chromosome(int n,int []path){
			
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

//			for (int i=0; i<n; i++)
//				genes[i] = i;
//			Tools.randomizeArr(genes);
//			for(int i=0;i<n;i++) {//修改第一个城市为0
//				if(genes[i] == 0) {
//					genes[i] = genes[0];
//					genes[0] = 0;
//				}
//			}
		}
	public String toString() {
		return "" + fitness + "; " + java.util.Arrays.toString(genes);
	}

	/**
	 * 染色体的克隆方法（即所有位点信息相同，且适应值也相同）
	 */
	public Chromosome clone() {
		Chromosome copy = new Chromosome(this.genes);
		copy.fitness = this.fitness;
		return copy;
	}

	/**
	 * 继承Comparable接口，重写该方法（才可调用java.util.Arrays.sort）
	 */
	public int compareTo(Chromosome that) {
		// 若当前染色体适应值小于要比较的染色体适应值则返回-1
		if (this.fitness < that.fitness)
			return -1;
		// 若当前染色体适应值大于要比较的染色体适应值则返回1
		if (this.fitness > that.fitness)
			return 1;
		// 若当前染色体适应值等于要比较的染色体适应值则返回0
		return 0;
	}

	/*
	 * 此方法用于序列调整。如果两个染色体交叉后，则有可能两条染色体已经不是完整路径了 用此方法调整
	 */
	public void modify() {
		// 获取基因数量
		int n = this.genes.length;
		// 标识是否重复出现
		boolean[] isRepeated = new boolean[n];
		// 记录各位点值的出现次数
		int[] counts = new int[n];
		int repeatNum = 0;
		// 循环遍历每个位点
		for (int i = 0; i < n; i++) {
			// 该位点值出现次数自增
			counts[genes[i]]++;
			// 若该位点值重复出现（即出现次数》1）
			if (counts[genes[i]] > 1) {
				// 记录该位点为重复
				isRepeated[i] = true;
				// 重复数量自增
				repeatNum++;
			}
		}
		// 若没有重复的位点则直接返回
		if (repeatNum == 0)
			return;
		// 记录缺失的位点值数组
		int[] repeats = new int[repeatNum];
		// 从0下标开始检索
		int pointer = 0;
		// 遍历现有所有位点
		for (int i = 0; i < n; i++) {
			// 若该位点值未曾出现则记录为缺失值
			if (counts[i] == 0) {
				// 将缺失值按照顺序存入
				repeats[pointer] = i;
				// 下一缺失值的下标
				pointer++;
			}
		}
		// 将得到的缺失值数组进行随机排列
		Tools.randomizeArr(repeats);
		// 默认从0按顺序将缺失值填入重复位点
		pointer = 0;
		// 遍历所有位点
		for (int i = 0; i < n; i++) {
			// 若该点未重复，则直接进行下一次循环
			if (!isRepeated[i])
				continue;
			// 如果该位点值重复，则替换为缺失值
			genes[i] = repeats[pointer];
			// 下一需替换的缺失值的下标
			pointer++;
		}
	}

	/*
	 * 染色体交叉：
	 */
	public void cross(Chromosome that) {
		// 获取基因数量
		int n = this.genes.length;
		// 若长度小于等于1则不进行操作，直接返回
		if (n <= 1)
			return;
		// 指定交叉长度（从左侧起随机得出）
		int leftLen = (int) ((n - 1) * Math.random()) + 1;// (n - 1) + 1
															// 保证交叉长度至少为1
		// 进行交叉
		for (int i = 0; i < leftLen; i++) {
			// 进行交换
			int temp = this.genes[i];
			this.genes[i] = that.genes[i];
			that.genes[i] = temp;
		}
		// 序列调整
		this.modify();
		that.modify();
	}
	//只改变this染色体的交叉
		public void singlecross(Chromosome that) {
			int n = this.genes.length;
			if(n<=1) {
				return;
			}
//			int leftLen = (int)(((n-1)/2) * Math.random()) + (n-1)/2;
//			int low =  (int)(((n-1)/2) * Math.random())+1;
//			for(;low<leftLen;low++) {
//				this.genes[low] = that.genes[low];
//			}
			int leftLen = (int)((n-1) * Math.random()) + 1;
			for (int i=1; i<leftLen; i++){//修改第一个城市为0
				this.genes[i] = that.genes[i];
			}
			this.modify();
		}
	/*
	 * 染色体突变：
	 */
	public void mutate() {
		// 获取基因数量
		int n = this.genes.length;
		// 若长度为1则不进行操作，直接返回
		if (n == 1)
			return;
		// 随机出需要突变的基因位点
		int pos1 = (int) (Math.random() * n);
		int pos2 = (int) (Math.random() * n);
		// 如果位点相同，则直接返回
		if (pos1 == pos2)
			return;
		// 交换两位点基因
		int temp = genes[pos1];
		genes[pos1] = genes[pos2];
		genes[pos2] = temp;
	}

	/*
	 * 获取适应度函数值。这个值就是路径对应的总路程。
	 */
	public void getFitness(double[][] dists) {
		this.fitness = BasicOperation.getDistFromPath(genes, dists);
	}
}