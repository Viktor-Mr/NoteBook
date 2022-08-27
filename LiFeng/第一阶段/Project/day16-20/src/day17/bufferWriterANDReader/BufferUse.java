package day17.bufferWriterANDReader;

import java.io.*;
//带缓冲区的字符输出流
public class BufferUse {
    public static void main(String[] args)throws  Exception {
        demo01();
        demo02();
    }
    public static void demo01()throws  Exception{
        Writer writer = new FileWriter("e:\\test4.txt");
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write("春眠不觉晓");
        bw.newLine();
        bw.write("处处闻啼鸟");
        bw.newLine();
        bw.write("夜来风雨声");
        bw.newLine();
        bw.write("花落知多少");
        bw.newLine();
        bw.flush();
    }
    public static void demo02()throws  Exception {
        Reader reader = new FileReader("e:\\test3.txt");
        BufferedReader br = new BufferedReader(reader);
        String line;
        while((line = br.readLine())!=null){
            System.out.println(line);
        }
    }

}
