package day12.StringBufferDome;

public class StringBufferDome {
    public static void main(String[] args){
        System.out.println("---------无参构造方法---------");
        StringBuffer sb = new StringBuffer();
        System.out.println(sb.capacity());
        System.out.println(sb.length());
        System.out.println("----------带int参数构造方法--------");
        StringBuffer sb2 = new StringBuffer(50);
        System.out.println(sb2.capacity());
        System.out.println(sb2.length());
        System.out.println("---------带String参数构造方法----------");
        StringBuffer sb3 = new StringBuffer("hello");
        System.out.println(sb3.capacity());  //计算容器大小
        System.out.println(sb3.length());
        System.out.println();
        System.out.println("----------append方法---------------");
        StringBuffer sb4 = new StringBuffer();
        //一步一步添加数据
//        sb4.append("中国");
//        System.out.println(sb4);
//        sb4.append("广州市");
//        System.out.println(sb4);
//        sb4.append("天河区");
//        System.out.println(sb4);

        //也可以一步完成，称为链式编程
        sb4.append("中国").append("广州市").append("天河区");
        System.out.println(sb4);

        System.out.println("-------------insert方法----------------");
        sb4.insert(2,"广东省");
        System.out.println("--------------reverse方法-----------------");
        StringBuffer sb5 = new StringBuffer();
        sb5.append("avaj");
        System.out.println(sb5);
        sb5.reverse();
        System.out.println(sb5);
        System.out.println("------------replace方法---------------------");
        sb5.append(",string");
        sb5.replace(5,11,"pyhton");
        System.out.println(sb5);
        System.out.println("---------------delete方法---------------------");
        sb5.delete(0,5);
        System.out.println(sb5);
        System.out.println("----------------toString方法------------------");
        String s6 = sb5.toString();
        System.out.println(s6);
        System.out.println(sb5);
        System.out.println(sb5.toString());
    }
}
