package com.centaline.trade.java8.third;

import java.util.Optional;

public class OptionalTest {

	public static void main(String[] args) {
		Person person = new Person();
		Optional<Person> optPerson = Optional.of(person);
		
		/*System.out.println(optPerson.flatMap(Person::getCar)
				.flatMap(Car::getInsurance)
				.map(Insurance::getName)
				.orElse("Unknown"));*/

	}

}
