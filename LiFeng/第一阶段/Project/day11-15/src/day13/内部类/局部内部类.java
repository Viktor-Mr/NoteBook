package day13.内部类;

public class 局部内部类 {
    public static void main(String[] args) {
        Outer3 o = new Outer3();
        o.method();
    }
}

class Outer3 {
    public void method() {
        int num = 10;
        class Inner {
            public void print() {
                System.out.println("内部类的print方法访问外部的局部变量："+num);
            }
        }
        Inner i = new Inner();//局部内部类,只能在其所在的方法中访问
        i.print();
    }
    public void run() {
//		Inner i = new Inner();		//这里报错，局部内部类只能在其所在的方法中访问
//		i.print();
    }
}
