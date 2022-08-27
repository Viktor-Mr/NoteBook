package day5;

import javax.swing.*;
import java.util.Scanner;

public class use_while {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("是否进行跑步y/n ：");
        String i = sc.next();
        int j=0;
        while(i.equals("y".toUpperCase())){
            j++;
            System.out.print("是否进行跑：");
            i=sc.next();
        }
        if(j==0)
            System.out.println("你不行啊，你是不是虚啊");
        else
            System.out.println("你跑了" + j+ "圈，好好休息吧");
    }
}
