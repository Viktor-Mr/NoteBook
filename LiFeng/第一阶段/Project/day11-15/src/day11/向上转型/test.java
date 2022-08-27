package day11.向上转型;

public class test {
    public static void main(String[] args){
        Driver driver = new Driver("张三"); // int b =10;
        Vehicle vehicle;  //int a

        vehicle = new Car();  //  a = b --> a= b = 10 ;//向上转型  子  - > 父
        driver.d(vehicle);  // 输出b的d
       // vehicle.transport();

        vehicle = new Truck();
        driver.d(new Truck());
        //vehicle.transport();



    }
}
