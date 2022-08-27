package day11.向下转型;

public class Driver {
    String name;
    public Driver(String name){
        this.name = name;
    }

    public void action(Vehicle vehicle){
        if(vehicle instanceof  Car){
            Car  car  = (Car) vehicle;
            car.run();
        }
        else if(vehicle instanceof  Boat){
            Boat boat = (Boat) vehicle;
            boat.swim();
        }
        else if(vehicle instanceof  Plan){
            Plan plan = (Plan)vehicle;
            plan.fly();
        }
    }
    public void d(Vehicle vehicle){
        System.out.println(name+"在开");
        vehicle.transport();
    }
}
