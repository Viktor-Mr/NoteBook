package day17.ת����;

import java.io.*;

//InputStreamReader�ֽ�������ת���������ڽ��ֽ�������ת��Ϊ�ַ�������

public class �ֽ���ת��Ϊ�ַ������� {
    public static void main(String[] args)throws  Exception {
    demo02();
    }
    public static void demo01() throws  Exception{
        FileInputStream inputStream = new FileInputStream("e:\\test3.txt"); //����һ���ֽ�������
        InputStreamReader reader  =new InputStreamReader(inputStream,"gbk");//���ֽ���������Ϊ����������һ���ֽ�����ת����
       //�ڶ��������ǽ������
        int data;
        while((data= reader.read())!=-1){
            System.out.print((char)data);
        }
    }


    public static void demo02() throws  Exception{
        InputStream inputStream = new FileInputStream("e:\\test3.txt");
        Reader reader  =new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        while((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }
    }

}
