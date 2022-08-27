package day17.对象流.运用集合;

import day17.对象流.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectOutputStreamDemo2 {

	public static void main(String[] args) {
		Person p1 = new Person("3", "zhansan", 3);
		Person p2 = new Person("4", "lisi", 4);
		Person p3 = new Person("5", "wangwu", 5);
		List<Person> persons=new ArrayList<Person>();
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		
		OutputStream ops = null;
		ObjectOutputStream oops = null;
		try {
			ops = new FileOutputStream("doc//person.txt",true);
			oops = new ObjectOutputStream(ops);
			oops.writeObject(persons);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ops.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oops.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
