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
class Cook3 implements Runnable
{
	private Table3 table;
	private String menu;
	public Cook3(Table3 table, String menu) {
		this.table = table;
		this.menu = menu;
		}
	public void run()
	{
		while(true)
		{
	//	int idx = (int)(Math.random() * table.dishNum());
		
		table.add(menu);
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			
		}
		}
	}
}
class Customer3 implements Runnable{
	private Table3 table;
	private String food;
	public Customer3(Table3 table, String food)
	{
		this.table = table;
		this.food = food;
	}
	public void run()
	{
		while(true)
		{
			try {Thread.sleep(10); } catch(InterruptedException e) {}
			String name = Thread.currentThread().getName();
			table.remove(food);
		//	System.out.println(name + " ate a " + food);
		}
	}
}
public class MyThread2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Table3 t = new Table3();
		new Thread(new Cook3(t, "donut"), "COOK1").start();
		new Thread(new Cook3(t, "burger"), "COOk2").start();
		new Thread(new Cook3(t, "pizza"), "COOK3").start();
		
		new Thread(new Customer3(t, "donut"), "CUST1").start();
		new Thread(new Customer3(t, "burger"), "CUST2").start();
		new Thread(new Customer3(t, "pizza"), "CUST3").start();
		
		try {
			Thread.sleep(2
					*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

}
class Table3 {
	String[] dishNames = {"donut", "donut", "burger", "pizza" };
	final int MAX_FOOD = 12;
	private ArrayList<String> dishes = new ArrayList<>(12);
	private  ReentrantLock loc = new ReentrantLock();
	private  ReentrantLock loc1 = new ReentrantLock();
	private  ReentrantLock loc2 = new ReentrantLock();
	private  ReentrantLock loc3 = new ReentrantLock();
	private Condition forCook = loc.newCondition();
	private Condition forCook_donut = loc1.newCondition();
	private Condition forCook_pizza = loc2.newCondition();
	private Condition forCook_burger = loc3.newCondition();
	private Condition forDonut = loc1.newCondition();
	private Condition forPizza = loc2.newCondition();
	private Condition forBurger = loc3.newCondition();
	static volatile boolean pizza_eat = false;
	static volatile boolean donut_eat = false;
	static volatile boolean burger_eat = false;
	static volatile boolean is_full= false;
	static volatile boolean is_full_pizza = false;
	static volatile boolean is_full_donut = false;
	static volatile boolean is_full_burger = false;
	public Table3()
	{
		
	}
	public void add(String dish)
	{
	/*	if(dish.equals("donut"))
			loc1.lock();
		else if(dish.equals("pizza"))
			loc2.lock();
		else if(dish.equals("burger"))
			loc3.lock();*/
		loc.lock();
		String name = Thread.currentThread().getName();
		try
		{
			if(is_full == true)
			{
				
				
			//		System.out.println(name + " is waiting.");
					
				try
				{
					
					if(dish.equals("donut") && is_full_donut==true)
					{
						System.out.println("Chef: " + dish + " is ready! eat!");
						forDonut.signal();
						forCook_donut.await();
					}
					else if(dish.equals("burger")  && is_full_burger==true)
					{
						System.out.println("Chef: " + dish + " is ready! eat!");
						forBurger.signal();
						forCook_burger.await();
					}
					else if(dish.equals("pizza")  && is_full_pizza==true)
					{
						System.out.println("Chef: " + dish + " is ready! eat!");
						forPizza.signal();
						forCook_pizza.await();
					}
				//	forCook.await();
			//		forBurger.signal();
					
					
				}
				catch(InterruptedException e) {}
				
			}
			dishes.add(dish);
			if(dishes.size() == MAX_FOOD)
			{
				is_full = true; is_full_pizza= true;
				is_full_donut= true;  is_full_burger= true;
			//	forCook.await();
			}
			System.out.println(name + " maked " + dish + " // "
					+ dishes.toString());
			
		}
		catch(Exception e)
		{
			
		}
		finally
		{
		/*	if(dish.equals("donut"))
				loc1.lock();
			else if(dish.equals("pizza"))
				loc2.lock();
			else if(dish.equals("burger"))
				loc3.lock();*/
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
			if(is_full == false)
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
		
			while(is_full == true)
			{
				for(int i=0;i<dishes.size();i+=1)
				{
					if(dishes.get(i).equals(dishname))
					{
						dishes.remove(i);
						System.out.println(name + " ate a " + dishname
								+ " // " );//+ dishes.toString());
						if(dishes.indexOf(dishname)==-1)
						{
							break;
						/*	if(dishname.equals("donut"))
							{
								is_full_donut=false;
								forCook_donut.signal();
								is_full=false;
							}
							else if(dishname.equals("burger"))
							{
								is_full_burger=false;
								forCook_burger.signal();
								is_full=false;
							}
							else if(dishname.equals("pizza"))
							{
								is_full_pizza=false;
								 forCook_pizza.signal();
								 is_full=false;
							}forCook.signal();
						/*	if(dishes.size()==0)
							{
								is_full=false;
								forCook.signal();
							}*/
						}
						return;
					}
				}
				
				try
				{
					is_full=false;
				//	System.out.println(name + " is waiting for " +  dishname);
					if(dishname.equals("donut"))
						forCook_donut.signal();
					else if(dishname.equals("burger"))
						forCook_burger.signal();
					else if(dishname.equals("pizza"))
						forCook_pizza.signal();
				}
				catch(Exception e) {}
				finally {loc.unlock();}
			}
		}
		finally {
		/*	if(dishname.equals("donut"))
				loc1.unlock();
			else if(dishname.equals("pizza"))
				loc2.unlock();
			else if(dishname.equals("burger"))
				loc3.unlock();
			*/loc.unlock();
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