package com.centaline.trans.performance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.performance.entity.Subject;
import com.centaline.trans.performance.repository.SubjectMapper;
import com.centaline.trans.performance.service.SubjectService;
@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectMapper subjectMapper;

	@Override
	public List<Subject> querySubjectList() {
		// TODO Auto-generated method stub
		return subjectMapper.querySubjectList();
	}

	@Override
	public List<Subject> querySubjectByCode(String subjectCode) {
		// TODO Auto-generated method stub
		return subjectMapper.querySubjectByCode(subjectCode);
	}

}
