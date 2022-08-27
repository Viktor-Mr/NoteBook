package day13.内部类;

public class 成员内部类 {
    public static void main(String[] args) {
        System.out.println("-----------测试外部类调用内部类-----------");
        Outer outer=new Outer();
        outer.method();
        System.out.println("-----------测试其他类访问内部类-----------");
        Outer.Inner inner=new Outer().new Inner();
        System.out.println("其他类访问内部类的成员变量num:"+inner.num);
        System.out.println("------");
        System.out.println("其他类访问内部类的成员方法:");
        System.out.println("------");
        inner.method();
    }
}
class Outer{
    private int num=10;
    private String name="tom";
    public void method() {
        System.out.println("这是外部类的method()方法");
        System.out.println("---外部类要使用内部类的属性与方法---");

        Inner inner=new Inner();
        System.out.println("外部类访问内部类的成员变量(公有):"+inner.num);
        System.out.println("外部类访问内部类的成员变量(私有):"+inner.num2);
        System.out.println("------");
        System.out.println("外部类访问内部类的成员方法:");
        inner.method2();

    }
    class Inner{
        int num=20;
        private int num2=40;
        public void method() {
            int num=30;
            System.out.println("这是内部类的method()方法");
            System.out.println("局部变量num:"+num);
            System.out.println("内部类的成员变量num(公有私有均可):"+this.num);
            System.out.println("内部类访问外部类的成员变量num:"+Outer.this.num);
        }
        public void method2() {
            System.out.println("这是内部类的method2()方法");
            System.out.println("内部类的method2()方法调用 外部类的name属性"+name);
        }
    }
}
