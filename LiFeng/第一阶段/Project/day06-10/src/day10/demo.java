package day10;

import java.util.Scanner;

public class demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String []p = s.split(" ");
        System.out.println("这个是个数组长度为" + p.length + "的数组 ---");
        for(int i=0; i< p.length; i++){
            System.out.print(p[i] + "\t");
        }
    }
}
