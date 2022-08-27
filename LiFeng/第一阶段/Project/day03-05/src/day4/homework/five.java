package day4.homework;

import java.util.Scanner;

public class five {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入需要判断的数字: ");
        int num = sc.nextInt();
        // 除于一个数等于零 而且不是1和它本身就不是素数
        int k = (int)(Math.sqrt(num)+1) , j=0;
        for(int i=2;i<= k; i++){
            if(num%i==0 && num!=i ){
                j=1;
                break;
            }
        }
        if(j==1)
            System.out.print("这个数是不素数");
        else
            System.out.print("这个数是素数");
    }
}
