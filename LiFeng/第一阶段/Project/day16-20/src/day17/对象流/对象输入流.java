package day17.对象流;

import java.io.*;
import java.util.ArrayList;

public class 对象输入流 {
    public static void main(String[] args) throws  Exception{
    demo01();
    }
    public static void demo01() throws  Exception{
        InputStream  fis = new FileInputStream("e:\\testvii.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);

        ArrayList <Person>per = (ArrayList<Person>)ois.readObject();
        for(Person p:per){
            System.out.println(p);
        }

//        Person []p = new Person[5];
//        int i = 0;
//        p[0]  = new Person();
//        p[1]  = new Person();
//        p[2]  = new Person();
//        while(true){
//            try {
//
//               p[i] = (Person)ois.readObject();
//                System.out.println(p[i]);
//                i++;
//            } catch (Exception e) {
//                break;
//            }
//        }
//        System.out.println(p[0]);
//        System.out.println(p[1]);
//        System.out.println(p[2]);





       }
}
