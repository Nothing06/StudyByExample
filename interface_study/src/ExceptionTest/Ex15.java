package ExceptionTest;
import java.io.*;
import java.util.Scanner;
public class Ex15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s;
		Scanner sc= new Scanner(System.in);
		System.out.print("���ڿ� �Է�: ");
		s = sc.nextLine();
			File f = createFile(s);
			System.out.println(f.getName() + "������ ���������� �����Ǿ����ϴ�");
	}
	static File createFile(String filename)
	{
		try
		{
			if(filename==null || filename.equals(""))
				throw new Exception("�����̸��� ��ȿ���� �ʽ��ϴ�");
			
			
		}
		catch(Exception e)
		{
			filename = "�������.txt";
		}
		finally
		{
			File f = new File(filename);
			createNewFile(f);
			return f;
		}
	}
	static void createNewFile(File f)
	{
		try
		{
			f.createNewFile();
		}
		catch(Exception e)
		{
			
		}
	}
}
