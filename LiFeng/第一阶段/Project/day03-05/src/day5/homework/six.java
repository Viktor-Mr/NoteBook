package day5.homework;

import javax.sound.midi.SoundbankResource;
import java.util.Scanner;

public class six {
    public static int fibNum(int i) {
        if (i == 1 || i == 2) {
            return 1;
        } else {
            int t = fibNum(i - 1) + fibNum(i - 2);
            return t;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("你想知道求的月份的兔子数量\\月份：");
        int month =sc.nextInt();
            int u = fibNum(month);
        System.out.print("兔子的数量是：" + u);
    }
}