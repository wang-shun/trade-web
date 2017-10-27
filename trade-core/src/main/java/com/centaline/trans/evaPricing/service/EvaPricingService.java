package com.centaline.trans.evaPricing.service;

import java.util.List;
import java.util.Map;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;

/**
 * 询价service
 * @author wbcaiyx
 *
 */
public interface EvaPricingService {
	
	/**
	 * 查询询价明细	
	 * @return
	 */
	ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID,String evaCode);
	
	/**
	 * 新增询价数据
	 * @param vo
	 */
	AjaxResponse<String> insertEvaPricing(ToEvaPricingVo vo);
	
	/**
	 * 记录询价数据
	 */
	void updateEvaPricing(ToEvaPricingVo vo);
	
	/**
	 * 取消询价
	 * @param PKID
	 */
	int cancelByPKID(Long PKID); 
	
	/**
	 * 检查是否存在与案件关联的询价单
	 * @param caseCode
	 * @return
	 */
	boolean queryInfoByCase(String caseCode);
	
	/**
	 * 案件评估申请检查
	 * @param caseCode
	 * @return
	 */
	AjaxResponse<Integer> checkEvalProcess(String caseCode);
	
	/**
	 * 获取评估 公司数据
	 * @return
	 */
	List<Map<String,String>>queryEvaFinOrg();
	
	/***
	 * 询价关联案件
	 * @param pkid
	 * @param caseCode
	 * @return
	 */
	boolean evalRelation(long pkid,String caseCode);
	
	/**
	 * 询价详情更新
	 * @param pkid
	 * @param isValid
	 * @param reason
	 * @return
	 */
	AjaxResponse<String> updateEvaPricingDetail(ToEvaPricingVo toEvaPricingVo);
	
}
