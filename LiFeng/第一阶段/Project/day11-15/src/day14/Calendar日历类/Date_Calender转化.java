package day14.Calendar日历类;

import java.util.Calendar;
import java.util.Date;

public class Date_Calender转化 {
    public static void main(String[] args) {
        System.out.println("-----毫秒数与Date的相互转化-----");
        Date date = new Date();
        System.out.println(date.toLocaleString());
        long num = date.getTime(); //date转化为毫秒数
        System.out.println(num);
        date.setTime(num + 1*24*60*60*1000);   //毫秒数转化为Date
        System.out.println(date.toLocaleString());

        System.out.println("-----Date转化为Calendar-----");
        Calendar cal = Calendar.getInstance(); //今天的日历
        cal.setTime(date); //date 装化为Calendar，相当于给cal重新赋值
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DATE);
        System.out.println(year + "年 " + month + "月 " + day + "日");

        System.out.println("------Calender转化为date-------");
        Date date2 = cal.getTime();
        System.out.println(date2.toLocaleString());

    }
}
