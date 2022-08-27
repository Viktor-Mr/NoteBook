package day11;

class Car {
    public void run() {
        System.out.println("这是父类run()方法");
    }

    public void speed() {
        System.out.println("speed:0");
    }

}

class BMW extends Car {
//    public void run() {
//        System.out.println("这是BMW的run()方法");
//    }

//    public void speed() {
//        System.out.println("speed:80");
//    }
}

class Benz extends Car {
    public void run() {
        System.out.println("这是Benz的run()方法");

    }

    public void speed() {
        System.out.println("speed:100");
    }

    public void price() {
        System.out.println("Benz:800000$");
    }

    public static void main(String[] args) {
        Car c  = new Benz();
        c.run();
        c.speed();

        c = new BMW();
        c.run();
        c.speed();


        show(new Benz());//向上转型实现
        show(new BMW());
    }

    public static void show(Car car) {//父类实例作为参数
        car.run();
        car.speed();
    }
}
