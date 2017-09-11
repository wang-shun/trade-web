package com.centaline.trans.cases.service;

import java.util.List;

import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.vo.CaseRecvVO;





/**
 * @author xiefei1
 * @since 2017年9月4日 下午3:38:09 
 * @description 接单跟进
 */
public interface CaseRecvService {

	int deleteByPrimaryKey(String caseCode);

    void insertSelective(CaseRecvVO record);

    ToCaseRecv selectByPrimaryKey(String caseCode);
    
    CaseRecvVO selectFullCaseRecvVO(String caseCode);

    void updateByPrimaryKeySelective(CaseRecvVO record);
    
    int updateByPrimaryKeySelective(ToCaseRecv record);

    
}
