package day16.File文件;

import java.io.File;

/*
��ȡ�Ĺ���:
public File getAbsoluteFile() :�����ļ��ľ���·���ļ�
public String getAbsolutePath() :�����ļ��ľ���·���ַ���
public String getPath() :�����ļ������·���ַ���.
public String getName(): ��ȡ�ļ���
public long length() :��ȡ�ļ��ĳ���
long lastModified(): ��ȡ�ļ����һ���޸ĵ�ʱ�䣨����ֵ)
*/
public class FileUse05 {
    public static void main(String[] args) {
        File file = new File("C:\\Windows\\setuperr.log");
        System.out.println(file.exists());

        String relativePath = file.getPath();//��ȡ���·��
        System.out.println(relativePath);

        String absolutePath = file.getAbsolutePath();  //�����ļ��ľ���·��
        System.out.println(absolutePath);

        String fileName  = file.getName();//��ȡ�ļ�
        System.out.println(fileName);

        Long fileSize  =  file.length();
        System.out.println(fileSize);
    }
}
