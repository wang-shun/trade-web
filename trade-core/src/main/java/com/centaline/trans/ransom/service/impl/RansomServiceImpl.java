package com.centaline.trans.ransom.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.RansomDiyaEnum;
import com.centaline.trans.common.enums.RansomPartEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.repository.RansomChangeMapper;
import com.centaline.trans.ransom.repository.RansomListFormMapper;
import com.centaline.trans.ransom.repository.RansomMapper;
import com.centaline.trans.ransom.service.RansomDiscontinueService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;

/**
 * 赎楼service实现
 * @author wbcaiyx
 *
 */
@Service
@Transactional
public class RansomServiceImpl implements RansomService{

	@Autowired
	private RansomMapper ransomMapper;
	@Autowired
	private RansomChangeMapper ransomChangeMapper;
	@Autowired
	private RansomListFormMapper ransomListFormMapper;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	RansomDiscontinueService ransomDiscontinueService;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Override
	public ToRansomDetailVo getRansomDetail(String ransomCode) {
		ToRansomDetailVo detailVo = ransomMapper.getRansomDetailInfoByCaseCode(ransomCode);
		
		if(detailVo != null){
			User financ = uamUserOrgService.getUserById(detailVo.getFinancial());
			if(financ !=null){
				detailVo.setFinancial(financ.getRealName());
				detailVo.setFinancialTel(financ.getMobile());
			}else{
				detailVo.setFinancial(null);
			}
		}
		return detailVo;
	}
	
	@Override
	public ToRansomDetailVo getRansomDetail(String caseCode, String ransomCode) {
		List<ToRansomDetailVo> detailVolist = ransomMapper.getRansomDetailInfoByCodes(caseCode, ransomCode);
		ToRansomDetailVo detailVo = null;
		if(detailVolist!= null && detailVolist.size() > 0) {
			detailVo = detailVolist.get(0);
		}else {
			return null;
		}
		if(detailVo != null){
			User financ = uamUserOrgService.getUserById(detailVo.getFinancial());
			if(financ !=null){
				detailVo.setFinancial(financ.getRealName());
				detailVo.setFinancialTel(financ.getMobile());
			}else{
				detailVo.setFinancial(null);
			}
//			User user = uamUserOrgService.getUserById(detailVo.getLeadingProcessId());
//			detailVo.setLeadingProcessName(user.getRealName()); //经办人
			SessionUser user = uamSessionService.getSessionUser();
			detailVo.setLeadingProcessName(user.getRealName()); //经办人
		}
		return detailVo;
	}

	@Override
	public List<ToRansomPlanVo> getPartPlanTime(String ransomCode) {
		List<ToRansomPlanVo> plans = ransomMapper.getRansomPlanByCode(ransomCode);
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		
		ToRansomPlanVo createPlan = new ToRansomPlanVo();
		createPlan.setPartName("受理");
		if(plans != null && plans.size() > 0){
			createPlan.setEstPartTimeStr(format.format(plans.get(0).getCreateTime()));
			createPlan.setCompleteTimeStr(format.format(plans.get(0).getCreateTime()));
		}
		plans.add(createPlan);
	
		for(ToRansomPlanVo plan : plans){
			String partName = RansomPartEnum.getName(plan.getPartCode());
			String table =RansomPartEnum.getTable(plan.getPartCode());
			String cloumn = RansomPartEnum.getCloumn(plan.getPartCode());
			Map<String,Date> data = ransomMapper.getCompleteTimeByCode(table, ransomCode, cloumn);
			plan.setPartName(partName);
			plan.setEstPartTimeStr(plan.getEstPartTime() == null ? null : format.format(plan.getEstPartTime()));
			plan.setCompleteTimeStr(data.get("completeTime") == null ? null : format.format(data.get("completeTime")));
		}
		return plans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StartProcessInstanceVo updateRansomIsStart(String ransomCode,String caseCode) {
		
		int count = ransomMapper.updateRansomIsStart(ransomCode);
		if(count == 0){
			throw new BusinessException("赎楼数据更新失败");
		}
		SessionUser user = uamSessionService.getSessionUser();
		//赎楼流程启动
		Map<String,Object> defValsMap = new HashMap<String,Object>();
		defValsMap.put("sessionUser", user.getUsername());
		String processDfId = propertyUtilsService.getProcessDfId("ransom_process");
		
		StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDfId, ransomCode, defValsMap);
		List<TaskVo> tasks = taskService.listTasks(pVo.getId(), false, user.getUsername()).getData();
		if (tasks != null && !tasks.isEmpty()) {
			pVo.setActiveTaskId(tasks.get(0).getId() + "");
		}
		ToWorkFlow wf = new ToWorkFlow();
		wf.setBusinessKey("ransom_process");
		wf.setCaseCode(caseCode);
		wf.setBizCode(ransomCode);
		wf.setProcessOwner(user.getId());
		wf.setProcessDefinitionId(processDfId);
		wf.setInstCode(pVo.getId());
		wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(wf);
		return pVo;
	}
	
