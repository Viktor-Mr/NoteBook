package day17.������;

import java.io.Serializable;

public class Person implements Serializable{//���л��ӿ�
        String id;
	//����
		String name;
		
		//����
		int age;
		
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
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
		public void setAge(int age) {
			this.age = age;
		}

		
		
		//����
		//String nationality;
		static String nationality;
		public Person(){

		}
		public Person(String id,String name,int age){
			this.age = age;
			this.name = name;
			this.id = id;
		}

	@Override
	public String toString() {
		return "Person{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
