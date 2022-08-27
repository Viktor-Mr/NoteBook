package day08.homework;

public class Five {
    public static void main(String[] args){
        Circle cc1 = new Circle(100);
        Circle cc2 = new Circle(200);
        System.out.println("c1的面积" + cc1.Area());
        System.out.println("c1的周长" + cc1.Perimneter());
        System.out.println("c2的面积" + cc2.Area());
        System.out.println("c2的周长" + cc2.Perimneter());
    }
}
class Circle{
    double radius;
    double PI = 3.14;
    double area;
    double perimneter;
    public Circle(double radius){
        this.radius = radius;
    }
    public int Area(){
        return (int)(Math.round(radius*radius*PI));
    }
    public int Perimneter(){
        return (int)(Math.round(radius*2*PI));
    }
}