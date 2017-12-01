package Thread;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadWaitEx3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Table table = new Table();
		new Thread(new Cook(table), "COOK1").start();
		new Thread(new Customer(table, "donut"), "CUST1").start();
		new Thread(new Customer(table, "burger"), "CUST2").start();
		new Thread(new Customer(table, "pizza"), "CUST3").start();
		new Thread(new Customer(table, "chicken"), "CUST4").start();
		
		try {
			Thread.sleep(3*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
class Table {
	String[] dishNames = {"donut", "donut", "burger",
			 "burger", "pizza", "pizza", "chicken"};
	final int MAX_FOOD = 12;
	private ArrayList<String> dishes = new ArrayList<>(12);
	private  ReentrantLock loc = new ReentrantLock();
	private Condition forCook = loc.newCondition();
	private Condition forDonut = loc.newCondition();
	private Condition forBurger = loc.newCondition();
	private Condition forPizza = loc.newCondition();
	private Condition forChicken = loc.newCondition();
	static volatile int food_cnt=0;
	static volatile boolean is_full=false;
	static volatile boolean eat= false;
	static volatile boolean once=false;
	public Table()
	{
		
	}
	public void add(String dish)
	{
		loc.lock();
		try {
		while(dishes.size() >= MAX_FOOD)
		{
		
			
				String name = Thread.currentThread().getName();
			//	System.out.println(name+" is waiting");
		//		System.out.println("foodcnt: " + food_cnt);
				
		}
			dishes.add(dish); food_cnt+=1;
			System.out.print("Cook maked " + dish + " // ");
			if(food_cnt == MAX_FOOD)
			{
				System.out.println("h5");
			try
			{
				is_full = true;
				eat = true;
				forDonut.signal();
				forBurger.signal();
				forPizza.signal();
				forChicken.signal();
				Thread.sleep(200);//forCook.await();
			}
			catch(InterruptedException e) { }
			}
			System.out.println("Dishes:" + dishes.toString());
		}
		finally
		{
			loc.unlock();
		}
	
		
	}
	public void remove(String dishName)
	{
		loc.lock();
		String name=Thread.currentThread().getName();
		System.out.println("h1");
		try {
		while( eat == false)
		{
			System.out.println("h2");
			System.out.println(name + " is waiting..");
			if(once == false)
			{
			try
			{
			//	if(name.equals("CUST1"))
				System.out.println("h3");
					forDonut.await();
			//	else if(name.equals("CUST2"))
					forPizza.await();
			//	else if(name.equals("CUST3"))
 					forBurger.await(); 
			//	else if(name.equals("CUST4"))
					forChicken.await();
				Thread.sleep(500);
			}catch(InterruptedException e) {}
			once= true;
			}
		}
		while(true)
		{
		//	System.out.println("h4");
			if(food_cnt == 0)
			{
				
				try
				{
					is_full = false;
					eat = false;
					forCook.signal();
					Thread.sleep(500);
				}catch(InterruptedException e) {}
				
			}
			for(int i=0;i<dishes.size();i+=1)
			{
				if(dishName.equals(dishes.get(i)))
				{
					dishes.remove(i);food_cnt-=1;
					System.out.println(name + "ate " + dishName);
			//		forCook.signal();
					return;
				}
			}
			
			
		}
		}finally
		{
			loc.unlock();
		}
		
	}
	public int dishNum() {
		return dishNames.length;
	}
}
class Cook implements Runnable
{
	private Table table;
	public Cook(Table table) {this.table = table;}
	public void run()
	{
		while(true)
		{
		int idx = (int)(Math.random() * table.dishNum());
		
		table.add(table.dishNames[idx]);
		try {
			Thread.sleep(10);
		}catch(InterruptedException e) {
			
		}
		}
	}
}
class Customer implements Runnable{
	private Table table;
	private String food;
	public Customer(Table table, String food)
	{
		this.table = table;
		this.food = food;
	}
	public void run()
	{
		while(true)
		{
			try {Thread.sleep(100); } catch(InterruptedException e) {}
			String name = Thread.currentThread().getName();
			table.remove(food);
			System.out.println(name + " ate a " + food);
		}
	}
}