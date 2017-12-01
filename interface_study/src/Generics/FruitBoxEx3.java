package Generics;

import java.util.ArrayList;


class Juice{
	String name;
	Juice(String name){this.name = name + "Juice";}
	public String toString() {return name;}
}
class Juicer{
	static Juice makeJuice(FruitBox<? extends Fruit>box)
	{
		String tmp="";
		for(Fruit f : box.getList())
			tmp += f + " ";
		return new Juice(tmp);
	}
}
public class FruitBoxEx3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FruitBox<Fruit> fruitBox = new FruitBox<Fruit>();
		FruitBox<Apple> appleBox = new FruitBox<Apple>();
		FruitBox<Grape> grapeBox = new FruitBox<Grape>();
//		FruitBox<Toy> toyBox = new FruitBox<Toy>();
		
		fruitBox.add(new Fruit());
		fruitBox.add(new Apple());
		fruitBox.add(new Apple());
		appleBox.add(new Apple());
		grapeBox.add(new Grape());
		System.out.println(Juicer.makeJuice(fruitBox));
		System.out.println(Juicer.makeJuice(appleBox));
	}

}

