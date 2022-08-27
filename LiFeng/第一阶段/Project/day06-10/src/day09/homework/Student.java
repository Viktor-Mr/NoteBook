package day09.homework;

public class Student {
    private static String name;
    private static int age;
    private static int grade;
    private int score;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Student.name = name;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        Student.age = age;
    }

    public static int getGrade() {
        return grade;
    }

    public static void setGrade(int grade) {
        Student.grade = grade;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public Student(){}

    public Student(String name,int age,int grade,int score){
        this.score=score;
        this.name =name;
        this.age =age;
        this.grade =grade;
    }
    public static void speak(){
        System.out.println("说中文….");
    }
}
