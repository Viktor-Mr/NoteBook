###11.15_常见对象(Object类的概述)(了解)
* A:Object类概述
	* 类层次结构的根类
	* 所有类都直接或者间接的继承自该类
* B:构造方法
	* public Object()
	* 回想面向对象中为什么说：
	* 子类的构造方法默认访问的是父类的无参构造方法

###11.16_常见对象(Object类的hashCode()方法)(了解)
* A:案例演示
	* public int hashCode()
	* a:返回该对象的哈希码值。默认情况下，该方法会根据对象的地址来计算。
	* b:不同对象的，hashCode()一般来说不会相同。但是，同一个对象的hashCode()值肯定相同。

###11.17_常见对象(Object类的getClass()方法)(在反射的时候掌握)
* A:案例演示
	* public final Class getClass()
	* a:返回此 Object 的运行时类。
	* b:可以通过Class类中的一个方法，获取对象的真实类的全名称。	
		* public String getName()

###11.18_常见对象(Object类的toString()方法)(掌握)
* A:案例演示
	* public String toString()
	* a:返回该对象的字符串表示。
* 
		
		public Stirng toString() {
			return name + "," + age;
		}
	* b:它的值等于： 
		* getClass().getName() + "@" + Integer.toHexString(hashCode()) 
	* c:由于默认情况下的数据对我们来说没有意义，一般建议重写该方法。
* B:最终版
	* 自动生成

###11.19_常见对象(Object类的equals()方法)(掌握)
* A:案例演示
	* a:指示其他某个对象是否与此对象“相等”。 
	* b:默认情况下比较的是对象的引用是否相同。即比较地址是否相同
	* c:由于比较对象的引用没有意义，一般建议重写该方法。

###11.20_常见对象(==号和equals方法的区别)(掌握)
* ==是一个比较运算符号,既可以比较基本数据类型,也可以比较引用数据类型,基本数据类型比较的是值,引用数据类型比较的是地址值
* equals方法是一个方法,只能比较引用数据类型,所有的对象都会继承Object类中的方法,如果没有重写Object类中的equals方法,equals方法和==号比较引用数据类型无区别,重写后的equals方法比较的是对象中的属性,也可以比较两个字符串的值是否相等
