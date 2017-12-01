package StringTokenizer;
import java.util.*;
public class Ex4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "이백삼십삼만천백십오";
		String form; 
		long num=2323230;
		System.out.println(input);
		System.out.println(num=hangeulToNum(input));
		System.out.println(numberComma(num));
	}
	public static String numberComma(long num)
	{
		String s = Long.toString(num);
		char src[];
		char s_arr[];
		String result;
		int len=0;
		int comma_cnt=0;
		int i=0;
		int comma=0;
		int c=0;
		
		len = s.length();
		comma_cnt = len/3;
		src = s.toCharArray();
	//	System.out.println(src);
		if(len % 3 == 0 && len>0)
		{
			comma_cnt-=1;
		}
		
		s_arr= new char[len+comma_cnt+1	];
		for(i=len-1;i>=0;i-=1)
		{
			s_arr[i+comma_cnt-comma] = src[i];
			c+=1;
		//	System.out.println("index: " + (i+comma_cnt-comma)  +s_arr[i+comma_cnt-comma]);
			if(c%3==0 && i>0)
			{
				comma+=1;
				s_arr[i+comma_cnt-comma] = ',';
			//	System.out.println("index: " + (i+comma_cnt-comma)  +s_arr[i+comma_cnt-comma]);
			}
		}
		System.out.println(s_arr);
		result = String.valueOf(s_arr);
		System.out.println(result);
		return result;
	}
	public static long hangeulToNum(String input)
	{
		final String NUMBER = "영일이삼사오육칠팔구";
		final String UNIT = "십백천만억조";
		final long UNIT_NUM[] = {10,100,1000,10000,(long)1e8,(long)1e12};
		long tmpResult=0;
		long result =0;
		long num=0;
		int check=0;
		String token; // "천백십일만오백육"
		StringTokenizer st = new StringTokenizer(input, UNIT, true);
		while(st.hasMoreTokens())
		{
			token = st.nextToken();
			check = NUMBER.indexOf(token);
			if(check == -1) // unit
			{
				if("만억조".indexOf(token) == -1)
				{
					tmpResult += (num!=0 ? num : 1) * (UNIT_NUM[UNIT.indexOf(token)]);
				}
				else
				{
					tmpResult+=num;
					result += (tmpResult!=0?tmpResult:1) * (UNIT_NUM[UNIT.indexOf(token)]);
					tmpResult=0;
				}
				num=0;
			}
			else
			{
				num = check;
			}
		}
		return result + tmpResult + num;
	}
	/*
	public static long hangeulToNum(String input)
	{
		long result =0;
		long num=0;
		long tmpResult =0;
		
		final String NUMBER="영일이삼사오육칠팔구";
		final long UNIT_NUM[] = {10,100,1000,10000,(long)1e8,(long)1e12};
		final String UNIT = "십백천만억조";
		String s = "이십일억오천구백만삼";
		StringTokenizer st = new StringTokenizer(input,UNIT,true);
		
		while( st.hasMoreTokens())
		{
			String token = st.nextToken();
			int check = NUMBER.indexOf(token);
			if(check == -1)
			{
				if("만억조".indexOf(token)!=-1)
				{
					tmpResult += (num!=0 ? num:1) * UNIT_NUM[UNIT.indexOf(token)];
				}
				else
				{
					tmpResult+=num;
					result += (tmpResult!=0 ? tmpResult : 1) * (UNIT_NUM[UNIT.indexOf(token)]);
					tmpResult=0;
				}
				num=0;
			}
			else
			{
				num = check;
			}
		}
		return result + tmpResult + num;
	}*/
	/*
	public static long hangeulToNum(String input)
	{
		long result=0;
		long tmpResult=0;
		long num=0;
		
		final String NUMBER="영일이삼사오육칠팔구";
		final String UNIT ="십백천만억조";
		final long[] UNIT_NUM = {10,100,1000,10000,(long)1e8, (long)1e12};
		
		StringTokenizer st = new StringTokenizer(input,UNIT, true);
		
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			int check = NUMBER.indexOf(token);
			if(check == -1)
			{
				if("만억조".indexOf(token) == -1)
				{
					tmpResult += (num!=0 ? num : 1) * (UNIT_NUM[UNIT.indexOf(token)]);
				}
				else
				{
					tmpResult+=num;
					result += (tmpResult != 0 ? tmpResult : 1) * (UNIT_NUM[UNIT.indexOf(token)]);
					tmpResult=0;
				}
				num=0;
			}
			else
			{
				num = check;
			}
		}
		return result+tmpResult+num;*/
		/*
		while(st.hasMoreTokens())
		{
			String token = st.nextToken();
			int check = NUMBER.indexOf(token);
			if(check == -1)
			{
				if("만억조".indexOf(token)==-1)
				{
					tmpResult += ((num!=0 ? num : 1) * UNIT_NUM[UNIT.indexOf(token)]);
				}
				else
				{
					tmpResult += num;
					result += (tmpResult!=0 ? tmpResult : 1) * UNIT_NUM[UNIT.indexOf(token)];
					tmpResult=0;
					//result=0;
				}
				num=0;
			}
			else
			{
				num = check;
			}
			System.out.println("In while::" + result);
		}
		return  num+ result + tmpResult;
	}*/

}
