package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ServiceRestartService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.common.entity.ToWorkFlow;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.ToWorkFlowMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.service.ToTransPlanService;
import com.centaline.trans.task.service.UnlocatedTaskService;
@Service
@Transactional(readOnly=true)
public class ServiceRestartServiceImpl implements ServiceRestartService {
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ToApproveRecordService toApproveService;
	@Autowired
	private ToCaseService toCaseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToTransPlanService toTransPlanService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToWorkFlowMapper toWorkFlowMapper;
	@Autowired
	private ToMortgageService toMortgageService;
	
	@Override
	@Transactional(readOnly=false)
	public StartProcessInstanceVo restart(ServiceRestartVo vo) {

		ToWorkFlow twf=new ToWorkFlow();
		twf.setBusinessKey(vo.getCaseCode());
		twf.setCaseCode(vo.getCaseCode());
		toMortgageService.deleteTmpBankProcess(twf);

		ToWorkFlow wf=new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if(sameOne!=null){
			throw new BusinessException("褰撳墠閲嶅惎娴佺▼灏氭湭缁撴潫锛�");
		}
		
		ProcessInstance pi=new ProcessInstance(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode());
		StartProcessInstanceVo spv=workFlowManager.startCaseWorkFlow(pi, vo.getUserName(),vo.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}
	
	@Transactional(readOnly=false)
	@Override
	public boolean apply(ServiceRestartVo vo) {
		workFlowManager.submitTask(null, vo.getTaskId(),vo.getInstCode(),null, vo.getCaseCode());
		ToApproveRecord record=new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		return true;
	}
	@Transactional(readOnly=false)
	@Override
	public boolean approve(ServiceRestartVo vo) {
		List<RestVariable> vs=new ArrayList<>();
		RestVariable v=new RestVariable("is_approved",vo.getIsApproved());
		vs.add(v);
		workFlowManager.submitTask(vs, vo.getTaskId(),vo.getInstCode(),null, vo.getCaseCode());
		ToApproveRecord record=new ToApproveRecord();
		record.setApproveType("7");
		record.setCaseCode(vo.getCaseCode());
		record.setContent(vo.getContent());
		record.setOperator(vo.getUserId());
		record.setOperatorTime(new Date());
		record.setPartCode(vo.getPartCode());
		record.setProcessInstance(vo.getInstCode());
		toApproveService.insertToApproveRecord(record);
		if(vo.getIsApproved()){
			doApproved(vo);
		}
		return true;
	}
	/**
	 * 瀹℃壒鍚屾剰鎿嶄綔
	 * @param vo
	 */
	private void doApproved(ServiceRestartVo vo){
		toTransPlanService.deleteTransPlansByCaseCode(vo.getCaseCode());
		//鍒犻櫎鍘熶富娴佺▼ 鏇存柊鍘熶富娴佺▼璁板綍
		ToWorkFlow t=new ToWorkFlow();
		t.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		t.setCaseCode(vo.getCaseCode());
		ToWorkFlow mainflow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(t);
		if(mainflow!=null){
			try{
				unlocatedTaskService.deleteByInstCode(mainflow.getInstCode());
				workFlowManager.deleteProcess(mainflow.getInstCode());
			} catch (WorkFlowException e) {
				if(!e.getMessage().contains("statusCode[404]"))throw e;
			}
			mainflow.setStatus(WorkFlowStatus.TERMINATE.getCode());
			toWorkFlowService.updateByPrimaryKeySelective(mainflow);
		}
		//鍚姩鏂扮殑涓绘祦绋嬪苟璁板綍娴佺▼琛�
		ToCase cas=toCaseService.findToCaseByCaseCode(vo.getCaseCode());
		cas.setCaseProperty(CasePropertyEnum.TPZT.getCode());
		cas.setStatus(CaseStatusEnum.YFD.getCode());
		//鏇存柊Case琛�
		toCaseService.updateByCaseCodeSelective(cas);
		User u=uamUserOrgService.getUserById(cas.getLeadingProcessId());
		//鏃犳晥涓氬姟琛ㄥ崟
		toWorkFlowService.inActiveForm(vo.getCaseCode());
    	//鏇存柊褰撳墠娴佺▼涓虹粨鏉�
		ToWorkFlow tf= toWorkFlowService.queryWorkFlowByInstCode(vo.getInstCode());
		tf.setStatus(WorkFlowStatus.COMPLETE.getCode());
		toWorkFlowService.updateByPrimaryKeySelective(tf);
		
		ProcessInstance process = new ProcessInstance();
    	process.setBusinessKey(vo.getCaseCode());
    	process.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
    	/*娴佺▼寮曟搸鐩稿叧*/
    	Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		List<RestVariable> variables = new ArrayList<RestVariable>();
	    Iterator it = defValsMap.keySet().iterator();  
	    while (it.hasNext()) {  
            String key = it.next().toString();  
    		RestVariable restVariable = new RestVariable();
    		restVariable.setName(key); 
    		restVariable.setValue(defValsMap.get(key));
    		variables.add(restVariable);
	    }
    	process.setVariables(variables);
    	StartProcessInstanceVo spi=workFlowManager.startCaseWorkFlow(process, u.getUsername(),vo.getCaseCode());
    	ToWorkFlow wf=new ToWorkFlow();
    	wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(cas.getLeadingProcessId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		wf.setInstCode(spi.getId());
		toWorkFlowService.insertSelective(wf);
	}
	@Override
	public StartProcessInstanceVo restartAndDeleteSubProcess(ServiceRestartVo vo) {
		ToWorkFlow wf=new ToWorkFlow();
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		ToWorkFlow sameOne= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(wf);
		if(sameOne!=null){
			throw new BusinessException("褰撳墠閲嶅惎娴佺▼灏氭湭缁撴潫锛�");
		}
		deleteMortFlowByCaseCode(vo.getCaseCode());
		ProcessInstance pi=new ProcessInstance(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()), vo.getCaseCode());
		StartProcessInstanceVo spv=workFlowManager.startCaseWorkFlow(pi, vo.getUserName(),vo.getCaseCode());
		wf.setBusinessKey(WorkFlowEnum.SERVICE_RESTART.getCode());
		wf.setCaseCode(vo.getCaseCode());
		wf.setProcessOwner(vo.getUserId());
		wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.SERVICE_RESTART.getCode()));
		wf.setInstCode(spv.getId());
		toWorkFlowService.insertSelective(wf);
		return spv;
	}
	
	/****
	 *  删除该案件下的所有贷款类型
	 * 
	 * 
	 */
	private void deleteMortFlowByCaseCode(String caseCode) {
		ToWorkFlow workFlow = new ToWorkFlow();
		workFlow.setCaseCode(caseCode);
		List<ToWorkFlow> wordkFlowDBList = toWorkFlowMapper.getMortToWorkFlowByCaseCode(workFlow);
		for(ToWorkFlow workFlowDB : wordkFlowDBList) {
			workFlowManager.deleteProcess(workFlowDB.getInstCode());
			toWorkFlowMapper.deleteWorkFlowByInstCode(workFlowDB.getInstCode());
		}
	}

}
