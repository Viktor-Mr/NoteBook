package day14.Date类;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date转化为字符串 {    //2019/3/22
    public static void main(String[] args) {
        Date  date  = new Date();
        System.out.println("默认格式:" + date.toLocaleString());
        SimpleDateFormat  sdf  = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String date1 = sdf.format(date);
        System.out.println("自定义格式1: " + date1);

        sdf = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
        String date2 = sdf.format(date);
        System.out.println("自定义格式2 " + date2);

    }
}
