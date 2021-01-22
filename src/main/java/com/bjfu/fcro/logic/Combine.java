package com.bjfu.fcro.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Combine {

	public static void main(String[] path) {

		// 系统标识量
		boolean isContinue = true;

		// TODO Auto-generated method stub
		if (path == null || path.length == 0) {
			System.out.println(">>>>>>>>>>请拖入数据样本文件！<<<<<<<<<<");
			return;
		}

		double[][] points = readPoints(path[0]);
		double[][] dists = getDists(points);

		String[] methodGroup = new String[] { "贪婪算法", "对换算法", "局部调整", "遗传算法", "动态规划" };

		System.out.println("\n>>>>>>>>>>样本容量为" + dists.length);

		Scanner scanner = new Scanner(System.in);

		while (isContinue) {

			System.out.println("");

			System.out.println("请输入测试容量(<=" + dists.length + "):");

			int count = scanner.nextInt();

			double[][] testDists;

			if (count == dists.length)
				testDists = dists;
			else
				testDists = new double[count][count];

			for (int i = 0; i < count; i++)
				for (int j = 0; j < count; j++)
					testDists[i][j] = dists[i][j];

			StringBuilder title = new StringBuilder("**********[");

			for (int i = 0; i < methodGroup.length; i++) {
				title.append(" " + i + "-" + methodGroup[i] + " ");
				if (i < methodGroup.length - 1)
					title.append("|");
			}

			title.append(methodGroup.length + "-" + "综合测试 | ");

			title.append(methodGroup.length + 1 + "-" + "分组算法");

			title.append("]**********");

			System.out.println(title);

			System.out.println("请输入测试组合:");

			String combine = scanner.next();

			int[] bestPath = new int[count];

			Tools.randomizeArr(bestPath);

			// 开始计时
			long executeDuration = System.nanoTime();

			for (int m = 0; m < combine.length(); m++) {

				int element = Integer.parseInt(String.valueOf(combine.charAt(m)));

				switch (element) {
				// 贪婪算法
				case 0:

					bestPath = GreedySearch.greedySearch(testDists);

					break;
				// 置换算法
				case 1:

					bestPath = SwapSearch.swapSearch(bestPath, testDists);

					break;
				// 局部调整
				case 2:

					bestPath = TuneSearch.tuneSearch(bestPath, testDists);

					break;
				// 遗传算法
				case 3:

					bestPath = GaSearch.gaSearch(testDists).genes;

					break;
				case 4:
					String[] temp = new Dynamic(testDists).getFirnalCityFlow().split("->");

					for (int i = 0; i < count; i++)
						bestPath[i] = Integer.parseInt(temp[i]);

					break;

				case 5:
					for (int t = 0; t < 2; t++) {
						StringBuilder sb1 = new StringBuilder();
						StringBuilder sb2 = new StringBuilder();

						String testTitle1 = "";
						String testTitle2 = "";

						for (int i = 9; i <= count; i++) {
							testDists = new double[i][i];

							for (int j = 0; j < i; j++)
								for (int k = 0; k < i; k++)
									testDists[j][k] = dists[j][k];

							executeDuration = System.nanoTime();

							switch (t) {

							case 0:
								testTitle1 = "贪心算法";

								bestPath = GreedySearch.greedySearch(testDists);

								sb1.append(String.format("% 3d", i)).append(" ")
										.append(String.format("%.5f",
												(System.nanoTime() - executeDuration) / Math.pow(10, 6)))
										.append(" ").append(BasicOperation.getDistFromPath(bestPath, testDists))
										.append("\n");

								testTitle2 = "贪心+改进算法";

								bestPath = TuneSearch.tuneSearch(bestPath, testDists);

								bestPath = SwapSearch.swapSearch(bestPath, testDists);

								sb2.append(String.format("% 3d", i)).append(" ")
										.append(String.format("%.5f",
												(System.nanoTime() - executeDuration) / Math.pow(10, 6)))
										.append(" ").append(BasicOperation.getDistFromPath(bestPath, testDists))
										.append("\n");

								break;
							case 1:
								testTitle1 = "遗传算法";
								/*
								 * GaSearch.PoolSize = i;
								 * 
								 * GaSearch.SeaSize = i * 2;
								 * 
								 * GaSearch.EliteSize = i / 10;
								 * 
								 * GaSearch.GenerationNum = i * 5;
								 */

								bestPath = GaSearch.gaSearch(testDists).genes;

								sb1.append(String.format("% 3d", i)).append(" ")
										.append(String.format("%.5f",
												(System.nanoTime() - executeDuration) / Math.pow(10, 6)))
										.append(" ").append(BasicOperation.getDistFromPath(bestPath, testDists))
										.append("\n");

								testTitle2 = "遗传+改进算法";

								bestPath = TuneSearch.tuneSearch(bestPath, testDists);

								bestPath = SwapSearch.swapSearch(bestPath, testDists);

								sb2.append(String.format("% 3d", i)).append(" ")
										.append(String.format("%.5f",
												(System.nanoTime() - executeDuration) / Math.pow(10, 6)))
										.append(" ").append(BasicOperation.getDistFromPath(bestPath, testDists))
										.append("\n");
								break;

							default:
								break;
							}

						}

						try {
							String location1 = URLDecoder.decode(
									Combine.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
									"UTF-8");
							File file1 = new File(new File(location1).getParentFile().getAbsolutePath() + File.separator
									+ testTitle1 + ".txt");
							if (!file1.exists())
								file1.createNewFile();
							FileOutputStream out1 = new FileOutputStream(file1, true);
							out1.write(sb1.toString().getBytes("utf-8"));
							out1.close();

							String location2 = URLDecoder.decode(
									Combine.class.getProtectionDomain().getCodeSource().getLocation().getFile(),
									"UTF-8");
							File file2 = new File(new File(location2).getParentFile().getAbsolutePath() + File.separator
									+ testTitle2 + ".txt");
							if (!file2.exists())
								file2.createNewFile();
							FileOutputStream out2 = new FileOutputStream(file2, true);
							out2.write(sb2.toString().getBytes("utf-8"));
							out2.close();

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					return;

				case 6:
					System.out.println("输入组数：");

					int groupAmount = scanner.nextInt();

					int[][] groups = Grouping.grouping(testDists, groupAmount, (int) Math.pow(testDists.length, 2) / 2);

					System.out.println(
							"算法执行时间：" + String.format("%.5f", (System.nanoTime() - executeDuration) / Math.pow(10, 6)));

					for (int i = 0; i < groups.length; i++) {
						int[] finalGroup = Grouping.addInit(groups[i]);
						for (int j = 0; j < finalGroup.length; j++)
							System.out.print("" + finalGroup[j] + ", ");
						System.out.println();
					}

					/*
					 * List<int[]> allGroups = new ArrayList<int[]>();
					 * 
					 * for (int i = 0; i < groups.length; i++) {
					 * 
					 * for (int j = 0; j < groups[i].length; j++)
					 * System.out.print("" + groups[i][j] + ", ");
					 * System.out.println();
					 * 
					 * 
					 * allGroups.add(Grouping.addInit(groups[i])); }
					 * 
					 * StringBuilder stringBuilder = new StringBuilder();
					 * 
					 * int groupCouont = 0;
					 * 
					 * for (int[] elements : allGroups) {
					 * stringBuilder.append(groupCouont).append(":").append("\n"
					 * ); for (int point : elements) {
					 * stringBuilder.append(points[point][0]).append(" ").append
					 * (points[point][1]).append("\n"); } groupCouont++;
					 * stringBuilder.append("\n"); }
					 * 
					 * try { String location = URLDecoder.decode(
					 * Combine.class.getProtectionDomain().getCodeSource().
					 * getLocation().getFile(), "UTF-8"); File file = new
					 * File(new File(location).getParentFile().getAbsolutePath()
					 * + File.separator + count + "分组.txt"); if (file.exists())
					 * file.delete(); file.createNewFile();
					 * 
					 * FileOutputStream out = new FileOutputStream(file, true);
					 * out.write(stringBuilder.toString().getBytes("utf-8"));
					 * out.close();
					 * 
					 * System.out.println("\n文件写入成功 ===> " +
					 * file.getAbsolutePath()); } catch (IOException e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); }
					 */

					return;
				default:
					break;
				}
			}

			executeDuration = System.nanoTime() - executeDuration;

			StringBuilder method = new StringBuilder("");

			for (int n = 0; n < combine.length(); n++) {
				int element = Integer.parseInt(String.valueOf(combine.charAt(n)));

				method.append(methodGroup[element]);

				if (n < combine.length() - 1)
					method.append("+");
			}

			StringBuilder displayContent = new StringBuilder();

			displayContent.append("\n" + count).append("\n" + method)
					.append("\n" + executeDuration + "ns | " + executeDuration / Math.pow(10, 6) + "ms")
					.append("\n" + java.util.Arrays.toString(bestPath))
					.append("\n" + BasicOperation.getDistFromPath(bestPath, testDists));

			System.out.println("\n本次测试结果：");

			System.out.println("\n测试样本容量 ===> " + count);

			System.out.println("\n测试算法 ===> " + method);

			System.out.println("\n测试耗时 ===> " + executeDuration + "ns | " + executeDuration / Math.pow(10, 6) + "ms");

			System.out.println("\n最优路径 ===> " + java.util.Arrays.toString(bestPath));

			System.out.println("\n路程总长 ===> " + BasicOperation.getDistFromPath(bestPath, testDists));

			System.out.println("\n是否要保存此次执行结果？(Y/N)");

			String result = scanner.next();

			result = result.toUpperCase();

			if (result.equals("Y")) {

				try {
					String location = URLDecoder.decode(
							Combine.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
					File file = new File(new File(location).getParentFile().getAbsolutePath() + File.separator + count
							+ ")" + method + ".txt");
					if (!file.exists())
						file.createNewFile();
					FileOutputStream out = new FileOutputStream(file, true);
					out.write(displayContent.toString().getBytes("utf-8"));
					out.close();

					System.out.println("\n文件写入成功 ===> " + file.getAbsolutePath());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//
		scanner.close();

	}

	public static double[][] readPoints(String path) {
		String content = Tools.readFileText(path);
		content = content.trim();
		String[] strLines = content.split("\n");
		int n = strLines.length;
		double[][] points = new double[n][2];
		for (int i = 0; i < n; i++) {
			String[] subs = strLines[i].trim().split(" ");
			points[i][0] = Double.parseDouble(subs[0]);
			points[i][1] = Double.parseDouble(subs[1]);
		}
		return points;
	}

	public static double[][] getDists(double[][] points) {
		int n = points.length;
		double[] x = new double[n];
		double[] y = new double[n];
		for (int i = 0; i < n; i++) {
			x[i] = points[i][0];
			y[i] = points[i][1];
		}
		double[][] dists = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				double xDiff = x[i] - x[j];
				double yDiff = y[i] - y[j];
				dists[i][j] = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
			}
		}
		return dists;
	}

}
