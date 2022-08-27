package day17.打印流;

import java.io.*;

public class 写入打印流 {
    public static void main(String[] args)throws Exception {
        demo01();
        demo02();
    }
    public static  void demo01()throws Exception{
        OutputStream fos = new FileOutputStream("e:\\对象输出流.txt",true);
        PrintStream ps  =new PrintStream(fos);
        ps.println("你好");
        ps.println(18);
        ps.println(true);
        System.out.println("控制台打印  打印-----");

    }
    public static void demo02 ()throws  Exception{
        Writer writer = new FileWriter("E:\\对象输出流.txt",true);
        PrintWriter pw = new PrintWriter(writer);
        pw.println("你好");
        pw.println(18);
        pw.println(true);
        pw.flush();
        System.out.println("控制台打印  打印-----");
    }
}
