package day17.������;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ��������� {
    public static void main(String[] args)throws  Exception{
        demo();

    }
    public static void demo()throws Exception{
        ArrayList per = new ArrayList();
        Person p  = new Person("001","���޼�",18);

        OutputStream fos = new FileOutputStream("e:\\testvii.txt");
        ObjectOutputStream os  = new ObjectOutputStream(fos);
        per.add(p);

//        os.writeObject(p);
//
        p = new Person("002","��Ѱ��",21);
//        os.writeObject(p);
//        System.out.println("д��ɹ�");

        per.add(p);
        os.writeObject(per);

    }

}
