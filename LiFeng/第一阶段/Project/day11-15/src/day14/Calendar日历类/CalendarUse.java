package day14.Calendar日历类;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarUse {
    public static void main(String[] args) {
//        Calendar cal  = new Calendar(); //不可以， Calender是抽象类
//        Calendar cal  = new GregorianCalendar();  //实例化一个日历
        Calendar cal = Calendar.getInstance();
        System.out.println(cal);
        int year = cal.get(Calendar.YEAR);  //get获得年份
        System.out.println(year);

        int month = cal.get(Calendar.MONTH)+1; //get获得月份
        System.out.println(month);

        int data = cal.get(Calendar.DATE);  //get获得几号
        System.out.println(data);

        cal.add(Calendar.YEAR, -1);  // 正数代表加多少 负数代表减多少
        System.out.println(cal.get(Calendar.YEAR));
        cal.set(2008,7,8,20,0,0);
        System.out.println(cal);
    }
}
