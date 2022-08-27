import org.omg.CORBA.ARG_OUT;

import java.util.Scanner;

public class unimportant_test {
    public static void main(String[] args) {
//        byte  a  =  1;
//        short b =   2;
//        char  c =  '3';
//        int   d =  4;
//        long  e =  5;
//        float f =  6;
//        double g = 7;
//        Scanner  sc = new Scanner(System.in);
//        System.out.print("test_one:  ");
//        String num1 = sc.nextLine();
//        System.out.print("test_two:  ");
//
//        String num2 = sc.nextLine();
//        System.out.println("test_three ....");
//
//
//        System.out.println("num1 =" + num1 + "test");
//        System.out.println("num2 =" + num2 + "test");
////

//        long k=0;
//        int sum,j;
//        for(int i =1; i<=4;i++) {
//            for (j = 1,sum=1; j <= i; j++)
//                sum = sum *j;
//            k = sum + k ;
//        }
//        System.out.println(k);
//
//
//        for(int i=100;i>=1;i--)
//            System.out.print(i + " ");


//        int sum = 0;
//        for (int i = 1; i <= 100; i++) {
//            if (i % 2 == 0)
//                sum = sum + i;
//        }
//
//        System.out.println(sum);

//        int i, sum = 0;
//        for (i = 1; i <= 3; sum++)
//            sum += i;
//        System.out.println(sum);
        int a, y;
        a = 10;
        y = 0;
        do {
            a += 2;
            y += a;
            System.out.println("a=" + a + " y=" + y);
            if (y > 20)
                break;
        } while (a == 14);
}}

