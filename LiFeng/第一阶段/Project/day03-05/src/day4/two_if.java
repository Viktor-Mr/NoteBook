package day4;

import java.util.Scanner;

public class two_if {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("是否为会员yes\\no:");
        String if_vip = sc.nextLine();
        System.out.print("购物金额是:");
        int money  = sc.nextInt();
        if ( if_vip.equals("yes")||if_vip.equals("YES")||if_vip.equals("Yes")){
            if(money >= 200){
                System.out.println("你最终的购物金额为" + money*0.8 + "元");
            }
            else
                System.out.println("你最终的购物金额为" + money*0.9 + "元");
        }
        else{
            if(money>=200)
                System.out.println("你最终的购物金额为" + money*0.95 + "元");
            else
                System.out.println("你最终的购物金额为" + money + "元");
        }
    }
}
