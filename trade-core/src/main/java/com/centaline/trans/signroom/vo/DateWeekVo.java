package com.centaline.trans.signroom.vo;

import java.util.List;

import com.centaline.trans.signroom.entity.TradeCenterSchedule;

/**
 * 值班信息vo类
 * @author zhoujp7
 *
 */
public class DateWeekVo {

	private String date;//日期yyyy-MM-dd
	private int day;//几号20
	private int week;//星期几
	
	private boolean isLight;//是否高亮
	private boolean isEdit;//是否可编辑（上个月的不可编辑）
	
	private List<TradeCenterSchedule> tcs;//值班人员
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public List<TradeCenterSchedule> getTcs() {
		return tcs;
	}
	public void setTcs(List<TradeCenterSchedule> tcs) {
		this.tcs = tcs;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public boolean isLight() {
		return isLight;
	}
	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	public boolean isEdit() {
		return isEdit;
	}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}
	
	
}
