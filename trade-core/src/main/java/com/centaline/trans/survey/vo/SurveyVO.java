package com.centaline.trans.survey.vo;

import java.util.List;

import com.centaline.trans.survey.entity.ToSurvey;
import com.centaline.trans.survey.entity.ToSurveyAttach;

public class SurveyVO {
	
	private ToSurvey toSurvey;
	
	private List<ToSurveyAttach> toSurveyAttachs;

	public ToSurvey getToSurvey() {
		return toSurvey;
	}

	public void setToSurvey(ToSurvey toSurvey) {
		this.toSurvey = toSurvey;
	}

	public List<ToSurveyAttach> getToSurveyAttachs() {
		return toSurveyAttachs;
	}

	public void setToSurveyAttachs(List<ToSurveyAttach> toSurveyAttachs) {
		this.toSurveyAttachs = toSurveyAttachs;
	}


}
