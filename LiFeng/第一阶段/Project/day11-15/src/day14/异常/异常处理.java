package day14.异常;

public class 异常处理 {
    public static void main(String[] args) {
       demo4();

    }
    public static  void demo2(){
        int []a  = {1,2,3,4};
        try {
            System.out.println(a[6]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("错误,test");
        }
        System.out.println("错误,test");
    }
    public static  void demo3(){

        try {
            int a =10/0;
        } catch (ArithmeticException e) {
            e.printStackTrace();
            System.out.println("错误,test");
        }
        System.out.println("错误,test");
    }
    public static  void demo4(){
        int []a  = {1,2,3,4};
        try {
            a = null;
            System.out.println(a[6]);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("错误,test");
        }
        System.out.println("错误,test");
    }

}
