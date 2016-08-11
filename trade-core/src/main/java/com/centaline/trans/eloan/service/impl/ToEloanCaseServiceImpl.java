package com.centaline.trans.eloan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.repository.ToEloanCaseMapper;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
@Service
public class ToEloanCaseServiceImpl implements ToEloanCaseService {
	
	@Autowired
	private ProcessInstanceService processInstanceService; 
	@Autowired
	private TaskService taskService; 
	@Autowired
	private ToEloanCaseMapper toEloanCaseMapper; 
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Override
	public void saveEloanApply(SessionUser user, ToEloanCase tEloanCase) {
		// 保存申请数据
    	//产品类型、案件绑定、合作机构、客户姓名、客户电话、申请金额、申请时间、申请期数
    	//转介人姓名、转介人员工编号、合作人姓名、合作人员工编号、产品部合作人、分成比例贷款。
    	//E+编号
		User excutor = uamUserOrgService.getUserById(tEloanCase.getExcutorId());
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		if(excutor!=null) {
			tEloanCase.setExcutorTeam(excutor.getOrgId());
		}
		if(districtOrg!=null) {
			tEloanCase.setExcutorDistrict(districtOrg.getId());
		}
    	toEloanCaseMapper.insertSelective(tEloanCase);
    	
    	User manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(excutor.getOrgId(), "Manager");
    	Map<String, Object> vars = new HashMap<String,Object>();
    	vars.put("Consultant", excutor.getUsername());
    	vars.put("Manager", manager==null?null:manager.getUsername());
    	
    	String demo=propertyUtilsService.getProcessEloanDfKey();
    	StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessEloanDfKey(),tEloanCase.getEloanCode(),vars);
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(tEloanCase.getCaseCode());
		workFlow.setBusinessKey(WorkFlowEnum.ELOAN_BUSSKEY.getCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus("0");
		toWorkFlowService.insertSelective(workFlow);
    	
    	PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
    	List<TaskVo> taskList = pageableVo.getData();
    	for(TaskVo task : taskList) {
    		taskService.complete(task.getId()+"");
    	}

	}
	



	@Override
	public int updateEloanApply(SessionUser user, ToEloanCase tEloanCase) {
		User excutor = uamUserOrgService.getUserById(tEloanCase.getExcutorId());
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		if(excutor!=null) {
			tEloanCase.setExcutorTeam(excutor.getOrgId());
		}
		if(districtOrg!=null) {
			tEloanCase.setExcutorDistrict(districtOrg.getId());
		}
		
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setStatus("0");
		workFlow.setCaseCode(tEloanCase.getCaseCode());
		workFlow.setInstCode(tEloanCase.getProcessInstanceId());
		toWorkFlowService.updateWorkFlowByInstCode(workFlow);
		
		taskService.complete(tEloanCase.getTaskId());
    	return toEloanCaseMapper.updateEloanCaseByEloanCode(tEloanCase);
	}
	
	@Override
	public List<ToEloanCase> getToEloanCaseListByProperty(ToEloanCase tEloanCase) {
		return toEloanCaseMapper.getToEloanCaseListByProperty(tEloanCase);
	}

	@Override
	@TaskOperate
	public void saveEloanSign(String taskId, ToEloanCase tEloanCase) {
		toEloanCaseMapper.updateEloanCaseByEloanCode(tEloanCase);
	}

	@Override
	@TaskOperate(submitVal="map")
	public void eloanProcessConfirm(String taskId, Map<String, Object> map, ToEloanCase toEloanCase, boolean isUpdate) {
		if(isUpdate) {
			toEloanCaseMapper.updateEloanCaseByEloanCode(toEloanCase);
		}
	}

	@Override
	public ToEloanCase getToEloanCaseByPkId(Long pkid) {
		return toEloanCaseMapper.selectByPrimaryKey(pkid);
	}




	@Override
	public List<String> validateEloanApply(ToEloanCase tEloanCase) {
		return toEloanCaseMapper.validateEloanApply(tEloanCase);
	}
}
