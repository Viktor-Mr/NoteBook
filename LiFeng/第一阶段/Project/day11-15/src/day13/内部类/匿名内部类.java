package day13.内部类;

public class 匿名内部类 {
    public static void main(String[] args) {
        Outer4 o = new Outer4();
        o.method();
    }
}
abstract class innerInterface{
    public abstract void print1();
    public abstract void print2();
}
class Outer4{
    String name;
    int age;
    public void method() {
        new innerInterface() {
            @Override
            public void print1() {
                System.out.println("这是print1方法");
            }
            @Override
            public void print2() {
                System.out.println("这是print2方法");
            }
        }.print1();

        new innerInterface() {
            @Override
            public void print1() {
                System.out.println("这是print1方法");
            }
            @Override
            public void print2() {
                System.out.println("这是print2方法");
            }
        }.print2();


        System.out.println("------------如果接口有多个抽象方法就用有名内部类--------------");
        innerInterface i= new innerInterface() {
            @Override
            public void print1() {
                System.out.println("这是print1方法");
            }
            @Override
            public void print2() {
                System.out.println("这是print2方法");
            }
        };
        i.print1();
        i.print2();
    }
}
