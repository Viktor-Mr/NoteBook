package day11.继承实现多态使用抽象;

public class Cubiod extends Container{
    double length; //长
    double width;  //宽
    double height; //高
    public Cubiod(double length,double width, double height){
        this.height = height;
        this.length = length;
        this.width = width;
    }
    @Override
    public void getCapacity(){
        System.out.println("长方体的容器是" + (length*width*height));
    }
}
