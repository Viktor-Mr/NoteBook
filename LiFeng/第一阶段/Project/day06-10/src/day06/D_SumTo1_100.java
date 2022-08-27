package day06;

public class D_SumTo1_100 {
    public static void main(String[] args){
        int i=100,sum=0;
        sum = Sum(i);
        System.out.println("Sum = " + sum);
    }

    public static int Sum(int i){
        if(i==1)
            return 1;
        else {
            return  i+ Sum(i-1);
        }

    }


}
