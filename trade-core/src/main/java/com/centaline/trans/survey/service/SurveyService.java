package com.centaline.trans.survey.service;

import java.util.List;

import com.centaline.trans.survey.vo.SurveyVO;

public interface SurveyService {

	int updateSurveyVO(SurveyVO surveyVO);
	
	int insertSurveyVO(SurveyVO surveyVO);
	
	List<SurveyVO> querySurveyVOList();
	
	SurveyVO querySurveyVOById(int id);

}
