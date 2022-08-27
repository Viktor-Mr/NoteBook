package day09.homework;

public class Circle {
    private  int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    public Circle(){
        radius = 5;
    }
    public double area(){
        return 3.14*radius*radius;
    }
    public double girth(){
        return 2*3.14*radius;
    }
}
