package day3.HomeWork;

import java.util.Scanner;

public class judgeyear {
    public static void main(String[] args){
        Scanner  sc = new Scanner(System.in);
        System.out.print("请输入年份:");
        int year = sc.nextInt();

        System.out.println( (year%4 ==0&&year%100!=0)||(year%400==0)  ?  (year+"是闰年"):(year+"不是闰年"));
    }
}
