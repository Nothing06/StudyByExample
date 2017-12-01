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
			System.out.println("������ ���۵Ǿ����ϴ�");
			while(true)
			{
				socket = serverSocket.accept();
				System.out.println("[" + socket.getInetAddress()
								+ ":" + socket.getPort() + "]" + "���� �����Ͽ����ϴ�.");
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
				sendToAll("#" + name + "���� �����̽��ϴ�.", name);
				clients.put(name,  out);
				System.out.println("���� ������ ���� " + clients.size() +"�Դϴ�. ");
				while(in!=null)
				{
					sendToAll(in.readUTF(), name);
				}
			}
			catch(IOException e)
			{
				
			}
			finally {
				sendToAll("#" + name + "���� �����̽��ϴ�.",name);
				clients.remove(name);
				System.out.println("[" + socket.getInetAddress() + ":"
										+ socket.getPort() + "]" + "���� ������ ����.");
				System.out.println("���� ���������� ���� " + clients.size() + "�Դϴ�.");
			}//try
		}//run
	}//ReceiverThread
	
}//class

