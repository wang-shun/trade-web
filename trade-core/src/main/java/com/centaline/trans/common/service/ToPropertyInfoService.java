package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.signroom.vo.PropertyAddrInfoVo;
import com.centaline.trans.signroom.vo.PropertyAddrSearchVo;
import com.centaline.trans.task.entity.ToPropertyResearchVo;

public interface ToPropertyInfoService {
	ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode);

	/**
	 * 根据userid获取到物业信息
	 * 
	 * @param userId
	 * @return
	 */
	List<ToPropertyInfo> getPropertyInfoByUserId(String userId);

	int insertSelective(ToPropertyInfo record);

	ToPropertyResearchVo getPropertyDepInfoByuserDepId(String depId);

	ToPropertyInfo findToPropertyInfoByCaseCodeAndAddr(ToPropertyInfo record);

	ViHouseDelBaseVo getHouseBaseByHoudelCode(String delCode);

	ToPropertyResearchVo getPropertyDepInfoByuserDepIdEloan(String depId);

	/**
	 * 预约取号---根据用户输入的产证地址信息查找出对应的产证地址列表
	 * 
	 * @param propertyAddrSearchVo
	 *            产证地址信息查询条件
	 * 
	 * @return 产证信息列表
	 */
	public List<PropertyAddrInfoVo> getPropertyInfoListByInputValue(
			PropertyAddrSearchVo propertyAddrSearchVo);

	/**
	 * 根据产证地址获取对应的案件编号
	 * 
	 * @param propertyAddrSearchVo
	 *            产证地址条件
	 * @return 案件编号
	 */
	public String getCaseCodeByPropertyAddr(
			PropertyAddrSearchVo propertyAddrSearchVo);

	/**
	 * 根据产证地址获取对应案件的服务顾问(交易顾问)
	 * 
	 * @param propertyAddrSearchVo
	 *            产证地址条件
	 * @return 服务顾问(交易顾问)名称
	 */
	public String getServiceSpecialistByPropertyAddr(
			PropertyAddrSearchVo propertyAddrSearchVo);

}
