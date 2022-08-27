package day08.homework;

public class Two {
    public static void main(String[] args){
        B b = new B();
        System.out.println(b.num);
        f(b);
        System.out.println(b.num);
    }
    public static void f(B bb){
      bb.num = 200;
    }
}
class B{
    int num = 100;
}
