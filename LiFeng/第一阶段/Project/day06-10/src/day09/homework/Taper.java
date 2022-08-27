package day09.homework;

public class Taper {
    private int high;
    private  int radius;

    public Taper(){
        this.radius =5;
        this.high = 7;
    }

    public Taper(int high,int radius){
        this.radius = radius;
        this.high = high;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Taper{" +
                "high=" + high +
                ", radius=" + radius +
                '}';
    }

    public double bulk(){
        return 1*1.0/3*high*radius*radius*3.14;
    }
}
