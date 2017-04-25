package com.centaline.trans.mortgage.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.vo.MortgageVo;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface ToMortgageService {

	/**
	 * 更新临时案件状态
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 如果返回1,说明更新成功;如果返回0,说明更新失败。
	 */
	public int updateTmpBankStatus(String caseCode);

	void updateToMortgageBySign(ToMortgage toMortgage);

	/**
	 * 根据案件编号查询贷款信息
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return 返回贷款信息
	 */
	ToMortgage getMortgageByCaseCode(String caseCode);

	/**
	 * 保存按揭贷款信息
	 * 
	 * @param toMortgage
	 */
	ToMortgage saveToMortgage(ToMortgage toMortgage);

	/**
	 * 修改按揭贷款信息
	 * 
	 * @param toMortgage
	 */
	void updateToMortgage(ToMortgage toMortgage);

	void updateToMortgageByCode(String caseCode);

	/**
	 * 根据案件编号查询最终贷款信息
	 * 
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(String caseCode);

	/**
	 * 根据案件编号查询最终贷款信息
	 * 
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode2(String caseCode);

	/**
	 * 根据主键查询按揭贷款信息
	 * 
	 * @param id
	 * @return
	 */
	ToMortgage findToMortgageById(Long id);

	/**
	 * 保存按揭贷款及补件信息
	 * 
	 * @param toMortgage
	 */
	void saveToMortgageAndSupDocu(ToMortgage toMortgage);

	/**
	 * 根据案件编号查询按揭贷款信息
	 * 
	 * @param toMortgage
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage);

	/*
	 * 签约时同步更新贷款主贷人信息 获取casecode和custcode获取贷款表信息
	 */
	List<ToMortgage> findToMortgageByCaseCodeAndCustcode(ToMortgage toMortgage);

	ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode,
			String mortType);

	ToMortgage findToSelfLoanMortgage(String caseCode);

	void inActiveMortageByCaseCode(String caseCode);

	ToMortgage findToMortgageByCaseCodeWithCommLoan(ToMortgage toMortgage);

	/***
	 * 公积金审批提交流程
	 * 
	 * @param toMortgage
	 */
	void submitMortgage(ToMortgage toMortgage, List<RestVariable> variables,
			String taskId, String processInstanceId);

	void deleteTmpBankProcess(ToWorkFlow twf);

	/**
	 * 启动临时银行流程
	 * 
	 * @param caseCode
	 * @return
	 */
	AjaxResponse<String> startTmpBankWorkFlow(String caseCode,
			String loanerInstCode);

	/**
	 * 临时银行三级审批
	 * 
	 * @param mortage
	 * @param prAddress
	 * @param tmpBankName
	 * @param tmpBankCheck
	 * @param taskId
	 * @param bankCode
	 * @param temBankRejectReason
	 * @param processInstanceId
	 * @param caseCode
	 * @param post
	 * @return
	 */
	AjaxResponse<?> tmpBankThriceAduit(ToMortgage mortage, String prAddress,
			String tmpBankName, String tmpBankCheck, String taskId,
			String bankCode, String temBankRejectReason,
			String processInstanceId, String caseCode, String post);

	/**
	 * test
	 * 
	 * @param record
	 * @return
	 */
	int updateByTest(ToMortgage record);

	/**
	 * 合作机构人员增加按揭贷款环节跟进 插入comment， 更新mortgage的stateInBank
	 * 
	 * @param record
	 * @return
	 */
	public int addMortgageTrack4Par(ToCaseComment track);

	/**
	 * 按揭贷款信贷员接单和打回
	 * 
	 * @param mortgageVo
	 *            按揭贷款案件前台传值对象
	 * @return 返回true,接单或打回成功;返回false,接单或打回失败
	 */
	public boolean accept(MortgageVo mortgageVo);

	/**
	 * 银行审核
	 * 
	 * @param mortgageVo
	 *            按揭贷款案件前台传值对象
	 * 
	 * @return 返回true,操作成功;返回false,操作失败;
	 */
	public boolean followUp(MortgageVo mortgageVo);

	/**
	 * 信息补充
	 * 
	 * @param mortgageVo
	 *            按揭贷款案件前台传值对象
	 */
	public void suppleInfo(MortgageVo mortgageVo);

	List<ToMortgage> findToMortgageByCaseCodeNoBlank(String caseCode);
	
	/**
	 * 
	 * submitLoanlostApply:(提交贷款流失申请). <br/> 
	 * @author gongjd 
	 * @param request
	 * @param toMortgage
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param partCode
	 * @param lapPkid
	 * @return 
	 * @since JDK 1.8
	 */
	Result2 submitLoanlostApply(HttpServletRequest request, ToMortgage toMortgage, 
			ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO, String partCode,Long lapPkid);

	Boolean submitSelfLoanApprove(HttpServletRequest request, ToMortgage toMortgage, String taskId,
			String processInstanceId);

	Result2 submitLoanRelease(HttpServletRequest request, ToMortgage toMortgage, String taskitem,
			Date estPartTime, String taskId, String processInstanceId, String partCode);

	Boolean submitPsfApply(HttpServletRequest request, ToMortgage toMortgage, String taskitem, Date estPartTime,
			String taskId, String processInstanceId);
}
