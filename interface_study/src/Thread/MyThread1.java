package Thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 요리사가 12개의 음식을 만들어놓으면
 * 손님들이 먹기시작하고   모든 손님(3명)이 음식을 다 먹을때까지 기다리다가
 * 모든손님이 음식을 다 먹으면
 * 요리사가 새로 음식을 만든다.(12개를 만들때까지는 손님들 전부 대기하도록 하고   12개 다 만들면  또 손님 먹기시작하도록.  구현.)
 */
class Cook2 implements Runnable
{
	private Table2 table;
	public Cook2(Table2 table) {this.table = table;}
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
class Customer2 implements Runnable{
	private Table2 table;
	private String food;
	public Customer2(Table2 table, String food)
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
		//	System.out.println(name + " ate a " + food);
		}
	}
}
public class MyThread1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Table2 table = new Table2();
		new Thread(new Cook2(table), "COOK1").start();
		new Thread(new Customer2(table, "donut"), "CUST1").start();
		new Thread(new Customer2(table, "burger"), "CUST2").start();
		new Thread(new Customer2(table, "pizza"), "CUST3").start();
		
		try {
			Thread.sleep(15
					*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
class Table2 {
	String[] dishNames = {"donut", "donut", "burger", "pizza"
			 };
	final int MAX_FOOD = 12;
	private ArrayList<String> dishes = new ArrayList<>(12);
	private  ReentrantLock loc = new ReentrantLock();
	private Condition forCook = loc.newCondition();
	private Condition forDonut = loc.newCondition();
	private Condition forPizza = loc.newCondition();
	private Condition forBurger = loc.newCondition();
	static volatile boolean eat = false;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
	public Table2()
	{
		
	}
	public void add(String dish)
	{
		loc.lock();
		try
		{
			if(eat == true)
			{
				String name = Thread.currentThread().getName();
				
					System.out.println(name + " is waiting.");
					
				try
				{
					System.out.println("Chef: Eating time has begun!");
					forDonut.signal();
					forPizza.signal();
					forBurger.signal();
					forCook.await();
				}
				catch(InterruptedException e) {}
				
			}
			dishes.add(dish);
			if(dishes.size() == MAX_FOOD)
			{
				eat = true;
			}
			System.out.println("Cook maked " + dish + " // "
					+ dishes.toString());
			
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			loc.unlock();
		}
	}
/*	public void add(String dish)
	{
		loc.lock();
		try
		{
			
			if(eat == true)
			{
				String name = Thread.currentThread().getName();
				
				try
				{
					forDonut.signal();
					forPizza.signal();
					forBurger.signal();
					System.out.println("Chef: Eating time has begun!");
					forCook.await();
					
					Thread.sleep(500);;
				}
				catch(InterruptedException e) {}
			}
			
			dishes.add(dish);
			if(dishes.size() == MAX_FOOD) {
		//	forCust.signalAll();
			eat = true;
			}
			System.out.print("Cook maked " + dish + " // ");
			System.out.println("Dishes:" + dishes.toString());
			
		}
		finally
		{
			loc.unlock();
		}
	}*/
	
	public void remove(String dishname)
	{
		loc.lock();
		String name = Thread.currentThread().getName();
		
		try
		{
			if(eat == false)
			{
				try
				{
					if(dishname.equals("donut"))
						forDonut.await();
					else if(dishname.equals("burger"))
						forBurger.await();
					else if(dishname.equals("pizza"))
						forPizza.await();
					
				}
				catch(InterruptedException e) {}
			}
		
			while(eat == true)
			{
				for(int i=0;i<dishes.size();i+=1)
				{
					if(dishes.get(i).equals(dishname))
					{
						dishes.remove(i);
						System.out.println(name + " ate a " + dishname
								+ " // " + dishes.toString());
						if(dishes.indexOf(dishname)==-1)
						{
							if(dishes.size() == 0)
							{
								eat=false; forCook.signal();
							}
						}
						return;
					}
				}
				
				try
				{
					System.out.println(name + " is waiting for " +  dishname);
					if(dishname.equals("donut"))
						forDonut.await();
					else if(dishname.equals("burger"))
						forBurger.await();
					else if(dishname.equals("pizza"))
						forPizza.await();
				}
				catch(InterruptedException e) {}
			}
		}
		finally {
			loc.unlock();
		}
	}
	/*
	public void remove(String dishName)
	{
		loc.lock();
	//	int no_remain=0;
		String name = Thread.currentThread().getName();
		try
		{
			if(eat == false)
			{
				System.out.println(name + " is waiting for eating time.");
				try
				{
					if(dishName.equals("burger"))
						forBurger.await();
					else if(dishName.equals("donut"))
						forDonut.await();
					else if(dishName.equals("pizza"))
						forPizza.await();
					Thread.sleep(500);
				}catch(InterruptedException e) {}
			}
			while(eat==true)
			{
				
			//	System.out.println("size is " + dishes.size());
				for(int i=0; i<dishes.size(); i+=1)
				{
					
					if(dishName.equals(dishes.get(i)))
					{
						dishes.remove(i);
						System.out.print(name + " ate a " + dishName + "// "
								+ "dishSize:" +
													dishes.size() + " // " + eat);
						System.out.println(" Dishes:" + dishes.toString());
						//forCook.signal();
						
						if(dishes.indexOf(dishName)==-1)
						{
							try
							{
								System.out.println(name + " is waiting for " + 
							                        dishName );
								if(dishes.size() <=0) {
									forCook.signal();
									eat = false;
									//return;
									//break;
									}
								if(dishName.equals("burger"))
									forBurger.await();
								else if(dishName.equals("donut"))
									forDonut.await();
								else if(dishName.equals("pizza"))
									forPizza.await();
								
								Thread.sleep(500);
							}catch(InterruptedException e) {}
							
						}
						return;
					}//if
					
				}//for
				
				try
				{
					System.out.println(name + " is waiting");
					if(dishName.equals("burger"))
						forBurger.await();
					else if(dishName.equals("donut"))
						forDonut.await();
					else if(dishName.equals("pizza"))
						forPizza.await();
					
					Thread.sleep(500);
				}catch(InterruptedException e) {}
			}// while
		}//try
		finally { loc.unlock();}
	}*/
	public int dishNum() { return dishNames.length;}
}