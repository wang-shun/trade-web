package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.SpvCashFlowApplyStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.entity.ToSpvDeDetailMix;
import com.centaline.trans.spv.repository.ToSpvAduitMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCaseFlowOutInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.utils.NumberUtil;

@Service
public class CashFlowOutServiceImpl implements CashFlowOutService {
	@Autowired
	private ToSpvService toSpvService;	
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private PropertyUtilsServiceImpl propertyUtilsService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
		
	@Autowired
	private ToSpvMapper toSpvMapper;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
		
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private MessageService messageService;

	@Override
	public void cashFlowOutPage(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
			setRequestAttribute(request,businessKey);	
	}	

	@Override
	public void saveSpvChargeInfo(SpvChargeInfoVO spvChargeInfoVO) throws Exception {
		if(spvChargeInfoVO == null || spvChargeInfoVO.getToSpvCashFlowApply() == null) throw new BusinessException("申请信息不存在！");
		
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		
		if(spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					cashFlows.add(spvCaseFlowOutInfoVO);
				}
			}
			spvChargeInfoVO.setSpvCaseFlowOutInfoVOList(cashFlows);
		}
		
		SessionUser user = uamSessionService.getSessionUser();
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//创建spvApplyCode
		String spvApplyCode = createSpvApplyCode();
		if(StringUtils.isBlank(cashflowApplyCode)){
			spvChargeInfoVO.getToSpvCashFlowApply().setCashflowApplyCode(spvApplyCode);
		}
		
		//设置申请人为当前用户
		spvChargeInfoVO.getToSpvCashFlowApply().setApplier(user.getId());
		//乘万操作
		multiplyTenThousand(spvChargeInfoVO);

		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		}

	@Override
	public void cashFlowOutPageDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey) throws Exception {
		SessionUser user = uamSessionService.getSessionUser();
        // 判断流程是否已存在
		if(StringUtils.isNotBlank(businessKey)){
			PageableVo pVo = processInstanceService.getByBusinessKey(businessKey);
			List<TaskVo> tList  = pVo.getData();
			for(TaskVo tVo : tList){
				if(tVo.getProcessDefinition().contains(WorkFlowEnum.SPV_CASHFLOW_OUT_DEFKEY.getCode())){
					throw new BusinessException("流程已存在，请勿重复开启！");
				}
			}	
		}
		
		if(spvChargeInfoVO == null || spvChargeInfoVO.getToSpvCashFlowApply() == null) throw new BusinessException("申请信息不存在！");
		
		//更新状态
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTINPROGRESS.getCode());
		
		if(spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTINPROGRESS.getCode());
					cashFlows.add(spvCaseFlowOutInfoVO);
				}
			}
			spvChargeInfoVO.setSpvCaseFlowOutInfoVOList(cashFlows);
		}
	
		//创建spvApplyCode
		String spvApplyCode = createSpvApplyCode();
		if(StringUtils.isBlank(spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode())){
			spvChargeInfoVO.getToSpvCashFlowApply().setCashflowApplyCode(spvApplyCode);
		}
		//设置申请人为当前用户
		spvChargeInfoVO.getToSpvCashFlowApply().setApplier(user.getId());
		//乘万操作
		multiplyTenThousand(spvChargeInfoVO);
		//保存数据
		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		//获取合约
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode());
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("assignee", "jinjj02");
		
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), cashflowApplyCode, vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey(cashflowApplyCode);
		workFlow.setCaseCode(toSpv.getCaseCode());
		workFlow.setInstCode(processInstance.getId());
		workFlow.setProcessDefinitionId(propertyUtilsService.getSPVCashflowOutProcessDfKey());
		workFlow.setProcessOwner(user.getId());
		workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
		toWorkFlowService.insertSelective(workFlow);
		
		// 提交申请任务
		PageableVo pageableVo = taskService.listTasks(processInstance.getId(), false);
		List<TaskVo> taskList = pageableVo.getData();
		for (TaskVo task : taskList) {
			if ("CashflowOutApply".equals(task.getTaskDefinitionKey())) {
				taskService.complete(task.getId() + "");
			}
		}
	}

	@Override
	public void cashFlowOutApplyProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		setRequestAttribute(request,businessKey);       
	}

	@Override
	public void cashFlowOutApplyDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey,Boolean chargeOutAppr) throws Exception {
		
		//更新状态
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTINPROGRESS.getCode());
		
		if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTINPROGRESS.getCode());
					cashFlows.add(spvCaseFlowOutInfoVO);
				}
			}
			spvChargeInfoVO.setSpvCaseFlowOutInfoVOList(cashFlows);
		}

		multiplyTenThousand(spvChargeInfoVO);
		
	    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDirectorAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutDirectorAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		    
		    SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setApplyAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("directorAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutFinanceAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		    
			SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setFtPreAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceSecondAduitProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutFinanceSecondAduitDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCE2ADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCE2ADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		    
			SessionUser user = uamSessionService.getSessionUser();
		    //设置申请复审人
		    spvChargeInfoVO.getToSpvCashFlowApply().setFtPostAuditor(user.getId());
		    //添加审批记录
		    addAduitRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeSecondAduit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDealProcess(HttpServletRequest request, String source, String instCode, String taskId,
			String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutDeal(HttpServletRequest request, String instCode, String taskId,String taskitem, String handle,
			SpvChargeInfoVO spvChargeInfoVO, Boolean chargeOutAppr) throws Exception {
		
		    String spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
		    
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode());
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode());
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setCloseTime(new Date());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		
			Map<String, Object> variables = new HashMap<String, Object>();
			taskService.submitTask(taskId, variables);
			
			//当出款总额等于监管金额时发起消息通知资金尽管流程 ：SpvProcess
			Map<String,Object> completeCashFlowInfoMap = getCompleteCashFlowInfoBySpvCode(spvCode);
	    	//所有合约下已完成的出账金额总和
			BigDecimal totalCashFlowOutAmount = (BigDecimal) completeCashFlowInfoMap.get("totalCashFlowOutAmount");
	    	//监管总额
			ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);	
			BigDecimal toSpvTotalAmount = toSpv.getAmount();
	    	if(totalCashFlowOutAmount.compareTo(toSpvTotalAmount) == 0){
				messageService.sendSpvFinishMsgByIntermi(instCode);	
	    	}
	}	
	
	private String createSpvApplyCode() {
		return uamBasedataService.nextSeqVal("SPV_CODE", new SimpleDateFormat("yyyyMMdd").format(new Date()));
	}

	private void setRequestAttribute(HttpServletRequest request, String businessKey){
		String spvCode;
        SpvBaseInfoVO spvBaseInfoVO;
        SpvChargeInfoVO spvChargeInfoVO = toSpvService.findSpvChargeInfoVOByCashFlowApplyCode(businessKey);
        if(StringUtils.isBlank(businessKey)){
        	spvCode = (String) request.getAttribute("spvCode");   	
        }else{
        	spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
        }
        
        ToSpv toSpv = toSpvService.findToSpvBySpvCode(spvCode);
    	spvBaseInfoVO = toSpvService.findSpvBaseInfoVOByPkid(toSpv == null?null:toSpv.getPkid());
    	if(spvChargeInfoVO != null){       		
        	divideTenThousand(spvChargeInfoVO);
        	List<ToSpvAduit> toSpvAduitList = spvChargeInfoVO.getToSpvAduitList();
        	if(toSpvAduitList != null && !toSpvAduitList.isEmpty()){
        		for(ToSpvAduit toSpvAduit:toSpvAduitList){
        			toSpvAduit.setOperatorName(uamSessionService.getSessionUserById(toSpvAduit.getOperator()).getRealName());
        			toSpvAduit.setOperatorJobName(uamSessionService.getSessionUserById(toSpvAduit.getOperator()).getServiceJobName());
            	}
        	}   	
    	}
    	
    	//生成约定详情整合list
    	if(spvBaseInfoVO != null && spvBaseInfoVO.getToSpvDeDetailList() != null && !spvBaseInfoVO.getToSpvDeDetailList().isEmpty()){
    		List<ToSpvDeDetailMix> deDetailMixList = new ArrayList<ToSpvDeDetailMix>();

    		for(ToSpvDeDetail toSpvDeDetail : spvBaseInfoVO.getToSpvDeDetailList()){
    			Boolean repeatFlag = false;
                for(ToSpvDeDetailMix mix : deDetailMixList){
                	if(toSpvDeDetail.getDeCondCode().equals(mix.getDeCondCode())){
                		repeatFlag = true;
            			if(toSpvDeDetail.getPayeeAccountType().equals("FUND")){
            				mix.setFundDeAmount(toSpvDeDetail.getDeAmount());
            			}else if(toSpvDeDetail.getPayeeAccountType().equals("SELLER")){
            				mix.setSellerDeAmount(toSpvDeDetail.getDeAmount());
            			}
            			mix.setTotalDeAmount(NumberUtil.add(mix.getFundDeAmount(), mix.getSellerDeAmount()));
                	}
                }
                
                if(!repeatFlag){   
    			ToSpvDeDetailMix deDetailMix = new ToSpvDeDetailMix();
    			deDetailMix.setDeCondCode(toSpvDeDetail.getDeCondCode());
    			deDetailMix.setFundDeAmount(BigDecimal.ZERO);
    			deDetailMix.setSellerDeAmount(BigDecimal.ZERO);
    			if(toSpvDeDetail.getPayeeAccountType().equals("FUND")){
    				deDetailMix.setFundDeAmount(toSpvDeDetail.getDeAmount());
    			}else if(toSpvDeDetail.getPayeeAccountType().equals("SELLER")){
    				deDetailMix.setSellerDeAmount(toSpvDeDetail.getDeAmount());
    			}
    			
    			deDetailMix.setTotalDeAmount(NumberUtil.add(deDetailMix.getFundDeAmount(), deDetailMix.getSellerDeAmount()));
    			deDetailMixList.add(deDetailMix);
                }else{
                	continue;
                }
    		}
    		
    		request.setAttribute("deDetailMixList", deDetailMixList);
    	}
    	
    	Map<String,Object> completeCashFlowInfoMap = getCompleteCashFlowInfoBySpvCode(spvCode);
    	request.setAttribute("cashFlowList", completeCashFlowInfoMap.get("cashFlowList"));
    	request.setAttribute("totalCashFlowInAmount", completeCashFlowInfoMap.get("totalCashFlowInAmount"));
    	request.setAttribute("totalCashFlowOutAmount", completeCashFlowInfoMap.get("totalCashFlowOutAmount"));

    	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
	}
	
	//添加审批记录
	private void addAduitRecord(String instCode, String taskId,String taskitem,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeOutAppr){
		SessionUser user = uamSessionService.getSessionUser();
		//设置审批记录表的：流程实例、流程节点、任务id、操作人
		List<ToSpvAduit> toSpvAduitList = new ArrayList<ToSpvAduit>();
		
		ToSpvAduit toSpvAduit = new ToSpvAduit();
		toSpvAduit.setApplyId(spvChargeInfoVO.getToSpvCashFlowApply().getPkid().toString());
		toSpvAduit.setActProcId(instCode);
		toSpvAduit.setTaskDefKey(taskitem);
		toSpvAduit.setTaskId(taskId);
		toSpvAduit.setOperator(user.getId());
		toSpvAduit.setResult(chargeOutAppr?"通过":"未通过");
		toSpvAduit.setContent(spvChargeInfoVO.getToSpvAduitList().get(0).getContent());
		toSpvAduit.setCreateBy(user.getId());
		toSpvAduit.setCreateTime(new Date());
	
		toSpvAduitList.add(toSpvAduit);
		spvChargeInfoVO.setToSpvAduitList(toSpvAduitList);
	}
	
	//除万操作
	private void divideTenThousand(SpvChargeInfoVO spvChargeInfoVO){
		List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList = spvChargeInfoVO.getSpvCaseFlowOutInfoVOList();
		if(spvCaseFlowOutInfoVOList != null && !spvCaseFlowOutInfoVOList.isEmpty()){
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvCaseFlowOutInfoVOList){
				ToSpvCashFlow toSpvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
				toSpvCashFlow.setAmount(toSpvCashFlow.getAmount() == null?null:toSpvCashFlow.getAmount().divide(new BigDecimal(10000)));
			}		
		}
	}
	
	//乘万操作
	private void multiplyTenThousand(SpvChargeInfoVO spvChargeInfoVO){
		List<SpvCaseFlowOutInfoVO> spvCaseFlowOutInfoVOList = spvChargeInfoVO.getSpvCaseFlowOutInfoVOList();
		if(spvCaseFlowOutInfoVOList != null && !spvCaseFlowOutInfoVOList.isEmpty()){
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvCaseFlowOutInfoVOList){
				ToSpvCashFlow toSpvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
				toSpvCashFlow.setAmount(toSpvCashFlow.getAmount() == null?null:toSpvCashFlow.getAmount().multiply(new BigDecimal(10000)));
			}		
		}
	}
	
	//查询spvCode对应合约下所有已完成的流水信息
	private Map<String, Object> getCompleteCashFlowInfoBySpvCode(String spvCode){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
    	List<ToSpvCashFlow> cashFlowList = toSpvCashFlowMapper.selectCashFlowListBySpvCode(spvCode);
    	List<ToSpvCashFlow> cashFlowNewList = new ArrayList<ToSpvCashFlow>();
    	
    	BigDecimal totalCashFlowInAmount = BigDecimal.ZERO;
    	BigDecimal totalCashFlowOutAmount = BigDecimal.ZERO;
    	
    	for(ToSpvCashFlow cashFlow: cashFlowList){
    		ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(cashFlow.getCashflowApplyId());
    		//只选取完成的流水记录
    		if("in".equals(apply.getUsage()) && !SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
    			continue;         
    		}else if("out".equals(apply.getUsage()) && !SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
    			continue;
    		}
    		String applyAuditor = apply.getApplyAuditor();
    		String applyAuditorName = applyAuditor == null?null:uamSessionService.getSessionUserById(applyAuditor).getRealName();
    		String ftPreAuditor = apply.getFtPreAuditor();
        	String ftPreAuditorName = ftPreAuditor == null?null:uamSessionService.getSessionUserById(ftPreAuditor).getRealName();
    		String ftPostAuditor = apply.getFtPostAuditor();
        	String ftPostAuditorName = ftPostAuditor == null?null:uamSessionService.getSessionUserById(ftPostAuditor).getRealName();
        	cashFlow.setUsage(apply.getUsage());
        	cashFlow.setApplyAuditorName(applyAuditorName);
        	cashFlow.setFtPreAuditorName(ftPreAuditorName);
        	cashFlow.setFtPostAuditorName(ftPostAuditorName);
        	cashFlow.setCreateByName(cashFlow.getCreateBy() == null?null:uamSessionService.getSessionUserById(cashFlow.getCreateBy()).getRealName());
        	cashFlow.setAmount(cashFlow.getAmount() == null?null:cashFlow.getAmount().divide(new BigDecimal(10000)));
        	if("in".equals(cashFlow.getUsage())){
        		totalCashFlowInAmount = totalCashFlowInAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:(cashFlow.getAmount().divide(new BigDecimal(10000))));
        	}else if("out".equals(cashFlow.getUsage())){
        		totalCashFlowOutAmount = totalCashFlowOutAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:(cashFlow.getAmount().divide(new BigDecimal(10000))));
        	}	
        	cashFlowNewList.add(cashFlow);
    	}
    	
    	resultMap.put("cashFlowList", cashFlowNewList);
    	resultMap.put("totalCashFlowInAmount", totalCashFlowInAmount);
    	resultMap.put("totalCashFlowOutAmount", totalCashFlowOutAmount);
    	
		return resultMap;
	}

}
