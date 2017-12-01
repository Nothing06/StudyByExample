package MyStarCraft;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

interface summonable
{
	public abstract void summon();
}
interface bunkerable
{
	
}
public class terran_building {
	static int all_cnt=0;
	static String[] building_names = {"CommandCenter", "refinery", "barrack", "factory",
	"starport", "bunker", "academy", "engineeringBay", "turret",
			"supplyDepot", "scienceFacility", "armory"};
	static int[] building_mineral = { 400, 150, 150, 200, 150, 100, 150, 150, 100, 100, 200, 200};
	static int[] building_gas =      {  0,   0,   0, 100, 100,   0, 100,   0,   0,   0, 200, 100 };
	public terran_building()
	{
	//	all_cnt+=1;
	}
	void play()
	{
		
	}
}
abstract class research extends terran_building
{
	abstract void upgrade_atk();
	abstract void upgrade_def();
}

class CommandCenter extends terran_building implements summonable
{
	static int scv_cnt=0;
	static int cnt=0;
	static HashMap<String,Scv> scv_arr = new HashMap();
	static int mineral = 400;
	CommandCenter()
	{
		super();
		cnt+=1;
		all_cnt+=1;
	}
	public void summon() {
		Scv s = new Scv();
		scv_arr.put(myStarCraft.make_name("scv",scv_cnt+1),s);
		scv_cnt+=1;
		System.out.println("scv가 소환되었습니다.(" +scv_cnt+"마리 보유중.)");
	}
	void make_scanner()
	{
		
	}
	void make_nuclearStation()
	{
		
	}
	static void list()
	{
		System.out.println("==================================================\n");
		System.out.println("1. Scv생산\n");
		System.out.println("2. 집결지점설정\n");
		System.out.println("3. 이륙\n");
		System.out.println("4. 착륙\n");
		System.out.println("5. 작업 취소\n");
		System.out.println("6. Combat Station 건설\n");
		System.out.println("7. Nuclear 사일로 건설\n");
	}
}
class barrack extends terran_building implements summonable{
	static int cnt =0;
	static Vector<marine> marine_arr = new Vector();
	static int marine_cnt=0;
	static Vector<firebat> firebat_arr = new Vector();
	static int firebat_cnt=0;
	static Vector<medic> medic_arr = new Vector();
	static int medic_cnt=0;
	static Vector<ghost> ghost_arr = new Vector();
	static int ghost_cnt=0;
	static String[] unit_names = {"marine", "firebat", "medic", "ghost"};
	static int mineral = 150;
	barrack()
	{
		super();
		cnt+=1;
		all_cnt+=1;
	}
	static void show_units()
	{
		System.out.println("==================================================\n");
		System.out.println("1. marine");
		System.out.println("2. firebat");
		System.out.println("3. medic");
		System.out.println("4. ghost");
	}
	
	@Override
	public void summon() {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		int num=0;
		String str=null;
		int no;
		show_units();
		System.out.print("choose number: ");
		num = s.nextInt();
		switch(num)
		{
		case 1:
			marine m = new marine();
			marine_arr.add(m);
			no = marine_cnt+1;
			terran_unit.units.put(str=myStarCraft.make_name(unit_names[num-1], no), m);
			marine_cnt+=1;
			break;
		case 2: // firebat.  and  인구수 문제, 자원계산 적용
			firebat f = new firebat();
			firebat_arr.add(f);
			no = firebat_cnt+1;
			terran_unit.units.put(str = myStarCraft.make_name(unit_names[num-1], no), f);
			firebat_cnt+=1;
			break;
		case 3:
			medic mc = new medic();
			medic_arr.add(mc);
			no = medic_cnt+1;
			terran_unit.units.put(str = myStarCraft.make_name(unit_names[num-1], no), mc);
			medic_cnt+=1;
			break;
		case 4:
			ghost g = new ghost();
			ghost_arr.add(g);
			no = ghost_cnt+1;
			terran_unit.units.put(str = myStarCraft.make_name(unit_names[num-1], no), g);
			ghost_cnt+=1;
			break;
		}
		System.out.println(str + " is summoned.");
	}
}
class factory extends terran_building {
	static int cnt =0;
	static int mineral = 200;
	static int gas = 100;
	factory()
	{
		super();
		cnt+=1;
		all_cnt+=1;
	}
}
class starport extends terran_building {
	static int cnt =0;
	static int mineral = 150;
	static int gas = 100;
	starport()
	{
		super();
		cnt+=1;
		all_cnt+=1;
	}
}
class academy extends research{
	static int cnt =0;
	static int mineral = 150;
	static int gas = 100;
	academy()
	{
		super();
		cnt+=1;
		all_cnt+=1;
	}
	
	@Override
	void upgrade_atk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void upgrade_def() {
		// TODO Auto-generated method stub
		
	}
	
}
class bunker extends terran_building implements bunkerable{
	static int cnt=0;
	static int mineral = 100;
	bunker()
	{
		super();
		cnt+=1;
		all_cnt++;
	}
	
}
class armory extends research{
	static int cnt=0;
	static int mineral = 200;
	static int gas = 100;
	armory()
	{
		super();
		cnt+=1;
		all_cnt++;
	}
	
	@Override
	void upgrade_atk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void upgrade_def() {
		// TODO Auto-generated method stub
		
	}
	
}
class refinery extends terran_building
{
	static int mineral = 150;
	static int cnt=0;
	refinery()
	{
	//	super();
		cnt+=1;
		all_cnt++;
	}
}
class supplyDepot extends terran_building
{
	static int cnt=0;
	static int mineral = 100;
	supplyDepot()
	{
		super();
		cnt+=1;
		all_cnt++;
	}
}
class turret extends terran_building
{
	static int cnt=0;
	static int mineral = 100;
	turret()
	{
		super();
		cnt+=1;
		all_cnt++;
	}
}
class engineeringBay extends research
{
	static int cnt=0;
	static int mineral = 150;
	engineeringBay()
	{
		super();
		cnt+=1;
		all_cnt++;
	}
	@Override
	void upgrade_atk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void upgrade_def() {
		// TODO Auto-generated method stub
		
	}
	
}
class scienceFacility extends research
{
	static int cnt=0;
	static int mineral = 200;
	static int gas = 200;
	scienceFacility()
	{
		super();
		cnt+=1;
		all_cnt++;
	}

	@Override
	void upgrade_atk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void upgrade_def() {
		// TODO Auto-generated method stub
		
	}
	
}