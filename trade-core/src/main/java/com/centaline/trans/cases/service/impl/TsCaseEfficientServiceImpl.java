package com.centaline.trans.cases.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.TsCaseEfficient;
import com.centaline.trans.cases.repository.TsCaseEfficientMapper;
import com.centaline.trans.cases.service.TsCaseEfficientService;

/**
 * 案件时效管理service实现类
 * 
 * @author yinjf2
 *
 */
@Service
public class TsCaseEfficientServiceImpl implements TsCaseEfficientService
{

    @Autowired
    private TsCaseEfficientMapper tsCaseEfficientMapper;

    @Override
    public int save(TsCaseEfficient tsCaseEfficient)
    {
        return tsCaseEfficientMapper.insertSelective(tsCaseEfficient);
    }

}
