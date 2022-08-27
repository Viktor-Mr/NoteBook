package day3.HomeWork;


import java.util.Scanner;

public class numbertemp {
    public static void main(String[] args){
        Scanner  sc = new Scanner(System.in);
        System.out.print("请输入数值num_one：");
        int num_one = sc.nextInt();
        System.out.print("请输入数值num_two：");
        int num_two = sc.nextInt();

        num_one = num_one^num_two;
        num_two = num_one^num_two;
        num_one = num_one^num_two;

        System.out.println("num_one:"+num_one + ",num_two:" + num_two );

    }
}
