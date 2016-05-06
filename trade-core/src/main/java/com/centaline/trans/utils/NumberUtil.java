package com.centaline.trans.utils;

import java.math.BigDecimal;

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
