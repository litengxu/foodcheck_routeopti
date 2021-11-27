package com.bjfu.fcro.algorithm;

import java.io.*;

/*
 * 工具类
 */
public class Tools {
	public static void randomizeArr(int[] arr){
		int n = arr.length;
		for (int i=0; i<n; i++){
			int pos = (int)(Math.random() * n);
			int temp = arr[i];
			arr[i] = arr[pos];
			arr[pos] = temp;
		}
	}
	
	public static String readFileText(String filePath){
		File f = new File(filePath);
		if (!f.exists())
			return null;
		try{
			String content = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
//			BufferedReader br = new bufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ( (line = br.readLine()) != null)
				content += line + "\r\n";
			br.close();
			content = content.trim();
			return content;
		}catch(IOException ioe){
			return null;
		}
	}
	
	public static void main(String[] args){
		int[] arr = new int[5];
		for (int i=0; i<5; i++)
			arr[i] = i;
		randomizeArr(arr);
		System.out.println(java.util.Arrays.toString(arr));
	}
}