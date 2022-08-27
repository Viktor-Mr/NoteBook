package day5;

public class car_problem {
    public static void main(String[] args){
        double num = 10;
        int i = 2021;
        while(num<20){
            i +=1;
            num = num + num*0.08;
        }
        System.out.println("到" +i+ "年，汽车的数量超过20万，"+ "销量是" +  num);
    }
}
