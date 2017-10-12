package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;

@MyBatisRepository
public interface RansomListFormMapper {

	/**
	 * 添加赎楼列表信息数据
	 * @param trco
	 * @return
	 */
	int addRansomDetail(ToRansomCaseVo trco);
	
	/**
	 * 查询赎楼单列表信息数据by caseCode
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
	
	/**
	 * 获取客户信息
	 * @param caseCode
	 * @return
	 */
	List<TgGuestInfo> getGuestInfo(String caseCode);

	/**
	 * 获取机构信息
	 * @param finOrgCode
	 * @return
	 */
	TsFinOrg getTsFinOrgIinfo(String finOrgCode);
	
	/**
	 * 修改赎楼列表信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomCaseByRansomCode(ToRansomCaseVo caseVo);
	
	/**
	 * 修改尾款信息
	 * @param ransomCode
	 * @return
	 */
	boolean updateRansomTailinsByRansomCode(ToRansomTailinsVo tailinsVo);
}
