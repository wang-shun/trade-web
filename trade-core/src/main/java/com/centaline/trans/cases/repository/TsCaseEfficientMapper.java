package com.centaline.trans.cases.repository;

import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.common.MyBatisRepository;

/**
 * 案件时效管理mapper
 * 
 * @author yinjf2
 *
 */
@MyBatisRepository
public interface TsCaseEfficientMapper
{

    int insert(TsCaseEfficient record);

    int insertSelective(TsCaseEfficient record);
}