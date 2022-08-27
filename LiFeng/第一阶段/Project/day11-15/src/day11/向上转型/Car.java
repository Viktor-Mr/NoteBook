package day11.向上转型;

public class Car implements Vehicle{
    @Override
    public void transport(){
        System.out.println("这是一辆小车");
    }
}
