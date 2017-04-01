package com.centaline.trans.signroom.vo;

import java.util.List;

public class SignroomInfo {

	private int numberOfPeople; // 房间容纳数

	private List<RoomProp> roomPropList; // 房间具体信息

	private String bespeakTime; // 预约时间段

	private int signroomNumber; // 房间数

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	public List<RoomProp> getRoomPropList() {
		return roomPropList;
	}

	public void setRoomPropList(List<RoomProp> roomPropList) {
		this.roomPropList = roomPropList;
	}

	public String getBespeakTime() {
		return bespeakTime;
	}

	public void setBespeakTime(String bespeakTime) {
		this.bespeakTime = bespeakTime;
	}

	public int getSignroomNumber() {
		return signroomNumber;
	}

	public void setSignroomNumber(int signroomNumber) {
		this.signroomNumber = signroomNumber;
	}

}
