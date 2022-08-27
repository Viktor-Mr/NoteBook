package com.seehope.test;

public class type_num {
    public static void main(String[] args){


        int num_one = 0b1101; //数字前加0（数字）b 可以使用二进制进行赋值
        int num_two = 070;    //数字前加0 （数字） 可以使用八进制进行赋值
        int num_three =0xa9;   //数字前加0（数字）x 可以使用十六进制进行赋值

        System.out.println(num_one +" "+ num_two + "  "+ num_three);
        System.out.println();
        char c1 = 'c';
        char c2 = 99;
        System.out.println(c2);

        boolean bigger,biggerTwo;
        bigger = true;
        biggerTwo = false;
        //bigger =100;

        double data10 = 1.87D;   //小数点的数默认是double;
        double data20 =1.54;
        //float data30 = 1.87;   //错误，必须要加上f才能转换为float类型
        float data40 = 1.54f;
        long dataLongone = 1000000000;
        //long datalongtwo = 10000000000;   //数据默认为int  超出int范围，需要改变默认类型
        long datalongthree =10000000000l; //在尾部加l/L可以改变interesting默认类型为long
        System.out.println(dataLongone);
        System.out.println(datalongthree);
    }
}
