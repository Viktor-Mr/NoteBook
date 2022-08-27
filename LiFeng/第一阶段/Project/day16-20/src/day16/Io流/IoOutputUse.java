package day16.Io流;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class IoOutputUse {
    public static void main(String[] args) throws Exception{
        demo02();
    }
//    public static void demo01(){
//        OutputStream  outputStream = null;
//
//    }

    public static void demo02() throws Exception{
        OutputStream outputStream = null;

        outputStream = new FileOutputStream("e:\\widefa.txt");

        byte []  bytes ="dilicode".getBytes();
        outputStream.write(bytes);

        outputStream.close();


    }
}
