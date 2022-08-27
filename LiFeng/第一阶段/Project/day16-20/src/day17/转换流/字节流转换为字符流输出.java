package day17.转换流;

import java.io.*;

public class 字节流转换为字符流输出 {
    public static void main(String[] args) throws  Exception{
        demo01();
    }
    public static void demo01()throws  Exception{
        OutputStream  outputStream = new FileOutputStream("e:\\vivijie.txt");//创建一个字节输出流对象
        Writer writer = new OutputStreamWriter(outputStream,"gbk"); //第二个参数是编码规则

        writer.write("大乱斗"); //以字符输出流的方式写入数据
        writer.flush();  //刷新缓冲区

    }

}
