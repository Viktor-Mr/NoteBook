package day14.Date类;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class 字符串转化为Date {
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.print("输入（格式 yyyy/MM/dd hh:mm:ss）:");
        String dateStr1 = sc.nextLine();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = sdf.parse(dateStr1);
        System.out.println(date.toLocaleString());
    }
}
