package day5.homework;

public class three {
    public static void main(String[] args){
        int num=0,hour=0;
        do{
            num+=4;
            hour++;
            if(num>=100)
                break;
            num--;
        }while(num<=100);
        System.out.println("需要" + hour +"个小时.");
    }
}
