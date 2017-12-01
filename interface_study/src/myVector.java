class point
{
	int x;
	int y;
	point(int x, int y)
	{
		this.x= x;
		this.y = y;
	}
}
public class myVector {
	static Object[] objArr;
	static int size;
	public myVector(int size)
	{
		if(size<0)
			throw new IllegalArgumentException("유효하지 않은값입니다.");
		objArr = new Object[size];
		
	}
	public myVector()
	{
		this(10);
	}
	public static Object remove(int idx)
	{
		Object obj = null;
		if(idx<0 || idx>=size)
			throw new IndexOutOfBoundsException("범위를 벗어났습니다.");
		obj = objArr[idx];
		
		if(idx != size-1)
		{
			System.arraycopy(objArr, idx+1, objArr, idx, size-idx-1);
		}
		objArr[size-1]=null;
		size-=1;
		return obj;
	}
	public static void add(Object o)
	{
		objArr[size] = o;
		size+=1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		objArr = new Object[10];
			point[] p = new point[10];
			for(int i=0;i<10;i+=1)
			{
				p[i] = new point(i, 10-i);
				objArr[i] = p[i];
				add(objArr[i]);
				System.out.println("p.x : " + p[i].x + "p.y: " + p[i].y);
			}
			System.out.println();
			System.out.println("p.x : " + p[0].x + "p.y: " + p[0].y);
			remove(0);
			System.out.println("p.x : " + p[0].x + "p.y: " + p[0].y);
	}
	
	int size() {return size;}
	int capacity() {return objArr.length;};
	boolean isEmpty() {return size==0;}
	void clear()
	{
		for(int i=0;i<size;i+=1)
		{
			objArr[i] = null;
		}
		size=0;
	}
	Object get(int idx)
	{
		if(idx < 0 || idx >= size)
			throw new IndexOutOfBoundsException("범위를 벗어났습니다.");
		return objArr[idx];
	}
	int indexOf(Object obj)
	{
		int i=0;
		for( i=0;i<size;i+=1)
		{
			if(objArr[i].equals(obj))
				return i;
		}
		return -1;
	}
	void ensureCapacity(int min)
	{
		if(min - size > 0)
			setCapacity(min);
	}
	void setCapacity(int capacity)
	{
		Object[] tmp = new Object[capacity];
		System.arraycopy(objArr, 0, tmp, 0, Math.min(size, capacity));
		objArr= tmp;
	}
}
