package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaCommPersonAmount;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
/**
 * @author xiefei1
 * @since 2017年10月11日 下午3:14:04 
 * @description 调佣对象与调佣金额
 */
public interface ToEvaCommPersonAmountService {
	/**
	 * 
	 * @since:2017年10月26日 下午4:46:57
	 * @description:此方法会先判断DB有没有当前案件的调佣信息，如果有的话就返回，无则再从CCAI获取
	 * @author:xiefei1
	 * @param caseCode
	 * @return
	 */
    EvalChangeCommVO getFullEvalChangeCommVO(String caseCode);
	/**
	 * 
	 * @since:2017年10月26日 下午4:29:38
	 * @description:从CCAI获取完整的调佣对象与调佣金额VO
	 * 				getFullEvalChangeCommVOFromCCAI 从CCAI获取完整的调佣对象与调佣金额VO 与 getFullEvalChangeCommVOFromDB 的
	 * 				区别是 CCAI是调佣前的，DB是权证经理调佣后的信息
	 * @author:xiefei1
	 * @param caseCode
	 * @return
	 */
	EvalChangeCommVO getFullEvalChangeCommVOFromCCAI(String caseCode);
	/**
	 * 
	 * @since:2017年10月26日 下午4:29:38
	 * @description:从DB获取完整的调佣对象与调佣金额VO
	 * 				getFullEvalChangeCommVOFromCCAI 从CCAI获取完整的调佣对象与调佣金额VO 与 getFullEvalChangeCommVOFromDB 的
	 * 				区别是 CCAI是调佣前的，DB是权证经理调佣后的信息
	 * @author:xiefei1
	 * @param caseCode
	 * @return
	 */
	EvalChangeCommVO getFullEvalChangeCommVOFromDB(String caseCode);
//	保存调佣对象与调佣金额VO
	void saveEvalChangeCommVO(EvalChangeCommVO EvalChangeCommVO,ToEvaCommissionChange toEvaCommissionChange);
	
    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommPersonAmount record);

    int insertSelective(ToEvaCommPersonAmount record);

    ToEvaCommPersonAmount selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommPersonAmount record);

    int updateByPrimaryKey(ToEvaCommPersonAmount record);
}
