package day5.homework;

public class four {
    public static void main(String[] args){
        for(int i=1; i<=5; i++){
            for (int k=1; k<=i; k++){
                System.out.print(" ");
            }
            for(int j=1; j<=5+1-i; j++){
                System.out.print("* ");
            }
            System.out.println();

        }
    }
}
