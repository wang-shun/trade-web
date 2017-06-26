package com.centaline.trans.performance.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.performance.entity.Subject;

@MyBatisRepository
public interface SubjectMapper {

	List<Subject> querySubjectList();

	List<Subject> querySubjectByCode(String subjectCode);

}
