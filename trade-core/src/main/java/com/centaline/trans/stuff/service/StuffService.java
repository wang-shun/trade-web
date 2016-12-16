package com.centaline.trans.stuff.service;

import com.centaline.trans.comment.entity.ToCaseComment;

/**
 * 
 * @author linjiarong
 *
 */
public interface StuffService {
	
	/**
	 * 提供给手机端Controller， 进行补件材料申请。
	 * 功能：1. 调用caseCommentService增加一条补件申请备注。
	 *      2. 调用流程引擎启动相关的补丁流程。
	 * @param stuffComment
	 */
	public void reqStuff(ToCaseComment stuffComment);

}
