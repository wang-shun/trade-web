package com.centaline.trans.report.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.report.entity.CaseProcessStatis;

@MyBatisRepository
public interface CaseProcessStatisMapper {

	/**
	 * 插入一条数据
	 * 
	 * @param caseProcessStatis
	 * @return
	 */
	int insert(CaseProcessStatis caseProcessStatis);

	/**
	 * 更新数据
	 * 
	 * @param caseProcessStatis
	 * @return
	 */
	int update(CaseProcessStatis caseProcessStatis);
}
