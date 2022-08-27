package day07;
import java.util.Scanner;
public class 冒泡排序 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int []a = new int [5];
        for(int i=0; i<a.length; i++){
            System.out.print("输入第" + (i+1) + "个数：");
            a[i] =  sc.nextInt();
        }
        showAll(a);
        for(int i=0; i<a.length; i++){
            System.out.print("\t"+ a[i]);
        }
    }

    public static void showAll(int []i){
        int p = 1;
        for(int j=0; j<i.length-1;j++){
            for (int k=0;k<i.length-p;k++){
                if(i[k]>i[k+1]){
                    i[k]=i[k]^i[k+1];
                    i[k+1]=i[k]^i[k+1];
                    i[k]=i[k]^i[k+1];
                }
            }
            p=p+1;
        }
    }

}
