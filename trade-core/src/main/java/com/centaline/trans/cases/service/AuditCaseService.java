package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.common.entity.ToCcaiAttachment;



/**
 * @author xiefei1
 * @since 2017年8月31日 上午9:18:25 
 * @description 接单审核详情查询付款方式和ccai附件；
 */

public interface AuditCaseService {

    public String getPayType(String caseCode);
    
    public List<ToCcaiAttachment> getCcaiAttachment(String caseCode);
    /**
     * 
     * @since:2017年10月28日 下午3:47:53
     * @description:把loanName作为贷款权证添加到当前案件
     * @author:xiefei1
     * @param loanName
     * @param caseCode
     * @return
     */
    public int addLoanProcessor(String loanName,String caseCode);
    
    public String getLeaderUserName(ToCaseParticipant toCaseParticipant);

	void updateAuditCaseSuccess(String caseCode);
//	驳回到CCAI,返回成功与否
	public int returnCaseToCCAI(String caseCode,String returnReason,String returnComment);
    
}
