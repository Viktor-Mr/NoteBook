package day4;

import java.util.Scanner;

public class one_if {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入你的成绩:");
        double score = sc.nextDouble();
        System.out.println(score);
        if(score>100||score<0){
            System.out.println("成绩错误");
        }
        else if(score>90){
            System.out.println("成绩优秀");
        }
        else if(score>60){
            System.out.println("成绩及格");
        }
        else{
            System.out.println("成绩不良");
        }
    }
}
