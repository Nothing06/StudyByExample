package Thread;

public class ThreadEx15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			RunImpleEx15 r = new RunImpleEx15();
			Thread t1=new Thread(r,"A thread");
			Thread t2 = new Thread(r, "B Thread");
			Thread t3 = new Thread(r, "C Thread");
			
			t1.start();
			t2.start();
			t3.start();
			
			try
			{
				Thread.sleep(2000);
				t1.suspend();
				Thread.sleep(2000);
				t2.suspend();
				Thread.sleep(3000);
				t1.resume();
				Thread.sleep(3000);
				t1.stop();
				t2.stop();
				Thread.sleep(2000);
				t3.stop();
			}
			catch(InterruptedException e)
			{
				
			}
	}

}
class RunImpleEx15 implements Runnable{
	public void run()
	{
		while(true)
		{
			System.out.println(Thread.currentThread().getName());
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				
			}
		}
	}
}