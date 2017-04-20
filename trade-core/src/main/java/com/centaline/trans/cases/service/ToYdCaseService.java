package com.centaline.trans.cases.service;

import com.centaline.trans.cases.vo.CaseMergeVo;

public interface ToYdCaseService {
	
	/**
	 * 新建外单案件
	 * @author hejf10
	 * @date 2017年4月19日16:04:14
	 * @param caseMergeVo【页面外单输入信息】
	 * @return
	 */
	public void addYDCaseSave(CaseMergeVo caseMergeVo) throws Exception;
}
