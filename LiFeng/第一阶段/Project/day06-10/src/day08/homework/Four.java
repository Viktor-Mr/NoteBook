package day08.homework;

import java.util.Scanner;

public class Four {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入计算数据  如 x * y :");
        double x = sc.nextDouble();
        char  o = sc.next().charAt(0);
        double y = sc.nextDouble();
        FourUse  test = new FourUse();
        switch (o){
            case '+':
                System.out.println(x + " + " + y + " = " + test.Add(x,y));
                break;
            case '-':
                System.out.println(x + " - " + y + " = " + test.subtract(x,y));
                break;
            case '*':
                System.out.println(x + " * " + y + " = " + test.multiply(x,y));
                break;
            case '/':
                if(x == 0){
                    System.out.println("X不能是零");
                    break;
                }
                System.out.println(x + " / " + y + " = " + test.divide(x,y));
                break;

             default:
                 System.out.println("你输入的计算方式有误");
        }

    }
}

class FourUse{
    int x,y;
    public  double  Add(double x,double y){
        return x+y;
    }
    public double  subtract(double x,double y){
        return x-y;
    }
    public  double  multiply(double x,double y){
        return x*y;
    }
    public  double  divide(double x,double y){
        return (int)(x/y*100.0)/100.0;
    }
}