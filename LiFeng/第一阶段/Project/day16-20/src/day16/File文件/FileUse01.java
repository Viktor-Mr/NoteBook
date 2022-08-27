package day16.File文件;

import java.io.File;
import java.io.IOException;

public class FileUse01 {
    public static void main(String[] args) throws Exception{
    FileDome03();
    }
    public static void FileDome01() throws  Exception{
        File file =  new File("E:\\test1.txt"); //文件可以是存在也可以是不存在

       System.out.println(file.createNewFile());
    }
    public static void FileDome02() {
        File file =  new File("E:\\aa","teset2.txt"); //文件可以是存在也可以是不存在

        try {
            System.out.println(file.createNewFile());
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("dsd");
    }
    public static void FileDome03() throws  Exception{
        File file =  new File("e:\\a\\c");
        System.out.println(file.mkdir());
        File file2 =  new File(file,"est1.txt"); //文件可以是存在也可以是不存在
        try {
            System.out.println(file2.createNewFile());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
