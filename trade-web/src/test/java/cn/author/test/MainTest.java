package cn.author.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainTest {

	public static void main(String[] args) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmssSSS");		
		System.out.println(simpleDateFormat.format(new Date()));

	}

}
