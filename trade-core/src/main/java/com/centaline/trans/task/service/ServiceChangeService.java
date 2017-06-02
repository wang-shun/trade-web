package com.centaline.trans.task.service;

import java.util.List;
import java.util.Map;

import com.centaline.trans.common.entity.ToServChangeHistroty;

public interface ServiceChangeService {

	/**
	 * 
	 * @param caseCode
	 * @param content 
	 * 1  删除流程异常
	 * 2 更新业务单失败
	 * 11 爆单完成
	 * 4启动新流程失败
	 * 5工作流插入失败
	 * 6业务单流程重启异常
	 * 12业务单流程重启完成
	 * 13 服务变更完成
	 * 9
	 * @return
	 */
	public Integer updateServItemAndProcessor(String caseCode, String content);
	
	public Integer updateServChangeHistroty(String caseCode);
	
	public List<ToServChangeHistroty> queryDelServChangeHistroty(String caseCode);
	
	public String queryAddServChangeHistroty(String caseCode);
	
	public Map<String, Object> qureyServChangeHistrotyInfo(String caseCode);
}
