package day10.继承_多重;

public class Father extends Grandpa {
    int fatherMoney = 1000;
    int  grandpaMoney = 4000;
    @Override
    public void grandpaM(){

        System.out.println("爷爷有" + grandpaMoney + "千万元");
    }
    public void fatherM(){
        System.out.println("爸爸有" + fatherMoney + "千万");
    }
}
