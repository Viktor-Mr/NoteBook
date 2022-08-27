package day14.异常.自定义异常;

public class Student {
    private  String name;
    private  int age;
    private  String gender;

    public Student(String 张无忌, int i) {
    }
    public Student(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws AgeException {
        if (age>0 && age <=100) {
            this.age = age;
        } else {
            throw  new AgeException("年龄范围在0-100之间");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws GenderException {
        if (gender.equals("男")| gender.equals("女")) {
            this.gender = gender;
        }
        else{
            throw new GenderException("性别只能是男或者女");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
