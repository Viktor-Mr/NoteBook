package day14.HomeWork;

import javax.sound.midi.SoundbankResource;

public class Two {
    public static void main(String[] args)throws Exception {

        try {
            division(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("这个是错误的");
        }

    }

    public static void division (int c) throws Exception{
        if(c!=0){
            int a=100/c;
            System.out.println("100/c" + a);
        }
        else{
            throw  new Exception("异常");
        }
    }
}
