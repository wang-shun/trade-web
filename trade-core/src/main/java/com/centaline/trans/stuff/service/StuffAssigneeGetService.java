package com.centaline.trans.stuff.service;

import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.comment.entity.ToCaseComment;

/**
 * 补件流程查找执行人Service
 * 
 * @author jjm
 */
public interface StuffAssigneeGetService {
	public User getAssignee(ToCaseComment stuffComment);
}
