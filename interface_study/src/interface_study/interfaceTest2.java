package interface_study;

public class interfaceTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			int number=100;
			int result=0;
			int i=0;
			int arr[] = new int[10];
			for(i=0;i<10;i+=1)
			{
				try {
					if(i!=3)
					{
					result = number / (int)(Math.random()*10);
					System.out.println(result);
					}
					else
					{
						arr[10]=10;
					}
				}
				catch(ArithmeticException e)
				{
					System.out.println(0);
				}
				catch(Exception e)
				{
					System.out.println("Exception occured.");
				}
			}
	}

}
