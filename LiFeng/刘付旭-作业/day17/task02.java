package day17homework;

import java.io.FileWriter;
import java.io.Writer;

public class task02 {
    public static void main(String[] args)throws Exception {
    demo01();
    }
    public static void demo01() throws Exception{
        Writer writer = new FileWriter("d:\\aa.txt",true);

        writer.write("砺锋科技\n");
        writer.flush();
    }

}
