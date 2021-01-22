package com.bjfu.fcro.logic;

import java.io.*;

/*
 * ������
 */
public class Tools {
	public static void randomizeArr(int[] arr) {
		int n = arr.length;
		for (int i = 1; i < n; i++) {
			int pos = (int) (Math.random() * (n - 1) + 1);
			int temp = arr[i];
			arr[i] = arr[pos];
			arr[pos] = temp;
		}
	}

	public static String readFileText(String filePath) {
		System.out.println("\n"+filePath);
		File f = new File(filePath);
		if (!f.exists())
			return null;
		try {
			String content = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
			// BufferedReader br = new bufferedReader(new InputStreamReader(new
			// FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null)
				content += line + "\r\n";
			br.close();
			content = content.trim();
			return content;
		} catch (IOException ioe) {
			return null;
		}
	}

	public static void main(String[] args) {
		int[] arr = new int[5];
		for (int i = 0; i < 5; i++)
			arr[i] = i;
		randomizeArr(arr);
		System.out.println(java.util.Arrays.toString(arr));
	}
}