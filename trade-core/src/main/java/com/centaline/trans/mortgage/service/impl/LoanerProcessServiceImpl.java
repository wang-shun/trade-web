package com.centaline.trans.mortgage.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.LoanerProcessService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.UnlocatedTaskService;

@Service
@Transactional
public class LoanerProcessServiceImpl implements LoanerProcessService {

    @Autowired
    private ToMortgageMapper         toMortgageMapper;

    @Autowired
    private ToSupDocuService         toSupDocuService;
    @Autowired
    private TgGuestInfoService       tgGuestInfoService;
    @Autowired(required = true)
    private ToCaseService            toCaseService;
    @Autowired
    private WorkFlowManager          workFlowManager;
    @Autowired
    MessageService                   messageService;
    @Autowired
    private ToWorkFlowService        toWorkFlowService;
    @Autowired
    private UnlocatedTaskService     unlocatedTaskService;
    @Autowired
    private ToApproveRecordService   toApproveRecordService;

    @Autowired(required = true)
    private UamUserOrgService        uamUserOrgService;
    @Autowired
    private PropertyUtilsServiceImpl propertyUtilsService;
    @Autowired(required = true)
    UamSessionService                uamSessionService;
    @Autowired
    private UamTemplateService       uamTemplateService;
    @Qualifier("uamMessageServiceClient")
    
    @Autowired
    private UamMessageService        uamMessageService;
    
	@Autowired
	private ToMortgageService toMortgageService;

    
    /*
     * @author:zhuody
     * @date:2017-03-24
     * @des:启动信贷员流程 
     * 
     */
	@Override
	public AjaxResponse<String> startLoanerOrderWorkFlow(String caseCode,String loanerUserId, String loanerOrgId, String bankOrgCode,int bankLevel) {
				
		AjaxResponse<String> response  = new AjaxResponse<String>();
        /*流程引擎相关*/
        List<RestVariable> variables = new ArrayList<RestVariable>();
        SessionUser user = uamSessionService.getSessionUser();      
		
		try {
			ToWorkFlow toWorkFlow = new ToWorkFlow();
			toWorkFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
			toWorkFlow.setCaseCode(caseCode);
			//查询流程表记录
			ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(toWorkFlow);
			if(null != record){
				throw new BusinessException("启动失败,该流程正在运行！");
			}
			User loaner = uamUserOrgService.getUserById(loanerUserId);								
			
			variables.add(new RestVariable("loanerUserId", loaner.getUsername()));
			variables.add(new RestVariable("bankLevel", bankLevel)); 
			variables.add(new RestVariable("sessionUserId", user.getUsername()));   //派单人	

            
            //启动流程 			
            ProcessInstance process = new ProcessInstance(propertyUtilsService.getProcessLoanerDfKey(), caseCode, variables);
            StartProcessInstanceVo vo = workFlowManager.startCaseWorkFlow(process,loaner.getUsername(), caseCode);    
            
            
            Map<String,Object>  map = new HashMap<String,Object>();
            map.put("caseCode", caseCode);
            map.put("loanerId", loanerUserId);
            ToMortgage toMortgageInfo = toMortgageMapper.findToMortgageByCaseCodeAndLoanerId(map);	
            String bizCode = "";
            if(null != toMortgageInfo){
            	bizCode = toMortgageInfo.getPkid() == null ?"":toMortgageInfo.getPkid().toString();
            }
            //插入工作流表
            ToWorkFlow workFlow = new ToWorkFlow();
            workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
            workFlow.setCaseCode(caseCode);
            workFlow.setBizCode(bizCode);
            workFlow.setInstCode(vo.getId());
            workFlow.setProcessDefinitionId(propertyUtilsService.getProcessLoanerDfKey());//////propertyUtilsService.getProcessTmpBankAuditDfKey()  变成 信贷员的
            workFlow.setProcessOwner(user.getId());
            workFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
            toWorkFlowService.insertSelective(workFlow);             
            
            
            //派单给BC级别银行的信贷员之后,启动银行分级审批
            if(bankLevel ==1 || bankLevel == 9 ){
            	toMortgageService.startTmpBankWorkFlow(caseCode,vo.getId());
            }            
            
            response.setSuccess(true);
            response.setMessage("信贷员接单流程启动成功！");
        } catch (BusinessException e) {
        	response.setSuccess(false);
        	throw new BusinessException("信贷员接单流程启动失败！"); 
        }
        return response;
	}

	
	
	    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:信贷员是否接单确认
     * 
     */
    @Override
    public AjaxResponse<String> isLoanerAcceptCase(boolean isLonaerAcceptCase,String taskId, String processInstanceId,
                                              String caseCode) {
    	
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("信贷员接单确认请求参数异常！");			
		}	
    	
    	AjaxResponse<String> response  = new AjaxResponse<String>();         
    	SessionUser user = uamSessionService.getSessionUser(); 
        List<RestVariable> variables = new ArrayList<RestVariable>();        
        
