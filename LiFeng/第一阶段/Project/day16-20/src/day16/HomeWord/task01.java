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
            System.out.println("�ļ��д����ɹ�");
        else
            System.out.println("�ļ����Ѿ�����");

        name1 += "\\day16.txt";
        file = new File(name1);
        if(file.createNewFile())
            System.out.println("�ɹ������ļ�");
        else
            System.out.println("�޷��������ļ�");
        String name2 = file.getName();

        File file1two = new File(name2);
        if (file1two.createNewFile())
            System.out.println("�ɹ���ֲ�ļ����������ݣ�");
        else
            System.out.println("�ļ������������ݣ��ѱ���ֲ");

    }
}



class FileUse{
    public static void newFile(String path,String name){
        File file = new File(path,name);
        if (!file.mkdir()){
            System.out.println("�ɹ������ļ���");
        }else
            System.out.println("�Ѿ������ļ���");
    }
}
