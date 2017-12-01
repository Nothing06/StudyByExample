package FileIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class IOEx1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			byte[] inSrc;
			byte[] outSrc= null;
			
			ByteArrayInputStream input;
			ByteArrayOutputStream output;
			
			inSrc = new byte[10];
			for(int i=0;i<inSrc.length;i+=1)
				inSrc[i] = (byte) i;
			input = new ByteArrayInputStream(inSrc);
			output = new ByteArrayOutputStream();
			
			int data=0;
			
			while((data = input.read())!=-1)
			{
				output.write(data);
				System.out.print(data +", ");
			}System.out.println();
			outSrc = output.toByteArray();
//			System.out.println("Input Source: " + Arrays.toString(inSrc));
		//	System.out.println("Output Source: " + Arrays.toString(outSrc));
			System.out.println("Output Source: " + Arrays.toString(outSrc));
	}

}
