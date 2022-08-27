package day09.汽车模拟;

import java.util.Scanner;

public class UseCar {
    public static void main(String[] args){
        Scanner  sc =  new Scanner(System.in);
        Car []car = new Car[3];
        int sum_num = 0 ,sum_price=0;
        for(int i =0; i < car.length; i++ ){
            System.out.println("请录入第" + (i+1) + "种车的信息");
            System.out.print("品牌：");
            String brand = sc.next();
            System.out.print("型号：");
            String type = sc.next();
            System.out.print("颜色：");
            String color = sc.next();
            System.out.print("座位数：");
            int seating = sc.nextInt();
            System.out.print("单价：");
            int price = sc.nextInt();
            System.out.print("上个月销售量：");
            int  num = sc.nextInt();
            System.out.println();
            car[i] = new Car(brand,type,color,seating,price,num);
            sum_num += car[i].getNum();
            sum_price += car[i].getSum_price();
        }
        System.out.println("\t\t上个月销售情况表");
        System.out.println("品牌\t型号\t颜色\t座位数\t单价\t月销量\t月销售额");
        System.out.println("------------------------------------------------------");
        for(int i=0; i<car.length; i++){
            System.out.println(car[i].getBrand()+"\t"+car[i].getType()+"\t\t"+car[i].getColor()+"\t"+car[i].getSeating()+"\t\t"+car[i].getPrice()+"\t"+car[i].getNum()+"\t"+car[i].getSum_price());
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("上个月汽车的总销售" + sum_num);
        System.out.println("总销售额度" + sum_price);

    }
}



class Car{
    private String brand;
    private String type;
    private String color;
    private int  seating;
    private int  price;
    private int  num;
    private int sum_price;

    public Car(String brand, String type,String color,int  seating,int  price,int  num){
        this.brand =brand;
        this.type =type;
        this.color = color;
        this.num = num;
        this.seating = seating;
        this.price = price;
        this.sum_price =  num * price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSum_price() {
        return sum_price;
    }

    public void setSum_price(int sum_price) {
        this.sum_price = sum_price;
    }
}