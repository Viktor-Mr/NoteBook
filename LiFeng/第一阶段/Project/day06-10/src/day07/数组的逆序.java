package day07;

public class 数组的逆序 {
    static int []a ={1,2,3,4,5};
    public static void main(String[] args){
        System.out.print("输出原来的数组：");
        for(int i=0; i<a.length; i++){
            System.out.print("\t" + a[i]);
        }
        System.out.println();
        for(int i=0; i<a.length/2;i++){
            a[i] = a[i]^a[a.length-1-i];
            a[a.length-1-i] = a[i]^a[a.length-1-i];
            a[i] = a[i]^a[a.length-1-i];
        }
        System.out.print("输出变化的数组：");
        for(int i=0; i<a.length; i++){
            System.out.print("\t" + a[i]);
        }


    }
}
