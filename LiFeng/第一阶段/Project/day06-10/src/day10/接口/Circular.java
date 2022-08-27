package day10.接口;

public class Circular extends  Printer implements Circle,Draw {
    double radius;
    public Circular(double radius){
        this.radius = radius;
    }

    @Override
    public void test(){

    }
    @Override
    public double getArea(){
        return PI*radius*radius;
    }
    @Override
    public void draw(){
        System.out.println("用铅笔画一个圆形");
    }
    public static void main(String[]  args){
        Circular c = new Circular(10);
        System.out.println("圆形面积：" + c.getArea());
        c.draw();
        c.print();
    }
}
