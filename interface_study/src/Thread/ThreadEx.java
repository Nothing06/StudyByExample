package Thread;

import javax.swing.JOptionPane;

public class ThreadEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		myThread mt;
		mt = new myThread();
		mt.start();
		
		String input = JOptionPane.showInputDialog("�ƹ����̳� �Է�: ");
		mt.interrupt();
	}

}
class myThread extends Thread
{
	public void run()
	{
		int i=3;
		while(i!=0 && !isInterrupted())
		{
			System.out.println(i--);
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
				interrupt();
			}
		}
		System.out.println("ī��Ʈ�� ����Ǿ����ϴ�");
	}
}