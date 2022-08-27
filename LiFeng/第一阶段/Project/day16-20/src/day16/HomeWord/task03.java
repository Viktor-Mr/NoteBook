package day16.HomeWord;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class task03 {
    public static void main(String[] args) throws Exception{
        FileOutputStream file1 = new FileOutputStream("d:\\TooBug\\work.avi");   //输出
        FileInputStream file2 = new FileInputStream("d:\\work.avi");  //输入
        byte[] bytes = new byte[file2.available()];
        file1.write(bytes);
    }
}
