package day12.String;

public class StringTwo {
    public static void main(String[] args){
        String s1 = "helloworld";
		String s2 = "Helloworld";
		String s3 = "HelloWorld";
		System.out.println("-------------判断两个字符串是否相等,考虑大小写--------------");
		System.out.println(s1.equals(s2));
		System.out.println(s2.equals(s3));
		System.out.println("-------------判断两个字符串是否相等,忽略大小写-------------");
		System.out.println(s1.equalsIgnoreCase(s2));
		System.out.println(s2.equalsIgnoreCase(s3));
		System.out.println("-------------判断字符串是否包含指定的子字符串---------------");
		System.out.println(s1.contains("hello"));
		System.out.println(s2.contains("world"));
		System.out.println(s3.contains("hello"));
		System.out.println("-------------判断字符串是否以指定的子字符串开头--------------");
		System.out.println(s1.startsWith("h"));
		System.out.println(s1.startsWith("he"));
		System.out.println(s1.startsWith("hello"));
		System.out.println(s3.startsWith("hello"));
		System.out.println("-------------判断字符串是否以指定的子字符串结尾--------------");
		System.out.println(s1.endsWith("d"));
		System.out.println(s1.endsWith("ld"));
		System.out.println(s1.endsWith("world"));
		System.out.println(s3.endsWith("world"));
		System.out.println("-----------------判断字符串是否为空----------------");
		String s4 = "";
		String s5 = " ";
		System.out.println(s4.isEmpty());
		System.out.println(s5.isEmpty());
		System.out.println("----------compare比较字符串------------");
		String s6="abc";
		String s7="cbc";
		System.out.println(s6.compareTo(s7));
		System.out.println(s7.compareTo(s6));

    }
}
