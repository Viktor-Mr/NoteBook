package day06;

public class A_Method {
    public static void main(String[] args){
        int a=10, b=20;
        int r = min(a,b);
        System.out.println(r);
    }


    public static int min(int x,int y){
        if(x>y)
            return y;
        else
            return x;

    }
}
