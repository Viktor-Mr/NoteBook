package day13.正则表达式;

public class RegexOne {
    public static void main(String[] args){
         String str4  = "chen_liweiAB168@sina.com.cn";
         String regex4 = "\\w+@\\w+(\\.[a-z]{2,3}){1,2}";
         System.out.println("验证邮箱: " + str4.matches(regex4) );

         String s = "长亭外8古道边77芳草碧连天23456788765432晚风吹";
         String []arr  = s.split("\\w+");
         for (int i = 0; i <arr.length ; i++) {
            System.out.println(arr[i]);
         }

         String s3 = "123abc123abcabc";
         String regex2 = "(\\d+)(abc)\\1(abc)\\3";
        System.out.println(s3.matches(regex2));


         String s2 = "today18 we are 65going to7 outside";
         String regex = "[0-9]+";
         String new_str = s2.replaceAll(regex," "); //替换为空格
         System.out.println(new_str);
    }
}
