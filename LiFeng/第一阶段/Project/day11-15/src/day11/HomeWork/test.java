package day11.HomeWork;


public class test {
    public static void main(String[] args){
        A a1 = new A();
        A a2 = new B(); // 对象A new 一个 B对象 向上转型  A 是父类  B 是子类
        B b  = new B();
        C c  = new C();
        D d  = new D();
        System.out.println(" 1 " + a1.show(b));  // b -> B
        System.out.println(" 2 " + a1.show(c));  // c -> C
        System.out.println(" 3 " + a1.show(d));  // d -> D
        System.out.println("------------------------");

        System.out.println(" 4 " + a2.show(b));  // B
        System.out.println(" 5 " + a2.show(c));  // B
        System.out.println(" 6 " + a2.show(d));  // B
        System.out.println("------------------------");

        System.out.println(" 7 " + b.show(b));
        System.out.println(" 8 " + b.show(c));
        System.out.println(" 9 " + b.show(d));
    }
}

// A 为父类 B C D 为子类
class A {

    //进行方法的重载
    public String show(D obj) {
        return ("A and D");
    }

    public String show(A obj) {
        return ("A and A");
    }
}

class B extends A {
    public String show(B obj) {
        return ("B and B");
    }

    public String show(A obj) {
        return ("B and A");
    }
}

class C extends B {
}

class D extends B {
}