package day16.Io流;

import java.util.Arrays;

public class 编码与解码 {
    public static void main(String[] args) throws Exception{
        String school = "你爹";
        byte[] bytes1 =school.getBytes();//默认字符集进行编码
        byte[] bytes2 =school.getBytes("GBK");
        byte[] bytes3 =school.getBytes("utf-8");
        byte[] bytes4 =school.getBytes("unicode");
        System.out.println("--------------比较四种编码格式----------------");

        System.out.println(Arrays.toString(bytes1));
        System.out.println(Arrays.toString(bytes2));
        System.out.println(Arrays.toString(bytes3));
        System.out.println(Arrays.toString(bytes4));
        System.out.println("--------------解码--------------");
        System.out.println(new String(bytes1));
        System.out.println(new String((bytes1),"utf-8"));

        System.out.println(new String(bytes2));
        System.out.println(new String((bytes2),"gbk"));

        System.out.println(new String((bytes3),"utf-8"));
        System.out.println(new String((bytes4),"unicode"));
    }

}
