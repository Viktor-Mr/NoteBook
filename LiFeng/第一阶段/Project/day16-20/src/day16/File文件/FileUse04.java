package day16.File文件;

/*
判断功能:
public boolean isDirectory() :判断file对象是否为目录
public boolean isFile() :判断file对象是否为文件
public boolean exists(): 判断file对象是否存在

//以下不常用
public boolean canRead() :判断是否可读.
public boolean canWrite() :判断是否可写
public boolean isHidden() :判断是否隐藏
*/


import java.io.File;

public class FileUse04 {
    public static void main(String[] args) throws  Exception {
        //创建文件
        File file = new File("e:\\test1.txt");

        //public boolean exists(); //判断文件是否存在
        System.out.println(file.exists());

        if(!file.exists()){
            System.out.println(file.createNewFile());
        }

        // public boolean isDirectory(); 判断是否为根目录
        System.out.println(file.isDirectory()); //false
       // public boolean isFile() :判断file对象是否为文件
        System.out.println(file.isFile());  //true
    //    public boolean exists(): 判断file对象是否存在
        System.out.println(file.isFile());  //true

        if(file.exists()){
            System.out.println(file.delete()); //true
        }

    }
}
