package day17homework;



import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class task05 {
    public static void main(String[] args) throws Exception{
        demo();
    }
    public static  void demo()throws Exception{
        Reader rd = new FileReader("d:\\aa.txt");
        char []arr = new char[1];
        int num;
        while((num =rd.read(arr))!=-1){
            System.out.print(new String(arr));
            arr = new char[3];
        }
    }
}
