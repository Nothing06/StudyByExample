import java.util.Arrays;
import java.util.Comparator;

public class ComparableTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Employee[] e = { new Employee(1,"100"), new Employee(3,"90"),
								new Employee(2, "50"), new Employee(4, "80"),
										new Employee(5, "20")};
			
			Arrays.sort(e);
			System.out.println(Arrays.toString(e));
			Arrays.sort(e, new descending());
			System.out.println(Arrays.toString(e));
			}
	}

class descending implements Comparator{
	public int compare(Object o1, Object o2)
	{
		Employee e1 =  (Employee)o1;
		Employee e2 = (Employee)o2;
		return e1.name.compareTo(e2.name);
	}
}

class Employee implements Comparable{
	int id;
	String name;
	
	Employee(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public String toString()
	{
		return id + "," + name;
	}
	public int compareTo(Object obj)
	{
		Employee e2 = (Employee)obj;
		return this.id - e2.id;
	}
}
