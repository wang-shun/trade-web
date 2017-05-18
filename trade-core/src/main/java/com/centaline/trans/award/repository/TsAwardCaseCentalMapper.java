package com.centaline.trans.award.repository;

import com.centaline.trans.award.entity.TsAwardCaseCental;
import com.centaline.trans.common.MyBatisRepository;



@MyBatisRepository
public interface TsAwardCaseCentalMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TsAwardCaseCental record);

    int insertSelective(TsAwardCaseCental record);

    TsAwardCaseCental selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TsAwardCaseCental record);

    int updateByPrimaryKey(TsAwardCaseCental record);
}