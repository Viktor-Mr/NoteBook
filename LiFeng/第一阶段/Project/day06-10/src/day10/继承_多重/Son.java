package day10.继承_多重;

public class Son extends  Father{
    int myMoney = 0;
    int sum = grandpaMoney +fatherMoney;
    public void myM(){
        System.out.println("自己有" + myMoney + "财富");
    }
}
