package day06;

import javax.print.DocFlavor;

public class E_Array {
    public static void main(String[] args){
        int[] arr = new int[]{ 10,20,30,40};
        System.out.println("arr的长度- " +arr.length);
        System.out.println("arr的地址- " +arr );
        System.out.println("arr[0] =" + arr[0]);
        System.out.println("arr[1] = " + arr[1]);
        System.out.println("arr[2] =" + arr[2]);
        System.out.println("arr[3] = " + arr[3]);

        String[] str = new String[]{"张三" ,"李四"};
        System.out.println("\nstr的长度- " +str.length);
        System.out.println("str的地址- " +str );
        System.out.println("str[0] =" + str[0]);
        System.out.println("str[1] = " + str[1]);

        int [] scores;
        scores = new int[3];
        scores[0] = 80;
        scores[2] = 90;

        String[] names  = new String[3];
        names[0] = "张无忌";
        names[1] = "李寻欢";
        names[2] = "施拉姆";

        System.out.println(names[0] +"的分数是" + scores[0]);
        System.out.println(names[1] +"的分数是" + scores[1]);
        System.out.println(names[2] +"的分数是" + scores[2]);


        int[] array = {1,2,3};
        int[] ar = array;
        array = null;     //null 抹去连接线
        System.out.println(ar[0]);
        System.out.println("{i}");



    }
}
