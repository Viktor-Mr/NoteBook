package day16.File文件;

import java.io.File;
import java.io.IOException;

public class FileUse03 {
    public static void main(String[] args) {
        demo2();
    }
    private  static void demo1(){
        File file = new File("e:\\test1.txt");
        System.out.println(file.delete());
    }
    private  static void demo2(){
        //创建文件夹
        File file1 = new File("e:\\aa");
        System.out.println(file1.mkdir());
        //创建文件
        File file2 = new File(file1,"a.txt");
        try {
            System.out.println(file2.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //删除文件
        //删除错误，文件夹必须为空才能删除
        System.out.println(file1.delete());
        System.out.println("---------------先删除文件再删除文件夹-------------------");
        System.out.println(file2.delete());
        System.out.println(file1.delete());

    }

}
