package day17homework;



import java.io.FileInputStream;
import java.io.InputStream;

public class task04 {
    public static void main(String[] args) throws Exception{
        demo02();
    }
    public static void demo01()throws Exception{
        InputStream is = new FileInputStream("d:\\aa.txt");
        int data;
        while((data = is.read())!=-1){
            System.out.print((char)data);

        }
    }
    public static void demo02()throws Exception{
        InputStream is = new FileInputStream("d:\\aa.txt");
        byte []bytes = new byte[is.available()];
        is.read(bytes);
        System.out.println(new String(bytes));
    }
}
