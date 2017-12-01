package Generics;

import java.util.ArrayList;

interface Eatable{
	
}

public class FruitBoxEx2 {

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
		
		System.out.println("fruitBox - " + fruitBox);
		System.out.println("appleBox - " + appleBox);
		System.out.println("grapeBox - " + grapeBox);
	}

}
class FruitBox<T extends Fruit & Eatable> extends Box<T>{}

