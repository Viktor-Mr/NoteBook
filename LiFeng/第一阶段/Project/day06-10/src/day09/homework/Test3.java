package day09.homework;

public class Test3 {
    public static void main(String[] args){
        Circle c = new Circle();
        System.out.println(c.area());
        System.out.println(c.girth());

        c.setRadius(10);
        System.out.println(c.area());
        System.out.println(c.girth());
    }
}
