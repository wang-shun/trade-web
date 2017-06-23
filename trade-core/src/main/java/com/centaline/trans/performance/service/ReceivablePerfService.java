package com.centaline.trans.performance.service;

import com.centaline.trans.performance.vo.ReceivablePerfVo;

public interface ReceivablePerfService {
	/**
	 * 生成应收业绩
	 * @param vo
	 */
	void generatePerf(ReceivablePerfVo vo);
	
}
