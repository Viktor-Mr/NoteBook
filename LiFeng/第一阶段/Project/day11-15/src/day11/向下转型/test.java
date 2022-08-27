package day11.向下转型;

public class test {
    public static void main(String[] args){
        Driver driver = new Driver("张三");
        Vehicle vehicle;
        vehicle = new Car();
        vehicle.transport();
        driver.action(vehicle);

        vehicle = new Boat();
        vehicle.transport();
        driver.action(vehicle);

        vehicle = new Plan();
        vehicle.transport();
        driver.action(vehicle);

    }
}
