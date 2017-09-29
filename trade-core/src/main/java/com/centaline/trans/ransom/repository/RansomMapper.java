package com.centaline.trans.ransom.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;

/**
 * 赎楼Mapper
 * @author wbcaiyx
 *
 */
@MyBatisRepository
public interface RansomMapper {
	
	/**
	 * 根据赎楼编号获取详情
	 * @param caseCode
	 * @return
	 */
	ToRansomDetailVo getRansomDetailInfoByCode(String caseCode);
	
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
	 * 申请数据新增
	 * @param signVo
	 * @return
	 */
	int insertRansomApply(ToRansomApplyVo applyVo);
	
	/**
	 * 赎楼案件信息更新
	 * @param signVo
	 * @return
	 */
	int updateRansomInfo(ToRansomSubmitVo submitVo);
	
	/**
	 * 检查是否存在二抵
	 * @param ransomCode
	 * @return
	 */
	int queryErdi(String ransomCode);
	
	/**
	 * 面签数据插入
	 * @param signVo
	 * @return
	 */
	int insertRansomSign(ToRansomSignVo signVo);
	
	/**
	 * 陪同还贷数据插入
	 * @param mortVo
	 * @return
	 */
	int insertRansomMortgage(ToRansomMortgageVo mortVo);
	
	/**
	 * 陪同还贷计划 时间
	 * @param planVo
	 * @return
	 */
	int insertRansomPlanTime(ToRansomPlanVo planVo);
	
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
	
	/**
	 * 状态更新
	 * @param ransomCode
	 * @return
	 */
	int updateCaseStatusComplete(String ransomCode);
}
