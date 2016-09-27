package com.centaline.trans.eloan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.entity.ToEloanRel;
import com.centaline.trans.eloan.repository.ToEloanCaseMapper;
import com.centaline.trans.eloan.repository.ToEloanRelMapper;
import com.centaline.trans.eloan.service.ToEloanRelService;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
@Service
public class ToEloanRelServiceImpl implements ToEloanRelService {
	
	@Autowired
	private ToEloanCaseMapper toEloanCaseMapper; 
	@Autowired
	private ToEloanRelMapper toEloanRelMapper;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TaskService taskService; 
	
	@Override
	public List<ToEloanRel> getEloanRelByEloanCode(String eloanCode) {
		return toEloanRelMapper.getEloanRelByEloanCode(eloanCode);
	}

	@Override
	@TaskOperate(submitVal="#map")
	public int saveEloanRelease(String taskId, List<ToEloanRel> toEloanRelList, Map map,String isRelFinish) {
		String eloanCode = toEloanRelList.get(0).getEloanCode();
		ToEloanCase eloanCase = new ToEloanCase();
		eloanCase.setEloanCode(eloanCode);
		eloanCase.setIsRelFinish(isRelFinish);
		toEloanCaseMapper.updateEloanCaseByEloanCode(eloanCase);
		
		int i = 0;
		for(ToEloanRel toEloanRel : toEloanRelList) {
			i+= toEloanRelMapper.insertSelective(toEloanRel);
		}
		
		return i;
	}

	@Override
	public int saveEloanReleaseConfirm(String taskId, String approved, String eloanCode, SessionUser user, Map map,String processInstanceId) {
		// 流程结束状态
		PageableVo pageableVo = taskService.listTasks(processInstanceId, false);
    	List<TaskVo> taskList = pageableVo.getData();
    	for(TaskVo taskVO : taskList) {
    		taskService.complete(taskVO.getId()+"", map);
    	}
		PageableVo pageableVo2 = taskService.listTasks(processInstanceId, false);
    	List<TaskVo> taskList2 = pageableVo2.getData();
    	if(CollectionUtils.isEmpty(taskList2)) {
    		ToWorkFlow workFlowOld =new ToWorkFlow();
			workFlowOld.setStatus("4");
			workFlowOld.setInstCode(processInstanceId);
			toWorkFlowService.updateWorkFlowByInstCode(workFlowOld);
    	}
		ToEloanRel eloanRel = new ToEloanRel();
		eloanRel.setConfirmUser(user.getId());
		eloanRel.setConfirmTime(new Date());
		eloanRel.setConfirmStatus(approved);
		eloanRel.setEloanCode(eloanCode);
		return toEloanRelMapper.updateEloanRelByEloanCode(eloanRel);
	}

	@Override
	public void updateEloanRelByEloanCodeForModify(List<ToEloanRel> eloanRelList) {
		// TODO Auto-generated method stub
		toEloanRelMapper.updateEloanRelByEloanCodeForModify(eloanRelList);
	}

}
