package day14.Date类;

import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date d1 = new Date();
        System.out.println("今天：" + d1.toString());
        System.out.println("今天：" + d1.toLocaleString());
        System.out.println("毫秒数1 " + d1.getTime());
        System.out.println("毫秒数2 " + System.currentTimeMillis());
        System.out.println((d1.getYear() +1900) + "年份");
        System.out.println(d1.getMonth() + 1 + "月份");
        System.out.println(d1.getDate() + "日");
        System.out.println(d1.getHours() + ":" + d1.getMinutes() + ":" + d1.getSeconds());

        long num1 =  System.currentTimeMillis();
        long num2  = num1 + 1*24*60*60*1000;
        //获得明天的data类型
        Date d2 = new Date(num2);
        System.out.println(d2.getYear() +"," + d2.getMonth() + "," + d2.getDay());
        System.out.println(d2.getDate() + " " +d2.getHours() + ":" + d2.getMinutes() + ":" + d2.getSeconds());

        //获取明天date类型 （setTime 方法）
        Date d3 = new Date();
        System.out.println(d3.getYear() +"," + d3.getMonth() + "," + d3.getDay());
        d3.setTime(num2);
        System.out.println(d3.getYear() +"," + d3.getMonth() + "," + d3.getDay());
    }
}
