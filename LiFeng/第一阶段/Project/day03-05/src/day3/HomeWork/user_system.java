package day3.HomeWork;

import java.util.Scanner;

public class user_system {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String user_name = sc.nextLine();
        System.out.print("请输入用户密码：");
        String user_password = sc.nextLine();
        String pp = user_name.equals("admin") ? (user_password.equals("12345")?"登录成功":"用户密码错误"):"用户名错误";
        System.out.println(pp);




    }
}
