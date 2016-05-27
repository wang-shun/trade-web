package com.centaline.trans.cases.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToLoanAgent;

public interface ToLoanAgentService {
	int deleteByPrimaryKey(Long pkid);

    int insert(ToLoanAgent record);

    int insertSelective(ToLoanAgent record);

    ToLoanAgent selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToLoanAgent record);

    int updateByPrimaryKey(ToLoanAgent record);
    List<ToLoanAgent> selectByCaseCode(String caseCode);
}
