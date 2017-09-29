package com.centaline.trans.ransom.service;

import com.centaline.trans.ransom.entity.ToRansomCaseVo;

public interface RansomListFormService {
	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
}
