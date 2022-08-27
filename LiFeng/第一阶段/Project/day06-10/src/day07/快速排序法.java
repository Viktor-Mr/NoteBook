package day07;

import java.util.Arrays;

public class 快速排序法 {
    static  int []a = {5,-1,1,8,7,9,10};
    public static void main(String[] args){
        quicksort(0,a.length-1);
        for(int q=0;  q<a.length; q++){
            System.out.print("\t" + a[q]);
        }
    }

    public static void quicksort(int left,int right){
        if(left>right)
            return ;
        int temp = a[left];
        int i = left;
        int j = right;
        while(i != j){
            while(a[j] >= temp && i<j)
                j--;
            while(a[i] <= temp && i<j )
                i++;
            if(i<j){
                a[i] = a[i]^a[j];
                a[j] = a[i]^a[j];
                a[i] = a[i]^a[j];
            }
            a[left] = a [i];
            a[i]    =  temp;
            quicksort(left,i-1);
            quicksort(i+1,right);
            return;
        }
      //  Arrays.sort();
    }
}
