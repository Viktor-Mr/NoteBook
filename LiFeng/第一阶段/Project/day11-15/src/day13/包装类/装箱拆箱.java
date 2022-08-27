package day13.包装类;

public class 装箱拆箱 {
    public static void main(String[] args) {
        System.out.println("--------手动装箱int--àInteger---------");
        //方式1
        Integer i1=new Integer(100);
        //方式2
        System.out.println("i1:"+i1);
        Integer i2=Integer.valueOf(200);
        System.out.println("i2:"+i2);
        System.out.println("--------手动拆箱Integer--àint---------");
        int num=i1.intValue();
        System.out.println("num:"+num);
        System.out.println("--------自动装箱---------");
        Integer i3 = 100; //直接把int 赋值给Integer
        System.out.println("i3:" + i3);
        System.out.println("--------自动拆箱---------");
        int num3=i3;//直接把Integer赋值给int类型或参与运算
        //int num3=i3+200;
        System.out.println("num3:" + num3);
    }

}
