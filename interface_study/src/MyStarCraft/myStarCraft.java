package MyStarCraft;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
class getResource implements Runnable{
	volatile boolean processing = false;
	static int mineral = 0;
	static int gas=0;
	static int people_cnt=0; // 인구수
	volatile int worker_cnt=0;
	static void show_resource()
	{
		System.out.printf("mineral(scv:%d) : %d // gas(scv:%d) : %d \n",Scv.mineral_scv_cnt,
											mineral, Scv.gas_scv_cnt, gas);
	}
	getResource()
	{
		processing =true;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(processing)
		{
			mineral += (8 * CommandCenter.scv_cnt);
			gas += (8 * CommandCenter.scv_cnt);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("mineral : %d // gas : %d\n", mineral, gas);
		}
	}
	void getResourceInfo()
	{
		System.out.println("Mineral : " + mineral + " Gas: " + gas);
	}
	
}
public class myStarCraft {

	String tribe;
	String[] tribe_kind = {"terran", "zerg", "protoss"};
	terran_building my_terran;
	protoss my_protoss;
	zerg my_zerg;
	static HashMap<String,terran_building> my_buildings;
	String[] building_name = {"CommandCenter", "refinery", "barrack", "factory",
									"starport", "bunker", "academy", "engineeringBay", "turret",
											"supplyDepot", "scienceFacility", "armory"};
	
