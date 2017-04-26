package com.centaline.trans.survey.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.survey.entity.ToSurveyAttach;

@MyBatisRepository
public interface ToSurveyAttachMapper {
    int deleteByPrimaryKey(Long pkid);

    int insert(ToSurveyAttach record);

    int insertSelective(ToSurveyAttach record);

    ToSurveyAttach selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToSurveyAttach record);

    int updateByPrimaryKey(ToSurveyAttach record);
}