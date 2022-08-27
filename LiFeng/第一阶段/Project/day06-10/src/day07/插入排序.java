package day07;

import java.util.Scanner;

public class 插入排序 {
    static int []a ={1,2,3,6,9,10,15,19,100,0};
    public static void main(String[] args){
        Scanner  sc = new Scanner(System.in);
        System.out.print("输入新插入的数字：");
        int newNum = sc.nextInt();
        sort(newNum);

    }

    public static  void sort(int newNum){
        int index = a.length -1;
        for(int i=0; i<a.length-1;i++){
            if(newNum < a[i]) {
                index = i;
                break;
            }
        }
        for (int j = a.length-1; j > index; j-- ){
            a[j] = a[j-1];
        }
        a[index] = newNum;

        for (int i = 0; i<a.length;i++){
            System.out.print("\t" + a[i]);
        }
    }
}
