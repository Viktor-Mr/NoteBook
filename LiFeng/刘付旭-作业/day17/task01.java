package day17homework;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class task01 {
    public static void main(String[] args) throws Exception{
        demo01();
    }
    public static void  demo01()throws Exception{
        OutputStream os = new FileOutputStream("d:\\aa.txt",true);
        byte [] bytes = "砺锋科技\n".getBytes();
        os.write(bytes);
    }



}
