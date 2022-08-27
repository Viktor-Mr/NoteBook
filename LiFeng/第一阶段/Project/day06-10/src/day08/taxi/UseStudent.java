package day08.taxi;
//使用构造方法
public class UseStudent {
    public static void main(String[] args){
        Student stu1 = new  Student();
        System.out.println(stu1.age + " " + stu1.name );
        Student stu2 = new  Student("叔叔",20);
        System.out.println(stu2.age + " " + stu2.name );
        Student stu3 = new  Student("尼克",22);
        System.out.println(stu3.age + " " + stu3.name );
    }
}
