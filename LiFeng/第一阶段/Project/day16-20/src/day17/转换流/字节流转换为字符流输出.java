package day17.ת����;

import java.io.*;

public class �ֽ���ת��Ϊ�ַ������ {
    public static void main(String[] args) throws  Exception{
        demo01();
    }
    public static void demo01()throws  Exception{
        OutputStream  outputStream = new FileOutputStream("e:\\vivijie.txt");//����һ���ֽ����������
        Writer writer = new OutputStreamWriter(outputStream,"gbk"); //�ڶ��������Ǳ������

        writer.write("���Ҷ�"); //���ַ�������ķ�ʽд������
        writer.flush();  //ˢ�»�����

    }

}
