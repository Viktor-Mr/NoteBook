package day10.多重继承的构造方法;

public class Son extends Father{

	
	public Son() {
		System.out.println("这个是儿子 最底层ࣩ");
	}

	public Son(String name) {
		//super();             //Ĭ�ϵ��ã���Ҫ�ɲ�Ҫ������ȡ��ע�ͽ��в���
		//super(name);           //ָ�����ø���Ĵ��εĹ��췽��
		System.out.println("son 名叫"+name);
	}
	
}
