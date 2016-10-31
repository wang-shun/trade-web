package com.centaline.trans.spv.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
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
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import com.centaline.trans.spv.entity.ToSpv;
import com.centaline.trans.spv.entity.ToSpvAccount;
import com.centaline.trans.spv.entity.ToSpvAduit;
import com.centaline.trans.spv.entity.ToSpvCashFlow;
import com.centaline.trans.spv.entity.ToSpvCashFlowApply;
import com.centaline.trans.spv.entity.ToSpvDeDetail;
import com.centaline.trans.spv.entity.ToSpvDeDetailMix;
import com.centaline.trans.spv.repository.ToSpvAccountMapper;
import com.centaline.trans.spv.repository.ToSpvAduitMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCaseFlowOutInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;
import com.centaline.trans.team.entity.TsTeamProperty;
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
	private TsFinOrgService tsFinOrgService;
		
	@Autowired
	private ToSpvMapper toSpvMapper;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
	@Autowired
	private ToSpvAccountMapper toSpvAccountMapper;
		
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	private MessageService messageService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;

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
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode());
		
		if(spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode());
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
		vars.put("RiskControlOfficer", user.getUsername());
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("RiskControlDirector", riskControlDirector.getUsername());
		
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), cashflowApplyCode, vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		//workFlow.setBusinessKey(cashflowApplyCode);
		workFlow.setBusinessKey("SPVCashflowOutProcess");
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
			if ("CashFlowOutApply".equals(task.getTaskDefinitionKey())) {
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
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode());
		
		if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORADUIT.getCode());
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
		    
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCashFlow.setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setReceiver(spvCashFlow.getPayer());
					spvCashFlow.setReceiverAcc(spvCashFlow.getPayerAcc());
					spvCashFlow.setReceiverBank(spvCashFlow.getPayerBank());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
					//spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					//toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCashFlow);
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
		
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCE2ADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCE2ADUIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setReceiver(spvCashFlow.getPayer());
					spvCashFlow.setReceiverAcc(spvCashFlow.getPayerAcc());
					spvCashFlow.setReceiverBank(spvCashFlow.getPayerBank());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
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
		    
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setReceiver(spvCashFlow.getPayer());
					spvCashFlow.setReceiverAcc(spvCashFlow.getPayerAcc());
					spvCashFlow.setReceiverBank(spvCashFlow.getPayerBank());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
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
		    
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					//spvCaseFlowOutInfoVO.getToSpvCashFlow().setCloseTime(new Date());
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCashFlow.setReceiver(spvCashFlow.getPayer());
					spvCashFlow.setReceiverAcc(spvCashFlow.getPayerAcc());
					spvCashFlow.setReceiverBank(spvCashFlow.getPayerBank());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
					spvCashFlow.setCloseTime(new Date());
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
    		//除万
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
            			if("FUND".equals(toSpvDeDetail.getPayeeAccountType())){
            				mix.setFundDeAmount(toSpvDeDetail.getDeAmount());
            			}else if("SELLER".equals(toSpvDeDetail.getPayeeAccountType())){
            				mix.setSellerDeAmount(toSpvDeDetail.getDeAmount());
            			}
            			mix.setTotalDeAmount(NumberUtil.add(mix.getFundDeAmount(), mix.getSellerDeAmount()));
           			List<ToSpvAccount> toSpvAccounts = toSpvAccountMapper.selectBySpvCode(spvCode);
           				if(toSpvAccounts  != null && !toSpvAccounts.isEmpty()){
            				for(ToSpvAccount account : toSpvAccounts){
            					if("FUND".equals(toSpvDeDetail.getPayeeAccountType()) && "FUND".equals(account.getAccountType())){
            						mix.setFundName(account.getName());
            					}else if("SELLER".equals(toSpvDeDetail.getPayeeAccountType()) && "SELLER".equals(account.getAccountType())){
            						mix.setSellerName(account.getName());
            					}
            				}		
            			}
                	}
                }
                
                if(!repeatFlag){   
    			ToSpvDeDetailMix deDetailMix = new ToSpvDeDetailMix();
    			deDetailMix.setDeCondCode(toSpvDeDetail.getDeCondCode());
    			deDetailMix.setFundDeAmount(BigDecimal.ZERO);
    			deDetailMix.setSellerDeAmount(BigDecimal.ZERO);
    			if("FUND".equals(toSpvDeDetail.getPayeeAccountType())){
    				deDetailMix.setFundDeAmount(toSpvDeDetail.getDeAmount());		
    			}else if("SELLER".equals(toSpvDeDetail.getPayeeAccountType())){
    				deDetailMix.setSellerDeAmount(toSpvDeDetail.getDeAmount());   					
    			}
    			
    			List<ToSpvAccount> toSpvAccounts = toSpvAccountMapper.selectBySpvCode(spvCode);
    			if(toSpvAccounts  != null && !toSpvAccounts.isEmpty()){
    				for(ToSpvAccount account : toSpvAccounts){
    					if("FUND".equals(toSpvDeDetail.getPayeeAccountType()) && "FUND".equals(account.getAccountType())){
    						deDetailMix.setFundName(account.getName());
    					}else if("SELLER".equals(toSpvDeDetail.getPayeeAccountType()) && "SELLER".equals(account.getAccountType())){
    						deDetailMix.setSellerName(account.getName());
    					}
    				}		
    			}
    			
    			deDetailMix.setTotalDeAmount(NumberUtil.add(deDetailMix.getFundDeAmount(), deDetailMix.getSellerDeAmount()));
    			deDetailMixList.add(deDetailMix);
                }else{
                	continue;
                }
    		}
    		
    		request.setAttribute("deDetailMixList", deDetailMixList);
    	}
    	
    	List<Map<String, String>> bankNameList = new ArrayList<Map<String, String>>();
    	boolean ty = false;
    	List<JSONObject> jsonList = new ArrayList<JSONObject>();
		if(spvBaseInfoVO.getToSpvAccountList() != null && !spvBaseInfoVO.getToSpvAccountList().isEmpty()){
			
			for(int k=0;k<spvBaseInfoVO.getToSpvDeDetailList().size();k++){
				ToSpvDeDetail d = spvBaseInfoVO.getToSpvDeDetailList().get(k);
				for(int i=0;i< spvBaseInfoVO.getToSpvAccountList().size();i++){
		    		ToSpvAccount account = spvBaseInfoVO.getToSpvAccountList().get(i);
		    		JSONObject subJsonObj = new JSONObject();
	    			TsFinOrg to = tsFinOrgService.findBankByFinOrg(account.getBank());
	    			subJsonObj.put("type",d.getDeCondCode());
	    			subJsonObj.put("name", account.getName());
	    			subJsonObj.put("account", account.getAccount());
	    			subJsonObj.put("bankName", to != null?to.getFinOrgName():"");
	    			if(d.getPayeeAccountId().equals(account.getPkid()))
	    				jsonList.add(subJsonObj);
		    	}
			}
			
	    	
    	}
    	
    	
