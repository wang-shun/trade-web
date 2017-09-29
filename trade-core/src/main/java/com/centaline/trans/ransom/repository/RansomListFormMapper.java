package com.centaline.trans.ransom.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;

@MyBatisRepository
public interface RansomListFormMapper {

	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
}
