package com.centaline.trans.cases.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.centaline.trans.cases.entity.ToCaseComment;
import com.centaline.trans.cases.repository.ToCaseCommentMapper;
import com.centaline.trans.cases.service.ToCaseCommentService;

@Service
@Transactional(readOnly = true)
public class ToCaseCommentServiceImpl implements ToCaseCommentService {
	
	@Autowired
	private ToCaseCommentMapper toCaseCommentMapper;

	@Override
	public List<ToCaseComment> getToCaseCommentList(ToCaseComment record) {
		return toCaseCommentMapper.getToCaseCommentList(record);
	}

	@Override
	public int insertToCaseComment(ToCaseComment record) {
		return toCaseCommentMapper.insertSelective(record);
	}
}
