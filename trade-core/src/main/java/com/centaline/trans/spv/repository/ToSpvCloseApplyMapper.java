package com.centaline.trans.spv.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvCloseApply;

@MyBatisRepository
public interface ToSpvCloseApplyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvCloseApply record);

    int insertSelective(ToSpvCloseApply record);

    ToSpvCloseApply selectByPrimaryKey(Long pkid);
    
    //ToSpvCloseApply selectBySpvCloseApplyCode(String spvCloseApplyCode);

    int updateByPrimaryKeySelective(ToSpvCloseApply record);

    int updateByPrimaryKey(ToSpvCloseApply record);
}