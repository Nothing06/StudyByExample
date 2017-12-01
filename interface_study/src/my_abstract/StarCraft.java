package my_abstract;

import java.util.Scanner;

abstract class Unit
{
	volatile int x,y; // 위치
	int atk; // 공격력
	int def; // 방어력
	volatile int hp; // 체력
	protected int no;
	//protected int all_no;
	static int all_cnt;
	Unit()
	{
		no = all_cnt;
		all_cnt+=1;
	}
	abstract void move(int x, int y);
	void stop() {}
}
interface Movable {void quick_move(int x, int y);}
interface Attackable{void attack(Unit u);}
interface Fightable extends Movable, Attackable{}

class Dropship extends Unit implements Runnable{
	private int object_idx;
	static int cnt=0;

	Dropship()
	{
		super();
		object_idx = cnt++;
		this.hp = 50;
		this.atk = 10;
		this.def = 10;
		this.x = 6;
		this.y = 6;
	}
	void move(int x, int y) {
		this.x = x;
		this.y = y;System.out.printf("Dropship moved to (%d,%d)\n", this.x,this.y);}
	void stop() { System.out.println("Stopped");}
	void load() { System.out.println("Loaded");}
	void unload() {}
	
	
		public void run() {
			// TODO Auto-generated method stub
		//	String hp = Thread.currentThread().getName(); 
		//	int HP = Integer.valueOf(hp);
			while(hp > 0)
			{
		//		System.out.println("D");
			}
			System.out.println("Dropship exploded by Suicider!");
		}
	
}
class suicider extends Unit implements Movable{

	static int cnt=0;
	private int object_idx=0;
	suicider()
	{
		super();
		object_idx = cnt++;
		this.hp = 300;
		this.atk = 0;
		this.def = 10;
		this.x = 10;
		this.y =10;
	}
	void explode()
	{
		this.hp=0;
	//	System.out.println("all_cnt : " + all_cnt);
		System.out.printf("suicider%d exploded!\n",object_idx);
		for(int i=0;i<all_cnt;i+=1)
		{
			if(this.no!= i)
			{
				/*System.out.printf(i+ "))distance x: %d - %d = %d , distance y: %d - %d = %d\n", 
						x , StarCraft.group[i].x, Math.abs(x - StarCraft.group[i].x),
						y, StarCraft.group[i].y, Math.abs(y-StarCraft.group[i].y)	);*/
				if(Math.abs(x - StarCraft.group[i].x) < 5 && Math.abs(y-StarCraft.group[i].y) < 5)
				{
					
					StarCraft.group[i].hp = 0;
				}
			}
		
			
		}
		
	}
	@Override
	public void quick_move(int x, int y) {
		// TODO Auto-generated method stub
		System.out.println("suicider runned to " + "("+x + "," + y + ")");
	}

	@Override
	void move(int x, int y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
		System.out.println("suicider moved to " + "("+ this.x + "," + this.y + ")");
	}
	
}
class Marine extends Unit{
	static int cnt=0;
	private int objective_idx=0;
	Marine()
	{
		super();
		objective_idx = no;
		this.hp = 50;
		this.atk = 10;
		this.def = 10;
		this.x = 10;
		this.y =10;
	}
	void move(int x, int y)
	{
		this.x = x;
		this.y = y;
		System.out.printf("Marine moved to (%d,%d)\n", this.x,this.y);
	}
	void stimpack()
	{
		this.hp-=20;
		
	}
}
class Tank extends Unit implements Runnable{

	static int cnt=0;
	private boolean siege_mode = false;
	private int objective_idx=0;
	Thread change_mode_thread;
	Tank()
	{ 
		super();
		objective_idx = no;
		this.hp = 50;
		this.atk = 10;
		this.def = 10;
		this.x = 10;
		this.y =10;
	}
	@Override
	void move(int x, int y) {
		// TODO Auto-generated method stub
		if(!siege_mode)
		{
		this.x = x;
		this.y = y;
		System.out.printf("Tank moved to (%d,%d)\n", x,y);
		}
		else
			System.out.println("Tank Can't move on Siege mode");
	}
	void changeMode()
	{
		if(!siege_mode)
		{
			this.atk = 100;
			siege_mode = true;
//			change_mode_thread = new Thread
		}
		else
		{
			this.atk = 10;
			siege_mode = false;
		}
	}
	public void run()
	{
		
	}
}
public class StarCraft {
	public static Unit[] group;
	public suicider s;
	
	public StarCraft()
	{
		group = new Unit[4];
		group[0] = new Marine();
		group[1] = new Tank();
		group[2] = new suicider();
		group[3] = new Dropship();
	//	s = new Drp();
		Thread expl = new Thread((Runnable) group[3]);
		
		for(int i=0;i<4;i+=1)
		{
			group[i].move(100,200);
		}expl.start();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int menu = 0;
	new StarCraft();
	
	((suicider) group[2]).explode();
		while(true)
		{
			
			menu = sc.nextInt();
		}
	}

}
