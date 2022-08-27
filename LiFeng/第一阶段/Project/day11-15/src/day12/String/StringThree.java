package day12.String;

public class StringThree {
    public static void main(String[] args){
       //字符串的截取
        String str = "HelloWorld";
        String s8 = str.substring(5);
        System.out.println(s8);

        //字符串的替换功能
        String s1 = "good morning";
        String s2 = s1.replace('g','G');
        System.out.println(s2);
        String s3 = s1.replace("go","GG");
        System.out.println(s3);
        String s4 = "  helloworld   ";
        System.out.println("/" + s4 + "/");
        String s5 = s4.trim();
        System.out.println(s5);


    }
    class nu extends Object {

    }
}
