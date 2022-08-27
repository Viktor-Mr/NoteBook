package day17.转换流;

import java.io.*;

//InputStreamReader字节输入流转换流，用于将字节输入流转换为字符输入流

public class 字节流转换为字符流输入 {
    public static void main(String[] args)throws  Exception {
    demo02();
    }
    public static void demo01() throws  Exception{
        FileInputStream inputStream = new FileInputStream("e:\\test3.txt"); //创建一个字节输入流
        InputStreamReader reader  =new InputStreamReader(inputStream,"gbk");//将字节输入流作为参数，创建一个字节输入转换流
       //第二个参数是解码规则
        int data;
        while((data= reader.read())!=-1){
            System.out.print((char)data);
        }
    }


    public static void demo02() throws  Exception{
        InputStream inputStream = new FileInputStream("e:\\test3.txt");
        Reader reader  =new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        while((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }
    }

}
