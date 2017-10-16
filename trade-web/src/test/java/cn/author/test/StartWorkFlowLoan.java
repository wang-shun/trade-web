package cn.author.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.common.enums.CaseParticipantEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.utils.WorkFlowUtils;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

@WebAppConfiguration
@ActiveProfiles({"development","jedis"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml", 
		"classpath*:com/aist/common/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/**/META-INF/httpInvoke-beans.xml", 
		"classpath*:com/aist/uam/**/META-INF/beans.xml",
		"classpath*:com/aist/uam/auth/META-INF/shiro-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/core-beans.xml",
		"classpath*:com/centaline/trans/**/META-INF/web-beans.xml",
		"classpath*:com/aist/message/**/META-INF/beans.xml"})
public class StartWorkFlowLoan {
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	ToCaseParticipantMapper participantMapper;
	@Autowired
	private WorkFlowEngine engine;
	
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Test
	public void startWorkFlow(){
		//开启流程
		String caseCode = "ZY-TJ-2017100224";
		List<ToCaseParticipant> participants = participantMapper.selectByCaseCode(caseCode);
		//流程引擎相关
		Map<String, Object> defValsMap = propertyUtilsService.getProcessDefVals(WorkFlowEnum.WBUSSKEY.getCode());
		ToCaseParticipant owner = null;
		for (ToCaseParticipant pa : participants) {
			//将所有的参与人 以参与人类型放入到流程引擎参数中
			defValsMap.put(pa.getPosition(), pa.getUserName());
			//有贷款权证 则贷款权证是案件拥有者 否则为过户权证
			if (owner == null && CaseParticipantEnum.WARRANT.getCode().equals(pa.getPosition())) {
				owner = pa;
			} else if (CaseParticipantEnum.LOAN.getCode().equals(pa.getPosition())) {
				owner = pa;
			}
		}
		if (owner != null) {
			//设置流程引擎平台登录用户 否则无法创建httpclient
			engine.setAuthUserName(owner.getUserName());
			//接单跟进人员
			defValsMap.put("receiver", owner.getUserName());
			//权证经理环节
			defValsMap.put("manager", owner.getGrpMgrUsername());
		} else {
			throw new BusinessException("交易案件 编号[" + caseCode + "] 未获取到案件贷款或过户权证信息 启动流程失败.");
		}

		ToWorkFlow toWorkFlow = new ToWorkFlow();
		//启动流程引擎
		StartProcessInstanceVo pIVo = startWorkFlowBase(propertyUtilsService.getProcessDfId(WorkFlowEnum.LOANANDASSE_PROCESS.getCode()), caseCode, defValsMap);
		toWorkFlow.setInstCode(pIVo.getId());
		toWorkFlow.setBusinessKey(WorkFlowEnum.LOANANDASSE_PROCESS.getCode());
		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
		User user = uamUserOrgService.getUserByUsername(owner.getUserName());
		toWorkFlow.setProcessOwner(user.getId());
		toWorkFlow.setCaseCode(caseCode);
		toWorkFlow.setBizCode(caseCode);
		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());

		toWorkFlowService.insertSelective(toWorkFlow);
	}
	
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
