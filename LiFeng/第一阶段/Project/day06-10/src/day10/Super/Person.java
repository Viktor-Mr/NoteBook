package day10.Super;

public class Person {
    String name;
    int age = 18;
    public Person(){
        this.name = "一个人";
    }
    public Person(String name){
        this.name =name;
    }
    public void show(){
        System.out.println("这是父类的show方法");
    }
}
