package com.bjfu.fcro;

import java.util.Arrays;
import java.util.Comparator;

public class javatest {

    public static void main(String[] args) {
/*        Long nums = 9223372036854775807L;
        String a = Long.toBinaryString(nums);
        System.out.println(a.length());*/
        String s = "1-2-3";
        String []str = s.split("-");
        for (String index:str){
            System.out.println(index);
        }

        int [][]a ={{1,2},{3,4}};
        Arrays.sort(a, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1]-o1[1];
            }
        });

        for(int i=0;i<a.length;i++){
            System.out.println(a[i][1]);
        }
    }
}
