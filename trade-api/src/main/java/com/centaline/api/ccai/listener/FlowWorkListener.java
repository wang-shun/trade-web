package com.centaline.api.ccai.listener;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.ToWorkFlowStartLog;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.repository.ToWorkFlowStartLogMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 导入成功的成交报告信息消费者
 * 用于监听消息队列 启动交易主流程
 * @author yinchao
 * @date 2017/9/19
 */
@Component
public class FlowWorkListener {
	//案件消息队列名称
	private static final String caseQueueName = "casequeue";
	Logger logger = LoggerFactory.getLogger(FlowWorkListener.class);
	@Autowired
	ToCaseParticipantMapper participantMapper;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private ToWorkFlowStartLogMapper logMapper;
	@Autowired
	private WorkFlowEngine engine;//该处使用engine 否则无法进行访问流程引擎平台
	@Value("${trade.flowAutoStart:true}")
	private boolean autoStart;//是否自动启动流程

	@JmsListener(destination = caseQueueName)
	public void startTradeFlow(String caseCode){
		if(!autoStart){
			logger.info("get a caseCode"+caseCode+" but not auto start flow");
			return;
		}
		ToWorkFlowStartLog startLog = new ToWorkFlowStartLog();
		startLog.setCaseCode(caseCode);
		startLog.setCreateBy("SYSTEM");
		startLog.setCreateDate(new Date());
		startLog.setStartTime(new Date());
		try {
			startProcess(caseCode);
			startLog.setStatus("0");
		}catch (Exception e){
			startLog.setStatus("-1");
			startLog.setErrMsg(e.getMessage());
		}
		logMapper.insertSelective(startLog);
	}

	public static String getCaseQueueName() {
		return caseQueueName;
	}

	private void startProcess(String caseCode) throws BusinessException{
		//TODO  后面的更多启动流程 在该处添加处理
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		//流程引擎相关
		Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		ToCaseParticipant owner = null;
		for(ToCaseParticipant pa : participants){
			//将所有的参与人 以参与人类型放入到流程引擎参数中
			defValsMap.put(pa.getPosition(), pa.getUserName());
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if(pa==null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())){
				owner = pa;
			}else if(CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())){
				owner = pa;
			}
		}

		if(owner!=null){
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(owner.getUserName());
			//接单跟进人员
			defValsMap.put("receiver",owner.getUserName());
			//权证经理环节
			defValsMap.put("manager",owner.getGrpMgrUsername());
			//TODO 兼容原有流程 否则会无法启动 后面正式流程删除
			defValsMap.put("caseOwner",owner.getGrpMgrUsername());
		}else{
			throw new BusinessException("交易案件 编号["+caseCode+"] 未获取到案件贷款或过户权证信息 启动流程失败.");
		}

		ToWorkFlow toWorkFlow = new ToWorkFlow();
		//启动流程引擎
		StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()), caseCode, defValsMap);
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		User user = uamUserOrgService.getUserByUsername(owner.getUserName());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(caseCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());

		toWorkFlowService.insertSelective(toWorkFlow);

		logger.info("交易案件 编号["+caseCode+"] 主流程启动成功");
	}

	/**
	 * 流程启动
	 * 主要用于在启动流程前设置用户 否则httpclien会报控制空指针 复制原方法见see
	 * @see com.centaline.trans.engine.service.impl.ProcessInstanceServiceImpl#startWorkFlowByDfId(String, String, Map)
	 * @param processDefinitionId
	 * @param businessKey
	 * @param vars
	 * @return
	 */
	private StartProcessInstanceVo startWorkFlowBase(String processDefinitionId, String businessKey, Map<String, Object> vars) {
		ProcessInstance process = new ProcessInstance();
		process.setProcessDefinitionId(processDefinitionId);
		process.setBusinessKey(businessKey);
		process.setVariables(WorkFlowUtils.mapToVarList(vars));
		StartProcessInstanceVo obj = (StartProcessInstanceVo) engine
				.RESTfulWorkFlow(WorkFlowConstant.START_PROCESS_INSTANCE_KEY, StartProcessInstanceVo.class, process);
		return obj;
	}

}
