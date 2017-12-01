package network;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.InetAddress;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// 키보드로 전송문자열 입력받아 서버로 전송하는 스레드

class WriteThread{

       Socket socket;
       String name;
       ClientFrame cf;
       DataOutputStream out;
       String str;
       String password;
       String id;

       public WriteThread(ClientFrame cf) {

             this.cf  = cf;
             
          //   this.socket= cf.socket;
            
       }
       public void set_socket(Socket socket)
       {
    	   this.socket = socket;
    	   try
           {
          	 out = new DataOutputStream(
          			 socket.getOutputStream());
          
           }catch(Exception e) {}
       }
       public void sendMsg() {

             //키보드로부터 읽어오기 위한 스트림객체 생성

            // BufferedReader br=

           //  new BufferedReader(new InputStreamReader(System.in));

           //  PrintWriter pw=null;

             try{

                    //서버로 문자열 전송하기 위한 스트림객체 생성

               //     pw=new PrintWriter(socket.getOutputStream(),true);

                    //첫번째 데이터는 id 이다. 상대방에게 id와 함께 내 IP를 전송한다.

                    if(cf.isFirst==true){

                           InetAddress iaddr=socket.getLocalAddress();                       

                           String ip = iaddr.getHostAddress();                         

                           getId_from_ClientFrame(); getPassword_from_ClientFrame();

                           System.out.println("ip:"+ip+"id:"+id);

                           str = "["+id+"]"+ "[" + password + "]" + " 님 로그인 (" + ip +")"; 
                       	cf.isFirst = false;

                    }else{

                           str= "[" + id + "] " + cf.txtF.getText();
                         //  	out.writeUTF(); 
                           
                    }
                    
                    //입력받은 문자열 서버로 보내기
                    System.out.println(str);
                   	out.writeUTF(str);
             //       pw.println(str);
                   
             

             }catch(Exception ie){

                    System.out.println(ie.getMessage());

             }finally{

            /*        try{

                 //          if(br!=null) br.close();

                           //if(pw!=null) pw.close();

                           //if(socket!=null) socket.close();

                    }catch(IOException ie){

                           System.out.println(ie.getMessage());

                    }*/

             }

       }      

       public void getId_from_ClientFrame(){             

             id = Id.getId(); 

       }
       public void getPassword_from_ClientFrame()
       {
    	   password = Id.getPassword();
       }
}

//서버가 보내온 문자열을 전송받는 스레드

class ReadThread extends Thread{

       Socket socket;

       ClientFrame cf;
       DataInputStream in;
       //DataOutputStream out;

       public ReadThread(Socket socket, ClientFrame cf) {

             this.cf = cf;
             this.socket=socket;
             try
             {
            	 in = new DataInputStream(socket.getInputStream());
            	// out = new DataOutputStream(socket.getOutputStream());
            	 
             }
             catch(IOException e) {}
       }

//       
//       public void ServerLoop()
//       {
//    	   while(CheckRunFlag()) {
//    		   runServerTask();
//    	   }
//       }
//       public void run2() { 
//             try{
//            	 
//            	 String ConnectStatus = in.readUTF();
//            	 if(ConnectStatus.isOk()) {
//            		 ServerLoop();
//            	 }else
//            	 { 
//            		 LogFail();
//            	 }
//            	 
//            	 
//            	 str = in.readUTF();
//            	 if(str.equals("true"))
//            	 {
//            		 System.out.println("연결성공!");
//            		 gui_client.login_status = 2;
//                    while(in!=null){
//                           //소켓으로부터 문자열 읽어옴
//                    		str = in.readUTF();
//                          
//                           if(str==null){
//                                 System.out.println("접속이 끊겼음");
//                                 break;
//                           }
//
//                         //전송받은 문자열 화면에 출력
//                         //System.out.println("[server] " + str);
//                           cf.txtA.append(str+"\n");
//                           ClientFrame.scr.getVerticalScrollBar().setValue(  ClientFrame.scr.getVerticalScrollBar().
//                        		   															getMaximum());
//                    }
//            	 }
//            	 else if(str.equals("false"))
//            	 {
//            	//	 chat_app.gc = new gui_client();
//            		 System.out.println("연결실패!");
//            		 gui_client.login_status= 1;
//         //   		 JOptionPane.showMessageDialog(new JFrame(), "ID or password WRONG.");
//            	 }
//
//             }catch(IOException ie){
//
//                    System.out.println(ie.getMessage());
//
//             }finally{
//
//                    try{
//
//                           if(in!=null) in.close();
//
//                           if(socket!=null) socket.close();
//
//                    }catch(IOException ie){}
//
//             }
//
//       }

