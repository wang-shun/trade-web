package com.centaline.trans.comment.service;

import java.util.List;

import com.centaline.trans.comment.entity.ToCaseComment;

public interface ToCaseCommentService {
	List<ToCaseComment> getToCaseCommentList(ToCaseComment record);

	int insertToCaseComment(ToCaseComment record);
}
