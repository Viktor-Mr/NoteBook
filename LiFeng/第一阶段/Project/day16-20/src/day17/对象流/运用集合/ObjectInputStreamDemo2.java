package day17.������.���ü���;

import day17.������.Person;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class ObjectInputStreamDemo2 {

	public static void main(String[] args) {
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream("doc//person.txt");
			try {
				ois = new ObjectInputStream(is);
			} catch (Exception e) {
				e.printStackTrace();
			}


			List<Person> persons=(List)ois.readObject();

			for(Person p:persons) {
				System.out.println(p);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("��ָ���쳣");
			e.printStackTrace();
		}
		
	}
}
