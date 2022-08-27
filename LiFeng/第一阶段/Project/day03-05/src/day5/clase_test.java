package day5;

import java.util.Scanner;

public class clase_test {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        int i=1,score,sum=0;
        do{
            System.out.print("请输入第"+ i +"门课的成绩:");
            i++;
             score = sc.nextInt();
             if(score<=80)
                 continue;
               sum++;

        }while(i<=5);
        System.out.println(sum);



//        int sum=0,score;
//        for(int i=1; i<=5;i++){
//            System.out.print("请输入第"+ i +"门课的成绩:");
//            score = sc.nextInt();
//            if(score<80)
//                continue;
//              sum++;
//        }
//        System.out.println(sum);
    }
}
