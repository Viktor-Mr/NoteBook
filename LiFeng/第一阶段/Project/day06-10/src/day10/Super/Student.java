package day10.Super;

public class Student extends Person {
    String gender;
    int age =19; //属性与父类重复了
    public Student(){
        super();
        this.gender ="女";
    }
    public Student (String name,String gender,int age){
      //  super(name);
        this.name =name;
        this.gender =gender;
        this.age =age;
    }
    @Override
    public void show(){
        System.out.println("这是子类的show方法");
        System.out.println("子类中的年龄：" + this.age);
        System.out.println("父类中的年龄：" + super.age);
    }
}
