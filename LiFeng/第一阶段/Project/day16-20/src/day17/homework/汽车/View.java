package day17.homework.汽车;

import day17.homework.汽车.Car;

import java.io.*;
import java.util.Scanner;

public class View {
    static Scanner sc = new Scanner(System.in);
    public static void mainMenu(){
      //  Scanner sc = new Scanner(System.in);
        System.out.println("1.录入  2.读取");
        System.out.print("请选择:");
        int input = sc.nextInt();
        switch (input) {
            case 1:
                outputView();     //从java输出信息到文本文件
                break;

            case 2:
                 inputView();     //java从文本文件读取信息
                break;

            default:
                System.out.println("输入的数字有无效，跳出，跳出--------");
                System.exit(0);
        }
    }
    public static void inputView(){    //java从文本文件读取信息
        carOld();
    }
    public static void carOld(){

        System.out.println("\t\t上个月销售情况表");
        System.out.println("品牌\t型号\t颜色\t座位数\t单价\t月销量\t月销售额");
        System.out.println("------------------------------------------------------");

        try {
            InputStream fis = new FileInputStream("doc\\car.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);




           Car []cars = new Car[3];
            int i = 0;
            cars[0]  = new Car();
            cars[1]  = new Car();
            cars[2]  = new Car();
            while(true){
                try {
                    cars[i] = (Car)ois.readObject();
                    System.out.println(cars[i]);
                    i++;
                } catch (Exception e) {
                    break;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("读出信息失败,没有找到文件");
        } catch (IOException e) {
            System.out.println("Io读写出现异常");
        } catch (Exception e) {
           System.out.println("其他错误");
       }
    }



    public static void outputView(){   //从java输出信息到文本文件
        carNew();
    }

    public static  void carNew() {

        try {
            OutputStream fos = new FileOutputStream("doc\\car.txt");
            ObjectOutputStream os = new ObjectOutputStream(fos);

            Car []car = new Car[3];

            int sum_num = 0;
            int sum_price = 0;
            for(int i =0; i < 3; i++ ){
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
                os.writeObject(car[i]);
            }


        }
        catch  (FileNotFoundException e) {
            System.out.println("读出信息失败,没有找到文件");
        } catch (IOException e) {
            System.out.println("Io读写出现异常");
        }
    }



}
