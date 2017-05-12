package com.centaline.trans.award.repository;

import com.centaline.trans.award.entity.TtsAwardCaseCental;

public interface TtsAwardCaseCentalMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsAwardCaseCental record);

    int insertSelective(TtsAwardCaseCental record);

    TtsAwardCaseCental selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsAwardCaseCental record);

    int updateByPrimaryKey(TtsAwardCaseCental record);
}