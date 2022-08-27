package day17homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class task06 {
    public static void main(String[] args)throws Exception {
        demo();
    }
    public static void demo()throws Exception{
        Reader rd = new FileReader("d:\\aa.txt");
        BufferedReader br = new BufferedReader(rd);
        String line;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }
    }
}