	@Override
	public int updateRansomApply(ToRansomSubmitVo submitVo) {
		
		SessionUser user = uamSessionService.getSessionUser();
		
		submitVo.setUpdateUser(user.getId());
		ToRansomApplyVo applyVo = new ToRansomApplyVo();
		applyVo.setRansomCode(submitVo.getRansomCode());
		applyVo.setPartCode(RansomPartEnum.APPLY.getCode());
		applyVo.setApplyUser(submitVo.getBorrowName());
		applyVo.setApplyTime(submitVo.getApplyTime());
		applyVo.setApplyOrgCode(submitVo.getApplyOrgCode());
		applyVo.setLoanOfficer(submitVo.getLoanOfficer());
		applyVo.setUpdateUser(user.getId());
		applyVo.setCreateUser(user.getId());
		
		//申请数据插入
		int applyCount = ransomMapper.insertRansomApply(applyVo);

		if(applyCount ==0){
			throw new BusinessException("申请数据插入失败!");
		}
		//赎楼案件数据更新
		int count = ransomMapper.updateRansomInfo(submitVo);
		
		if(count ==0){
			throw new BusinessException("赎楼数据更新失败!");
		}
		//面签计划时间插入
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		planVo.setEstPartTime(submitVo.getPlanSignTime());
		planVo.setPartCode(RansomDiyaEnum.SING.getPart());
		//检查是否已有计划时间
		Integer isExistPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.SING.getPart());
		int signPlan = 0 ;
		if(isExistPlan != null &&isExistPlan > 0){
			signPlan = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			signPlan = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(signPlan ==0){
			throw new BusinessException("面签计划时间更新失败!");
		}
		//提交任务
		taskService.submitTask(submitVo.getTaskId());
		
		return applyCount;
	}

	
	@Override
	public int queryErdiByRansomCode(String ransomCode) {
		
		return ransomMapper.queryErdi(ransomCode);
	}
	
