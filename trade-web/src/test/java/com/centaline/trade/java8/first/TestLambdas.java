package com.centaline.trade.java8.first;

import java.util.ArrayList;
import java.util.List;

public class TestLambdas {
	
	public static void main(String[] args) {
		List<Apple> appleList = new ArrayList<Apple>();
		
		Apple apple1 = new Apple();
		apple1.setColor("red");
		apple1.setWeight(120);
		
		
		Apple apple3 = new Apple();
		apple3.setColor("green");
		apple3.setWeight(185);
		
		Apple apple2 = new Apple();
		apple2.setColor("red");
		apple2.setWeight(125);

		appleList.add(apple1);
		appleList.add(apple2);
		appleList.add(apple3);
		
		List<Apple> filterAppleList = FilterUtils.filter(appleList, (Apple a) -> "red".equals(a.getColor()));
		
		List<Apple> filterAppleList2 = FilterUtils.filter(appleList, TestLambdas::isGreenApple);
		
		/*List<Apple> filterAppleList = appleList.parallelStream().filter(
				)).collect(Collectors.toList());;
		System.out.println(filterAppleList.size());*/
		
		//appleList.stream().filter(predicate)
	}
	
	public static boolean isGreenApple(Apple apple) {
		return "green".equals(apple.getColor());
	}

}
