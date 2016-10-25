package com.centaline.trans.eloan.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.KeyValueMapper;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.KeyValueService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.repository.ToEloanCaseMapper;
import com.centaline.trans.eloan.repository.ToEloanRelMapper;
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
	@Autowired
	private ToEloanRelMapper toEloanRelMapper; 
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TgServItemAndProcessorMapper servItemMapper;
	
	@Override
	public void saveEloanApply(SessionUser user, ToEloanCase tEloanCase) {
		// 保存申请数据
    	//产品类型、案件绑定、合作机构、客户姓名、客户电话、申请金额、申请时间、申请期数
    	//转介人姓名、转介人员工编号、合作人姓名、合作人员工编号、产品部合作人、分成比例贷款。
    	//E+编号
		
		//如果当前用户和提交用户是同一个人，用户部门就用当前session部门  deptype-- yes:同一个用户 no:不同用户
		String depType = "no";
		if(user != null && tEloanCase != null && user.getId().equals(tEloanCase.getExcutorId())){
			depType = "yes";
		}
		//申请人信息
		User excutor = uamUserOrgService.getUserById(tEloanCase.getExcutorId());
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		if(excutor!=null) {
			//如果当前用户和提交用户是同一个人，用户部门就用当前session部门  deptype-- yes:同一个用户 no:不同用户
			if(depType.equals("yes")){
				tEloanCase.setExcutorTeam(user.getServiceCompanyId());
				depType = "no";
			}else{
				tEloanCase.setExcutorTeam(excutor.getOrgId());
			}
		}
		if(districtOrg!=null) {
			tEloanCase.setExcutorDistrict(districtOrg.getId());
		}
		bindServItem(tEloanCase);
    	toEloanCaseMapper.insertSelective(tEloanCase);    	
    	
    	// start
    	User manager=new User();
    	if(excutor.getId().equals(user.getId())) {
    		 manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "Manager");
    	} else {
    		 manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(excutor.getOrgId(), "Manager");
    	}
    	// end
    	
    	Map<String, Object> vars = new HashMap<String,Object>();
    	//vars.put("Consultant", excutor.getUsername());
    	vars.put("Consultant", user.getUsername());
    	//当前申请人是否是主管   
    	String jobCode=user.getServiceJobCode();
    	if("Manager".equals(jobCode)){
    		vars.put("Manager", user.getUsername());
    	}else{
    		vars.put("Manager", manager==null?null:manager.getUsername());
    	}	
    	
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
	
	private void bindServItem(ToEloanCase tEloanCase) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if(!StringUtils.isBlank(tEloanCase.getCaseCode())){
			p.setCaseCode(tEloanCase.getCaseCode());
			p.setSrvCat(tEloanCase.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper.findTgServItemAndProcessor(p);
			boolean isNew=false;
			if(ts==null){
				ts=p;
				isNew=true;
			}
			ts.setSrvCat(tEloanCase.getLoanSrvCode());
			ts.setSrvCode(tEloanCase.getLoanSrvCode()+"01");
			ts.setProcessorId(tEloanCase.getExcutorId());
			ts.setOrgId(tEloanCase.getExcutorTeam());
			if(isNew){
				servItemMapper.insertSelective(ts);
			}else{
				servItemMapper.updateByPrimaryKeySelective(ts);
			}
		}
	}

	private void unbindServItem(ToEloanCase newObj, ToEloanCase obj) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if (!StringUtils.isBlank(obj.getCaseCode()) && obj.getLoanSrvCode() != null
				&& !obj.getLoanSrvCode().equals(newObj.getLoanSrvCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper.findTgServItemAndProcessor(p);
			if (ts != null) {
				servItemMapper.deleteByPrimaryKey(ts.getPkid());//confirmed by xiacheng 
			}
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
		
		ToEloanCase property = new ToEloanCase();
		property.setEloanCode(tEloanCase.getEloanCode());
		List<ToEloanCase> eloanCaseList = toEloanCaseMapper.getToEloanCaseListByProperty(property);
	    if(!CollectionUtils.isEmpty(eloanCaseList)) {
	    	unbindServItem(tEloanCase,eloanCaseList.get(0));
	    }
		bindServItem(tEloanCase);
    	return toEloanCaseMapper.updateApplyEloanCaseByEloanCode(tEloanCase);
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

	@Override
	public AjaxResponse<Boolean> validateIsFinishRelease(String eloanCode, Double sumAmount) {
		AjaxResponse response = new AjaxResponse();
		
		ToEloanCase property = new ToEloanCase();
		property.setEloanCode(eloanCode);
		List<ToEloanCase> eloanCaseList = toEloanCaseMapper.getToEloanCaseListByProperty(property);
		
		//查询eloanCode对应的面签总金额
		BigDecimal signAmount = new BigDecimal(0);
		if(!CollectionUtils.isEmpty(eloanCaseList)) {
			signAmount = eloanCaseList.get(0).getSignAmount();
		}
		// 查询所有审批通过的的放款总额
		Double sumReleaseAmount = toEloanRelMapper.getSumReleaseAmountByEloanCode(eloanCode);
		BigDecimal sumRelAmount = new BigDecimal(0);
		if(sumReleaseAmount!=null) {
			sumRelAmount = new BigDecimal(sumReleaseAmount);
		}
		
		
		BigDecimal sumAmountDecimal = new BigDecimal(sumAmount);
		if(sumRelAmount.add(sumAmountDecimal).doubleValue()==signAmount.doubleValue()){
			response.setMessage("请选择放款完成!");
			response.setContent(false);
		}
		else if(sumRelAmount.add(sumAmountDecimal).doubleValue()>signAmount.doubleValue()){
			response.setMessage("放款总金额不能大于面签金额!");
			response.setContent(false);
		} else {
			response.setContent(true);
		}
		return response;
	}

	@Override
	public void deleteById(Long pkid) {
		ToEloanCase eloanCase=getToEloanCaseByPkId(pkid);
		ToEloanCase newObj=new ToEloanCase();
		unbindServItem(newObj, eloanCase);
		toEloanCaseMapper.deleteByPrimaryKey(pkid);
	}
	
	@Override
	public void abanById(ToEloanCase eloanCase) {
		ToEloanCase newObj=new ToEloanCase();
		unbindServItem(newObj, eloanCase);
		eloanInfoForUpdate(eloanCase);
	}

	@Override
	public void eloanInfoForUpdate(ToEloanCase toEloanCase) {
		// TODO Auto-generated method stub
		toEloanCaseMapper.eloanInfoForUpdate(toEloanCase);
	}


}
