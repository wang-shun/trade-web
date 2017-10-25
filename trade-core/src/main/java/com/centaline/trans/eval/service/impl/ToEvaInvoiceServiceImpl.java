package com.centaline.trans.eval.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.eval.entity.ToEvaInvoice;
import com.centaline.trans.eval.repository.ToEvaInvoiceMapper;
import com.centaline.trans.eval.service.ToEvaInvoiceService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;
@Service
public class ToEvaInvoiceServiceImpl implements ToEvaInvoiceService {
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper; 
	@Autowired
	private ToEvaInvoiceMapper toEvaInvoiceMapper; 
	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired
    private ToCaseParticipantMapper toCaseParticipantMapper;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return toEvaInvoiceMapper.insertSelective(record);
	}

	@Override
	public ToEvaInvoice selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return toEvaInvoiceMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ToEvaInvoice record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
//	权证经理发票审核是否通过
	public int updateEvalInvoiceApproveRecord(ToApproveRecord toApproveRecord) {
			return toApproveRecordMapper.insertSelective(toApproveRecord);
	}

	@Override
	public ToEvaInvoice selectByCaseCode(String caseCode) {
		ToEvaInvoice toEvaInvoice = toEvaInvoiceMapper.selectByCaseCode(caseCode);
		return toEvaInvoice;
	}

	@Override
	public ToEvaInvoice selectByCaseCodeWithEvalCompany(String caseCode) {
		return toEvaInvoiceMapper.selectByCaseCodeWithEvalCompany(caseCode);
	}

	@Override
	public ToEvaInvoice selectByEvaCode(String evaCode) {
		// TODO Auto-generated method stub
		return toEvaInvoiceMapper.selectByEvaCode(evaCode);
	}

	@Override
	public List<ToEvaInvoice> selectByCaseCodeWithEvaPointer(String caseCode) {
		// TODO Auto-generated method stub
		return toEvaInvoiceMapper.selectByCaseCodeWithEvaPointer(caseCode);
	}

//	开启工作流
	@Override
	public int updateStartEvaInvoiceProcess(String caseCode,String evaCode,String warrantManager) throws Exception{
		ToWorkFlow toWorkFlow = new ToWorkFlow();
//    	开启工作流
    		Map<String,Object>vars=new HashMap<>();  
//    		设置内勤assignee变量
    		ToCaseParticipant assistant = new ToCaseParticipant();
    		assistant.setCaseCode(caseCode);
    		assistant.setPosition("assistant");
			List<ToCaseParticipant> participantList = toCaseParticipantMapper.selectByCondition(assistant);
			if(participantList.size()>0){
				ToCaseParticipant assistantList = participantList.get(0);
				if(StringUtils.isNotBlank(assistantList.getUserName())){
					vars.put("assistant", assistantList.getUserName());    				
				}
			}else{
				throw new BusinessException("该案件在系统中没有指定的内勤，请指定内勤；");
			}
//		设置assignee变量warrantManager
    		if(StringUtils.isBlank(warrantManager)){    			
    			ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
    			toCaseParticipant.setCaseCode(caseCode);
    			toCaseParticipant.setPosition("warrant");
    			List<ToCaseParticipant> list = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
    			if(list.size()>0){
    				ToCaseParticipant toCaseParticipant2 = list.get(0);
    				if(StringUtils.isNotBlank(toCaseParticipant2.getGrpMgrUsername())){
    					vars.put("warantManager", toCaseParticipant2.getGrpMgrUsername());    				
    				}
    			}else{
    				throw new BusinessException("该案件在系统中没有指定的权证经理来审核，请指定权证经理；");
    			}   			
    		}else{
//    			toWorkFlow.setProcessOwner("2c9280825cf33828015d54a166c5008c");   	
    			toWorkFlow.setProcessOwner(warrantManager);   
    			vars.put("warantManager", warrantManager);
    		}
//    		开启流程中使用的变量是userName
//    		orgId=2c9280825cf33828015d073422be000d
//    		如果是单元测试就走下面的方法
    		StartProcessInstanceVo pIVo=null;
    		if(null!=uamSessionService.getSessionUser()){
    			 pIVo =processInstanceService.startWorkFlowByDfId(   			
    					propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_INVOICE.getCode()), caseCode, vars);
    		}else{
    			 pIVo =processInstanceService.startWorkFlowByDfId(   			
    					propertyUtilsService.getProcessDfId(WorkFlowEnum.EVAL_INVOICE.getCode(), "2c9280825cf33828015d073422be000d"), caseCode, vars);
    		}
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.EVAL_INVOICE.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(evaCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());		
		toWorkFlowService.insertSelective(toWorkFlow);
		return toWorkFlowService.insertSelective(toWorkFlow);
	}
	@Override
	/**
	 * 提交任务，插入评论，回写CCAI
	 * 		//1是通过，0是没通过
		//notApprove为空表示通过，不为空表示没通过
	 */
	public int updateEvalInvoiceSubmit(ToApproveRecord toApproveRecord,String taskId, String processInstanceId) {
		if(StringUtils.isNotBlank(taskId)&&StringUtils.isNotBlank(processInstanceId)){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			RestVariable restVariable = new RestVariable();
			restVariable.setName("approve");
			if(null!=toApproveRecord.getNotApprove()){
				restVariable.setValue(false);
			}else{
				restVariable.setValue(true);
			}
			variables.add(restVariable);
			SessionUser user = uamSessionService.getSessionUser();
//			提交任务用的是userID
			try {
				workFlowManager.submitTask(variables, taskId, processInstanceId, user.getId(), toApproveRecord.getCaseCode());
				//回写CCAI逻辑可以写在这里
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}
		}
		return toApproveRecordMapper.insertSelective(toApproveRecord);
	}

	@Override
	/**
	 *  关联案件时，把当前案件之前所关联的评估单都取消
	 */
	public ToEvaInvoice updateLinkInvoice(String evaCode) {	
		ToEvaInvoice invoice =null;
		if(StringUtils.isNotBlank(evaCode)){
			 invoice = selectByEvaCode(evaCode);
//			 关联案件时，把当前案件之前所关联的评估单都取消
			 List<ToEvaInvoice> invoiceList = selectByCaseCodeWithEvaPointer(invoice.getCaseCode());
			 if(invoiceList.size()>1){
				 for (ToEvaInvoice toEvaInvoiceA : invoiceList) {
					 toEvaInvoiceA.setEvaPointer(0);
					 updateByPrimaryKeySelective(toEvaInvoiceA);
				 }					 
			 }
			 ToEvaInvoice toEvaInvoice2 = new ToEvaInvoice();
			 toEvaInvoice2.setPkid(invoice.getPkid());
			 toEvaInvoice2.setEvaPointer(1);
			 int result = updateByPrimaryKeySelective(toEvaInvoice2);	
			 if(result==1){
				 invoice.setEvaPointer(1);
			 }
		}
		return invoice;
		
	}


}
