package com.centaline.trans.eval.service;

import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.task.entity.ToApproveRecord;



/**
 * @author xiefei1
 * @since 2017年9月22日 下午2:11:07 
 * @description 评估公司变更调佣模块
 */

public interface ToEvaCommissionChangeService {
	/**
	 * 
	 * @since:2017年10月17日 下午5:25:17
	 * @description:开启工作流
	 * @author:xiefei1
	 * @param caseCode
	 * @param warrantManager 即权证经理 任务办理人，   若不指定warrantManager，则会读取该案件在交易系统中的warrantManager；
	 * @return
	 * @throws Exception
	 */
	int updateStartChangeCommProcess(String caseCode,String warrantManager) throws Exception;
//	提交任务，插入评论，回写CCAI
	int updateEvalChangeApproveRecord(ToApproveRecord toApproveRecord,String taskId, String processInstanceId);

    int deleteByPrimaryKey(Long pkid);

    int insert(ToEvaCommissionChange record);

    int insertSelective(ToEvaCommissionChange record);

    ToEvaCommissionChange selectByPrimaryKey(Long pkid);

    int updateByPrimaryKeySelective(ToEvaCommissionChange record);

    int updateByPrimaryKey(ToEvaCommissionChange record);
}
