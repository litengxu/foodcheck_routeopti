package com.bjfu.fcro.algorithm;

import java.util.Arrays;

/**
 * @Program: foodcheck_routeopti
 * @ClassName: DoSort
 * @Author: 李腾旭
 * @Date: 2021-01-27 22:15
 * @Description: ${todo}
 * @Version: V1.0
 */
public class DoSort {

    public static int[][] sort(int [][]groups,double [][]dists){
        for(int i=0;i<groups.length;i++){
            // 将出发点插入路线首位
            int[] tempGroup = Grouping.addInit(groups[i]);
            // 得到该组路线的距离矩阵
            double[][] partDists = Grouping.getPartDists(dists, tempGroup);
            // 调用贪心算法得到最优路径
            int[] path = GreedySearch.greedySearch(partDists);
            // 按照已排好序的索引，调整数组
            // 例：groups[0]= 3,5,4 path 0,1,3,2 => groups[0] = 3,4,5
            int temp[] = Arrays.copyOf(groups[i],groups[i].length);
            for (int j = 1; j < path.length; j++) {
                groups[i][j-1] = temp[path[j] - 1];
            }
        }
        return groups;
    }
}
