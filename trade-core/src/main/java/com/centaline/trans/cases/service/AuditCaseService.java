package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.common.entity.ToCcaiAttachment;



/**
 * @author xiefei1
 * @since 2017年8月31日 上午9:18:25 
 * @description 接单审核详情查询付款方式和ccai附件；
 */

public interface AuditCaseService {

    public String getPayType(String caseCode);
    
    public List<ToCcaiAttachment> getCcaiAttachment(String caseCode);
    
}
