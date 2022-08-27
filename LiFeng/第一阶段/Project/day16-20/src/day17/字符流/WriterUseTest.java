package day17.字符流;
//字符输出流,好处:直接写文本，不需要转化为字节数组再写
//在文件c: \b.txt写中文文字

import java.io.FileWriter;
import java.io.Writer;

public class WriterUseTest {
    public static void main(String[] args) throws  Exception{
        demo();

    }

    public static void demo()throws  Exception{
        Writer writer = new FileWriter("e:\\test3.txt",false);  //默认为false为新建 true 为追加

        writer.write("春眠不觉晓\n");
        writer.write("处处闻啼鸟\r\n");
        writer.write("夜来风雨声\n");
        writer.write("花落知多少\n");
        writer.write(48); //整型被装换为字符型
        writer.flush();

    }
}
