package day5.homework;
import javax.sound.midi.SoundbankResource;
import java.util.Scanner;
public class two {
    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);
        int sum_min = 999999,sum_max=-1,sum_sum=0;
        int max=-1,min=999999;
        for (int i=1; i<=3; i++){
            int sum=0;
            System.out.print("请输入第" + i + "个学生的姓名：");
            String name = sc.next();
            for(int j=1; j<=5; j++){
                System.out.print("请输入" + name + "同学第" + j + "门成绩：");
                int score = sc.nextInt();
                if(score>max)
                    max =score;
                if(score<min)
                    min = score;
                sum = sum +score;
                if(score>sum_max)
                    sum_max =score;
                if(score<sum_min)
                    sum_min = score;
                sum_sum = sum_sum +score;
        }
            System.out.println("学生" +name +"五门成绩总分：" +sum+ ",平均分：" + sum*1.0/5 + ",最高分：" + max + ",最低分： "+ min + ".");
    }
        System.out.println("\n全班学生五门成绩总分：" +sum_sum+ ",平均分：" + sum_sum*1.0/5 + ",最高分：" + sum_max + ",最低分： "+ sum_min + ".");
    }
}
