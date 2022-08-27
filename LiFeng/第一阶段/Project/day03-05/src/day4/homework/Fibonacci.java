package day4.homework;

public class Fibonacci {
    public static void main(String[] args){
        int first=1,second=1,third=0;
        for(int i=1; i<=20; i++){
            if(i==1 || i==2) {
                System.out.print("\t1");
            }
            else{
                third = first + second;
                System.out.print("\t" + third);
                first = second;
                second = third;
            }
        }
    }
}
