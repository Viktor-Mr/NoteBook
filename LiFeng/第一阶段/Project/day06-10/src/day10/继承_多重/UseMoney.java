package day10.继承_多重;

public class UseMoney {
    public static void main(String[] args){
        Son son =  new Son();
        son.myM();
        System.out.println("继承财富：");
        son.grandpaM();
        son.fatherM();
        System.out.println("一共可以获得" + son.sum +"千万");
    }
}
