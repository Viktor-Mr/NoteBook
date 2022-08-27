package day13.正则表达式;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexThree {
    public static void main(String[] args){
        String num ="我的手机是18988888888,我曾3456用过18987654321,还用过15912345678";
        String regex = "1\\d{10}"; //手机号码的正则表达式
        Pattern p = Pattern.compile((regex)); //获取到正则表达式
        Matcher m = p.matcher(num);  //获取匹配器
        while (m.find()){            // 遍历Matcher 对象
            System.out.println(m.group());   //循环输出
        }
    }
}
