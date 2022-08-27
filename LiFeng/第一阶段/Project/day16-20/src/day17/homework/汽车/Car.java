package day17.homework.汽车;


import java.io.Serializable;

class Car  implements Serializable {
    private String brand;
    private String type;
    private String color;
    private int  seating;
    private int  price;
    private int  num;
    private int sum_price;
    public Car(){

    }

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

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", seating=" + seating +
                ", price=" + price +
                ", num=" + num +
                ", sum_price=" + sum_price +
                '}';
    }
}