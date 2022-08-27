package day10.继承;

public class UseOther {
    public static void main(String[] args){
        Student student = new Student();
        student.name = "张三";
        student.age  = 19;
        student.gender = "男";
        student.grade = 9;
        student.studenNo = 15000;
        student.eat();
        student.scoreNum();
    }
}
