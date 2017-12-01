package MyStarCraft;

import java.util.HashMap;
import java.util.Scanner;

interface Fightable
{
	public abstract void attack();
}
interface Repairable
{
	public abstract void repair(terran_unit tu);
}
class Scv extends ground_unit implements Repairable,Runnable{
	int no;
	static int cnt=0;
	static int mineral_scv_cnt=0;
	static int gas_scv_cnt=0;
	boolean gas_scv = false;
	boolean mineral_scv = false;
	boolean is_working = false;
	Scv()
	{
		
	}
	public void run() {
		// TODO Auto-generated method stub
			
		
		is_working = true;
		while(is_working)
		{
			if(mineral_scv)
				getResource.mineral += (8 * CommandCenter.scv_cnt);
			else if(gas_scv)
				getResource.gas += (8 * CommandCenter.scv_cnt);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	System.out.printf("mineral : %d // gas : %d\n", getResource.mineral, getResource.gas);
		}
		
	}
	
	public void stop_working()
	{
		is_working = false;
		mineral_scv = false;
		gas_scv=false;
	}
	public void repair(terran_unit tu)
	{
		if(tu instanceof ground_unit)
		{
			ground_unit gu = (ground_unit)tu;
			if(tu instanceof tank)
			{
				tank t = (tank)gu;
				while(t.hp < t.MaxHP)
				{
					t.hp += 20;
					if(t.hp>t.MaxHP)
						t.hp = t.MaxHP;
					System.out.println("수리중입니다.. " + t.hp + "/" + t.MaxHP);
					try
					{
					Thread.sleep(1);
					}
					catch(Exception e) {}
				}
				
			}
			System.out.println("수리가 완료되었습니다.");
		}
		else if(tu instanceof air_unit)
		{
			System.out.println("Can't repair air unit.");
		}
	}
	
	
}
class marine extends ground_unit implements Fightable
{
	protected final int MaxHP = 50;
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	
}
class firebat extends ground_unit implements Fightable
{
	protected final int MaxHP = 50;
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	
}
class medic extends ground_unit 
{
	protected final int MaxHP = 200;
	
	
	
}
class ghost extends ground_unit implements Fightable
{
	protected final int MaxHP = 150;
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	
}
class tank extends ground_unit implements Fightable,Repairable
{
	protected final int MaxHP = 50;
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void repair(terran_unit tu) {
		// TODO Auto-generated method stub
		
	}
	
}
public abstract class terran_unit {
//	protected final int MaxHP;
	protected int hp;
	protected int atk;
	protected int def;
	protected int spd;
	protected int range;
	protected final int marine_hp = 50;
	protected final int firebat_hp=50;
	protected final int ghost_hp = 100;
	protected final int medic_hp = 200;
	protected final int vulture_hp = 150;
	protected final int tank_hp = 200;
	protected final int goliath_hp = 200;
	protected final int race_hp = 250;
	protected final int dropship_hp = 200;
	protected final int science_vessel = 300;
	protected final int battle_cruiser = 400;
	protected final int valkirie = 250;
	public static HashMap<String,terran_unit> units = new HashMap<>();
	
	abstract void move();
}
class ground_unit extends terran_unit
{
	
	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}
	
}
class air_unit extends terran_unit
{

	@Override
	void move() {
		// TODO Auto-generated method stub
		
	}
	
}
