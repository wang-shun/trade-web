package com.centaline.trans.cases.service;


import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.wdcase.vo.TpdPaymentVO;


public interface CaseMergeService {	
	
	void saveCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo,String caseCode);
    /**
     * 保存新建外单案件信息
     * @author hejf10
     * @date 2017年4月21日14:09:17
     * @param request
     * @param caseMergeVo
     * @param caseCode
     * @throws Exception
     */
	String saveWdCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception;
	/**
	 * 查询外单详细
	 * @author hejf10
	 * @date 2017年4月24日17:26:51
	 * @param request
	 * @param caseCode
	 * @throws Exception
	 */
	//public void getCaseInfo(HttpServletRequest request,String caseCode)throws Exception;
	/**
	 * 保存外单支付流水
	 * @author hejf10
	 * @date 2017年4月26日15:11:35
	 * @param request
	 * @param caseMergeVo
	 * @return
	 * @throws Exception
	 */
    String saveLiushui(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception;
    /**
     * 查询应收款额流水
     * @author hejf10
     * @date 2017年4月26日18:17:35
     * @param caseCode
     * @return
     */
    void getTpdPaymentVO(String caseCode,ServletRequest request);
    /**
     * 外单应收金额
     * @author hejf10
     * @date 2017年4月26日18:46:00
     * @param caseCode
     * @return
     */
    BigDecimal getCommCostAmount(String caseCode);

}
