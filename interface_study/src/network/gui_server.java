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

//클라이언트로 부터 전송된 문자열을 받아서 다른 클라이언트에게 문자열을
//보내주는 스레드
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
			out = new DataOutputStream(socket.getOutputStream());  // 클라이언트 한개!.에 대응하는  서버의 in과 out 스트림이다.  
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
		//	sendMsg("#"+name+"님이 입장하셨습니다.");
			System.out.println("현재 접속자수는 " + vec.size()+ "명입니다.");
			
			str = in.readUTF();
		//	sendMsg(str);
			
			StringTokenizer st =new StringTokenizer(str, "[]");System.out.println("현재 서버 접속자 수는 " + vec.size() + "입니다.");
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
			//	System.out.println("현재 서버 접속자 수는 " + vec.size() + "입니다.");
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
	//		sendMsg("#"+name + "님이 나가셨습니다.");
			if(is_registered == true)
			{
			vec.remove(socket);
			System.out.println("["+socket.getInetAddress() + ":" + socket.getPort()
									+ "]" + "에서 접속을 종료하였습니다.");
			System.out.println("현재 서버 접속자 수는 " + vec.size() + "입니다.");
			}
		}
	}
	void sendFlag(String msg)
	{

        /*     for(Socket socket:vec){

                    //for를 돌되 현재의 socket이 데이터를 보낸 클라이언트인 경우를 제외하고 

                    //나머지 socket들에게만 데이터를 보낸다.

                    if(socket != this.socket){

                          PrintWriter pw = 

                                 new PrintWriter(socket.getOutputStream(), true);

                          pw.println(str);

                          pw.flush();

                          //단,여기서 얻어온 소켓들은 남의것들이기 때문에 여기서 닫으면 안된다.

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

                    //for를 돌되 현재의 socket이 데이터를 보낸 클라이언트인 경우를 제외하고 

                    //나머지 socket들에게만 데이터를 보낸다.

                    if(socket != this.socket){

                          PrintWriter pw = 

                                 new PrintWriter(socket.getOutputStream(), true);

                          pw.println(str);

                          pw.flush();

                          //단,여기서 얻어온 소켓들은 남의것들이기 때문에 여기서 닫으면 안된다.

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

                           //클라이언트로 부터 문자열 받기

                           str=br.readLine();

                           //상대가 접속을 끊으면 break;

                           if(str==null){

                                 //벡터에서 없애기

                                 vec.remove(socket);

                                 break;

                           }

                           //연결된 소켓들을 통해서 다른 클라이언트에게 문자열 보내주기

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

       

       //전송받은 문자열 다른 클라이언트들에게 보내주는 메서드

       public void sendMsg(String str){

             try{

               /*     for(Socket socket:vec){

                           //for를 돌되 현재의 socket이 데이터를 보낸 클라이언트인 경우를 제외하고 

                           //나머지 socket들에게만 데이터를 보낸다.

                           if(socket != this.socket){

                                 PrintWriter pw = 

                                        new PrintWriter(socket.getOutputStream(), true);

                                 pw.println(str);

                                 pw.flush();

                                 //단,여기서 얻어온 소켓들은 남의것들이기 때문에 여기서 닫으면 안된다.

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
				System.out.println("클라이언트 기다리는 중");
				socket = server.accept();
				System.out.println("HELLO");
				System.out.println("IP: "+socket.getInetAddress() +
										" Port: "+socket.getPort() + "에서 접속함.");
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

             //클라이언트와 연결된 소켓들을 배열처럼 저장할 벡터객체 생성

             Vector<Socket> vec = new Vector<Socket>();

             try{

                    server= new ServerSocket(3000);

                    while(true){

                           System.out.println("접속대기중..");

                           socket = server.accept();

                           //클라이언트와 연결된 소켓을 벡터에 담기

                           vec.add(socket);

                           //스레드 구동

                           new EchoThread(socket, vec).start();

                    }

             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }

       }

}
*/