	int[] building_energy = {1500, 150, 1000, 750, 1000, 250, 300, 400, 150, 300, 300, 300 };
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		myStarCraft starc;
		int c=0;
		while(true)
		{
			main_menu();
			do {
				try {
					c = s.nextInt();
				}
				catch(Exception e)
				{
					
					System.out.print("잘못된입력입니다. ");
					System.out.println("다시 입력해주세요(1~3 중 선택):");
					s = new Scanner(System.in);
				}
				
			}while(c<1 || c>3);
			switch(c)
			{
			case 1:
				starc = new myStarCraft();
				starc.play();
				break;
			case 2:
				show_guide();
				break;
			case 3:
				System.out.println("게임을 종료합니다.");
				break;
				
			}
			if(c == 3)
				break;
		}
	}
	
	public myStarCraft()
	{
		choose_tribe();
		if(tribe.equals("terran"))
		{
			my_terran = new terran_building();
			
		}
		else if(tribe.equals("protoss"))
			my_protoss = new protoss();
		else if(tribe.equals("zerg"))
			my_zerg = new zerg();
	}
	void play()
	{
		Scanner s = new Scanner(System.in);
		Scanner s2 = new Scanner(System.in);
		getResource res = null;
		Thread res_th;
		int c2=0;
		int c=0;
		
		CommandCenter com = new CommandCenter();
		
		my_buildings = new HashMap();
		String num = Integer.toString(CommandCenter.cnt);
		String str = "commandcenter";
		str = str.concat(num);
	//	build(com);
	//	build(1);
		my_buildings.put(str,com);
		
		com.summon();
		
		while(true)
		{
			show_task();
			do {
			try
			{
			c = s.nextInt();
			}
			catch(Exception e)
			{
				
					System.out.print("잘못된입력입니다. ");
					System.out.println("다시 입력해주세요(1~6 중 선택):");
					s = new Scanner(System.in);
				//e.printStackTrace();
			}
			}while(c<1 || c>6);
			switch(c)
			{
			case 1: // 자원캐기/멈추기
				if(CommandCenter.scv_cnt <= 0)
				{
					System.out.println("No scv exists.");
					break;
				}
				int i=0;
				String st;
				String choice;
				Vector<String> list = new Vector();
				int list_cnt=0;
				Scanner sc = new Scanner(System.in);
				
				Iterator it = CommandCenter.scv_arr.keySet().iterator();
				System.out.println("==================================================\n");
				System.out.println(" * scv list *");
	//			System.out.println("terran_building.all_cnt : " + terran_building.all_cnt);
				for(i=0;i<CommandCenter.scv_cnt;i+=1)
				{
					if(it.hasNext())
					{
						st = (String)it.next();
						if(st.contains("scv") )
						{
							list.add(st);
							list_cnt+=1;
							System.out.print(st+ ", ");
							System.out.println();
						}
					}
				}
			//	Scanner scan = new Scanner(System.in);
				boolean ifexists=false;
				int x=0;
				do
				{
					System.out.print("input scv name(if want Quit, type x): ");
					choice = sc.nextLine();
					if(choice.equals("x"))
						break;
				//	System.out.println("list_cnt : " + list_cnt);
					for(x=0;x<list_cnt;x+=1)
					{
						if(choice.equals(list.get(x)))
						{
							ifexists = true;
							Scanner sr = new Scanner(System.in);
							int n=0;
							
							System.out.println("1. 미네랄 캐기.");
							System.out.println("2. 가스 캐기.");
							do {
									System.out.print("Choose one: ");
									n = sr.nextInt();
									if(n==1)
									{
										CommandCenter.scv_arr.get(choice).mineral_scv = true;
										CommandCenter.scv_arr.get(choice).mineral_scv_cnt+=1;
										Thread t = new Thread(CommandCenter.scv_arr.get(choice));
										t.start();
										break;
									}
									else if(n==2)
									{	
										CommandCenter.scv_arr.get(choice).gas_scv = true;
										CommandCenter.scv_arr.get(choice).gas_scv_cnt+=1;
										Thread t = new Thread(CommandCenter.scv_arr.get(choice));
										t.start();
										break;
									}
									System.out.println("press 1 or 2");
								}while(n<1 || n>2);
							break;
						}
						
					}
					if(ifexists == false)
					System.out.println("No scv exists which you wrote on console.");
				}while(ifexists==false);
			
					break;
			case 2: // 건물짓기
				building_list();
				c2 = s2.nextInt();
			//	terran_building.building_names[c2-1].
			//	if(getResource.mineral )
				if((getResource.mineral >= terran_building.building_mineral[c2-1])
							&& (getResource.gas >= terran_building.building_gas[c2-1])){
				build(c2);
				}
				else
				{
					System.out.println("Need more resources.");
				}
			//	my_buildings.add(e)
				break;
			case 3: // 유닛뽑기
				String f = summon_building_list_and_choose();  // show summonable building list
				if(f==null)
				{
					System.out.print("u canceled process.");
				}
				else
				{
					terran_building tb= my_buildings.get(f);
					if(tb instanceof barrack)
					{
						barrack b = (barrack)tb;
						b.summon();
					}
					else if(tb instanceof CommandCenter)
					{
						CommandCenter cc = (CommandCenter)tb;
						cc.summon();
						
					}
					else if(tb instanceof factory)
					{
						
					}
					else if(tb instanceof starport)
					{
						
					}
				}
			//	Object[] o = my_buildings.toArray();
		//		System.out.println(o.toString());
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				getResource.show_resource();
				break;
			}
		}
	}
	static String summon_building_list_and_choose()
	{
		int i=0;
		String s;
		String choice;
		Vector<String> list = new Vector();
		int list_cnt=0;
		Scanner sc = new Scanner(System.in);
		
		Iterator it = my_buildings.keySet().iterator();
		System.out.println("==================================================\n");
		System.out.println(" * Summonable Building list *");
		System.out.println("terran_building.all_cnt : " + terran_building.all_cnt);
		for(i=0;i<terran_building.all_cnt;i+=1)
		{
			if(it.hasNext())
			{
				s = (String)it.next();
				if(s.contains("commandcenter") || s.contains("barrack") || s.contains("factory") || s.contains("starport"))
				{
					list.add(s);
					list_cnt+=1;
					System.out.print(s+ ", ");
					System.out.println();
				}
			}
		}
	//	list = new String[list_cnt];
		boolean ifexists=false;
		int x=0;
		do
		{
			System.out.print("input building name(if want Quit, type x): ");
			choice = sc.nextLine();
			if(choice.equals("x"))
				break;
			System.out.println("list_cnt : " + list_cnt);
			for(x=0;x<list_cnt;x+=1)
			{
				if(choice.equals(list.get(x)))
				{
					ifexists = true;
					break;
				}
				//x+=1;
			}
			if(ifexists == false)
			System.out.println("No building exists which you wrote on console.");
		}while(ifexists==false);
		if(ifexists == true)
		{
			return list.get(x);
		}
		return null;
	}
	static void building_list()
	{
		System.out.println("==================================================\n");
		System.out.println("1. 커맨드센터");
		System.out.println("2. 가스");
		System.out.println("3. 배럭");
		System.out.println("4. 팩토리");
		System.out.println("5. 스타포트");
		System.out.println("6. 벙커");
		System.out.println("7. 아카데미");
		System.out.println("8. 엔지니어링베이");
		System.out.println("9. 터렛");
		System.out.println("10. 서플라이 디팟");
		System.out.println("11. 사이언스 패실리티");
		System.out.println("12. 아머리(머신 업그레이드용 건물)");
		
		
	}
	void build(int no)
	{
		int energy=0;
		int num=0;
		String s;
		String str;
		no-=1;
		while(energy < building_energy[no])
		{
			try
			{
				System.out.println(building_name[no] + " energy : " + energy + "/" + building_energy[no]);
				energy+=50;
				Thread.sleep(100);
			}catch(Exception e) {}
		}
		switch(no+1)
		{
		case 1:
			CommandCenter c= new CommandCenter();
			s = "commandcenter";
			 num = CommandCenter.cnt;
			str = s.concat(Integer.toString(num));
			
			getResource.mineral -= c.mineral;
			my_buildings.put(str,c);
			break;
		case 2:
			refinery r= new refinery();
			s = "refinery";
			num = refinery.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= r.mineral;
		
			my_buildings.put(str,r);
			break;
		case 3:
			barrack b= new barrack();
			s = "barrack";
			num = barrack.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= b.mineral;
		
			my_buildings.put(str,b);
			break;
		case 4:
			factory f= new factory();
			s = "factory";
			num = factory.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= f.mineral;
			getResource.gas -= f.gas;
			my_buildings.put(str, f);
		case 5:
			starport st= new starport();
			s = "starport";
			num = starport.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= st.mineral;
			getResource.gas -= st.gas;
			my_buildings.put(str,st);  
			break;
		case 6:
			bunker bk= new bunker();
			s = "bunker";
			num = bunker.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= bk.mineral;
			my_buildings.put(str,bk); 
			break;
		case 7:
			academy ac= new academy();
			s = "academy";
			num = academy.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= ac.mineral;
			getResource.gas -= ac.gas;
			my_buildings.put(str,ac);
			break;
		case 8:
			engineeringBay eb= new engineeringBay();
			s = "engineeringBay";
			num = eb.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= eb.mineral;
			my_buildings.put(str,eb);
			break;
		case 9:
			turret tr= new turret();
			s = "turret";
			num = tr.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= tr.mineral;
			my_buildings.put(str, tr);
			break;
			
		case 10:
			supplyDepot sd= new supplyDepot();
			s = "supplyDepot";
			num = supplyDepot.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= sd.mineral;
			my_buildings.put(str, sd);
			break;
		case 11:
			scienceFacility sf= new scienceFacility();
			s = "scienceFacility";
			num = scienceFacility.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= sf.mineral;
			getResource.gas -= sf.gas;
			my_buildings.put(str, sf);
			break;
		case 12:
			armory ar= new armory();
			s = "turret";
			num = ar.cnt;
			str = s.concat(Integer.toString(num));
			getResource.mineral -= ar.mineral;
			getResource.gas -= ar.gas;
			my_buildings.put(str, ar);
			break;
			
		}
		System.out.println("건축 완료.");
	}
	static void choose_task(int c2)
	{
		switch(c2)
		{
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		
		}
	}
	static void show_task()
	{
		System.out.println("==================================================\n");
		System.out.println("1. 자원 캐기/멈추기");
		System.out.println("2. 건물짓기");
		System.out.println("3. 유닛뽑기/기술연구하기/부속건물짓기");
		System.out.println("4. 유닛이동");
		System.out.println("5. 공격하기");
		System.out.println("6. 유닛 모드 변경");
		System.out.println("choose one : ");
	}
	static void main_menu()
	{
		System.out.println("==================================================\n");
		System.out.println("1. Play.\n");
		System.out.println("2. Guide.\n");
		System.out.println("3. Exit.\n");
	}
	static void show_guide()
	{
		
	}
	 void choose_tribe()
	 {
		 Scanner sc = new Scanner(System.in);
		 int c=0;
		System.out.println("==================================================\n");
		 System.out.println("Choosing tribe Mode");
		 System.out.println("1. Choice");
		 System.out.println("2. Random");
		 do {
		 try {
		 c = sc.nextInt();
		 }
		 catch(Exception e)
		 {
			 System.out.print("1이나 2를 선택하세요.");sc = new Scanner(System.in);
		 }}while(c<1 || c>2);
		 if(c == 1)
		 {
			 int tc=0;
			 System.out.println("1. Terran");
			 System.out.println("2. Protoss");
			 System.out.println("3. Zerg");
			 do {
				 try {
					 tc = sc.nextInt();
				 }catch(Exception e)
				 {
					 System.out.println("잘못입력했습니다. 다시 입력해주세요(1~3 중 한개 입력):");
					 sc = new Scanner(System.in);
				 }
			 }while(tc<1 ||  tc>3);
			 if(tc == 1)
				 tribe = "terran";
			 else if(tc == 2)
				 tribe = "protoss";
			 else if(tc == 3)
				 tribe = "zerg";
				 
			
			 System.out.println("You chose " + tribe);
		 }
		 else if(c==2)
		 {
			 int idx = (int) (3*Math.random());
			 tribe = tribe_kind[idx];
			 System.out.println("tribe is determined to " + tribe);
		 }
		 
	 }
	 public static String make_name(String name, int no)
		{
			String s= new String(name);
			s = s.concat(String.valueOf(no));
			return s;
		}
}
