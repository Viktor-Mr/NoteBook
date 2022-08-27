package day09.homework;

import java.util.Scanner;

public class Test4 {
    public static void main(String[] args){
        Taper ta = new Taper();
        System.out.println(ta.toString());
        System.out.println("初始化的圆锥体积是"+ta.bulk());

        Scanner sc = new Scanner(System.in);
        System.out.print("请输入新的高度：");
        int new_high = sc.nextInt();
        ta.setHigh(new_high);
        System.out.print("请输入新的半径:");
        int new_radius = sc.nextInt();
        ta.setRadius(new_radius);
        System.out.println(ta.bulk());

    }
}
