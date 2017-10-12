package com.centaline.trans.ransom.service;

import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;

public interface RansomListFormService {
	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
	
	/**
	 * 查询赎楼单列表
	 * @param caseCode
	 * @return
	 */
	ToRansomCaseVo getRansomCase(String caseCode);
	
	/**
	 * 根据caseCode保存赎楼中止信息
	 * @param caseCode
	 * @return
	 */
	int updateRansomDiscountinue(String caseCode);
	
	List getUpdateRansomInfo(String caseCode);
	
	/**
	 * 修改赎楼列表信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomCaseInfo(ToRansomCaseVo caseVo);
	
	/**
	 * 修改尾款信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomTailinsInfo(ToRansomTailinsVo tailinsVo);
}
