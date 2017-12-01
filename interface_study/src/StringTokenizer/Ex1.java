package StringTokenizer;

import java.util.StringTokenizer;

public class Ex1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String source = "100,200,300,400";
		String source2 = "x=100*(200+300)/2";
		StringTokenizer st = new StringTokenizer(source,",");
		StringTokenizer st2 = new StringTokenizer(source2,"=*+-()/", true);
		while(st2.hasMoreTokens())
		{
			//System.out.println(st.nextToken());
			System.out.println(st2.nextToken());
		}
	}

}
