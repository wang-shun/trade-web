package com.centaline.trans.eloan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.repository.ToEloanCaseMapper;
import com.centaline.trans.eloan.repository.ToEloanRelMapper;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.vo.ELoanVo;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
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
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private ToCaseCommentService toCaseCommentService;
	@Autowired
	private WorkFlowManager workFlowManager;

	@Override
	public void saveEloanApply(SessionUser user, ToEloanCase tEloanCase) {
		// 保存申请数据
		// 产品类型、案件绑定、合作机构、客户姓名、客户电话、申请金额、申请时间、申请期数
		// 转介人姓名、转介人员工编号、合作人姓名、合作人员工编号、产品部合作人、分成比例贷款。
		// E+编号

		// 如果当前用户和提交用户是同一个人，用户部门就用当前session部门 deptype-- yes:同一个用户 no:不同用户
		String depType = "no";
		if (user != null && tEloanCase != null
				&& user.getId().equals(tEloanCase.getExcutorId())) {
			depType = "yes";
		}
		// 申请人信息
		User excutor = uamUserOrgService.getUserById(tEloanCase.getExcutorId());
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(
				excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		if (excutor != null) {
			// 如果当前用户和提交用户是同一个人，用户部门就用当前session部门 deptype-- yes:同一个用户 no:不同用户
			if (depType.equals("yes")) {
				tEloanCase.setExcutorTeam(user.getServiceCompanyId());
				depType = "no";
			} else {
				tEloanCase.setExcutorTeam(excutor.getOrgId());
			}
		}
		if (districtOrg != null) {
			tEloanCase.setExcutorDistrict(districtOrg.getId());
		}
		bindServItem(tEloanCase);

		toEloanCaseMapper.insertSelective(tEloanCase);

		// start
		User manager = new User();
		if (excutor.getId().equals(user.getId())) {
			manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(
					user.getServiceDepId(), "Manager");
		} else {
			manager = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(
					excutor.getOrgId(), "Manager");
		}
		// end

		Map<String, Object> vars = new HashMap<String, Object>();
		// vars.put("Consultant", excutor.getUsername());
		vars.put("Consultant", user.getUsername());
		// 当前申请人是否是主管
		String jobCode = user.getServiceJobCode();
		if ("Manager".equals(jobCode)) {
			vars.put("Manager", user.getUsername());
		} else {
			vars.put("Manager", manager == null ? null : manager.getUsername());
		}
		// 信贷员
		vars.put("Loaner", tEloanCase.getLoanerUserName());

		/*
		 * ToEloanCase eloanCase=toEloanCaseMapper.selectByPrimaryKey((long)
		 * pkid);
		 */
		String demo = propertyUtilsService.getProcessEloanDfKey();
		StartProcessInstanceVo processInstance = processInstanceService
				.startWorkFlowByDfId(
						propertyUtilsService.getProcessEloanDfKey(),
						tEloanCase.getEloanCode(), vars);
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(tEloanCase.getCaseCode());
		workFlow.setBizCode(tEloanCase.getEloanCode());
		workFlow.setBusinessKey(WorkFlowEnum.ELOAN_BUSSKEY.getCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(processInstance
				.getProcessDefinitionId());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus("0");
		toWorkFlowService.insertSelective(workFlow);

		PageableVo pageableVo = taskService.listTasks(processInstance.getId(),
				false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			taskService.complete(task.getId() + "");
		}

	}

	private void bindServItem(ToEloanCase tEloanCase) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if (!StringUtils.isBlank(tEloanCase.getCaseCode())) {
			p.setCaseCode(tEloanCase.getCaseCode());
			p.setSrvCat(tEloanCase.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper
					.findTgServItemAndProcessor(p);
			boolean isNew = false;
			if (ts == null) {
				ts = p;
				isNew = true;
			}

			String caseCode = tEloanCase.getCaseCode();
			ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
			// User oldUser =
			// uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			SessionUser user = uamSessionService.getSessionUser();
			ts.setSrvCat(tEloanCase.getLoanSrvCode());
			ts.setSrvCode(tEloanCase.getLoanSrvCode() + "01");
			ts.setPreProcessorId(user.getId());
			ts.setPreOrgId(user.getServiceDepId());
			ts.setProcessorId(tEloanCase.getExcutorId());
			ts.setOrgId(tEloanCase.getExcutorTeam());
			if (isNew) {
				servItemMapper.insertSelective(ts);
			} else {
				servItemMapper.updateByPrimaryKeySelective(ts);
			}
		}
	}

	private void unbindServItem(ToEloanCase newObj, ToEloanCase obj) {
		TgServItemAndProcessor p = new TgServItemAndProcessor();
		if (!StringUtils.isBlank(obj.getCaseCode())
				&& obj.getLoanSrvCode() != null
				&& !obj.getLoanSrvCode().equals(newObj.getLoanSrvCode())) {
			p.setCaseCode(obj.getCaseCode());
			p.setSrvCat(obj.getLoanSrvCode());
			TgServItemAndProcessor ts = servItemMapper
					.findTgServItemAndProcessor(p);
			if (ts != null) {
				servItemMapper.deleteByPrimaryKey(ts.getPkid());// confirmed by
																// xiacheng
			}
		}
	}

	@Override
	public int updateEloanApply(SessionUser user, ToEloanCase tEloanCase) {
		User excutor = uamUserOrgService.getUserById(tEloanCase.getExcutorId());
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(
				excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		if (excutor != null) {
			tEloanCase.setExcutorTeam(excutor.getOrgId());
		}
		if (districtOrg != null) {
			tEloanCase.setExcutorDistrict(districtOrg.getId());
		}

		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setStatus("0");
		workFlow.setCaseCode(tEloanCase.getCaseCode());
		workFlow.setInstCode(tEloanCase.getProcessInstanceId());
		toWorkFlowService.updateWorkFlowByInstCode(workFlow);

		Map<String, Object> vars = new HashMap<String, Object>();
		// 交易顾问重新选择信贷员
		vars.put("Loaner", tEloanCase.getLoanerUserName());
		taskService.complete(tEloanCase.getTaskId(), vars);

		ToEloanCase property = new ToEloanCase();
		property.setEloanCode(tEloanCase.getEloanCode());
		List<ToEloanCase> eloanCaseList = toEloanCaseMapper
				.getToEloanCaseListByProperty(property);
		if (!CollectionUtils.isEmpty(eloanCaseList)) {
			unbindServItem(tEloanCase, eloanCaseList.get(0));
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
	@TaskOperate(submitVal = "map")
	public void eloanProcessConfirm(String taskId, Map<String, Object> map,
			ToEloanCase toEloanCase, boolean isUpdate) {
		if (isUpdate) {
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
	public AjaxResponse<Boolean> validateIsFinishRelease(String eloanCode,
			Double sumAmount) {
		AjaxResponse response = new AjaxResponse();

		ToEloanCase property = new ToEloanCase();
		property.setEloanCode(eloanCode);
		List<ToEloanCase> eloanCaseList = toEloanCaseMapper
				.getToEloanCaseListByProperty(property);

		// 查询eloanCode对应的面签总金额
		BigDecimal signAmount = new BigDecimal(0);
		if (!CollectionUtils.isEmpty(eloanCaseList)) {
			signAmount = eloanCaseList.get(0).getSignAmount();
		}
		// 查询所有审批通过的的放款总额
		Double sumReleaseAmount = toEloanRelMapper
				.getSumReleaseAmountByEloanCode(eloanCode);
		BigDecimal sumRelAmount = new BigDecimal(0);
		if (sumReleaseAmount != null) {
			sumRelAmount = new BigDecimal(sumReleaseAmount);
		}

		BigDecimal sumAmountDecimal = new BigDecimal(sumAmount);
		if (sumRelAmount.add(sumAmountDecimal).doubleValue() == signAmount
				.doubleValue()) {
			response.setMessage("请选择放款完成!");
			response.setContent(false);
		} else if (sumRelAmount.add(sumAmountDecimal).doubleValue() > signAmount
				.doubleValue()) {
			response.setMessage("放款总金额不能大于面签金额!");
			response.setContent(false);
		} else {
			response.setContent(true);
		}
		return response;
	}

	@Override
	public void deleteById(Long pkid) {
		ToEloanCase eloanCase = getToEloanCaseByPkId(pkid);
		ToEloanCase newObj = new ToEloanCase();
		unbindServItem(newObj, eloanCase);
		toEloanCaseMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public void abanById(ToEloanCase eloanCase) {
		ToEloanCase newObj = new ToEloanCase();
		unbindServItem(newObj, eloanCase);
		eloanInfoForUpdate(eloanCase);
	}

	@Override
	public void eloanInfoForUpdate(ToEloanCase toEloanCase) {
		// TODO Auto-generated method stub
		toEloanCaseMapper.eloanInfoForUpdate(toEloanCase);
	}

	@Override
	public ToEloanCase selectByEloanCode(String eloanCode) {
		return toEloanCaseMapper.selectByEloanCode(eloanCode);
	}

	@Override
	@TaskOperate(submitVal = "map")
	public boolean accept(ELoanVo eLoanVo, Map<String, Object> map,
			String taskId) {
		ToEloanCase toEloanCase = new ToEloanCase();
		toEloanCase.setEloanCode(eLoanVo.geteLoanCode());
		toEloanCase.setStateInBank(eLoanVo.getStateInBank());

		if ("true".equals(eLoanVo.getIsPass())) {
			if (eLoanVo.getUser() != null)
				toEloanCase.setLoanerId(eLoanVo.getUser().getId());

			toEloanCase.setLoanerConfTime(new Date());
		}

		// 设置案件跟进信息
		ToCaseComment toCaseComment = setToCaseComment(eLoanVo.getUser(),
				eLoanVo.getCaseCode(), eLoanVo.geteLoanCode(),
				eLoanVo.getStateInBank(), eLoanVo.getComment());

		// 保存案件跟进信息
		toCaseCommentService.insertToCaseComment(toCaseComment);

		// 更新E+案件信息
		toEloanCaseMapper.updateEloanCaseByEloanCode(toEloanCase);

		return true;
	}

	@Override
	public boolean followUp(ELoanVo eLoanVo) {

		// 设置案件跟进信息
		ToCaseComment toCaseComment = setToCaseComment(eLoanVo.getUser(),
				eLoanVo.getCaseCode(), eLoanVo.geteLoanCode(),
				eLoanVo.getStateInBank(), eLoanVo.getComment());

		// 保存案件跟进信息
		toCaseCommentService.insertToCaseComment(toCaseComment);

		ToEloanCase toEloanCase = new ToEloanCase();
		toEloanCase.setEloanCode(eLoanVo.geteLoanCode());
		toEloanCase.setStateInBank(eLoanVo.getStateInBank());

		// 更新E+案件信息
		toEloanCaseMapper.updateEloanCaseByEloanCode(toEloanCase);

		return true;
	}

	/**
	 * 设置E+贷款案件跟进信息
	 * 
	 * @param user
	 *            用户信息
	 * @param caseCode
	 *            案件编号
	 * @param srvCode
	 *            环节编码
	 * @param comment
	 *            跟进跟进内容
	 * @return 返回案件跟进信息
	 */
	private ToCaseComment setToCaseComment(SessionUser user, String caseCode,
			String eLoanCode, String srvCode, String comment) {
		// 添加案件跟进信息
		ToCaseComment toCaseComment = new ToCaseComment();
		toCaseComment.setCaseCode(caseCode);
		toCaseComment.setBizCode(eLoanCode);
		toCaseComment.setType("TRACK");
		toCaseComment.setSource("EPLUS");
		toCaseComment.setSrvCode(srvCode);
		toCaseComment.setComment(comment);
		toCaseComment.setCreateTime(new Date());

		if (user != null) {
			toCaseComment.setCreateBy(user.getId());
			toCaseComment.setCreatorOrgId(user.getServiceDepId());
		}

		return toCaseComment;
	}
	
	public int selectBackKaCountByTime(int endWeekDay){
		return toEloanCaseMapper.selectBackKaCountByTime(endWeekDay);
	}

	public int selectBackAppCountByTime(int endWeekDay){
		return toEloanCaseMapper.selectBackAppCountByTime(endWeekDay);
	}

	@Override
	public void batchChangeOwner(String[] eloanCodeList, String newConsultantId, String newManagerId) {
		if(eloanCodeList != null && eloanCodeList.length > 0){
			for(String spvCode:eloanCodeList){
				changeOwner(spvCode, newConsultantId, newManagerId);
			}
		}
	}
	
	@Override
	public void changeOwner(String eloanCode, String newConsultantId, String newManagerId) {
		//相关人员
		SessionUser newConsultant = uamSessionService.getSessionUserById(newConsultantId);
		SessionUser newManager = uamSessionService.getSessionUserById(newManagerId);
		SessionUser user = uamSessionService.getSessionUser();
		//1.更新t_to_eloan_case表中的excutor_id字段
		ToEloanCase eloanCase = toEloanCaseMapper.selectByEloanCode(eloanCode);
		String depType = "no";
		if (user != null && eloanCase != null
				&& user.getId().equals(newConsultantId)) {
			depType = "yes";
		}
		User excutor = uamUserOrgService.getUserById(newConsultantId);
		Org districtOrg = uamUserOrgService.getParentOrgByDepHierarchy(
				excutor.getOrgId(), DepTypeEnum.TYCQY.getCode());
		
		eloanCase.setExcutorId(newConsultantId);
		if (excutor != null) {
			// 如果当前用户和提交用户是同一个人，用户部门就用当前session部门 deptype-- yes:同一个用户 no:不同用户
			if (depType.equals("yes")) {
				eloanCase.setExcutorTeam(user.getServiceCompanyId());
				depType = "no";
			} else {
				eloanCase.setExcutorTeam(excutor.getOrgId());
			}
		}
		if (districtOrg != null) {
			eloanCase.setExcutorDistrict(districtOrg.getId());
		}

		toEloanCaseMapper.updateByPrimaryKey(eloanCase);
		
		//1.查询流程
		ToWorkFlow record = new ToWorkFlow();
		record.setBizCode(eloanCode);
		record.setBusinessKey(WorkFlowEnum.ELOAN_BUSSKEY.getCode());
		ToWorkFlow workFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(workFlow == null){
			throw new BusinessException("找不到E+申请流程！");
		}
		
		String instCode = workFlow.getInstCode();
		//2.更新流程变量+已生成待办任务
		String oldConsultant = (String) workFlowManager.getVar(instCode, "Consultant").getValue();
		String oldManager = (String) workFlowManager.getVar(instCode, "Manager").getValue();
		
		workFlowManager.setVariableByProcessInsId(instCode, "Consultant", new RestVariable("Consultant",newConsultant.getUsername()));
		workFlowManager.setVariableByProcessInsId(instCode, "Manager", new RestVariable("Manager",newManager.getUsername()));
		PageableVo pageableVo = taskService.listTasks(instCode, false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if (oldConsultant.equals(task.getAssignee())) {
				taskService.updateAssignee(task.getId().toString(), newConsultant.getUsername());
			}else if(oldManager.equals(task.getAssignee())){
				taskService.updateAssignee(task.getId().toString(), newManager.getUsername());
			}
		}
	}

	@Override
	public List<String> selectConsAndManager(Long pkId) {
		ToEloanCase toEloanCase = toEloanCaseMapper.selectByPrimaryKey(pkId);
		//1.查询流程
		ToWorkFlow record = new ToWorkFlow();
		record.setBizCode(toEloanCase.getEloanCode());
		record.setBusinessKey(WorkFlowEnum.ELOAN_BUSSKEY.getCode());
		ToWorkFlow workFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(workFlow == null){
			throw new BusinessException("找不到E+申请流程！");
		}
		
		String consultantUserName = (String) workFlowManager.getVar(workFlow.getInstCode(), "Consultant").getValue();
		String managerUserName = (String) workFlowManager.getVar(workFlow.getInstCode(), "Manager").getValue();
		
		List<UserOrgJob> consultants = uamUserOrgService.findUserOrgJobByUsername(consultantUserName);
		SessionUser consultant = uamSessionService.getSessionUserById(consultants.get(0).getUserId());
		List<UserOrgJob> managers = uamUserOrgService.findUserOrgJobByUsername(managerUserName);
		SessionUser manager = uamSessionService.getSessionUserById(managers.get(0).getUserId());
		
		List<String> mixUserList = new ArrayList<String>();
		mixUserList.add(consultant.getId()+","+consultant.getServiceDepId()+","+consultant.getRealName());
		mixUserList.add(manager.getId()+","+manager.getServiceDepId()+","+manager.getRealName());
		
		return mixUserList;
	}
}
