package day11.向下转型;

public class Boat extends Vehicle {
    public void swim(){
        System.out.println("小船在水里游");
    }
    @Override
    public void transport(){
        System.out.println("这是一艘船");
    }
}


