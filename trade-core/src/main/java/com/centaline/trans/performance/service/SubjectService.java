package com.centaline.trans.performance.service;

import java.util.List;

import com.centaline.trans.performance.entity.Subject;

public interface SubjectService {
	public List<Subject> querySubjectList();

	public List<Subject> querySubjectByCode(String subjectCode);
}
