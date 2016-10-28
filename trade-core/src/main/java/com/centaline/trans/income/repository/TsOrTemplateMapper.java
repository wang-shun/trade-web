package com.centaline.trans.income.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.income.entity.TsOrTemplate;

@MyBatisRepository
public interface TsOrTemplateMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsOrTemplate record);

    int insertSelective(TsOrTemplate record);

    TsOrTemplate selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsOrTemplate record);

    int updateByPrimaryKey(TsOrTemplate record);
    
    /**
	 * 功能：收益
	 * 作者：zhangxb16
	 */
    List<TsOrTemplate> selectByroleCodeandServcat(TsOrTemplate tte);
}