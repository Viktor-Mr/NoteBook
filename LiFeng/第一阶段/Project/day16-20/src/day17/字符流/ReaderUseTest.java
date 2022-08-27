package day17.字符流;

import java.io.FileReader;
import java.io.Reader;

public class ReaderUseTest {
    public static void main(String[] args)throws Exception {
        demo02();
    }
    public static void demo01()throws Exception{
        Reader reader = new FileReader("e:\\poem.txt");
        int data;
        while((data = reader.read())!=-1){
            System.out.print((char)data);
        }
    }
    public static void demo02()throws Exception{
        Reader reader = new FileReader("e:\\poem.txt");
        char []chars = new char[4];

        while(reader.read(chars)!=-1){
            System.out.print(new String(chars));

            chars = new char[3];
        }
    }
}
