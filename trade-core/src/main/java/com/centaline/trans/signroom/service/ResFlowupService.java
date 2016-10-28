package com.centaline.trans.signroom.service;

import java.util.List;

import com.centaline.trans.signroom.entity.ResFlowup;
import com.centaline.trans.signroom.vo.ResFlowupVo;

public interface ResFlowupService {

	/**
	 * 保存跟进信息
	 * 
	 * @param resFlowup
	 *            跟进信息
	 * @return 如果返回1,保存成功;返回0,保存失败。
	 */
	public int saveResFlowup(ResFlowup resFlowup);

	/**
	 * 根据预约单标识获取跟进信息列表
	 * 
	 * @param resId
	 *            预约单标识
	 * @return 跟进信息列表
	 */
	public List<ResFlowupVo> getResFlowupListByResId(Long resId);

}
