package day11.继承实现多态使用抽象;

public class test {
    public static void main(String[] args){
        Container container ;
        container = new Cubiod(10,20,30);
        container.getCapacity();;
        container = new Cylinder(10,30);
        container.getCapacity();
    }
}
