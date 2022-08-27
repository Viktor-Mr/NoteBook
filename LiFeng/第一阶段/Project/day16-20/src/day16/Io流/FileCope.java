package day16.Io��;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileCope {
    public static void main(String[] args) throws Exception {
        domoe01();
    }

    public static void domoe01() throws Exception {
        FileOutputStream file1 = new FileOutputStream("doc\\flower.jpg");   //���
        FileInputStream file2 = new FileInputStream("e:\\flower.jpg");  //����
        int data;
        while ((data = file2.read()) != -1) {
            file1.write(data);
        }
    }

    public static void domoe02() throws Exception {
        FileOutputStream file1 = new FileOutputStream("doc\\flower.jpg");   //���
        FileInputStream file2 = new FileInputStream("e:\\flower.jpg");  //����

        byte[] bytes = new byte[1024];

        while((file2.read(bytes)) != -1) {
            file1.write(bytes);
            bytes = new byte[1024];
        }
    }

    public static void domoe03() throws Exception {
        FileOutputStream file1 = new FileOutputStream("doc\\flower.jpg");   //���
        FileInputStream file2 = new FileInputStream("e:\\flower.jpg");  //����


        byte[] bytes = new byte[file2.available()];
        file1.write(bytes);
    }
}

