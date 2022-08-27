package 测试;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Demo {
    public static void main(String[] args) throws Exception {

      //  System.out.println(Demo05("174415451"));


       // Demo06();
        demo08();
        // demo07();
    }

    private static void demo08() {
        List list = new ArrayList();
        list.add("toobug");
        list.add("java");
        list.add("php");
        list.add("go");
        list.add("c++");
        list.add("c++");
        list.add("c++");
        list.add("r");
        list.add("c#");
        list.add("ds");
        list.add("java");
        list.add("c++");
        list.add("php");

        for (int i = 0; i < list.size()-1; i++) {
            for (int j = i+1; j <list.size() ; j++) {
                if ((list.get(i) .equals(list.get(j)))){
                    list.remove(j);
                    j--;
                }

            }
        }
        System.out.println(list);
    }

    private static void demo07() {
        ArrayList list = new ArrayList();
        list.add("toobug");
        list.add("java");
        list.add("php");
        list.add("go");
        list.add("c++");
        list.add("r");
        list.add("c#");
        list.add("java");
        list.add("c++");
        list.add("php");
        for (int i = 0; i <list.size() ; i++) {
            String s = (String) list.get(i);
            list.remove(i);
            if (!list.contains(s)){
                list.add(s);
            }
        }
        System.out.println(list);
    }

    //抛出自定义的异常
    private static void Demo06() {
        try {
            int a = 10;
            int b =2;
            if (b>1){
                Exception e = new Exception("B不能大于一");
                throw e;
               // throw  new Exception("B不能大于一");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void Demo01() throws Exception {
        byte[] by = "爸爸在此".getBytes("utf-8");
        System.out.println(Arrays.toString(by));
        String s = new String(by);
        System.out.println(s);
        s = "dsdsdsdasdadfwefwgsevxcvx";
        by = s.getBytes();
        for (byte one : by) {
            System.out.println((char) one);
            for (int i = 0; i < s.length(); i++) {
                System.out.println(s.charAt(i));
            }
        }
    }


    //Date 转换为 String
    public static void Demo02() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String s = sdf.format(date);
        System.out.println(s);

    }

    public static void Demo03() throws ParseException {
        String s = "1970/00/01 01:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        Date date = sdf.parse(s);
        System.out.println(date);
        System.out.println(date.getTime());

    }

    //计算二月份的天书数
    public static void Demo04() {
        Calendar cal = Calendar.getInstance();
        int year = 2000;
        cal.set(year, 2, 1);
        cal.add(Calendar.DATE, -1);
        System.out.println(cal.get(Calendar.DATE));
    }

    //判断QQ号 不使用正则表达
    public static boolean Demo05(String s) {
        boolean flag = true;
        if (s.length() >= 6 && (s.length() <= 11)) {
            try {
                Integer in = new Integer(s);
            } catch (NumberFormatException e) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }


}

