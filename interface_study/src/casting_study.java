class Car{
	String color;
	int door;
	void drive()
	{
		System.out.println("drive");
	}
}
class FireEngine extends Car{
	void water()
	{
		System.out.println("water");
	}
}
public class casting_study {
	Car car = null;
	FireEngine f=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car c = new Car();
		FireEngine a = new FireEngine();
		Car d = a;
		
		FireEngine f = (FireEngine)d;
		
	}

}
