package day16.HomeWord;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class task01 {
    public static void main(String[] args) throws  Exception{
        String name1 = "e:\\work";
        File file = new File(name1);
        if(file.mkdir())
            System.out.println("文件夹创建成功");
        else
            System.out.println("文件夹已经创建");

        name1 += "\\day16.txt";
        file = new File(name1);
        if(file.createNewFile())
            System.out.println("成功创建文件");
        else
            System.out.println("无法创建新文件");
        String name2 = file.getName();

        File file1two = new File(name2);
        if (file1two.createNewFile())
            System.out.println("成功移植文件（不算内容）");
        else
            System.out.println("文件（不包括内容）已被移植");

    }
}



class FileUse{
    public static void newFile(String path,String name){
        File file = new File(path,name);
        if (!file.mkdir()){
            System.out.println("成功创建文件夹");
        }else
            System.out.println("已经存在文件夹");
    }
}
