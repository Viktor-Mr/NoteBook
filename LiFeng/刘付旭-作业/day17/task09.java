package day17homework;

import java.io.*;

public class task09 {
    public static void main(String[] args) throws Exception{
        demo01();
    }
    public static void demo01()throws Exception{
        InputStream is = new FileInputStream("e:\\poem.txt");  //字节输入流

        Reader reader = new InputStreamReader(is);   //字符输入流

        BufferedReader br = new BufferedReader(reader); //封装为缓冲区字符流

        String line;
        while((line = br.readLine())!=null){
            System.out.println(line);
        }

    }
}
