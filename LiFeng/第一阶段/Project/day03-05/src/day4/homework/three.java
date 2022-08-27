package day4.homework;

public class three {
    public static void main(String[] args){
        double sum = 0;
        for(int i=2,j=1,k=1;k<=20;k++){
            if(k==1 || k==2){
                sum = sum + 1.0*i/j;
                i=j+i;
                j++;
            }
            else{
                sum = sum + 1.0*i/j;
                // i =5  //j=3
                // i =8    j=5
                int u = j;
                j=i;
                i =i + u;
            }
            System.out.println(" "+sum);
        }
    }

}
