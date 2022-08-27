package day06;

public class B_sushu {
    public static void main(String[] args){
        for(int i=1; i<=100; i++){
            if(true==fox(i))
            System.out.print("\t" + i);
        }
    }


    public static boolean fox(int num){
        int k = (int)(Math.sqrt(num)+1) , j=0;
        for(int i=2;i<= k; i++){
            if(num%i==0 && num!=i ){
                j=1;
                break;
            }
        }
        if(j==1)
            return false;
        else
            return true;

    }

}
