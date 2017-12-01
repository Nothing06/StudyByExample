package network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class multichatserver {

	@SuppressWarnings("rawtypes")
	HashMap clients;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	multichatserver()
	{
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new multichatserver().start();
	}
	public void start()
	{
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try
		{
			serverSocket = new ServerSocket(7777);
			System.out.println("서버가 시작되었습니다");
			while(true)
			{
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress()
								+ ":" + socket.getPort() + "]" + "에서 접속하였습니다.");
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	void sendToAll(String msg, String name)
	{
		@SuppressWarnings("rawtypes")
		Iterator it = clients.keySet().iterator();
		while(it.hasNext()) 
		{
			try {
				String token = (String) it.next();
				if(!name.equals(token)) {
				DataOutputStream out = (DataOutputStream)clients.get(token);
				out.writeUTF(msg);
				}
			}
			catch(IOException e)
			{
				
			}
		}
	}
	class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		ServerReceiver(Socket socket)
		{
			this.socket = socket;
			try
			{
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			}
			catch(IOException e) {}
		}
		@SuppressWarnings("unchecked")
		public void run()
		{
			String name ="";
			try
			{
				name = in.readUTF();
				sendToAll("#" + name + "님이 들어오셨습니다.", name);
				clients.put(name,  out);
				System.out.println("현재 접속자 수는 " + clients.size() +"입니다. ");
				while(in!=null)
				{
					sendToAll(in.readUTF(), name);
				}
			}
			catch(IOException e)
			{
				
			}
			finally {
				sendToAll("#" + name + "님이 나가셨습니다.",name);
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + ":"
										+ socket.getPort() + "]" + "에서 접속을 종료.");
				System.out.println("현재 서버접속자 수는 " + clients.size() + "입니다.");
			}//try
		}//run
	}//ReceiverThread
	
}//class

