package com.centaline.trans.ransom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;

/**
 * 赎楼Mapper
 * @author wbcaiyx
 *
 */
@MyBatisRepository
public interface RansomMapper {
	
	/**
	 * 根据赎楼编号获取详情
	 * @param ransomCode
	 * @return
	 */
	ToRansomDetailVo getRansomDetailInfoByCode(String ransomCode);
	
	/**
	 * 根据赎楼编号获取计划 时间
	 * @param ransomCode
	 * @return
	 */
	List<ToRansomPlanVo> getRansomPlanByCode(String ransomCode);
	
	/**
	 * 根据不同表,code及列获取 实际完成时间
	 * @param table
	 * @param ransomCode
	 * @param cloumn
	 * @return
	 */
	Map<String,Date> getCompleteTimeByCode( @Param("table")String table,
											@Param("ransomCode")String ransomCode,
											@Param("cloumn")String cloumn);
	
	/**
	 * 注销抵押插入数据
	 * @param cancelVo
	 * @return
	 */
	int insertRansomCancel(ToRansomCancelVo cancelVo);
	
	/**
	 * 领取产证插入数据
	 * @param permitVo
	 * @return
	 */
	int insertRansomPermit(ToRansomPermitVo permitVo);
	
	/**
	 * 回款结清插入数据
	 * @param paymentVo
	 * @return
	 */
	int insertRansomPayment(ToRansomPaymentVo paymentVo);
}
