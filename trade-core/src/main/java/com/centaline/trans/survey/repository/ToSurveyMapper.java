package com.centaline.trans.survey.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.survey.entity.ToSurvey;

@MyBatisRepository
public interface ToSurveyMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSurvey record);

    int insertSelective(ToSurvey record);

    ToSurvey selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSurvey record);

    int updateByPrimaryKey(ToSurvey record);
}