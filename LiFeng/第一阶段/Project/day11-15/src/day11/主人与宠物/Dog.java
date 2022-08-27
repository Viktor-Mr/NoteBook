package day11.主人与宠物;

public class Dog extends Pet{
    @Override
    public void eat(){
        System.out.println("小狗吃骨头");
    }
    public void guard(){
        System.out.println("小狗看小院");
    }
}
