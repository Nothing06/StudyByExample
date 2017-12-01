package network;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.sql.*;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

 
class Id extends JFrame implements ActionListener{

       static JTextField tf=new JTextField(8);
       JButton btn = new JButton("Login");       
//      public static WriteThread wt;     
       ClientFrame cf;
       BufferedImage img;
       JPanel img_panel = new JPanel();
       ImageIcon img_icon;
       JLabel label;
       JPanel info_panel = new JPanel();
       JPanel login_panel = new JPanel();
       JPanel login_btn = new JPanel();
       static JPasswordField pf = new JPasswordField(10);
       public static boolean logined =false;
       Font f;
    //   public Id(){}
       private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c,  
               int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty) {
         gbc.gridx = gridx;
         gbc.gridy = gridy;
         gbc.gridwidth = gridwidth;
         gbc.gridheight = gridheight;
         gbc.weightx = weightx;
         gbc.weighty = weighty;
         gbl.setConstraints(c, gbc);
         add(c);
   }
       public void set_writeThread(WriteThread wr_t)
       {
    	//   this.wt = wr_t;
    	   ClientFrame.wt = wr_t;
       }
       public void set_clientFrame(ClientFrame cf)
       {
    	   this.cf = cf;
       }
       public Id() {

             super("WeChat in PC");            

   

       //      this.cf = cf;
//
            
             

           //  setLayout(new GridLayout(0,1));
            

             img_icon = new ImageIcon(new ImageIcon("C:\\Users\\user\\Pictures\\wechat.png").getImage().
            		 						getScaledInstance(400,450, Image.SCALE_AREA_AVERAGING));
             label= new JLabel(img_icon);
             Font f = new Font("Times",Font.PLAIN,25);
             
             setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
            
             
             img_panel.add(label);
             add(img_panel);
             
             info_panel.setLayout(new BorderLayout());
             
             login_panel.setLayout(new GridLayout(2,2));
             login_panel.add(new JLabel("아이디", SwingConstants.CENTER));
             tf.setFont(f);
             login_panel.add(tf);
             login_panel.add(new JLabel("비밀번호", SwingConstants.CENTER));
             tf.setFont(f);
             login_panel.add(pf);
             
             login_btn.setLayout(new BorderLayout());
             login_btn.add(btn,BorderLayout.CENTER);
             
             info_panel.add(login_panel, BorderLayout.CENTER);
             info_panel.add(login_btn, BorderLayout.EAST);
      //       info_panel.add(tf);
          //   info_panel.add(btn);
        
             add(info_panel);
             

             btn.addActionListener(this);

             
             this.setSize(400,600);
             this.setResizable(false);
          //   setBounds(300, 300, 250, 100);

             setVisible(true);

       }

       

       public void actionPerformed(ActionEvent e) {         

    	 //  if(getId().equals("yanghoon") && getPassword().equals("yanghoon"))
    	//   if(is_registered==true)
    	   {
           
     //        gui_client.cf.wt.sendMsg();
            gui_client.is_logined = true;
             
    /*        if(gui_client.login_status==1)
            	gui_client.login_status = 0;
            else if(gui_client.login_status == 2)
            	gui_client.login_status = 0;*/
            
             while(gui_client.login_status == 0);
             
             if(gui_client.login_status == 2)
             {
              cf.setVisible(true);
            	 this.dispose();
             }
             else if(gui_client.login_status==1)
             {
        //    	 this.dispose();
            	 gui_client.is_logined = false;
            //	 gui_client.login_status = 0;//gui_client.login_status=0;
            	 JOptionPane.showMessageDialog(this, "Wrong ID/Password");
             }
             	
    	   }
       }

       static public String getId(){

             return tf.getText();

       }
       static public String getPassword()
       {
    	   return String.valueOf(pf.getPassword());
       }

}

 

 

 

 

public class ClientFrame extends JFrame implements ActionListener{

      public static JTextArea txtA = new JTextArea();
       public static JScrollPane scr = new JScrollPane(txtA);
       JTextField txtF = new JTextField(15);
     
       JButton btnTransfer = new JButton("전송");
       BufferedImage img;
       JButton btnExit = new JButton("닫기");

       boolean isFirst=true;
       JPanel p1 = new JPanel();
       Socket socket;
      public static  WriteThread wt;
      Font f;
             
      void connection_process(Socket socket)
       {
    	  this.socket = socket;
    	
            
       }
       public ClientFrame() {

             super("WeChat");
             	txtA.setEditable(false);
          
       //      this.socket = socket;
     	    f = new Font("Times", Font.PLAIN, 25);
     	    txtA.setFont(f);
       
             add("Center", scr);

             p1.add(txtF);

             p1.add(btnTransfer);

             p1.add(btnExit);

             add("South", p1);

             

             //메세지를 전송하는 클래스 생성.

             

             btnTransfer.addActionListener(this);

             btnExit.addActionListener(this);

             setDefaultCloseOperation(EXIT_ON_CLOSE);

          //   setBounds(300, 300, 350, 300);
             setSize(400, 400);
             setVisible(false);  

       }

      
       public void actionPerformed(ActionEvent e){

             String id = Id.getId();

             if(e.getSource()==btnTransfer){//전송버튼 눌렀을 경우

                    //메세지 입력없이 전송버튼만 눌렀을 경우

                    if(txtF.getText().equals("")){

                           return;

                    }                   

                    txtA.append("["+id+"] "+ txtF.getText()+"\n");

                    if(gui_client.is_logined == true)
                    {
                    ClientFrame c = gui_client.cf;
                    c.wt.sendMsg();
                   scr.getVerticalScrollBar().setValue(scr.getVerticalScrollBar().getMaximum());


                //    System.out.println("DD");
                    }
                    txtF.setText("");

             }else{

                    this.dispose();

             }

       }

}
