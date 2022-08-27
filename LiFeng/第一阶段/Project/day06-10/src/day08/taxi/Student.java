package day08.taxi;
//构造方法
class Student {
   int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(){
       age = 18;
       name = "阿姨";
   }

    public Student(String name,int age){
        this.age  = age;
        this.name = name;
    }
    public Student(int age,String name){
        this.age  = age;
        this.name = name;
    }
}
