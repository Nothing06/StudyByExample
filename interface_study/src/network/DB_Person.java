package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DB_Person extends JFrame implements ActionListener{

	Object ob[][]=new Object[0][5];
	public static DefaultTableModel model;
	public static JTable table;
	static int tuple_cnt=0;
	
	Connection con;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	String str[] = {"id","password","emp_no", "이름", "나이", "전화번호"};
	public DB_Person()
	{
		model = new DefaultTableModel(ob,str);
		table = new JTable(model);
		
		connect();
		select();
		this.addWindowListener(new WindowAdapter(){
		
				@Override
				public void windowClosing(WindowEvent e)
				{
					try
					{
						if(rs!=null)rs.close();
						if(pstmt!=null)pstmt.close();
						if(con!=null)con.close();
					}
					catch(Exception e2)
					{
						System.exit(0);
					}
				}
		
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/jdbctest";
			con = DriverManager.getConnection(url,"root","cs%s920026");
			System.out.println("접속: "+con);
			
		}
		catch(Exception e)
		{
			System.out.println("DB접속오류"+e);
		}
	}
	public void select()
	{
		try
		{
			String sql = "select id,password,emp_no,name,age,tel from person";
			pstmt = con.prepareStatement(sql);
			System.out.println("pstmt : " + pstmt);
			rs = pstmt.executeQuery();
			System.out.println("rs: "+ rs);
			while(rs.next())
			{
				String id = rs.getString("id");
				String password = rs.getString("password");
				int emp_no = rs.getInt("emp_no");
				String name=rs.getString("name");
				int age = rs.getInt("age");
				String tel = rs.getString("tel");
				
				Object data[] = {id,password,emp_no,name,age,tel};
				model.addRow(data);
				tuple_cnt+=1;
				System.out.println(id + ", " + password + ", "+ emp_no + ", " + name + ", " + age + ", " + tel);
			}
		}
		catch(Exception e)
		{
			System.out.println("select 실행오류: " + e);
		}
	}
}
