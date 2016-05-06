package com.centaline.trans.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
	
	/**
	 *  除以
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static BigDecimal divideNumber(BigDecimal decimal,int divideNumber) {
		if(decimal == null) {
			return new BigDecimal(0);
		} else {
			return decimal.divide(new BigDecimal(divideNumber));
		}
	}
	/**
	 *  除以
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static BigDecimal divideNumber(Double d1,Double d2) {
		if(d2 == null||d2==null) {
			return null;
		} else {
			return BigDecimal.valueOf(d1).divide(BigDecimal.valueOf(d2),2,RoundingMode.HALF_UP);
		}
	}
	
	/**
	 *  乘法运算
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static Double multiply(Double d1,Double d2) {
		if(d1 == null||d2==null) {
			return null;
		} else {
			return  BigDecimal.valueOf(d1).multiply(BigDecimal.valueOf(d2)).doubleValue();
		}
	}
	/**
	 *  加法运算
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static BigDecimal add(Double d1,Double d2) {
		if(d1 == null||d2==null) {
			return null;
		} else {
			return  BigDecimal.valueOf(d1).add(BigDecimal.valueOf(d2));
		}
	}
	/**
	 *  加法运算
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static BigDecimal add(BigDecimal b1,BigDecimal b2) {
		if(b1 == null||b2==null) {
			return null;
		} else {
			return  b1.add(b2);
		}
	}
	
	/**
	 *  乘法运算
	 * 
	 * @param decimal,divideNumber
	 * @return BigDecimal
	 * 
	 * @author 
	 */
	public static BigDecimal multiply(BigDecimal d1,BigDecimal d2) {
		if(d1 == null||d2==null) {
			return null;
		} else {
			return d1.multiply(d2);
		}
	}

}
