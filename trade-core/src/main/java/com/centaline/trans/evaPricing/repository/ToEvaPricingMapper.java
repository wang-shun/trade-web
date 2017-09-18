package com.centaline.trans.evaPricing.repository;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.evaPricing.entity.ToEvaPricingVo;
import com.centaline.trans.mortgage.entity.ToEguPropertyInfo;

@MyBatisRepository
public interface ToEvaPricingMapper {
	
	/**
	 * 查询询价明细	
	 * @return
	 */
	ToEvaPricingVo findEvaPricingDetailByPKID(Long PKID);
	
	/**
	 * 新增询价数据
	 * @param vo
	 */
	int insertSelective(ToEvaPricingVo vo);
	
	/**
	 * 新增询价物业信息
	 * @param vo
	 * @return
	 */
	int insertEguPropertyInfoSelective(ToEguPropertyInfo vo);
	
	/**
	 * 记录询价数据
	 * @param vo
	 * @return
	 */
	int updateEvaPricing(ToEvaPricingVo vo);
	
	/**
	 * 记录询价物业数据
	 * @param evaCode
	 * @return
	 */
	int updateEguPropertyInfo(@Param("evaCode")String evaCode,@Param("houseAge") Integer houseAge);
	
	/**
	 * 取消询价
	 * @param PKID
	 * @return 
	 */
	int cancelEvaPricingByPKID(Long PKID);
}
