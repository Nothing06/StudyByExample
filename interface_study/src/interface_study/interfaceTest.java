package interface_study;

public class interfaceTest {
		public static void main(String[] args)
		{
			A a =new A();
			a.methodA();
			a.methodA2();
		}
}
class A
{
	void methodA()
	{
		I i = InstanceManager.getInstance();
		i.methodB();
		B b_instance= new B(0);
		System.out.println(i.toString());
	}
	void methodA2()
	{
		I i = InstanceManager.getInstance();
		System.out.println(i.number);
	}
}
interface I{ 
	public static int number=0;
	void methodB();
}
class B implements I{
	int a=0;
	public B(int num)
	{
		a=num;
	}
	public void methodB()
	{
		System.out.println("methodB in B class");
	}
	public String toString() {
		 return "class B";
	}
}
class InstanceManager{
	static int no=0;
	public static I getInstance()
	{
		return new B(no);
	}
}