	@Override
	public int updateRansomSign(ToRansomSubmitVo submitVo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomSignVo signVo = new ToRansomSignVo();
		signVo.setRansomCode(submitVo.getRansomCode());
		signVo.setPartCode(RansomPartEnum.SIGN.getCode());
		signVo.setSignTime(submitVo.getSignTime());
		signVo.setSignMoney(submitVo.getSignMoney() !=null ? submitVo.getSignMoney().multiply(new BigDecimal(10000)):null);
		signVo.setInterest(submitVo.getInterest());
		signVo.setIsEntrust(submitVo.getIsEntrust());
		signVo.setUpdateUser(user.getId());
		signVo.setCreateUser(user.getId());
		int result = ransomMapper.insertRansomSign(signVo);
		
		if(result ==0){
			throw new BusinessException("面签数据插入失败!");
		}
		//插入陪同还贷计划时间
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		planVo.setEstPartTime(submitVo.getPlanMortgageTime());
		planVo.setPartCode(RansomDiyaEnum.PAYLOAN_ONE.getPart());
		
		//检查是否已有计划时间
		Integer mort1Plan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.PAYLOAN_ONE.getPart());
		int mort1 = 0 ;
		if(mort1Plan != null && mort1Plan > 0){
			mort1 = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			mort1 = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(mort1 ==0){
			throw new BusinessException("陪同还贷计划时间更新失败!");
		}
		
		//检查是否有二抵
		int count =  ransomMapper.queryErdi(submitVo.getRansomCode());
		if(count>0){
			planVo.setPartCode(RansomDiyaEnum.PAYLOAN_TWO.getPart());
			
			Integer mort2Plan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), RansomDiyaEnum.PAYLOAN_TWO.getPart());
			int mort2 = 0;
			if(mort2Plan != null && mort2Plan >0){
				mort2 = ransomMapper.updateRansomPlanTime(planVo);
			}else{
				mort2 = ransomMapper.insertRansomPlanTime(planVo);
			}
			if(mort2 ==0){
				throw new BusinessException("陪同还贷计划时间更新失败!");
			}
		}

		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		if(count >0){
			variables.add(new RestVariable("erdi", true));
		}else{
			variables.add(new RestVariable("erdi", false));
		}
		workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
		
		
		return result;
	}

	
	@Override
	public int updateRansomMortgage(ToRansomSubmitVo submitVo) {
		SessionUser user = uamSessionService.getSessionUser();
		ToRansomMortgageVo mortVo = new ToRansomMortgageVo();
		mortVo.setRansomCode(submitVo.getRansomCode());
		mortVo.setMortgageTime(submitVo.getMortgageTime());
		mortVo.setRepayLoanMoney(submitVo.getRepayLoanMoney()==null?null:submitVo.getRepayLoanMoney().multiply(new BigDecimal(10000)));
		mortVo.setDiyaType(submitVo.getDiyaType().toString());
		mortVo.setUpdateUser(user.getId());
		mortVo.setCreateUser(user.getId());
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			mortVo.setPartCode(RansomDiyaEnum.PAYLOAN_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			mortVo.setPartCode(RansomDiyaEnum.PAYLOAN_TWO.getPart());
		}
		int result = ransomMapper.insertRansomMortgage(mortVo);
		if(result ==0){
			throw new BusinessException("陪同还贷数据插入失败!");
		}
		
		//时间计划
		ToRansomPlanVo planVo = new ToRansomPlanVo();
		planVo.setRansomCode(submitVo.getRansomCode());
		planVo.setCreateUser(user.getId());
		planVo.setUpdateUser(user.getId());
		//注销抵押计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.CANCELDIYA_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.CANCELDIYA_TWO.getPart());
		}
		planVo.setEstPartTime(submitVo.getPlanCancelTime());
		
		Integer cancelPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
		int cancel = 0;
		if(cancelPlan != null && cancelPlan > 0){
			cancel = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			cancel = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(cancel ==0){
			throw new BusinessException("注销抵押计划时间更新失败!");
		}
		
		//领取产证计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.RECEIVE_ONE.getPart());
		}else if(RansomDiyaEnum.PAYLOAN_TWO.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.RECEIVE_TWO.getPart());
		}
		planVo.setEstPartTime(submitVo.getPlanPermitTime());
		Integer receivePlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
		int receive = 0;
		if(receivePlan != null && receivePlan > 0){
			receive = ransomMapper.updateRansomPlanTime(planVo);
		}else{
			receive = ransomMapper.insertRansomPlanTime(planVo);
		}
		if(receive ==0){
			throw new BusinessException("领取产证计划时间更新失败!");
		}
		
		//回款结清计划时间
		if(RansomDiyaEnum.PAYLOAN_ONE.getCode().equals(submitVo.getDiyaType().toString())){
			planVo.setPartCode(RansomDiyaEnum.PAY_CLEAR.getPart());
			planVo.setEstPartTime(submitVo.getPlanPaymentTime());
			
			Integer clearPlan = ransomMapper.findRansomPartPlanTime(submitVo.getRansomCode(), planVo.getPartCode());
			int clear = 0;
			if(clearPlan != null &&clearPlan > 0){
				clear = ransomMapper.updateRansomPlanTime(planVo);
			}else{
				clear = ransomMapper.insertRansomPlanTime(planVo);
			}

			if(clear ==0){
				throw new BusinessException("回款结清计划时间更新失败!");
			}
		}
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
		
		return result;
	}
	
	@Override
	public int updateRansomCancel(ToRansomSubmitVo submitVo) {
		
		SessionUser user = uamSessionService.getSessionUser();
		Integer diyaType = submitVo.getDiyaType();
		ToRansomCancelVo cancelVo = new ToRansomCancelVo();
		cancelVo.setRansomCode(submitVo.getRansomCode());
		cancelVo.setCancelTime(submitVo.getCancelDiyaTime());
		cancelVo.setDiyaType(diyaType.toString());
		cancelVo.setUpdateUser(user.getId());
		cancelVo.setCreateUser(user.getId());
		//判断一抵/二抵
		if(RansomDiyaEnum.CANCELDIYA_ONE.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_ONE.getPart());	
		}else if(RansomDiyaEnum.CANCELDIYA_TWO.getCode().equals(diyaType.toString())){
			cancelVo.setPartCode(RansomDiyaEnum.CANCELDIYA_TWO.getPart());
		}
		int result = ransomMapper.insertRansomCancel(cancelVo);
		if(result ==0){
			throw new BusinessException("注销抵押数据插入失败!");
		}
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
		
		return result;
	}

	@Override
	public int updateRansomPermit(ToRansomSubmitVo submitVo) {
		Integer diyaType = submitVo.getDiyaType();
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomPermitVo permitVo = new ToRansomPermitVo();
		permitVo.setRansomCode(submitVo.getRansomCode());
		permitVo.setRedeemTime(submitVo.getPermitTime());
		permitVo.setDiyaType(diyaType.toString());
		permitVo.setUpdateUser(user.getId());
		permitVo.setCreateUser(user.getId());
		//判断一抵/二抵
		if(RansomDiyaEnum.RECEIVE_ONE.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_ONE.getPart());	
		}else if(RansomDiyaEnum.RECEIVE_TWO.getCode().equals(diyaType.toString())){
			permitVo.setPartCode(RansomDiyaEnum.RECEIVE_TWO.getPart());
		}
		int result = ransomMapper.insertRansomPermit(permitVo);
		if(result ==0){
			throw new BusinessException("领取产证数据插入失败!");
		}
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();	
		workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
		
		return result;
	}

	@Override
	public int updateRansomPayment(ToRansomSubmitVo submitVo) {
		
		SessionUser user = uamSessionService.getSessionUser();
		
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		paymentVo.setRansomCode(submitVo.getRansomCode());
		paymentVo.setPaymentTime(submitVo.getPaymentTime());
		paymentVo.setUpdateUser(user.getId());
		paymentVo.setPartCode(RansomPartEnum.PAYCLEAR.getCode());
		paymentVo.setCreateUser(user.getId());
		int result = ransomMapper.insertRansomPayment(paymentVo);
		if(result ==0){
			throw new BusinessException("回款结清数据插入失败!");
		}		
		//完结赎楼单
		int count = ransomMapper.updateCaseStatusComplete(submitVo.getRansomCode());
		if(count ==0){
			throw new BusinessException("数据更新失败!");
		}	
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		workFlowManager.submitTask(variables, submitVo.getTaskId(), submitVo.getProcessInstanceId(), user.getId(), submitVo.getCaseCode());		
		
		//flow完结
		ToWorkFlow flow=new ToWorkFlow();
		flow.setBusinessKey("ransom_process");
		flow.setCaseCode(submitVo.getCaseCode());
		flow.setBizCode(submitVo.getRansomCode());
		ToWorkFlow ransomFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
		ransomFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(ransomFlow);
		
		return result;
	}

	@Override
	public List<ToRansomTailinsVo> getTailinsInfoByCaseCode(String caseCode) {
		List<ToRansomTailinsVo> tailinsVo = ransomMapper.getTailinsInfoByCaseCode(caseCode);
		return tailinsVo;
	}

	@Override
	public List<ToRansomTailinsVo> getTailinsInfoByRansomCode(String caseCode) {
		List<ToRansomTailinsVo> tailinsVo = ransomMapper.getTailinsInfoByRansomCode(caseCode);
		return tailinsVo;
	}

	@Override
	public ToRansomSignVo getInterviewInfo(String ransomCode) {
		ToRansomSignVo signVo = new ToRansomSignVo();
		signVo = ransomMapper.getInterviewInfoByRansomCode(ransomCode);
		return signVo;
	}

	@Override
	public ToRansomApplyVo getApplyInfo(String ransomCode) {
		ToRansomApplyVo applyVo = new ToRansomApplyVo();
		applyVo = ransomMapper.getApplyInfoByRansomCode(ransomCode);
		return applyVo;
	}

	@Override
	public Map<String,Date> getMortgageInfoByRansomCode(String ransomCode) {
		List<ToRansomMortgageVo> mortgageVoList = ransomMapper.getMortgageInfoByRansomCode(ransomCode);
		
		Map<String,Date> mortgageMap = new HashMap<String, Date>();
		for (ToRansomMortgageVo mortgageVo : mortgageVoList) {
			mortgageMap.put(mortgageVo.getPartCode(), mortgageVo.getMortgageTime());
		}
		
		return mortgageMap;
	}
	
	@Override
	public ToRansomMortgageVo getMortgageInfo(String ransomCode,Integer isEr) {
		ToRansomMortgageVo mortgageVo = new ToRansomMortgageVo();
		mortgageVo = ransomMapper.getMortgageInfo(ransomCode,isEr);
		return mortgageVo;
	}

	@Override
	public Map<String,Date> getCancelInfo(String ransomCode) {
		List<ToRansomCancelVo> cancelList = ransomMapper.getCancelInfoByRansomCode(ransomCode);
		
		Map<String,Date> cancelMap = new HashMap<String, Date>();
		for (ToRansomCancelVo cancelVo : cancelList) {
			cancelMap.put(cancelVo.getPartCode(), cancelVo.getCancelTime());
		}
		return cancelMap;
	}

	@Override
	public Map<String,Date> getPermitInfo(String ransomCode) {
		
		List<ToRansomPermitVo> permitList = ransomMapper.getPermitInfoByRansomCode(ransomCode);
		
		Map<String,Date> permitMap = new HashMap<String, Date>();
		for (ToRansomPermitVo permitVo : permitList) {
			permitMap.put(permitVo.getPartCode(), permitVo.getRedeemTime());
		}
		
		return permitMap;
	}

	@Override
	public ToRansomPaymentVo getPaymentInfo(String ransomCode) {
		ToRansomPaymentVo paymentVo = new ToRansomPaymentVo();
		paymentVo = ransomMapper.getPaymentInfoByRansomCode(ransomCode);
		return paymentVo;
	}


	@Override
	public ToRansomCaseVo getRansomInfoByRansomCode(String ransomCode) {
		ToRansomCaseVo caseVo = new ToRansomCaseVo();
		caseVo = ransomMapper.getRansomCaseInfoByRansomCode(ransomCode);
		return caseVo;
	}

	@Override
	public ToRansomLinkVo getRansomLinkInfo(String caseCode) {
		ToRansomLinkVo ransomLinkVo = new ToRansomLinkVo();
		ransomLinkVo = ransomListFormMapper.getRansomLinkInfoByCaseCode(caseCode);
		return ransomLinkVo;
	}

	@Override
	public boolean deleteRansomApplyByRansomCode(String ransomCode) {
		if(StringUtils.isNotBlank(ransomCode)) {
			return ransomMapper.deleteRansomApplyByRansomCode(ransomCode);
		}
		return false;
	}

	@Override
	public List<ToRansomPlanVo> getPlanTimeInfoByRansomCode(String ransomCode) {
		return ransomMapper.getPlanTimeInfoByRansomCode(ransomCode);
	}
	
	@Override
	public List<ToRansomPlanVo> getPlanTimeInfo(String ransomCode) {
		return ransomChangeMapper.getPlanTimeInfo(ransomCode);
	}

	/**
	 * 步骤:
	 * ①变更赎楼单责任人
	 * 	1、T_RA_CASE表CREATE_USER字段
	 *  2、T_RA_TAILINGS表CREATE_USER字段(可能有多条数据)
	 * ②如果流程存在则变更流程责任人(workFlowManager.setVariableByProcessInsId(processInstanceId, variableName, restVariable)方法)
	 * 	1、ACT_RU_TASK表ASIGNEE_字段
	 * 	2、T_TO_WORKFLOW表PROCESS_OWNER字段(可选)
	 */
	@Override
	public boolean changeRansomOwner(Map<String, Object> paramObj, String changeToUserId, String caseCode, String ransomCode) {
		SessionUser sessionUser = uamSessionService.getSessionUser();
		Date date = new Date();
		ToRansomTailinsVo tailVo = new ToRansomTailinsVo();
		tailVo.setRansomCode(ransomCode);
		tailVo.setCreateUser(changeToUserId);
		tailVo.setCreateTime(date);
		tailVo.setUpdateUser(sessionUser.getId());
		tailVo.setUpdateTime(date);
		ToRansomCaseVo caseVo = new ToRansomCaseVo();
		caseVo.setRansomCode(ransomCode);
		caseVo.setCreateUser(changeToUserId);
		caseVo.setCreateTime(date);
		caseVo.setUpdateUser(sessionUser.getId());
		caseVo.setUpdateTime(date);
		Map<String, Object> taskInfo = ransomDiscontinueService.getSingleRansomTaskInfo(paramObj, false, null, false, caseCode);
		if((boolean)taskInfo.get("hasData")) {
			TaskVo task = new TaskVo();
			task.setId(Long.valueOf(String.valueOf(taskInfo.get("ID"))));
			task.setAssignee(uamSessionService.getSessionUserById(changeToUserId).getUsername());
			RestVariable restVariable = new RestVariable();
			restVariable.setType("string");
			restVariable.setValue(uamSessionService.getSessionUserById(changeToUserId).getUsername());
			workFlowManager.updateTask(task);
			workFlowManager.setVariableByProcessInsId(String.valueOf(taskInfo.get("INST_CODE")), "sessionUser", restVariable);
		}
		ransomListFormMapper.updateRansomTailUserByRansomCode(tailVo);
		ransomListFormMapper.updateRansomCaseUserByRansomCode(caseVo);
		return true;
	}

	@Override
	public Map<String,String> getActTasks(String ransomCode) {
		List<String> tasks = ransomListFormMapper.getRansomActTasks(ransomCode);
		Map<String,String> map = new HashMap<String,String>();
		for(String str:tasks){
			map.put(str, str);
		}
		return map;
	}
	
}
