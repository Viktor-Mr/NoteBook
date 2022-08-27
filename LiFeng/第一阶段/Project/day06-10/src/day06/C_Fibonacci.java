package day06;

public class C_Fibonacci {
    public static void main(String[] args){
        int i = 6;
        System.out.println(Fibonacci(i));
    }

    public  static int  Fibonacci(int i){
        if(i==1 || i==2){
            return  1;
        }
        else{
            return Fibonacci(i-1) +Fibonacci(i-2);
        }
    }

}
