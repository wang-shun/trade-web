package com.centaline.trans.perform.service;

import com.centaline.trans.perform.vo.EplusVo;

public interface EplusService {
	/**
	 * 单条导入
	 * @param vo
	 * @return
	 */
	boolean importOne(EplusVo vo);
}
