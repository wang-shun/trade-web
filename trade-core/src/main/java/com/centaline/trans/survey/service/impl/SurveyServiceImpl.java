package com.centaline.trans.survey.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.survey.repository.ToSurveyAttachMapper;
import com.centaline.trans.survey.repository.ToSurveyMapper;
import com.centaline.trans.survey.service.SurveyService;
import com.centaline.trans.survey.vo.SurveyVO;

@Service
public class SurveyServiceImpl implements SurveyService {
	
	@Autowired
	private ToSurveyMapper toSurveyMapper;
	
	@Autowired
	private ToSurveyAttachMapper toSurveyAttachMapper;

	@Override
	public int updateSurveyVO(SurveyVO surveyVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSurveyVO(SurveyVO surveyVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SurveyVO> querySurveyVOList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SurveyVO querySurveyVOById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
