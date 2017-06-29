package com.centaline.trans.product.repository;

import com.centaline.trans.product.entity.TtsComSubject;

public interface TtsComSubjectMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(TtsComSubject record);

    int insertSelective(TtsComSubject record);

    TtsComSubject selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(TtsComSubject record);

    int updateByPrimaryKey(TtsComSubject record);
}