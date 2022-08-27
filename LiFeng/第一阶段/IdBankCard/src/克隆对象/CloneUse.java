package 克隆对象;

import java.io.*;

public class CloneUse {
    public static void main(String[] args) {

//        克隆对象.Animals animals1 = new 克隆对象.Animals();
//        System.out.println("a1 = " + animals1.toString());
//        克隆对象.Animals a2 =  (克隆对象.Animals) animals1.clone();
//        System.out.println("a1 = " + animals1.toString());
//        animals1.age = 20;
//        System.out.println("a1 = " + animals1.toString());
//        System.out.println("a1 = " + animals1.toString());

        Dog dog = new Dog();
        System.out.println("d1 = " + dog.toString());
        Dog dog2= null;
        try {
            dog2 = CloneUtil.clone(dog);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("d2 = " + dog2.toString());
        System.out.println("----------------------------------");
        dog.age = 19;
        System.out.println("d1 = " + dog.toString());
        System.out.println("d2 = " + dog2.toString());



    }
}

//浅拷贝
class Animals  implements Cloneable{
    int age;
    String sex;

    public Animals(int age, String sex) {
        this.age = age;
        this.sex = sex;
    }
    public Animals(){}

    @Override
    public Object clone() {
        Animals a = null;

        try {
            a = (Animals)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return  a ;
    }

    @Override
    public String toString() {
        return "克隆对象.Animals{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}

//深拷贝
//象实现Serializable接口，通过对象的序列化和反序列化实现克隆
class Dog extends Animals implements Serializable {
    int age;
    String sex;

    public Dog(){}

    public Dog(int age, String sex) {
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "克隆对象.Dog{" +
                "age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}

class CloneUtil {

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T clone(T object) throws Exception{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bout);
        oos.writeObject(object);

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bin);
        // 此处不需要释放资源，说明：调用ByteArrayInputStream或ByteArrayOutputStream对象的close方法没有任何意义
        // 这两个基于内存的流只要垃圾回收器清理对象就能够释放资源，这一点不同于对外部资源（如文件流）的释放
        return (T) ois.readObject();
    }
}