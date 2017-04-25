package com.centaline.trans.cases.service;


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
	 * 查询外单详细
	 * @author hejf10
	 * @date 2017年4月24日17:26:51
	 * @param request
	 * @param caseCode
	 * @throws Exception
	 */
	//public void getCaseInfo(HttpServletRequest request,String caseCode)throws Exception;

}
