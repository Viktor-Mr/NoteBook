package day17homework;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class task07 {
    public static void main(String[] args) throws Exception{
        demo();
    }
    public static void demo()throws  Exception{
        InputStream is = new FileInputStream("d:\\aa.txt");
        InputStreamReader  isr = new InputStreamReader(is,"utf-8");

        int data;
        while((data= isr.read())!=-1){
            System.out.print((char)data);
        }

    }
}
