package day14.HomeWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class One {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("输入（格式 yyyy/MM/dd）;");
        String dateStr1 = sc.nextLine();                                          //输入字符串
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(dateStr1);

        //方法一
        System.out.println((date.getYear() +1900)+ "年" + (date.getMonth()+1) + "月" +date.getDate() +"日");

        //方法二
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String date2 = sdf.format(date);
        System.out.println("自定义格式2 " + date2);

    }
}
