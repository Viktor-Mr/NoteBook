package day17homework;

import java.io.*;
import java.util.ArrayList;

public class task08 {
    public static void main(String[] args)throws Exception {
        //demo01();  //存入
        demo02();   //取出
    }
    public static void demo01()throws Exception{
        ArrayList<Person>  peoples= new ArrayList<Person>();
        Person p = new Person("19","张无忌",19);
        peoples.add(p);
       p = new Person("20","大中午",21);
        peoples.add(p);
        p = new Person("21","趣味小",21);
        peoples.add(p);

        OutputStream is = new FileOutputStream("d:task08.txt");
        ObjectOutputStream stream = new ObjectOutputStream(is);

       stream.writeObject(peoples);

    }
    public static void demo02()throws  Exception{

        ArrayList<Person> pers = new ArrayList<Person>();
        InputStream is = new FileInputStream("d:task08.txt");
        ObjectInputStream ois = new ObjectInputStream(is);

        pers = (ArrayList<Person>) ois.readObject();
        for (Person per:pers) {
            System.out.println(per);
        }
    }
}
