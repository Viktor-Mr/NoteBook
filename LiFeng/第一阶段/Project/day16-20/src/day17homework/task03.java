package day17homework;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class task03 {
    public static void main(String[] args) throws Exception{
        demo();
    }
    public static void demo() throws Exception{
        OutputStream outputStream = new FileOutputStream("d:\\aa.txt",true);//创建一个字节输出流对象
        Writer writer = new OutputStreamWriter(outputStream,"utf-8"); //第二个参数是编码规则

        writer.write("大乱斗\n"); //以字符输出流的方式写入数据
        writer.flush();  //刷新缓冲区

    }
}
