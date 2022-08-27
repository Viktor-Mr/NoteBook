class Student {
    int age;
    char sex;
    String name;
    static String teacher;

    public void show() {
        System.out.println(this.name+"--"+this.age+"--"+this.sex+"--"+this.teacher);
    }


}

public class Demo {
    public static void main(String[] args) {

        char c = 0x00c0;
        System.out.println(c);
        Student [] stu = new Student[10];
        stu[0] = new Student();
        stu[0].name = "刘小龙";
        stu[0].age = 10;
        stu[0].sex = '男';
        stu[0].teacher = "飞龙";
        stu[0].show();


    }
}
