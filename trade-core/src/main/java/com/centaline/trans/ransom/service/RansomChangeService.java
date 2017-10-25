package com.centaline.trans.ransom.service;

import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;

/**
 * 赎楼修改service
 * @author wbwumf
 *
 *2017年10月25日
 */
public interface RansomChangeService {

	/**
	 * 赎楼修改
	 * @param applyVo
	 */
	void updateRansomApplyInfo(ToRansomApplyVo applyVo);
	
	/**
	 * 修改赎楼面签信息
	 * @param signVo
	 */
	void updateRansomSignInfo(ToRansomSignVo signVo);
	
	/**
	 * 陪同还贷更新
	 */
	void updatePayloanInfo(ToRansomSubmitVo subVo);
}
