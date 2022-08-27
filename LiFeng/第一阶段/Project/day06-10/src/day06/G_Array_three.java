package day06;

import java.util.Scanner;

public class G_Array_three {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int[] scores1 = {1,25,30,410,-4,105,10};
        int[] scores2 = {2,3,4,5,6,7,-1};
        System.out.println("班级 1 的信息如下：\nSum = " +getSum(scores1) + ", Aver = " +Math.round(getSum(scores1)*1.0/scores1.length) + ", Max = " + getMax(scores1) + ", Min = " + getMin(scores1) +".");
        System.out.println("班级 2 的信息如下：\nSum = " +getSum(scores2) + ", Aver = " +getSum(scores2)*1.0/scores1.length + ", Max = " + getMax(scores2) + ", Min = " + getMin(scores2) +".");

    }

    public  static  int getMax(int[] scores){
        int max = scores[0];
        for(int i=0;i<scores.length;i++){
            if(max<scores[i]) max = scores[i];
        }
        return max;
    }
    public  static  int getMin(int[] scores){
        int min = scores[0];
        for(int i=0;i<scores.length;i++){
            if(min>scores[i]) min = scores[i];
        }
        return min;
    }
    public  static  int getSum(int[] scores){
        int sum = 0;
        for(int i=0;i<scores.length;i++){
            sum += scores[i];
        }
        return sum;
    }


}
