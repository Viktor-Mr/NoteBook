package day17.��ӡ��;

import java.io.*;

public class д���ӡ�� {
    public static void main(String[] args)throws Exception {
        demo01();
        demo02();
    }
    public static  void demo01()throws Exception{
        OutputStream fos = new FileOutputStream("e:\\���������.txt",true);
        PrintStream ps  =new PrintStream(fos);
        ps.println("���");
        ps.println(18);
        ps.println(true);
        System.out.println("����̨��ӡ  ��ӡ-----");

    }
    public static void demo02 ()throws  Exception{
        Writer writer = new FileWriter("E:\\���������.txt",true);
        PrintWriter pw = new PrintWriter(writer);
        pw.println("���");
        pw.println(18);
        pw.println(true);
        pw.flush();
        System.out.println("����̨��ӡ  ��ӡ-----");
    }
}
