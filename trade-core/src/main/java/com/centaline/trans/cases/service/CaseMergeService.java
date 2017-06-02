package com.centaline.trans.cases.service;


import java.math.BigDecimal;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.cases.vo.CaseMergeVo;


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
    /**
     * 查询外单详细信息外单编辑页面显示
     * @author hejf10
     * @date 2017年4月27日10:14:29
     * @param request
     * @param caseCode
     */
    void setCaseMergeVo(HttpServletRequest request,String caseCode);
    /**
     * 更新外单
     * @author hejf10
     * @date 2017年4月27日15:52:11
     * @param request
     * @param caseMergeVo
     * @return
     * @throws Exception
     */
    String editWdCaseInfo(HttpServletRequest request,CaseMergeVo caseMergeVo)throws Exception;
    /**
     * 查询案件基本信息
     * @author hejf10
     * @date 2017年5月15日17:07:14
     * @param caseCode
     * @param request
     */
    void setCaseAttribute(String caseCode,HttpServletRequest request);
}
