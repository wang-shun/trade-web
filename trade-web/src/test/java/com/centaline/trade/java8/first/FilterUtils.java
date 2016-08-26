package com.centaline.trade.java8.first;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class FilterUtils {
	
	static <T> List<T> filter(Collection<T> c, Predicate<T> p){
		List<T> result = new ArrayList<>();
		for (T t : c){
			if (p.test(t)) {
				result.add(t);
			}
		}
		return result;
	}

}
