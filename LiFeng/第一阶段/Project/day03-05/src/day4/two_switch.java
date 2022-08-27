package day4;

import java.util.Scanner;

public class two_switch {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入月份：");
        if(sc.hasNextInt()){
            int grade = sc.nextInt();
            switch (grade){
                case 2:
                case 3:
                case 4:
                    System.out.println(grade+"春天");
                    break;
                case 5:
                case 6:
                case 7:
                    System.out.println(grade+"是夏天");
                    break;
                case 8:
                case 9:
                case 10:
                    System.out.println(grade+"是秋天");
                    break;
                case 11:
                case 12:
                case 1:
                    System.out.println(grade+"月是冬天");
                    break;
                default:
                    System.out.println("输入的数字超过范围");
            }
        }
        else{
            System.out.println("输入的不是数字");
            System.exit(0);

        }

        System.out.println("test ");

    }
}
