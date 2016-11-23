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
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.SpvCashFlowApplyStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.TaskService;
import com.centaline.trans.engine.service.ToWorkFlowService;
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
import com.centaline.trans.spv.repository.ToSpvAduitMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowApplyMapper;
import com.centaline.trans.spv.repository.ToSpvCashFlowMapper;
import com.centaline.trans.spv.repository.ToSpvCloseApplyMapper;
import com.centaline.trans.spv.repository.ToSpvMapper;
import com.centaline.trans.spv.service.CashFlowOutService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.spv.vo.SpvBaseInfoVO;
import com.centaline.trans.spv.vo.SpvCaseFlowOutInfoVO;
import com.centaline.trans.spv.vo.SpvChargeInfoVO;

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
	private ToCaseService toCaseService;
		
	@Autowired
	private ToSpvMapper toSpvMapper;
	@Autowired
	private ToSpvCashFlowApplyMapper toSpvCashFlowApplyMapper;
	@Autowired
	private ToSpvCashFlowMapper toSpvCashFlowMapper;
	@Autowired
	private ToSpvAduitMapper toSpvAduitMapper;
	@Autowired
	ToSpvCloseApplyMapper toSpvCloseApplyMapper;
		
	@Autowired
	private UamSessionService uamSessionService;	
	@Autowired
	private UamBasedataService uamBasedataService;
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
		//获取合约
		String spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
		ToSpv toSpv = toSpvMapper.findToSpvBySpvCode(spvCode);
		String caseCode = toSpv.getCaseCode();
        // 判断流程是否已存在
		ToWorkFlow record = new ToWorkFlow();
		record.setCaseCode(caseCode);
		record.setBusinessKey(WorkFlowEnum.SPV_CLOSE_DEFKEY.getCode());
		ToWorkFlow toWorkFlow = toWorkFlowService.queryActiveToWorkFlowByBizCodeBusKey(record);
		if(toWorkFlow != null){
			throw new BusinessException("存在‘中止/结束’流程，暂不能开启出入账流程！");
			}
		
		if(spvChargeInfoVO == null || spvChargeInfoVO.getToSpvCashFlowApply() == null) throw new BusinessException("申请信息不存在！");
		
		//更新状态
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORAUDIT.getCode());
		
		if(spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO : spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORAUDIT.getCode());
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
	
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("RiskControlOfficer", user.getUsername());
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		vars.put("RiskControlDirector", riskControlDirector.getUsername());
		
		//设置申请人为当前用户，初审人为风控总监
		spvChargeInfoVO.getToSpvCashFlowApply().setApplier(user.getId());
		spvChargeInfoVO.getToSpvCashFlowApply().setApplyAuditor(riskControlDirector.getId());
		//乘万操作
		multiplyTenThousand(spvChargeInfoVO);
		//保存数据
		toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);
		
		String cashflowApplyCode = spvChargeInfoVO.getToSpvCashFlowApply().getCashflowApplyCode();
		//开启流程
		StartProcessInstanceVo processInstance = processInstanceService.startWorkFlowByDfId(
				propertyUtilsService.getSPVCashflowOutProcessDfKey(), cashflowApplyCode, vars);

		//插入工作流表
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setBusinessKey("SPVCashflowOutProcess");
		workFlow.setCaseCode(toSpv.getCaseCode());
		workFlow.setBizCode(cashflowApplyCode);
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
		SessionUser user = uamSessionService.getSessionUser();
		//更新状态
		spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORAUDIT.getCode());
		
		if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
			List<SpvCaseFlowOutInfoVO> cashFlows = new ArrayList<SpvCaseFlowOutInfoVO>();
			for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
				if(spvCaseFlowOutInfoVO.getToSpvCashFlow() != null){
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(SpvCashFlowApplyStatusEnum.OUTDIRECTORAUDIT.getCode());
					cashFlows.add(spvCaseFlowOutInfoVO);
				}
			}
			spvChargeInfoVO.setSpvCaseFlowOutInfoVOList(cashFlows);
		}
		
		User riskControlDirector = uamUserOrgService.getLeaderUserByOrgIdAndJobCode(user.getServiceDepId(), "JYFKZJ");
		spvChargeInfoVO.getToSpvCashFlowApply().setApplyAuditor(riskControlDirector.getId());
		
		multiplyTenThousand(spvChargeInfoVO);
		
	    toSpvService.saveSpvChargeInfoVO(spvChargeInfoVO);    
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutDirectorAuditProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutDirectorAuditDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		    
		    multiplyTenThousand(spvChargeInfoVO);
		    
		    //更新申请状态
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCashFlow.setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCEAUDIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCashFlow);
				}
			}

		    //通过时设置申请复审人
		    if(!chargeOutAppr){
		    	Long pkid = spvChargeInfoVO.getToSpvCashFlowApply().getPkid();
				ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(pkid);
				apply.setApplyAuditor(null);
				apply.setStatus(SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
				spvChargeInfoVO.setToSpvCashFlowApply(apply);
				toSpvCashFlowApplyMapper.updateByPrimaryKey(spvChargeInfoVO.getToSpvCashFlowApply());
		    }else{
		    	spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTFINANCEAUDIT.getCode());
		    	toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
		    }
		    
		    //添加审批记录
		    addAuditRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
		
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("directorAudit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceAuditProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey); 
	}

	@Override
	public void cashFlowOutFinanceAuditDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTFINANCE2AUDIT.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		    
			SessionUser user = uamSessionService.getSessionUser();
		    //通过时设置财务初审人，驳回时清空申请复审人
			if(chargeOutAppr){
			    spvChargeInfoVO.getToSpvCashFlowApply().setFtPreAuditor(user.getId());
			    spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTFINANCE2AUDIT.getCode());
			    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
			}else{
				Long pkid = spvChargeInfoVO.getToSpvCashFlowApply().getPkid();
				ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(pkid);
				apply.setStatus(SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
				apply.setApplyAuditor(null);
				spvChargeInfoVO.setToSpvCashFlowApply(apply);
				toSpvCashFlowApplyMapper.updateByPrimaryKey(spvChargeInfoVO.getToSpvCashFlowApply());
			}

		    //添加审批记录
		    addAuditRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);    
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeAudit",chargeOutAppr);
			
		    List<User> financeUsers = uamUserOrgService.getUserByOrgIdAndJobCode("ff808081566e099e01566e7701bb003e", "YCCWREVIEW");
		    List<String> financeUsers_ = new ArrayList<String>();
		    for(User financeUser : financeUsers){
		    	if(!financeUser.getUsername().equals(user.getUsername())){
		    		financeUsers_.add(financeUser.getUsername());
		    	}
		    }
		    variables.put("FinanceSecondAuditorList", financeUsers_);
			
			taskService.submitTask(taskId, variables);
	}

	@Override
	public void cashFlowOutFinanceSecondAuditProcess(HttpServletRequest request, String source, String instCode,
			String taskId, String handle, String businessKey) {
		setRequestAttribute(request,businessKey);
	}

	@Override
	public void cashFlowOutFinanceSecondAuditDeal(HttpServletRequest request, String instCode, String taskId,String taskitem,
			String handle, SpvChargeInfoVO spvChargeInfoVO, String businessKey, Boolean chargeOutAppr)
			throws Exception {
		
		    String spvCode = spvChargeInfoVO.getToSpvCashFlowApply().getSpvCode();
		    
		    multiplyTenThousand(spvChargeInfoVO);
		    //更新申请状态
		    if(spvChargeInfoVO != null && spvChargeInfoVO.getSpvCaseFlowOutInfoVOList() != null){
				for(SpvCaseFlowOutInfoVO spvCaseFlowOutInfoVO: spvChargeInfoVO.getSpvCaseFlowOutInfoVOList()){
					
					ToSpvCashFlow spvCashFlow = spvCaseFlowOutInfoVO.getToSpvCashFlow();
					spvCaseFlowOutInfoVO.getToSpvCashFlow().setStatus(chargeOutAppr?SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode():SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
					spvCashFlow.setPayer("上海中原物业顾问有限公司");
					spvCashFlow.setPayerAcc("76310188000148842");
					spvCashFlow.setPayerBank("光大银行市北支行");
					if(chargeOutAppr) spvCashFlow.setCloseTime(new Date());
					toSpvCashFlowMapper.updateByPrimaryKeySelective(spvCaseFlowOutInfoVO.getToSpvCashFlow());
				}
			}
		    
			SessionUser user = uamSessionService.getSessionUser();
			
		    //通过时设置财务复审人，驳回时清空申请复审人、财务初审人
			if(chargeOutAppr){
				spvChargeInfoVO.getToSpvCashFlowApply().setFtPostAuditor(user.getId());
				spvChargeInfoVO.getToSpvCashFlowApply().setStatus(SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode());
			    toSpvCashFlowApplyMapper.updateByPrimaryKeySelective(spvChargeInfoVO.getToSpvCashFlowApply());
			    
		    	//更新t_to_workflow表
		    	ToWorkFlow workFlow = toWorkFlowService.queryWorkFlowByInstCode(instCode);//更新状态
				if(workFlow != null){
					workFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
					toWorkFlowService.updateByPrimaryKeySelective(workFlow);
				}
			}else{
				Long pkid = spvChargeInfoVO.getToSpvCashFlowApply().getPkid();
				ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(pkid);
				apply.setStatus(SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode());
				apply.setApplyAuditor(null);
				apply.setFtPreAuditor(null);
				spvChargeInfoVO.setToSpvCashFlowApply(apply);
				toSpvCashFlowApplyMapper.updateByPrimaryKey(spvChargeInfoVO.getToSpvCashFlowApply());
			}
		    //添加审批记录
		    addAuditRecord(instCode, taskId, taskitem, spvChargeInfoVO, chargeOutAppr);
		    toSpvAduitMapper.insertSelective(spvChargeInfoVO.getToSpvAduitList().get(0));
	
			Map<String, Object> variables = new HashMap<String, Object>();
			variables.put("financeSecondAudit",chargeOutAppr);
			
			taskService.submitTask(taskId, variables);
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

    	List<JSONObject> jsonList = new ArrayList<JSONObject>();
		if(spvBaseInfoVO.getToSpvAccountList() != null && !spvBaseInfoVO.getToSpvAccountList().isEmpty()){			
			for(int k=0;k<spvBaseInfoVO.getToSpvDeDetailList().size();k++){
				ToSpvDeDetail d = spvBaseInfoVO.getToSpvDeDetailList().get(k);
				for(int i=0;i< spvBaseInfoVO.getToSpvAccountList().size();i++){
		    		ToSpvAccount account = spvBaseInfoVO.getToSpvAccountList().get(i);
		    		JSONObject subJsonObj = new JSONObject();
		    		String branchBank = account.getBranchBank() == null?"":account.getBranchBank();
		    		boolean result = branchBank.matches("[0-9]+");
		    		String bankName = "";
		    		if (result) {
		    			//纯数字
		    			TsFinOrg to = tsFinOrgService.findBankByFinOrg(branchBank);
		    			bankName = to.getFinOrgName();
		    		}else{
		    			//手动输入
		    			bankName = branchBank;
		    		}
		    		if(StringUtils.equals("广发银行股份有限公司北京石景山支行", account.getBranchBank())){
		    			subJsonObj.put("bankName","广发银行股份有限公司北京石景山支行");
		    		}else if(StringUtils.equals("中行上海南京西路支行", account.getBranchBank())){
		    			subJsonObj.put("bankName", "中行上海南京西路支行");
		    		}else{
		    			subJsonObj.put("bankName", bankName);
		    		}
		    		
		    		String accountName = "";
		    		switch (account.getAccountType()) {
					case "BUYER":
						accountName = "买方";
						break;
                    case "SELLER":
                    	accountName = "卖方";
						break;
                    case "SPV":
                    	accountName = "监管方";
						break;
                    case "FUND":
                    	accountName = "资金方";
						break;
					default:
						accountName = "自定义";
						break;
		    		}
	    			subJsonObj.put("type",d.getDeCondCode());
	    			subJsonObj.put("name", account.getName());
	    			subJsonObj.put("account", account.getAccount());
	    			subJsonObj.put("accountName", accountName);
	    			if(d.getPayeeAccountId().equals(account.getPkid()))
	    				jsonList.add(subJsonObj);
		    	}
			}   	
    	}
	    
    	Map<String,Object> completeCashFlowInfoMap = getCompleteCashFlowInfoBySpvCode(spvCode);
    	
    	request.setAttribute("jsonList", jsonList);
    	request.setAttribute("cashFlowList", completeCashFlowInfoMap.get("cashFlowList"));
    	request.setAttribute("totalProcessCashFlowOutAmout", completeCashFlowInfoMap.get("totalProcessCashFlowOutAmout"));
    	request.setAttribute("totalCashFlowInAmount", completeCashFlowInfoMap.get("totalCashFlowInAmount"));
    	request.setAttribute("totalCashFlowOutAmount", completeCashFlowInfoMap.get("totalCashFlowOutAmount"));

    	request.setAttribute("spvChargeInfoVO", spvChargeInfoVO);  
    	request.setAttribute("spvBaseInfoVO", spvBaseInfoVO);    
	}
	
	//添加审批记录
	private void addAuditRecord(String instCode, String taskId,String taskitem,SpvChargeInfoVO spvChargeInfoVO,Boolean chargeOutAppr){
		SessionUser user = uamSessionService.getSessionUser();
		//设置审批记录表的：流程实例、流程节点、任务id、操作人
		List<ToSpvAduit> toSpvAduitList = new ArrayList<ToSpvAduit>();
		
		ToSpvAduit toSpvAduit = new ToSpvAduit();
		toSpvAduit.setApplyId(spvChargeInfoVO.getToSpvCashFlowApply().getPkid().toString());
		toSpvAduit.setActProcId(instCode);
		toSpvAduit.setTaskDefKey(taskitem);
		toSpvAduit.setTaskId(taskId);
		toSpvAduit.setOperator(user.getId());
		toSpvAduit.setResult(chargeOutAppr?"通过":"驳回");
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
	@Override
	public Map<String, Object> getCompleteCashFlowInfoBySpvCode(String spvCode){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
    	List<ToSpvCashFlow> cashFlowList = toSpvCashFlowMapper.selectCashFlowListBySpvCode(spvCode);
    	List<ToSpvCashFlow> cashFlowNewList = new ArrayList<ToSpvCashFlow>();
    	
    	BigDecimal totalCashFlowInAmount = BigDecimal.ZERO;
    	BigDecimal totalCashFlowOutAmount = BigDecimal.ZERO;
    	BigDecimal totalProcessCashFlowOutAmout = BigDecimal.ZERO;
    	
	    List<User> financeUsers = uamUserOrgService.getUserByOrgIdAndJobCode("ff808081566e099e01566e7701bb003e", "YCCWREVIEW");
    	
    	for(ToSpvCashFlow cashFlow: cashFlowList){
    		ToSpvCashFlowApply apply = toSpvCashFlowApplyMapper.selectByPrimaryKey(cashFlow.getCashflowApplyId());
    		//只选取完成的流水记录
    		if("in".equals(apply.getUsage()) 
    				&& !SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
    			continue;         
    		}else if("out".equals(apply.getUsage())
    				&& SpvCashFlowApplyStatusEnum.OUTDRAFT.getCode().equals(cashFlow.getStatus())){
    			continue;	
    		}
    	    
    		String applyAuditorId = apply.getApplyAuditor();
    		String applyAuditorName = applyAuditorId == null?null:uamSessionService.getSessionUserById(applyAuditorId).getRealName();
    		String ftPreAuditorId = apply.getFtPreAuditor();
        	String ftPreAuditorName = ftPreAuditorId == null?getFtAuditorName(ftPreAuditorId,financeUsers):uamSessionService.getSessionUserById(ftPreAuditorId).getRealName();
    		String ftPostAuditorId = apply.getFtPostAuditor();
        	String ftPostAuditorName = ftPostAuditorId == null?getFtAuditorName(ftPreAuditorId,financeUsers):uamSessionService.getSessionUserById(ftPostAuditorId).getRealName();
        	cashFlow.setUsage(apply.getUsage());
        	cashFlow.setApplyAuditorName(applyAuditorName);
        	cashFlow.setFtPreAuditorName(ftPreAuditorName);
        	cashFlow.setFtPostAuditorName(ftPostAuditorName);
        	
    		cashFlow.setAmount(cashFlow.getAmount() == null?null:cashFlow.getAmount().divide(new BigDecimal(10000)));
        	cashFlow.setCreateByName(cashFlow.getCreateBy() == null?null:uamSessionService.getSessionUserById(cashFlow.getCreateBy()).getRealName());
        	if("in".equals(apply.getUsage()) && SpvCashFlowApplyStatusEnum.AUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
        		totalCashFlowInAmount = totalCashFlowInAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:cashFlow.getAmount());
        	}else if("out".equals(apply.getUsage())  && SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
        		totalCashFlowOutAmount = totalCashFlowOutAmount.add(cashFlow.getAmount() == null?BigDecimal.ZERO:cashFlow.getAmount());
        	}else if("out".equals(apply.getUsage())  && !SpvCashFlowApplyStatusEnum.OUTAUDITCOMPLETED.getCode().equals(cashFlow.getStatus())){
        		totalProcessCashFlowOutAmout = totalProcessCashFlowOutAmout.add(cashFlow.getAmount() == null?BigDecimal.ZERO:cashFlow.getAmount());
        	}	
      	
        	cashFlowNewList.add(cashFlow);
    	}
    	resultMap.put("totalCashFlowInDeailAmount", totalCashFlowInAmount);
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

	@Override
	public void getCashFlowList(HttpServletRequest request,String spvCode) {
	    Map<String,Object> completeCashFlowInfoMap = getCompleteCashFlowInfoBySpvCode(spvCode);
	    request.setAttribute("cashFlowList", completeCashFlowInfoMap.get("cashFlowList"));
	    request.setAttribute("totalCashFlowInAmount", completeCashFlowInfoMap.get("totalCashFlowInDeailAmount"));
	    request.setAttribute("totalCashFlowOutAmount", completeCashFlowInfoMap.get("totalCashFlowOutAmount"));
	}

	private String getFtAuditorName(String ftPreAuditorId, List<User> financeUsers){
	    StringBuffer financeName = new StringBuffer();
	    StringBuffer financeName2 = new StringBuffer();
	    User ftPreAuditor = uamUserOrgService.getUserById(ftPreAuditorId);
	    
	    for(User financeUser : financeUsers){
	    	String name = financeUser.getRealName();
	    	if(ftPreAuditor != null){
		    	financeName.append(name+"/");
	    		if(!financeUser.getUsername().equals(ftPreAuditor.getUsername())){
	    			financeName2.append(name+"/");
	    		}
	    	}else{
	    		financeName.append(name+"/");
	    		financeName2.append(name+"/");
	    	}
	    }	    
	    String financeName_ = financeName.substring(0,financeName.length()-1);
	    String financeName2_ = financeName2.substring(0,financeName2.length()-1);
	    
	    return ftPreAuditorId == null?financeName_:financeName2_;
	}

}
