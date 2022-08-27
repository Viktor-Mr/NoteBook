package day11.继承实现多态使用接口;

public class TestSport {
    public static void main(String[] args){
        Vehicle vehicle;  //变量 定义变量     int a；
        vehicle = new  Car(); // 赋值 地址     a = 10；
        vehicle.transport();
        vehicle = new Truck();
        vehicle.transport();
    }
}