       public void run() {
             String str="";
             try{
            	 
            	 
            	 
            	 str = in.readUTF();
            	 if(str.equals("true"))
            	 {
            		 System.out.println("연결성공!");
            		 gui_client.login_status = 2;
                    while(in!=null){
                           //소켓으로부터 문자열 읽어옴
                    		str = in.readUTF();
                          
                           if(str==null){
                                 System.out.println("접속이 끊겼음");
                                 break;
                           }

                         //전송받은 문자열 화면에 출력
                         //System.out.println("[server] " + str);
                           cf.txtA.append(str+"\n");
                           ClientFrame.scr.getVerticalScrollBar().setValue(  ClientFrame.scr.getVerticalScrollBar().
                        		   															getMaximum());
                    }
            	 }
            	 else if(str.equals("false"))
            	 {
            	//	 chat_app.gc = new gui_client();
            		 System.out.println("연결실패!");
            		 gui_client.login_status= 1;
         //   		 JOptionPane.showMessageDialog(new JFrame(), "ID or password WRONG.");
            	 }

             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }finally{

                    try{

                           if(in!=null) in.close();

                           if(socket!=null) socket.close();

                    }catch(IOException ie){}

             }

       }

}

class gui_client extends Thread{
	
	
		//public gui_client gc;
		public static ClientFrame cf;
		public static volatile boolean is_logined = false;
		public static volatile int login_status = 0;
		
		public gui_client()
		{
			Socket socket = null;
			
			if(login_status == 0 )
			{
            try{
               //      cf.setVisible(true);
            	 cf = new ClientFrame();
            //	  System.out.println("E");
                  Id i=  new Id(); // 여기서 cf.socket이 null이므로  모순;... 다시 생각해보자.
            //      System.out.println("f");
                  i.set_clientFrame(cf);
             //     System.out.println("g");

                  cf.wt = new WriteThread(cf);
                  
              //    new ReadThread(socket, cf).start();
                  
                  while(is_logined == false)
                  {
                	//  System.out.println("is_logined : false");
                	
                  }
                    
       //           System.out.println("h");
              
                    	socket=new Socket("10.0.26.10",3000);
                    	cf.wt.set_socket(socket);
        //            	  System.out.println("E");
                   cf.connection_process(socket);
           //        System.out.println("연결성공!");
                   
                  new ReadThread(socket, cf).start();
                 cf.wt.sendMsg();
             }catch(IOException ie){

                    System.out.println(ie.getMessage());

             }
			}
		}
		public static void loginUI()
		{
			
		}
	
		

}
public class chat_app
{
	public static volatile boolean login_f=false;
    public static void main(String[] args) {
    	 gui_client gc ;
    	 
      //    Socket socket=null
    	 while(true)
    	 {
    		 if(gui_client.login_status==1)
    	          	gui_client.login_status = 0;
    	 gc = new gui_client();
    	 if(gui_client.login_status==2)
    		 break;
    	 
   // 	 if(gui_client.login_status==2)
   // 		 break;
    	 }
  /*  	while(gc.login_failed == true)
    	{
	    	gc = new gui_client();
	    	
	    	if(gc.login_failed == true)
	    	{
	    		gc.login_failed = false;
	    		continue;
	    	}
	    	while(gc.login_failed == false)
	    	{
	    	}
	    	
    	}*/
    	
    		
    }
}
class login_failed
{
	public volatile boolean login_failed = false;
}