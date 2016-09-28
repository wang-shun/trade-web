package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.cases.vo.ViHouseDelBaseVo;
import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.vo.OrgVO;
import com.centaline.trans.signroom.vo.PropertyAddrInfoVo;
import com.centaline.trans.signroom.vo.PropertyAddrSearchVo;

@MyBatisRepository
public interface ToPropertyInfoMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(ToPropertyInfo record);

	int insertSelective(ToPropertyInfo record);

	ToPropertyInfo selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(ToPropertyInfo record);

	int updateByPrimaryKeyWithBLOBs(ToPropertyInfo record);

	int updateByPrimaryKey(ToPropertyInfo record);

	ToPropertyInfo findToPropertyInfoByCaseCode(String caseCode);

	ToPropertyInfo findToPropertyInfoByCaseCodeAndAddr(ToPropertyInfo record);

	/**
	 * 根据userid查询物业信息
	 */
	List<ToPropertyInfo> getPropertyInfoByUserId(String userId);

	// 根据房屋id 去查询同义词
	ViHouseDelBaseVo selectByHoudelCode(String property_agent_id);

	OrgVO getPropertyDepInfoByuserDepId(String depid);

	OrgVO getPropertyDepInfoByuserDepIdEloan(String depid);

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

}