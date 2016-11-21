package com.centaline.trans.spv.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvCloseApply;
import com.centaline.trans.spv.entity.ToSpvCloseApplyAudit;
import com.centaline.trans.spv.repository.ToSpvCloseApplyAuditMapper;
import com.centaline.trans.spv.repository.ToSpvCloseApplyMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.SpvCloseApplyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCloseInfoVO;

@Service
public class SpvCloseApplyServiceImpl implements SpvCloseApplyService {
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private ToSpvService toSpvService;
	
	@Autowired
	private ToSpvCloseApplyMapper ToSpvCloseApplyMapper;
	@Autowired
	private ToSpvCloseApplyAuditMapper toSpvCloseApplyAuditMapper;
	@Autowired
	private ToSpvMapper toSpvMapper;
	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	/**
	 * 中止/结束起始提交页面
	 */
	@Override
	public void spvCloseApplyPage(HttpServletRequest request, String spvCode, String businessKey) {
		setRequestAttribute(request,spvCode,businessKey);
	}

	/**
	 * 中止/结束驳回提交页面
	 */
	@Override
	public void spvCloseApplyProcess(HttpServletRequest request, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}

	/**
	 * 中止/结束主办审批页面
	 */
	@Override
	public void spvCloseManagerAuditProcess(HttpServletRequest request, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}

	/**
	 * 中止/结束总监审批页面
	 */
	@Override
	public void spvCloseDirectorAuditProcess(HttpServletRequest request, String businessKey) {
		setRequestAttribute(request,null,businessKey);
	}	
	
	/**
	 * 中止/结束起始提交操作
	 */
	@Override
	public void spvCloseApplyPageDeal(HttpServletRequest request, String spvCode, SpvCloseInfoVO spvCloseInfoVO, String businessKey) {
		// 资金监管出入账申请无在途申请的时候才可以开启此流程
		if(StringUtils.isNotBlank(businessKey)){
			ToWorkFlow record = new ToWorkFlow();
			record.setBizCode(businessKey);
			record.setBusinessKey("SpvCashflowInProcess");
			ToWorkFlow toWorkFlow1 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
			if(toWorkFlow1 != null){
				throw new BusinessException("尚有‘入款’流程进行中，不能开启‘中止/结束’流程！");
			}
			record.setBusinessKey("SPVCashflowOutProcess");
			ToWorkFlow toWorkFlow2 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
			if(toWorkFlow2 != null){
				throw new BusinessException("尚有‘出款’流程进行中，不能开启‘中止/结束’流程！");
			}
			record.setBusinessKey("spvCloseApplyProcess");
			ToWorkFlow toWorkFlow3 = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
			if(toWorkFlow3 != null){
				throw new BusinessException("‘中止/结束’流程已经存在，不能重复开启！");
			}
		}
		// 开启流程
		SessionUser user = uamSessionService.getSessionUser();
		
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("applier", user.getUsername());
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("manager", user.getUsername());
		vars.put("director", user.getUsername());
		
		String spvCloseCode = createSpvCloseCode();
		
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		apply.setSpvCloseCode(spvCloseCode);
		apply.setApplier(user.getId());
		apply.setAuditor(user.getId());
		apply.setReAuditor(user.getId());
		apply.setStatus("0");
		apply.setCreateBy(user.getId());
		apply.setCloseType("中止/结束");
		apply.setApplyTime(new Date());

		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), spvCloseCode, vars);
		
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCloseInfoVO.getToSpvCloseApply().getSpvCloseCode());
		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("spvCloseApplyProcess");
		workFlow.setCaseCode(toSpv.getCaseCode());
		workFlow.setBizCode(spvCloseCode);
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSPVCashflowOutProcessDfKey());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
	}

	/**
	 * 中止/结束驳回提交操作
	 */
	@Override
	public void spvCloseApplyDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey) {
		// 提交流程
		
	}

	/**
	 * 中止/结束主办审批操作
	 */
	@Override
	public void spvCloseManagerAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey) {
		// 提交流程
		
	}

	/**
	 * 中止/结束总监审批操作
	 */
	@Override
	public void spvCloseDirectorAuditDeal(HttpServletRequest request, SpvCloseInfoVO spvCloseInfoVO, String businessKey) {
		// 提交流程
		
	}
	
	private void setRequestAttribute(HttpServletRequest request, String spvCode_, String businessKey){
		String spvCode = null;
		SpvCloseInfoVO spvCloseInfoVO = findSpvCloseInfoVOBySpvCode(businessKey);
        if(StringUtils.isBlank(businessKey)){
        	spvCode = spvCode_;
        }else{
        	spvCode = spvCloseInfoVO.getToSpvCloseApply().getSpvCode();
        }
		ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
		SpvBaseInfoVO spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv == null?null:toSpv.getPkid());

		request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);
		request.setAttribute("spvCloseInfoVO", spvCloseInfoVO);
	}
	
	private SpvCloseInfoVO findSpvCloseInfoVOBySpvCode(String spvCloseApplyCode){
		SpvCloseInfoVO spvCloseInfoVO = new SpvCloseInfoVO();
		ToSpvCloseApply toSpvCloseApply = ToSpvCloseApplyMapper.selectBySpvCloseApplyCode(spvCloseApplyCode);
		List<ToSpvCloseApplyAudit> toSpvCloseApplyAuditList = toSpvCloseApplyAuditMapper.selectBySpvCloseApplyCode(spvCloseApplyCode);
		spvCloseInfoVO.setToSpvCloseApply(toSpvCloseApply);
		spvCloseInfoVO.setToSpvCloseApplyAuditList(toSpvCloseApplyAuditList);
		return spvCloseInfoVO;
	}
	
	private void saveSpvCloseInfoVO(SpvCloseInfoVO spvCloseInfoVO){
		SessionUser user = uamSessionService.getSessionUser();
		
		if(spvCloseInfoVO == null) return;
		
		//申请
		ToSpvCloseApply apply = spvCloseInfoVO.getToSpvCloseApply();
		if(apply == null) return;
		
		if(apply.getPkid() != null){
			apply.setUpdateBy(user.getId());
			apply.setUpdateTime(new Date());
			ToSpvCloseApplyMapper.updateByPrimaryKeySelective(apply);
		}else{;
			apply.setCreateBy(user.getId());
			apply.setCreateTime(new Date());
			ToSpvCloseApplyMapper.insertSelective(apply);
		}
			
		//审批意见
		List<ToSpvCloseApplyAudit> audits = spvCloseInfoVO.getToSpvCloseApplyAuditList();
		if(audits != null && !audits.isEmpty()){
			for(ToSpvCloseApplyAudit audit : audits){
				if(audit != null){
					if(audit.getPkid() != null){
						audit.setUpdateBy(user.getId());
						audit.setUpdateTime(new Date());
						toSpvCloseApplyAuditMapper.updateByPrimaryKeySelective(audit);
					}else{
						audit.setApplyId(apply.getPkid().toString());
						audit.setCreateBy(user.getId());
						audit.setCreateTime(new Date());
						toSpvCloseApplyAuditMapper.insertSelective(audit);
					}
				}
			}
		}

	}
	
	private String createSpvCloseCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yydd").format(new Date()));
	}

}
