package day11.向下转型;

public class Plan extends Vehicle {
    public void fly(){
        System.out.println("飞机在天上飞");
    }
    @Override
    public void transport(){
        System.out.println("这是一架飞机");
    }
}