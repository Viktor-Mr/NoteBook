package day11.向上转型;

public class Driver {
    String name;

    public Driver(String name){
        this.name = name;
    }
    public void d(Vehicle vehicle){
        System.out.println(name+"在开");
        vehicle.transport();
    }
}
