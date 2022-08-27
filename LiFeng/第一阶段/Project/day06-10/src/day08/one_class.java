package day08;

public class one_class {
    public static void main(String[] args) {
        Bycicle bike = new Bycicle(10);//创建一个Bycicle类的对象
        System.out.println(bike.price);
        bike.price = 1000.0; //调用类的成员变量
        System.out.println(bike.price);
        bike.f();
        System.out.println(bike.getPrice());
    }

}

