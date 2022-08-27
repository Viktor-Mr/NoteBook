package day13.内部类;

public class 静态内部类 {
    public static void main(String[] args) {
        System.out.println("-----------测试外部类调用内部类-----------");
        Outer2 outer2=new Outer2();
        outer2.show();
        System.out.println("-----------测试其他类访问内部类-----------");
        //外部类名.内部类名 对象名 = new 外部类名.内部类名();
        Outer2.Inner1 inner1 = new Outer2.Inner1();
        System.out.println("------访问普通方法------");
        inner1.method();
        System.out.println("------访问静态方法------");
        Outer2.Inner1.print();
    }
}
class Outer2 {
    int num=10;
    static int num2=20;
    public void show() {
        System.out.println("这是外部类的show()方法");
        Inner1 inner1=new Inner1();
        System.out.println("外部类访问内部类的成员变量:");
        System.out.println(inner1.num3);
        System.out.println(inner1.num4);
        System.out.println("外部类访问内部类的成员方法:");
        inner1.print();
    }
    public static void show2() {
        System.out.println("这是外部类的静态show2()方法");
    }
    static class Inner1 {
        int num3=40;
        private static int num4=80;
        public void method() {
            System.out.println("这是内部类的普通方法method");
//			System.out.println(num); //访问失败
//             show();
            show2(); //静态内部类只能访问外部类的静态成员
            System.out.println(num2);
        }
        public static void print() {//静态方法
            System.out.println("这是内部类的静态方法print");
        }
    }
}