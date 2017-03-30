package com.centaline.trans.award.service;

import com.centaline.trans.award.vo.BaseImportVo;

public interface BaseImportService {
	/**
	 * 单条导入
	 * @param vo
	 * @return
	 */
	boolean importOne(BaseImportVo vo);
}
