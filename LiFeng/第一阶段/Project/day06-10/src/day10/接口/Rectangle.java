package day10.接口;

public class Rectangle implements Shape{
    double width;
    double height;
    public Rectangle(double width,double height){
        this.height = height;
        this.width = width;
    }
    @Override
    public double getArea() {
        return height*width;
    }
}
