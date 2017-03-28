package com.centaline.trans.spv.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.spv.entity.ToSpvDeDetail;

@MyBatisRepository
public interface ToSpvDeDetailMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSpvDeDetail record);

    int insertSelective(ToSpvDeDetail record);

    ToSpvDeDetail selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSpvDeDetail record);

    int updateByPrimaryKey(ToSpvDeDetail record);
    
    List<ToSpvDeDetail> selectByDeId(Long deId);

	void deleteByDeId(Long pkid);
}