        try{
         	//信贷员接单
        	if(isLonaerAcceptCase == true){
    			variables.add(new RestVariable("loanerUserId", user.getUsername()));
    			variables.add(new RestVariable("loanerAccept", true));
    			
        	}else{
    			variables.add(new RestVariable("loanerUserId", user.getUsername()));
    			variables.add(new RestVariable("loanerAccept", false));
        	}
        	//提交流程
            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
            response.setSuccess(true);
            response.setMessage("信贷员确认接单成功！");
        }catch(BusinessException e) {
        	response.setSuccess(false);
        	throw new BusinessException("信贷员接单流程推进失败！");
        } 
        return response;        
    }
   
    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:信贷员是否接单   银行审批
     * 
     */
    @Override
    public AjaxResponse<String> isBankAcceptCase(boolean isBankAcceptCase,String taskId, String processInstanceId,String caseCode) {
    	
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("信贷员接单银行确认请求参数异常！");			
		}	
    	
    	AjaxResponse<String> response  = new AjaxResponse<String>();     	
        List<RestVariable> variables = new ArrayList<RestVariable>();
        try{
	        //信贷员接单
	    	if(isBankAcceptCase == true){		
				variables.add(new RestVariable("bankBusinessApprove", true));				
				//审批通过 更改流程状态
	            //更新流程状态为'4'：已完成
	            ToWorkFlow workFlow = new ToWorkFlow();
	            workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());
	            workFlow.setCaseCode(caseCode);
	            ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
	            if (record != null) {
	                record.setStatus(WorkFlowStatus.COMPLETE.getCode());
	                toWorkFlowService.updateByPrimaryKeySelective(record);
	            }
	
	    	}else{			
				variables.add(new RestVariable("bankBusinessApprove", false));			
	    	}
	    	//提交流程
	        workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);
	        response.setSuccess(true);
	        response.setMessage("信贷员接单银行审批通过！");
	     }catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("信贷员接单流程推进失败！");
	     } 
        
         return response;      
    }


    
    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:交易顾问派单 流程关闭
     * 
     */
	@Override
	public AjaxResponse<String> loanerProcessDelete(String caseCode,String taskitem, String processInstanceId) {
		
		AjaxResponse<String>  response = new AjaxResponse<String>();
		if((null == caseCode ||"".equals(caseCode)) || (null == taskitem ||"".equals(taskitem)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("结束交易顾问派单流程请求参数异常！");			
		}	
		
		try{
	        ToWorkFlow workFlow = new ToWorkFlow();
	        workFlow.setBusinessKey(WorkFlowEnum.LOANER_PROCESS.getCode());//Loaner_Process:1:1012544
	        workFlow.setCaseCode(caseCode);
	        ToWorkFlow record = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(workFlow);
	        if (record != null) {
	            record.setStatus(WorkFlowStatus.COMPLETE.getCode());
	            toWorkFlowService.updateByPrimaryKeySelective(record);
	        }
	        
	        response.setSuccess(true);
	        response.setMessage("交易顾问派单流程成功结束！");
		}catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("交易顾问派单流程结束异常！");
	     } 
		
		return response;
	}


    /*
     * @author:zhuody
     * @date:2017-03-28
     * @des:交易顾问派单信息提交
     * 
     */
	@Override
	public AjaxResponse<String> loanerProcessSubmit(ToMortgage toMortgage,String caseCode, String taskId, String processInstanceId,int bankLevel) {
		
		
		if((null == caseCode ||"".equals(caseCode)) || (null == taskId ||"".equals(taskId)) || (null == processInstanceId ||"".equals(processInstanceId)) ){
			throw new BusinessException("交易顾问派单流程请求参数异常！");			
		}
		
		AjaxResponse<String>  response = new AjaxResponse<String>();
        List<RestVariable> variables = new ArrayList<RestVariable>();
		try{
			
			variables.add(new RestVariable("bankLevel", bankLevel)); 
			
        	//提交流程
            workFlowManager.submitTask(variables, taskId, processInstanceId, null, caseCode);	
			
            //派单给BC级别银行的信贷员之后,启动银行分级审批
            if(bankLevel == 1 || bankLevel == 9 ){
            	toMortgageService.startTmpBankWorkFlow(caseCode,processInstanceId);
            }
			
			//更新 信贷员信息、贷款机构信息		
			if(null != toMortgage){
				toMortgageService.updateToMortgage(toMortgage);//toMortgage 主键pkid作为条件更新
			}
            
	        response.setSuccess(true);
	        response.setMessage("交易顾问派单信息提交成功！");
		}catch(BusinessException e) {
	      	response.setSuccess(false);
	      	throw new BusinessException("交易顾问派单流程结束异常！");
	     } 		
		return response;
	}
}
