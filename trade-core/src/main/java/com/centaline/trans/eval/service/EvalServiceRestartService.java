package com.centaline.trans.eval.service;

import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月12日
 * @version:
 */
public interface EvalServiceRestartService {
	
	/**
	 * 评估重启流程
	 * @param vo
	 * @return
	 */
	StartProcessInstanceVo restart(ServiceRestartVo vo);
	
	/**
	 * 校验是否可以发起评估
	 * @param vo
	 * @param userJob
	 * @return
	 */
	boolean checkIsCanRestart(ServiceRestartVo vo,String userJob);
	
	/**
	 * 此评估单相关流程挂起
	 * @param vo
	 * @return
	 */
	StartProcessInstanceVo SuspendEvalSubProcess(ServiceRestartVo vo);
	
	/**
	 * 评估流程重启申请
	 * @param vo
	 * @return
	 */
	boolean apply(ServiceRestartVo vo);
	
	/**
	 * 评估流程重启提交
	 * @param vo
	 * @return
	 */
	boolean approve(ServiceRestartVo vo);
}
