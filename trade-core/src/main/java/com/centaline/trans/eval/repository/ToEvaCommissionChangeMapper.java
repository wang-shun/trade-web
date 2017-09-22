package com.centaline.trans.eval.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;

/**
 * @author xiefei1
 * @since 2017年9月22日 下午2:11:07 
 * @description 评估公司变更模块
 */
@MyBatisRepository
public interface ToEvaCommissionChangeMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommissionChange record);

    int insertSelective(ToEvaCommissionChange record);

    ToEvaCommissionChange selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommissionChange record);

    int updateByPrimaryKey(ToEvaCommissionChange record);
    
    int deleteByCaseCode(String caseCode);
    
    int updateByCaseCodeSelective(ToEvaCommissionChange record);
    
    ToEvaCommissionChange selectByCaseCode(String caseCode);
}