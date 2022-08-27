package day16.HomeWord;

import java.io.File;

public class TwoTav02 {
    public static void main(String[] args) throws  Exception{

        File file = new File("f:\\11");
        Dome(file);
    }
    public  static void Dome(File file) throws Exception{

        File []files =  file.listFiles();

        for (File fi: files){
            if(fi.isDirectory())
            {
                System.out.println(fi.getAbsolutePath());
                Dome(fi);
            }
            if(fi.isFile())
            {
                if (fi.getName().endsWith(".txt"))  //以什么结尾
                System.out.println(fi.getAbsolutePath());
            }
        }

    }
}