/*    	if(spvBaseInfoVO.getToSpvAccountList() != null && !spvBaseInfoVO.getToSpvAccountList().isEmpty()){
    		for(ToSpvAccount account : spvBaseInfoVO.getToSpvAccountList()){
    			if(!StringUtils.isBlank(account.getAccountType())){
    				Map<String, String> map = new HashMap<String, String>();
    				TsFinOrg to = tsFinOrgService.findBankByFinOrg(account.getBank());
    				
    				if("SELLER".equals(account.getAccountType())){
    					map.put("type", account.getAccountType());
    					map.put("name", account.getName());
    					map.put("account", account.getAccount());
    					map.put("bankName", to != null?to.getFinOrgName():null);
    					bankNameList.add(0, map);
    				}else if("FUND".equals(account.getAccountType())){
    					map.put("type", account.getAccountType());
    					map.put("name", account.getName());
    					map.put("account", account.getAccount());
    					map.put("bankName", to != null?to.getFinOrgName():null);
    					bankNameList.add(1, map);
    				}else if("BUYER".equals(account.getAccountType())){
    					map.put("type", account.getAccountType());
    					map.put("name", account.getName());
    					map.put("account", account.getAccount());
    					map.put("bankName", to != null?to.getFinOrgName():null);
    					bankNameList.add(2, map);
    				}else if("SPV".equals(account.getAccountType())){
    					map.put("type", account.getAccountType());
    					map.put("name", account.getName());
    					map.put("account", account.getAccount());
    					map.put("bankName", to != null?to.getFinOrgName():null);
    					bankNameList.add(3, map);
    				}else if(account.getAccountType().indexOf("CUSTOM_") != -1){
    					map.put("type", account.getAccountType());
    					map.put("name", account.getName());
    					map.put("account", account.getAccount());
    					map.put("bankName", to != null?to.getFinOrgName():null);
    					bankNameList.add(4, map);
    				}
    			}
    		}
    	}
*/
    	Map<String,Object> completeCashFlowInfoMap = getCompleteCashFlowInfoBySpvCode(spvCode);
    	
    	request.setAttribute("jsonList", jsonList);
    	request.setAttribute("bankNameList", bankNameList);
    	request.setAttribute("cashFlowList", completeCashFlowInfoMap.get("cashFlowList"));
    	request.setAttribute("totalProcessCashFlowOutAmout", completeCashFlowInfoMap.get("totalProcessCashFlowOutAmout"));
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
    	BigDecimal totalProcessCashFlowOutAmout = BigDecimal.ZERO;
    	
    	for(ToSpvCashFlow cashFlow: cashFlowList){
    		ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(cashFlow.getCashflowApplyId());
    		//只选取完成的流水记录
    		if("in".equals(apply.getUsage()) && !SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
    			continue;         
    		}else if("out".equals(apply.getUsage()) /*&& !SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode().equals(cashFlow.getStatus())*/){
    			//if(!SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode().equals(cashFlow.getStatus())){
    				totalProcessCashFlowOutAmout = totalProcessCashFlowOutAmout.add(totalProcessCashFlowOutAmout);
    			//}
    			//continue;
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
        	cashFlow.setAmount(cashFlow.getAmount() == null?null:cashFlow.getAmount().divide(new BigDecimal(10000)));
        	cashFlow.setCreateByName(cashFlow.getCreateBy() == null?null:uamSessionService.getSessionUserById(cashFlow.getCreateBy()).getRealName());
        	if("in".equals(cashFlow.getUsage()) && !SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
        		totalCashFlowInAmount = totalCashFlowInAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:cashFlow.getAmount());
        	}else if("out".equals(cashFlow.getUsage())){
        		totalCashFlowOutAmount = totalCashFlowOutAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:cashFlow.getAmount());
        	}	
        	
        	cashFlowNewList.add(cashFlow);
    	}
    	ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
    	if(null != toSpv && null != toSpv.getAmount()){
    		if(null != totalCashFlowInAmount )
    			if(totalCashFlowInAmount.compareTo(toSpv.getAmount().divide(new BigDecimal(10000)))==1){
    				totalCashFlowInAmount = toSpv.getAmount().divide(new BigDecimal(10000));
    			}
    	}
    	
    	resultMap.put("cashFlowList", cashFlowNewList);
    	resultMap.put("totalProcessCashFlowOutAmout", totalProcessCashFlowOutAmout);
    	resultMap.put("totalCashFlowInAmount", totalCashFlowInAmount);
    	resultMap.put("totalCashFlowOutAmount", totalCashFlowOutAmount);
    	
		return resultMap;
	}

}
