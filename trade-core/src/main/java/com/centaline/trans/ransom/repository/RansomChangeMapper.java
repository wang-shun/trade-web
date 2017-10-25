package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;

@MyBatisRepository
public interface RansomChangeMapper {
	
	/**
	 * 赎楼申请更新
	 * @param applyVo
	 */
	void updateRansomApplyInfo(ToRansomApplyVo applyVo);
	
	/**
	 * 赎楼面签更新
	 * @param applyVo
	 */
	void updateRansomSignInfo(ToRansomSignVo signVo);
	
	/**
	 * 获取赎楼计划时间信息
	 * @param ransomCode
	 * @return
	 */
	List<ToRansomPlanVo> getPlanTimeInfo(String ransomCode);
	
	/**
	 * 陪同还贷更新
	 * @param ransomCode
	 */
	void updatePayloanInfo(ToRansomMortgageVo mortgageVo);
}