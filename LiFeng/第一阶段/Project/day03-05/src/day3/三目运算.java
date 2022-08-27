package day3;

public class 三目运算 {
    public static void main(String[] args){
        int num1 = 60,num2 = 50,num3=20;
        boolean a;
        int b = num1>num2 ?(num1>num3 ? num1:num3) : (num2>num3 ? num2:num3);
        System.out.println(b);


        int elephant = 280;
        int giraffe =180;
        String height = elephant>giraffe?"elephant":"giraffe";
        System.out.println(height);


        height = elephant>giraffe? "大象比长颈鹿高" +(elephant - giraffe) + "厘米" : "长颈鹿比大象高" + (giraffe -elephant) + "厘米";
        System.out.println(height);
    }

}
