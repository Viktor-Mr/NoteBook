package day11.向下转型;

public class Car extends Vehicle {
    public void run(){
        System.out.println("小车在马上跑");
    }
    @Override
    public void transport(){
        System.out.println("这是一辆小车");
    }
}
