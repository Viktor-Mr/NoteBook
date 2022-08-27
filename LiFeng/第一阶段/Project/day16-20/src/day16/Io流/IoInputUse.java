package day16.Io流;




import java.io.FileInputStream;
import java.io.InputStream;

public class IoInputUse {
    public static void main(String[] args) throws  Exception {
       dome03();

    }

    public static void dome01()throws Exception{
        InputStream file = new FileInputStream("e:\\对象输出流.txt");
        int data;
        while((data = file.read())!=-1){

            System.out.println((char)data);
        }
    }

    public static void dome02()throws Exception{
        InputStream file = new FileInputStream("e:\\对象输出流.txt");
        byte [] bs = new byte[2];
        int data;
        while((data = file.read(bs))!=-1){
           System.out.print(new String((bs),("utf-8")));
            bs = new byte[4];
        }
    }
    public static void dome03()throws  Exception{
//        try {
//            InputStream fis = new FileInputStream("e:\\对象输出流.txt");
//            byte []bytes = new byte[fis.available()];
//            fis.read(bytes);
//            System.out.println(new String(bytes));
//        } catch (IOException e) {
//
//        }
        InputStream fis = new FileInputStream("e:\\对象输出流.txt");
        byte[] a = new byte[1024];
        System.out.println(new String(a,0,fis.read(a)));
    }
}
