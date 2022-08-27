package day07.homework;


import java.util.Arrays;
import java.util.Scanner;

public class student_System {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入学生的人数:");
        int stuNum = sc.nextInt();
        int[] scores = new int[stuNum];
        int sum = 0, min = 999999, max = -100000;
        int qualified = 0;
        System.out.println("请输入每位同学的分数，分数之间用空格隔开：");
        for (int i = 0; i < stuNum; i++) {
            scores[i] = sc.nextInt();
            if (min > scores[i])
                min = scores[i];
            if (max < scores[i])
                max = scores[i];
            if (scores[i] >= 60)
                qualified++;
            sum += scores[i];
        }
        System.out.println("总分为:" + sum * 1.0 + " \n平均分成绩为:" + sum * 1.0 / stuNum +
                " \n合格的人数: " + qualified + " \n最高分为: " + max + " \n最低分为: " + min);
        Arrays.sort(scores);
        System.out.println("从底到高的排序是\n" + Arrays.toString(scores));

        for (int i = 0; i < scores.length / 2; i++) {
            scores[i] = scores[i] ^ scores[scores.length - 1 - i];
            scores[scores.length - 1 - i] = scores[i] ^ scores[scores.length - 1 - i];
            scores[i] = scores[i] ^ scores[scores.length - 1 - i];
        }
        System.out.println("从高到低的排序是\n" + Arrays.toString(scores));

    }
}


//逆序的接口