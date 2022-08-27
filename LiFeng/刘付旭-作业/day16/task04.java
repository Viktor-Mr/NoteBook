package day16.HomeWord;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;

public class task04 {
    public static void main(String[] args) throws Exception{
        FileInputStream inputStream = new FileInputStream("e:\\poem.txt");

//        byte[] bytes = new byte[inputStream.available()];
//        inputStream.read(bytes);
//        System.out.println(new String(bytes));

        byte[] bytes = new byte[3];
        while(inputStream.read(bytes)!= -1){
            System.out.print(new String(bytes,"utf-8"));
        }

        System.out.println();
        Reader reader = new FileReader("e:\\poem.txt");
        char []chars = new char[3];
        while(reader.read(chars)!=-1){
            System.out.print(new String(chars));
           chars = new char[3];
        }
    }
}
