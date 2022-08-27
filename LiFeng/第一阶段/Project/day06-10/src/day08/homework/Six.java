package day08.homework;

public class Six {
    public static void main(String[] args){
       Student s1 = new Student(10100,"张三",'b',20);
       Student s2 = new Student(10101,"王五",'g',30);

        System.out.println("第一位同学的性别是"+s1.showSex()+ " 年龄是 " + s1.showAge()+ " 姓名是" + s1.showName()+ " 学号是" +s1.showNo());
        System.out.println("第二位同学的性别是"+s2.showSex()+ " 年龄是 " + s2.showAge()+ " 姓名是" + s2.showName()+ " 学号是" +s2.showNo());

        s1.s_Age = 18;
        System.out.println("修改后s1的年龄是" + s1.s_Age);
    }
}



class Student{
    int s_No;
    String s_Name;
    char s_Sex;
    int s_Age;

    public Student(int s_No,String s_Name ,char s_Sex, int s_Age){
        this.s_Age=s_Age;
        this.s_Name=s_Name;
        this.s_No=s_No;
        this.s_Sex=s_Sex;
    }
    public int showNo(){
        return s_No;
    }
    public int showAge(){
       return s_Age;
    }
    public String showName(){
        return s_Name;
    }
    public char showSex(){
       return s_Sex;
    }

}