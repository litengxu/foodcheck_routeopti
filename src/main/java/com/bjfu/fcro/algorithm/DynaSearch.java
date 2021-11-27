package com.bjfu.fcro.algorithm;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
*
*  DynaSearch()
*
* */
public class DynaSearch {

    /**
     *
     * 对长度为初始路径，依次对0-2，0-3，，，，0-n做独立的kopt，并把上次的结果传到下一次
     *  time_sharing 是否使用跨时段适应度函数
     * */
    public static int[] dynaSearch(int path[],double dists[][],int iterors,int k,boolean time_sharing, List<DistInDifTime> Dlist){
        int n = path.length;
        if(n == 2) return path;

        int res[] = new int[n];//暂存数组
        res[0] = path[0];
        res[1] = path[1];
        for (int i = 2; i < n; i++) {
            res[i] = path[i];
            int temppath[] = Arrays.copyOfRange(res,0,i+1);
            if(time_sharing){
                temppath = Kopt.kopt2(temppath,dists,iterors,k,time_sharing,Dlist);
            }else{
                temppath = Kopt.kopt(temppath,dists,iterors,k);
            }


            for (int j = 0; j < temppath.length; j++) {
                res[j] = temppath[j];
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int path[] = {1,2,4,5,6,7,8};
        double dists[][] = {};
        dynaSearch(path,dists,10,1,false,null);
        System.out.println(Arrays.toString(Kopt.twooptswap(path,0,6)));
        int temppath[] = Arrays.copyOfRange(path,0,1);
        path[0] = 0;
        System.out.println(Arrays.toString(temppath));
    }
}
