package day3;

import java.util.Scanner;

public class test_two {
    public static void main(String[] args){
        Scanner   sc =  new Scanner(System.in);
        System.out.println("请输入分数：");
        int score;
        score =sc.nextInt();

        String grade;
        grade = (score>100 || score<0)?"错误成绩": (score>90)?("成绩A"): (score>68)? "成绩B":"成绩C";
        System.out.println(grade);
    }


}
