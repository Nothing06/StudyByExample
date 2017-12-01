package RandomTest;

import java.util.Arrays;

public class RandomEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				for(int i=0;i<10;i+=1)
					System.out.print(getRand(5,10)+",");
				System.out.println();
				int[] result = fillRand(new int[10], new int[] {2,3,7,5});
				System.out.println(Arrays.toString(result));
	}
	public static int[] fillRand(int[] arr, int from, int to)
	{
		for(int i=0;i<arr.length;i+=1)
		{
			arr[i] = getRand(from, to);
		}
		return arr;
	}
	public static int[] fillRand(int[] arr, int[] data)
	{
		for(int i=0;i<arr.length;i+=1)
		{
			arr[i] = data[getRand(0, data.length-1)];
		}
		return arr;
	}
	public static int getRand(int f,int t)
	{
		return (int)(Math.random() * (Math.abs(t-f)+1)) + Math.min(f, t);
	}
}
