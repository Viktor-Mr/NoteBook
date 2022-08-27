package day4;

import javax.swing.*;
import java.util.Scanner;

public class switch_case {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String grade = sc.next();
        switch (grade){
            case"A":
                System.out.println("优秀");
                break;
            case"B":
                System.out.println("良好");
                break;
            case"C":
                System.out.println("中等");
                break;
            case"D":
                System.out.println("及格");
            case"E":
                System.out.println("不及格");
            default:
                System.out.println("输入错误");
        }
    }

}
