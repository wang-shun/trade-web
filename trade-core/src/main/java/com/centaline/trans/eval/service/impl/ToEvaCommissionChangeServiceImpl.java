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
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.repository.ToEvaCommissionChangeMapper;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ToApproveRecordMapper;

/**
 * @author xiefei1
 * @since 2017年9月22日 下午2:11:07 
 * @description 评估公司变更模块
 */
@Service
public class ToEvaCommissionChangeServiceImpl implements ToEvaCommissionChangeService {
	@Autowired
	private ToEvaCommissionChangeMapper toEvaCommissionChangeMapper;
	@Autowired
	private ToApproveRecordMapper toApproveRecordMapper; 
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired
    private ToCaseParticipantMapper toCaseParticipantMapper;
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		toEvaCommissionChangeMapper.deleteByPrimaryKey(pkid);
		return 0;
	}

	@Override
	public int insert(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int insert = toEvaCommissionChangeMapper.insert(record);
		return insert;
	}

	@Override
	public int insertSelective(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int insertSelective = toEvaCommissionChangeMapper.insertSelective(record);
		return insertSelective;
	}

	@Override
	public ToEvaCommissionChange selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		ToEvaCommissionChange selectByPrimaryKey = toEvaCommissionChangeMapper.selectByPrimaryKey(pkid);
		
		return selectByPrimaryKey;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int updateByPrimaryKeySelective = toEvaCommissionChangeMapper.updateByPrimaryKeySelective(record);
		return updateByPrimaryKeySelective;
	}

	@Override
	public int updateByPrimaryKey(ToEvaCommissionChange record) {
		// TODO Auto-generated method stub
		int updateByPrimaryKey = toEvaCommissionChangeMapper.updateByPrimaryKey(record);
		return updateByPrimaryKey;
	}

	@Override
	/**
	 * 提交任务，插入评论，回写CCAI
	 * 		//1是通过，0是没通过
		//notApprove为空表示通过，不为空表示没通过
	 */
	public int updateEvalChangeApproveRecord(ToApproveRecord toApproveRecord,String taskId, String processInstanceId) {
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
//	开启工作流
	@Override
	public int updateStartChangeCommProcess(String caseCode,String warrantManager) throws Exception{
		ToWorkFlow toWorkFlow = new ToWorkFlow();
//    	开启工作流
    		Map<String,Object>vars=new HashMap<>();  		
//		设置assignee变量warrantManager
    		if(StringUtils.isBlank(warrantManager)){    			ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
    			toCaseParticipant.setCaseCode(caseCode);
    			toCaseParticipant.setPosition("warrant");
    			List<ToCaseParticipant> list = toCaseParticipantMapper.selectByCondition(toCaseParticipant);
    			if(list.size()>0){
    				ToCaseParticipant toCaseParticipant2 = list.get(0);
    				if(StringUtils.isNotBlank(toCaseParticipant2.getGrpMgrUsername())){
    					vars.put("warrantManager", toCaseParticipant2.getGrpMgrUsername());    				
    				}
    			}else{
    				throw new BusinessException("该案件在系统中没有指定的权证经理来审核，请指定权证经理；");
    			}   			
    		}else{
//    			toWorkFlow.setProcessOwner("2c9280825cf33828015d54a166c5008c");   	
    			toWorkFlow.setProcessOwner(warrantManager);   
    			vars.put("warrantManager", warrantManager);
    		}
//    		开启流程中使用的变量是userName
//    		orgId=2c9280825cf33828015d073422be000d
//    		如果是单元测试就走下面的方法
    		StartProcessInstanceVo pIVo=null;
    		if(null!=uamSessionService.getSessionUser()){
    			 pIVo =processInstanceService.startWorkFlowByDfId(   			
    					propertyUtilsService.getProcessDfId(WorkFlowEnum.CHANGE_COMMISION.getCode()), caseCode, vars);
    		}else{
    			 pIVo =processInstanceService.startWorkFlowByDfId(   			
    					propertyUtilsService.getProcessDfId(WorkFlowEnum.CHANGE_COMMISION.getCode(), "2c9280825cf33828015d073422be000d"), caseCode, vars);
    		}
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.CHANGE_COMMISION.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(caseCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());		
		toWorkFlowService.insertSelective(toWorkFlow);
		return toWorkFlowService.insertSelective(toWorkFlow);
	}

	/* (non-Javadoc)
	 * @see com.centaline.trans.eval.service.ToEvaCommissionChangeService#selectByCaseCode(java.lang.String)
	 */
	@Override
	public ToEvaCommissionChange selectByCaseCode(String caseCode) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public int deleteByCaseCode(String caseCode) {
//		// TODO Auto-generated method stub
//		int deleteByCaseCode = toEvaCommissionChangeMapper.deleteByCaseCode(caseCode);
//		return deleteByCaseCode;
//	}
}
