package com.bjfu.fcro;

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
    }
}
