import java.util.Random;

public class test {
    static int num =10;
    public  static void main(String[] args){
        int [][]a = new int[5][5];
        num --;
        System.out.println(num);
        System.out.println(a[1][1]);
        Random random = new Random();
        System.out.println(random.nextInt(21)+1);
        System.out.println((int) (Math.random()*100));
        int d=4,b=6,c=8;
        String s="abc";
        System.out.println(d+b+s+c);
        System.out.println();
        for (int i=0;i<5;i++){
            m();
            System.out.println(num);
        }

    }
    public static void m(){
        num--;

    }

}
