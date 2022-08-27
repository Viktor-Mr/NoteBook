package day11.主人与宠物;

public class Cat extends Pet{
    @Override
    public void eat(){
        System.out.println("小猫吃鱼");
    }
    public void  getMouse(){
        System.out.println("小猫抓老鼠");
    }

}
