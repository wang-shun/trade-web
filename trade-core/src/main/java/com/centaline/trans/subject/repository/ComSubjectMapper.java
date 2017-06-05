package com.centaline.trans.subject.repository;

import com.centaline.trans.subject.entity.ComSubject;

public interface ComSubjectMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ComSubject record);

    int insertSelective(ComSubject record);

    ComSubject selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ComSubject record);

    int updateByPrimaryKey(ComSubject record);
}