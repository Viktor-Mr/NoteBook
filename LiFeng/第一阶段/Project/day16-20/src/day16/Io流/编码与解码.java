package day16.Io��;

import java.util.Arrays;

public class ��������� {
    public static void main(String[] args) throws Exception{
        String school = "���";
        byte[] bytes1 =school.getBytes();//Ĭ���ַ������б���
        byte[] bytes2 =school.getBytes("GBK");
        byte[] bytes3 =school.getBytes("utf-8");
        byte[] bytes4 =school.getBytes("unicode");
        System.out.println("--------------�Ƚ����ֱ����ʽ----------------");

        System.out.println(Arrays.toString(bytes1));
        System.out.println(Arrays.toString(bytes2));
        System.out.println(Arrays.toString(bytes3));
        System.out.println(Arrays.toString(bytes4));
        System.out.println("--------------����--------------");
        System.out.println(new String(bytes1));
        System.out.println(new String((bytes1),"utf-8"));

        System.out.println(new String(bytes2));
        System.out.println(new String((bytes2),"gbk"));

        System.out.println(new String((bytes3),"utf-8"));
        System.out.println(new String((bytes4),"unicode"));
    }

}
