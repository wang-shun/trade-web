package com.centaline.trans.signroom.vo;

/**
 * 签约室预约信息
 * 
 * @author yinjf2
 *
 */
public class ReservationInfo {

	private int numberOfPeople; // 可容纳人数

	private int residualNumber; // 剩余间数

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public int getResidualNumber() {
		return residualNumber;
	}

	public void setResidualNumber(int residualNumber) {
		this.residualNumber = residualNumber;
	}

}
