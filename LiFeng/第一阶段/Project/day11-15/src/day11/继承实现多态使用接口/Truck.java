package day11.继承实现多态使用接口;

public class Truck implements Vehicle{
    @Override
    public void transport(){
        System.out.println("这是一辆大车");
    }
}
