package com.centaline.trans.cases.repository;

import java.util.List;

import com.centaline.trans.cases.entity.ToLoanAgent;
import com.centaline.trans.common.MyBatisRepository;
@MyBatisRepository
public interface ToLoanAgentMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToLoanAgent record);

    int insertSelective(ToLoanAgent record);

    ToLoanAgent selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToLoanAgent record);

    int updateByPrimaryKey(ToLoanAgent record);
    List<ToLoanAgent> selectByCaseCode(String caseCode);
}