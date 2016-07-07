package com.centaline.trans.cases.service;

import java.util.List;
import com.centaline.trans.cases.entity.ToCaseComment;

public interface ToCaseCommentService {
	List<ToCaseComment> getToCaseCommentList(ToCaseComment record);
	
	int insertToCaseComment(ToCaseComment record);
}
