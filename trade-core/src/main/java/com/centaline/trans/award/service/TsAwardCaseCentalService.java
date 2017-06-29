package com.centaline.trans.award.service;

import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.award.entity.TsAwardKpiPay;
import com.centaline.trans.award.entity.TsKpiSrvCase;


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
	void jumpToNewBonusJsp(HttpServletRequest request);

	TsAwardKpiPay getInitPage(HttpServletRequest request, TsAwardKpiPay tsAwardKpiPay);
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-25
	 * 
	 * @desc:更新计件奖金的步骤
	 */
	int updateAwardStep(HttpServletRequest request, TsAwardKpiPay tsAwardKpiPay);
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-05-26
	 * 
	 * @desc:判断是否已经同步满意度
	 */
	int isSycnSatis(HttpServletRequest request, TsKpiSrvCase tsKpiSrvCase);
	
	
	/*
	 * @author:zhuody
	 * 
	 * @date:2017-06-29
	 * 
	 * @desc:案件重启审批通过的话、删除未计件的案件和环节占比等信息
	 */
	int deleteNobonusCase(String caseCode);
}
