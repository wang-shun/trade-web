package com.centaline.trans.eloan.service;

import java.util.List;
import java.util.Map;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.vo.ELoanVo;

public interface ToEloanCaseService {

	ToEloanCase getToEloanCaseByPkId(Long pkid);

	void saveEloanApply(SessionUser user, ToEloanCase tEloanCase);

	int updateEloanApply(SessionUser user, ToEloanCase tEloanCase);

	void deleteById(Long pkid);

	void saveEloanSign(String taskId, ToEloanCase tEloanCase);

	List<ToEloanCase> getToEloanCaseListByProperty(ToEloanCase tEloanCase);

	// void eloanProcessConfirm(String taskId, String approved,ToEloanCase
	// toEloanCase);

	void eloanProcessConfirm(String taskId, Map<String, Object> map,
			ToEloanCase toEloanCase, boolean isUpdate);

	List<String> validateEloanApply(ToEloanCase tEloanCase);

	AjaxResponse<Boolean> validateIsFinishRelease(String eloanCode,
			Double sumAmount);

	void eloanInfoForUpdate(ToEloanCase toEloanCase);

	void abanById(ToEloanCase eloanCase);

	ToEloanCase selectByEloanCode(String eloanCode);

	/**
	 * E+贷款信贷员接单和打回
	 * 
	 * @param eLoanVo
	 *            E+贷款案件前台传值对象
	 * @param map
	 *            流程参数
	 * @param taskId
	 *            任务id
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	public boolean accept(ELoanVo eLoanVo, Map<String, Object> map,
			String taskId);

	/**
	 * E+案件跟进
	 * 
	 * @param eLoanVo
	 *            E+贷款案件前台传值对象
	 * @return 返回true,操作成功;返回false,操作失败。
	 */
	public boolean followUp(ELoanVo eLoanVo);
}
