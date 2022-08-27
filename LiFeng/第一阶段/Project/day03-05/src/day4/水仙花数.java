package day4;

public class 水仙花数 {
    public static void main(String[] args) {
//        for(int i=100; i<=999; i++){
//            int a,b,c;
//           a = i%10;
//           b = i/10%10;
//           c = i/100%10;
//           if(a*a*a+b*b*b+c*c*c==i){
//               System.out.println(i);
//           }
//        }

        for(int i=100; i<=999; i++){
            int sum = 0, k = i;
            while(k!=0){
                int a = k%10;
                k  = k/10;
                sum=sum+a*a*a;
            }
            if(sum == i)
                System.out.println(i);
        }



    }
}

