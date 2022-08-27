package day13.HomeWork;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class W6 {
    public static void main(String[] args){
        String s = "Zoo for children.意思是：孩子们的动物园。";
        String regex = "[a-zA-Z]+"; //手机号码的正则表达式

        Pattern p = Pattern.compile((regex)); //获取到正则表达式
        Matcher m = p.matcher(s);  //获取匹配器

        System.out.println("改变后的字符串:"+s);

        String aim = "";
        while(m.find()){            // 遍历Matcher 对象
            aim += m.group() + " ";
        }
        String ss[] = aim.toString().split(" ");
        System.out.println(Arrays.toString(ss));

        s = s.replaceAll(regex," ");
        System.out.println("改变后的字符串:"+s);

//        String s = "验证1980后的出生日期 格式要求yyyy/MM/dd";
//        String regex = "[a-zA-Z]+"; //手机号码的正则表达式
//        Pattern p = Pattern.compile((regex)); //获取到正则表达式
//        Matcher m = p.matcher(s);  //获取匹配器
//        StringBuffer sb = new StringBuffer();
//
//        while(m.find()){            // 遍历Matcher 对象
//            sb.append(m.group()+' ');
//        }
//        String ss[] = sb.toString().split(" ");
//
//        System.out.println(Arrays.toString(ss));
//
//        s = s.replaceAll(regex," ");
//        System.out.println("改变后的字符串:"+s);


    }

}
