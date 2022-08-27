package day13.HomeWork;

public class W2 {
    public static void main(String[] args){
        String s = "2099/10/11";
        String regex = "(19[8-9]\\d|[2-9][0-9][0-9][0-9])/(0[0-9]|1[0-2])/([0-3][0-9])";
        if (s.matches(regex))
            System.out.println("年份符合1980后出生");
        else
            System.out.println("年份不符合");
    }
}
