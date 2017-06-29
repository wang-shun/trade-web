package com.centaline.trans.subject.service;

import com.centaline.trans.subject.entity.ComSubject;

public interface ComSubjectService {
	void addCommSubject(ComSubject comSubject);

	void inActiveByPkid(Long pkid);

	void updateByPkid(ComSubject comSubject);
}
