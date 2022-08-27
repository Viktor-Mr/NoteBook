package day5.homework;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Scanner;

public class one {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int password,i=0;
        do {
            i++;
            System.out.print("请输入第" + i +"次密码：");
            password = sc.nextInt();
            if( password==123)
                break;
        }while(i<3 );
        if(password==123)
            System.out.println("登录成功");
        else
            System.out.println("登录失败，银行卡已被锁定");

    }
}
