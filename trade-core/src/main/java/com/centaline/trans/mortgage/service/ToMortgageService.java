package com.centaline.trans.mortgage.service;

import java.util.List;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.entity.ToMortgage;

public interface ToMortgageService {
	
	/**
	 * 更新临时案件状态
	 * @param caseCode 案件编号
	 * @return 如果返回1,说明更新成功;如果返回0,说明更新失败。
	 */
	public int updateTmpBankStatus(String caseCode);
	
	void updateToMortgageBySign(ToMortgage toMortgage);
	
	/**
	 * 根据案件编号查询贷款信息
	 * @param caseCode 案件编号
	 * @return 返回贷款信息
	 */
	ToMortgage getMortgageByCaseCode(String caseCode);
	
	/**
	 * 保存按揭贷款信息
	 * @param toMortgage
	 */
	ToMortgage saveToMortgage(ToMortgage toMortgage);
	
	/**
	 * 修改按揭贷款信息
	 * @param toMortgage
	 */
	void updateToMortgage(ToMortgage toMortgage);
	
	
	void updateToMortgageByCode(String caseCode);
	
	/**
	 * 根据案件编号查询最终贷款信息
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(String caseCode);
	
	/**
	 * 根据案件编号查询最终贷款信息
	 * @param caseCode
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode2(String caseCode);
	
	/**
	 * 根据主键查询按揭贷款信息
	 * @param id
	 * @return
	 */
	ToMortgage findToMortgageById(Long id);

	/**
	 * 保存按揭贷款及补件信息
	 * @param toMortgage
	 */
	void saveToMortgageAndSupDocu(ToMortgage toMortgage);
	
	/**
	 * 根据案件编号查询按揭贷款信息
	 * @param toMortgage
	 * @return
	 */
	ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage);
	
	
	/*签约时同步更新贷款主贷人信息
	 * 获取casecode和custcode获取贷款表信息
	 */
	ToMortgage  findToMortgageByCaseCodeAndCustcode(ToMortgage toMortgage);
	
	ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode,String mortType);
	
	ToMortgage findToSelfLoanMortgage(String caseCode);

	
	void inActiveMortageByCaseCode(String caseCode);

	ToMortgage findToMortgageByCaseCodeWithCommLoan(ToMortgage toMortgage);
	
	/***
	 *  公积金审批提交流程
	 * 
	 *  @param toMortgage
	 */
	void submitMortgage(ToMortgage toMortgage,List<RestVariable> variables,String taskId,String processInstanceId);

	void deleteTmpBankProcess(ToWorkFlow twf);

	/**
	 * 启动临时银行流程
	 * @param caseCode
	 * @return
	 */
	AjaxResponse<String> startTmpBankWorkFlow(String caseCode);
	
    /**
     * 临时银行三级审批
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
	AjaxResponse<?> tmpBankThriceAduit(ToMortgage mortage,String prAddress,
			String tmpBankName,String tmpBankCheck,String taskId,
			String bankCode,String temBankRejectReason,
			String processInstanceId,String caseCode,String post);

}
