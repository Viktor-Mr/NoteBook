package day17.�ַ���;
//�ַ������,�ô�:ֱ��д�ı�������Ҫת��Ϊ�ֽ�������д
//���ļ�c: \b.txtд��������

import java.io.FileWriter;
import java.io.Writer;

public class WriterUseTest {
    public static void main(String[] args) throws  Exception{
        demo();

    }

    public static void demo()throws  Exception{
        Writer writer = new FileWriter("e:\\test3.txt",false);  //Ĭ��ΪfalseΪ�½� true Ϊ׷��

        writer.write("���߲�����\n");
        writer.write("����������\r\n");
        writer.write("ҹ��������\n");
        writer.write("����֪����\n");
        writer.write(48); //���ͱ�װ��Ϊ�ַ���
        writer.flush();

    }
}
