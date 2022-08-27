package day13.HomeWork;

import java.util.Random;

public class W8 {
    public static void main(String[] args){
        String aim = "";
        Random r = new Random();
        int k;
        for (int i = 0; i <4 ; i++) {
            int j = r.nextInt(3);
            switch (j){
                case 0:
                     k= r.nextInt(10);       //数字
                     aim +=  k;
                     break;
                case 1:
                     k = r.nextInt(26)+65;  //大写英文之母
                     aim +=(char)k;
                     break;
                case 2:
                     k = r.nextInt(26)+97;  //小写英文之母
                    aim += (char)k;
                    break;

            }
        }
        System.out.println(aim);
    }
}
