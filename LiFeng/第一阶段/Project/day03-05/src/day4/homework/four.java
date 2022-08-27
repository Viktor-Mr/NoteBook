package day4.homework;

import java.util.Scanner;

public class four {
    public static void main(String[] args){
        int max=0,num=0,sum=0;
        for(int i=1; i<=5; i++){
            System.out.print("请输入第" + i + "门分数：");
            Scanner sc=new Scanner(System.in);
            num=sc.nextInt();
            if(i==1){
                max=num;
                sum = sum + num;
            }

            else{
                sum = sum + num;
                if(num>max)
                {
                    max =num;
                }
            }

                   ;
        }
        System.out.println("最大的数是：" + max+ "\n数总和是"+sum +"\n平均分是"+sum*1.0/5);
    }
}
