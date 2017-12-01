package interface_study;
interface Repairable{}
public class Ex7_26_RepairableTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
	}

}
class hitRange
{
	int min;
	int max;
	hitRange(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
}
class Unit{
	int hitPoint;
	hitRange hitRg;
	final int MAX_HP=100;
	int hp;
	int speed;
	int def; //¹æ¾î·Â
	Unit(int hp)
	{
		hp = MAX_HP;
	}
	boolean get_movability()
	{
		if(speed == 0)
			return false;
		return true;
	}
}
class Scv extends Unit
{
	
	boolean is_transformed = false;
	Scv(int hp)
	{
		super(hp);
		hitPoint = 10;
		def = 10;
		hitRg.max=1;
		hitRg.min=0;
		speed = 100;
	}
	void transform()
	{
		if(is_transformed == false)
		{
		hp = MAX_HP + 20;
		def += 5;
		speed += 20;
		is_transformed = true;
		}
	}
}
class Tank extends Unit
{
	Tank(int hp)
	{
		super(hp);
		hitPoint = 40;
		hitRg.min = 0;
		hitRg.max = 20;
	}
	void siege_mode()
	{
		hitPoint += 60;
		speed=0;
		hitRg.min= 5;
		hitRg.max = 50;
	}
	void upgrade(int level)
	{
		if(level == 1)
		{
			hitPoint+=5;
			def += 5;
		}
		else if(level == 2)
		{
			hitPoint+=5;
			def += 5;
		}
		else if(level == 3)
		{
			hitPoint+=5;
			def += 5;
		}
		else
		{
			System.out.println("Wrong argument.");
		}
	}
}
class Terran 
{
	
}
class Zerg extends Unit
{
	int extra_hitPoint;
	Zerg(int hp)
	{
		super(hp);
		extra_hitPoint = 30;
		hitPoint += extra_hitPoint;
	}
	void dig()
	{
		
	}
}
