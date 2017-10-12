package com.centaline.trans.eval.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;
/**
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣对象与调佣金额
 */
@MyBatisRepository
public interface ToEvaCommPersonAmountMapper {
	int updateByCaseCodeSelective(ToEvaCommPersonAmount toEvaCommPersonAmount);
	
	List<ToEvaCommPersonAmount> selectByCasecode(String caseCode);
	
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommPersonAmount record);

    int insertSelective(ToEvaCommPersonAmount record);

    ToEvaCommPersonAmount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommPersonAmount record);

    int updateByPrimaryKey(ToEvaCommPersonAmount record);
}