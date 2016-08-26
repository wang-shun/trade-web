package com.centaline.trade.java8.second;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapTest {

	public static void main(String[] args) {
		
		String[] arrayOfWords = {"Goodbye", "World"};
		
		List<String> uniqueCharacters =
				Arrays.stream(arrayOfWords)
				.map(w -> w.split(""))
				.flatMap(Arrays::stream)
				.distinct()
				.collect(Collectors.toList());
		// [G, o, d, b, y, e, W, r, l]
		System.out.println(uniqueCharacters);
	}
}
