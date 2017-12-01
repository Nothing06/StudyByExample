package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

//Ŭ���̾�Ʈ�� ���� ���۵� ���ڿ��� �޾Ƽ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ���
//�����ִ� ������
class EchoThread extends Thread{
	Socket socket;
	Vector<Socket> vec;
	DataInputStream in;
	DataOutputStream out;
	public boolean is_registered = false;
	
	public EchoThread(Socket socket, Vector<Socket> vec)
	{
		this.socket = socket;
		this.vec = vec;
		try
		{
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());  // Ŭ���̾�Ʈ �Ѱ�!.�� �����ϴ�  ������ in�� out ��Ʈ���̴�.  
		}
		catch(IOException e) {}
	}
	public void check_registered(String name)
	{
		
	}
	public void run( )
	{
		
		String name="";
		try
		{
			String str;
		//	name = in.readUTF();
		//	sendMsg("#"+name+"���� �����ϼ̽��ϴ�.");
			System.out.println("���� �����ڼ��� " + vec.size()+ "���Դϴ�.");
			
			str = in.readUTF();
		//	sendMsg(str);
			
			StringTokenizer st =new StringTokenizer(str, "[]");System.out.println("���� ���� ������ ���� " + vec.size() + "�Դϴ�.");
			String client_id = st.nextToken();
			String client_password = st.nextToken();
		//	int client_i = Integer.valueOf(client_id);
			String tmp_id;
			String tmp_password;
			
			System.out.println(client_id);
			for(int i=0;i< DB_Person.tuple_cnt; i+=1)
			{
				tmp_id  = (String) DB_Person.model.getValueAt(i,0) ;
				tmp_password = (String)DB_Person.model.getValueAt(i, 1);
			//	System.out.println(tmp_id);
				if(tmp_id.equals( client_id) && tmp_password.equals(client_password))
				{
					is_registered = true;
					break;
				}
			}
			if(is_registered == true)
			{
				vec.add(socket);
			//	System.out.println("���� ���� ������ ���� " + vec.size() + "�Դϴ�.");
				sendFlag("true");
				sendMsg(str);
				while(in!=null)
				{
					str = in.readUTF();
					
					sendMsg(str);
					System.out.println(str);
				}
			}
			else
			{
			//	vec.remove(socket);
				vec.add(socket);
				sendFlag("false");
				
				vec.remove(socket);
				socket.close();
				in.close();
				out.close();
				
			//	this.stop();
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
	//		sendMsg("#"+name + "���� �����̽��ϴ�.");
			if(is_registered == true)
			{
			vec.remove(socket);
			System.out.println("["+socket.getInetAddress() + ":" + socket.getPort()
									+ "]" + "���� ������ �����Ͽ����ϴ�.");
			System.out.println("���� ���� ������ ���� " + vec.size() + "�Դϴ�.");
			}
		}
	}
	void sendFlag(String msg)
	{

        /*     for(Socket socket:vec){

                    //for�� ���� ������ socket�� �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ� 

                    //������ socket�鿡�Ը� �����͸� ������.

                    if(socket != this.socket){

                          PrintWriter pw = 

                                 new PrintWriter(socket.getOutputStream(), true);

                          pw.println(str);

                          pw.flush();

                          //��,���⼭ ���� ���ϵ��� ���ǰ͵��̱� ������ ���⼭ ������ �ȵȴ�.

                    }*/
		
				try
				{
					DataOutputStream ou= new DataOutputStream(socket.getOutputStream());
					
					ou.writeUTF(msg);
				}
				catch(IOException e) {}
		
		
	}
	void sendMsg(String msg)
	{

        /*     for(Socket socket:vec){

                    //for�� ���� ������ socket�� �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ� 

                    //������ socket�鿡�Ը� �����͸� ������.

                    if(socket != this.socket){

                          PrintWriter pw = 

                                 new PrintWriter(socket.getOutputStream(), true);

                          pw.println(str);

                          pw.flush();

                          //��,���⼭ ���� ���ϵ��� ���ǰ͵��̱� ������ ���⼭ ������ �ȵȴ�.

                    }*/
		if(is_registered == true)
		{
			for(Socket socket:vec)
			{
				if(this.socket!=socket)
				{
					try
					{
						DataOutputStream ou= new DataOutputStream(socket.getOutputStream());
						
						ou.writeUTF(msg);
					}
					catch(IOException e) {}
				}
			}
		}
	}
}
/*
class EchoThread extends Thread{

       Socket socket;

       Vector<Socket> vec;

       public EchoThread(Socket socket, Vector<Socket> vec){

             this.socket = socket;

             this.vec = vec;

       }

       public void run(){

             BufferedReader br = null;

             try{

                    br = new BufferedReader(

                                 new InputStreamReader(socket.getInputStream()));

                    String str =null;

                    while(true){

                           //Ŭ���̾�Ʈ�� ���� ���ڿ� �ޱ�

                           str=br.readLine();

                           //��밡 ������ ������ break;

                           if(str==null){

                                 //���Ϳ��� ���ֱ�

                                 vec.remove(socket);

                                 break;

                           }

                           //����� ���ϵ��� ���ؼ� �ٸ� Ŭ���̾�Ʈ���� ���ڿ� �����ֱ�

                           sendMsg(str);                    

                    }

                    

             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }finally{

                    try{

                           if(br != null) br.close();

                           if(socket != null) socket.close();

                    }catch(IOException ie){

                           System.out.println(ie.getMessage());

                    }

             }

       }

       

       //���۹��� ���ڿ� �ٸ� Ŭ���̾�Ʈ�鿡�� �����ִ� �޼���

       public void sendMsg(String str){

             try{

               /*     for(Socket socket:vec){

                           //for�� ���� ������ socket�� �����͸� ���� Ŭ���̾�Ʈ�� ��츦 �����ϰ� 

                           //������ socket�鿡�Ը� �����͸� ������.

                           if(socket != this.socket){

                                 PrintWriter pw = 

                                        new PrintWriter(socket.getOutputStream(), true);

                                 pw.println(str);

                                 pw.flush();

                                 //��,���⼭ ���� ���ϵ��� ���ǰ͵��̱� ������ ���⼭ ������ �ȵȴ�.

                           }

                    }

             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }

       }

}*/
public class gui_server{
	public static DB_Person db;
	public static void main(String[] args)
	{
		ServerSocket server = null;
		Socket socket = null;
		
		Vector<Socket> vec = new Vector<Socket>();
		try
		{
			db = new DB_Person();
			server = new ServerSocket(3000);
			while(true)
			{
				System.out.println("Ŭ���̾�Ʈ ��ٸ��� ��");
				socket = server.accept();
				System.out.println("HELLO");
				System.out.println("IP: "+socket.getInetAddress() +
										" Port: "+socket.getPort() + "���� ������.");
		//		vec.add(socket);
				new EchoThread(socket,vec).start();
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}
}
/*
public class gui_server{

       public static void main(String[] args) {

             ServerSocket server = null;
             Socket socket =null;

             //Ŭ���̾�Ʈ�� ����� ���ϵ��� �迭ó�� ������ ���Ͱ�ü ����

             Vector<Socket> vec = new Vector<Socket>();

             try{

                    server= new ServerSocket(3000);

                    while(true){

                           System.out.println("���Ӵ����..");

                           socket = server.accept();

                           //Ŭ���̾�Ʈ�� ����� ������ ���Ϳ� ���

                           vec.add(socket);

                           //������ ����

                           new EchoThread(socket, vec).start();

                    }

             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }

       }

}
*/