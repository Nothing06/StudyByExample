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

// Ű����� ���۹��ڿ� �Է¹޾� ������ �����ϴ� ������

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

             //Ű����κ��� �о���� ���� ��Ʈ����ü ����

            // BufferedReader br=

           //  new BufferedReader(new InputStreamReader(System.in));

           //  PrintWriter pw=null;

             try{

                    //������ ���ڿ� �����ϱ� ���� ��Ʈ����ü ����

               //     pw=new PrintWriter(socket.getOutputStream(),true);

                    //ù��° �����ʹ� id �̴�. ���濡�� id�� �Բ� �� IP�� �����Ѵ�.

                    if(cf.isFirst==true){

                           InetAddress iaddr=socket.getLocalAddress();                       

                           String ip = iaddr.getHostAddress();                         

                           getId_from_ClientFrame(); getPassword_from_ClientFrame();

                           System.out.println("ip:"+ip+"id:"+id);

                           str = "["+id+"]"+ "[" + password + "]" + " �� �α��� (" + ip +")"; 
                       	cf.isFirst = false;

                    }else{

                           str= "[" + id + "] " + cf.txtF.getText();
                         //  	out.writeUTF(); 
                           
                    }
                    
                    //�Է¹��� ���ڿ� ������ ������
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

//������ ������ ���ڿ��� ���۹޴� ������

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
//            		 System.out.println("���Ἲ��!");
//            		 gui_client.login_status = 2;
//                    while(in!=null){
//                           //�������κ��� ���ڿ� �о��
//                    		str = in.readUTF();
//                          
//                           if(str==null){
//                                 System.out.println("������ ������");
//                                 break;
//                           }
//
//                         //���۹��� ���ڿ� ȭ�鿡 ���
//                         //System.out.println("[server] " + str);
//                           cf.txtA.append(str+"\n");
//                           ClientFrame.scr.getVerticalScrollBar().setValue(  ClientFrame.scr.getVerticalScrollBar().
//                        		   															getMaximum());
//                    }
//            	 }
//            	 else if(str.equals("false"))
//            	 {
//            	//	 chat_app.gc = new gui_client();
//            		 System.out.println("�������!");
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
            		 System.out.println("���Ἲ��!");
            		 gui_client.login_status = 2;
                    while(in!=null){
                           //�������κ��� ���ڿ� �о��
                    		str = in.readUTF();
                          
                           if(str==null){
                                 System.out.println("������ ������");
                                 break;
                           }

                         //���۹��� ���ڿ� ȭ�鿡 ���
                         //System.out.println("[server] " + str);
                           cf.txtA.append(str+"\n");
                           ClientFrame.scr.getVerticalScrollBar().setValue(  ClientFrame.scr.getVerticalScrollBar().
                        		   															getMaximum());
                    }
            	 }
            	 else if(str.equals("false"))
            	 {
            	//	 chat_app.gc = new gui_client();
            		 System.out.println("�������!");
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
                  Id i=  new Id(); // ���⼭ cf.socket�� null�̹Ƿ�  ���;... �ٽ� �����غ���.
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
           //        System.out.println("���Ἲ��!");
                   
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