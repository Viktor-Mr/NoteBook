package com.seehope.test;

public class 自动转化课堂 {
    public static void main(String[] args){
        byte b=100;
        short s=b;
        System.out.println(s);
        int i=s;
        System.out.println(i);
        char c = 'a';
        i=c;
        System.out.println(i);
        long l=i;

        System.out.println(l);
        float f = l;

        System.out.println(f);
        double d=f;
        System.out.println(d);
         int main=600;
    //强转
        int num = (int)2.2;
        int j = (byte)num;
        System.out.println(j);
        System.out.println(b);

        int num1=10;
        System.out.println(num1);
        int num2 = num1;
        int num3 = num1 + num2;
        System.out.println(num3);


    }
}
