package com.centaline.trans.award.service;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.award.entity.TsAwardCaseCental;


public interface TsAwardCaseCentalService {
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-18
	 * 
	 * @desc:案件过户审批通过时保存信息进计件奖金池
	 */
	void  saveAwardCaseInfo(TsAwardCaseCental tsAwardCaseCental);

	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-22
	 * 
	 * @desc:页面跳转至计件奖金明细
	 */
	void jumpToNewBonus(HttpServletRequest request);
}
