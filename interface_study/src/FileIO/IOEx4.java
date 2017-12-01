package FileIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.*;
import java.util.Arrays;

public class IOEx4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			byte[] inSrc = {0,1,2,3,4,5,6,7,8,9};
			byte[] outSrc=null;
			int len=0;
			byte[] temp = new byte[4];
			int read_cnt=1;
			
			ByteArrayInputStream input = null;
			ByteArrayOutputStream output = null;
			
			input = new ByteArrayInputStream(inSrc);
			output = new ByteArrayOutputStream();
			
			
			{
				//len = input.read(temp,0, temp.length);
				do
				{
					len = input.read(temp,0,temp.length);
					output.write(temp, 0, len);
					System.out.print("read" +read_cnt++ + ": ");
					System.out.println(Arrays.toString(temp));
					
					
				}while(input.available()>0);
			}
			
			outSrc = output.toByteArray();
			System.out.println("Input Source : " + Arrays.toString(inSrc));
			System.out.println("Output Source : " + Arrays.toString(outSrc));
	}

}
