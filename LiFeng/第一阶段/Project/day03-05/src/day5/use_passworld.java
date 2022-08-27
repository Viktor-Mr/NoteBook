package day5;

import javax.swing.*;
import java.util.Scanner;

public class use_passworld {
    public static void main(String[] args){
        Scanner  sc = new Scanner(System.in);
        System.out.print("请输入用户名: ");
        String use_name = sc.next();
        String use_password;
        do {
            System.out.print("请输入用户密码：");
             use_password = sc.next();
        }while(!use_password.equals("123"));


    }
}
