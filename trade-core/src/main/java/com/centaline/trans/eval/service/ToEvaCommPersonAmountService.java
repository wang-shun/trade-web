package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
/**
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣对象与调佣金额
 */
public interface ToEvaCommPersonAmountService {
//	获取完整的调佣对象与调佣金额VO
	EvalChangeCommVO getFullEvalChangeCommVO(String caseCode);
//	保存调佣对象与调佣金额VO
	void saveEvalChangeCommVO(EvalChangeCommVO EvalChangeCommVO);
	
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommPersonAmount record);

    int insertSelective(ToEvaCommPersonAmount record);

    ToEvaCommPersonAmount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommPersonAmount record);

    int updateByPrimaryKey(ToEvaCommPersonAmount record);
}
