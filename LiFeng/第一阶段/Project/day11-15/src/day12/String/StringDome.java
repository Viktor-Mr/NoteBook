package day12.String;

public class StringDome {
    public static void main(String[] args){
        //字符串的长度
        String s1 = "Hello";
        String s2 = "大佬，带带我";
        System.out.println(s1 + "的长度 " + s1.length());
        System.out.println(s2 + "的长度 " + s2.length());

        String a1 = "提取字符串的单个字符";
        for(int i = 0 ; i<a1.length(); i++){
            System.out.print(a1.charAt(i) + "\t");
        }
        System.out.println();

        String A1 =  "字符串转化为数组";
        char []A2 = A1.toCharArray();
        for(int i=0; i< A2.length; i++){
            System.out.print(A2[i] + "\t");
        }

        String o1 = "HolleWorld";
        System.out.println(o1.indexOf('o',5));
        System.out.println(o1.indexOf('l'));
        System.out.println(o1.lastIndexOf('o'));
        System.out.println(o1.lastIndexOf('l'));
        System.out.println(o1.lastIndexOf("hello"));
        System.out.println(o1.lastIndexOf("Hello"));

        String poem = "长亭外,古道边,芳草碧连天,晚风";
        String []p = poem.split(",");
        for(int i=0; i< p.length; i++){
            System.out.print(p[i] + "\t");
        }


    }
}
