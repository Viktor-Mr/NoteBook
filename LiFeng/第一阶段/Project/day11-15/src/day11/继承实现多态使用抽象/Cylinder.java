package day11.继承实现多态使用抽象;

public class Cylinder extends  Container {
    double radius;
    double height;
    public Cylinder(double radius,double height){
        this.height = height;
        this.radius = radius;
    }
    @Override
    public void getCapacity(){
        System.out.println("圆柱体的容器是" + (3.14*radius*radius*height));
    }
}
