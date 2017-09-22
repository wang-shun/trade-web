package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvalRebate;
/***
 * 
 * @author wbwangxj
 *
 */
@MyBatisRepository
public interface ToEvalRebateMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvalRebate record);

    int insertSelective(ToEvalRebate record);

    ToEvalRebate selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvalRebate record);

    int updateByPrimaryKey(ToEvalRebate record);

	ToEvalRebate findToEvalRebateByCaseCode(String caseCode);

}