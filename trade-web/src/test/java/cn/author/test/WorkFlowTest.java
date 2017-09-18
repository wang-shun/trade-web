package cn.author.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Job;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.repository.ToCaseRecvMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.AuditCaseMapper;

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
public class WorkFlowTest {
	@Autowired
    private ToCaseRecvMapper toCaseRecvMapper;	
	@Autowired
    private ToCaseMapper toCaseMapper;
	@Autowired
    private AuditCaseService auditCaseService;
	@Autowired
	private QuickGridService quickGridService;	
	@Autowired
	private CaseRecvService caseRecvService;
    private AuditCaseMapper auditCaseMapper;
	@Autowired
	private UamUserOrgService uamUserOrgServiceClient;	
	@Autowired
    private ToCaseParticipantMapper toCaseParticipantMapper;
	@Autowired
	private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	@Autowired
	private UamBasedataService dictService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ProcessInstanceService processInstanceService;
    @Autowired(required = true)
    private ToCaseService toCaseService;
    /**
     * 
     * @since:2017年9月15日 下午3:50:34
     * @description:特别提醒：待办任务列表会关联T_TO_WORKFLOW 的caseCode字段，否则查不出来
     * 				所以强烈建议在开启流程时先调用ToCaseService.caseAssign()方法，
     * 				该方法里面会开启流程，而且还会在T_TO_WORKFLOW插入一条数据 哈哈！
     * @author:xiefei1
     * @throws Exception
     */
    @Test
	public void teststartprocess() throws Exception {
		String caseCode="ZY-TJ-2017080030";
		Map<String,Object>vars=new HashMap<>();
	    vars.put("caseOwner", "mytest1");
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
	    StartProcessInstanceVo pIVo =processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum
				  .WBUSSKEY.getCode()), caseCode, vars);
		System.out.println("aa");
	}
	@Test
	public void testQueryTaskByAssignee() throws Exception {
		String caseCode="ZY-TJ-2017080033";
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
//		taskQuery.setAssignee("xiefeifei");
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		if (listTasks.getData().size() > 0) {
			TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
			System.out.println("getProcessInstanceId:"+taskVo.getProcessInstanceId());
			RestVariable var = workFlowManager.getVar(taskVo.getProcessInstanceId(), "auditManagerAssignee");
			System.out.println("aa");
		}		
		System.out.println(listTasks.toString());
	}
	@Test
	public void testGetLeaderUsername() throws Exception {
		String caseCode="ZY-TJ-2017080033";
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCaseCode(caseCode);
		toCaseParticipant.setUserName("humin.tj");
		//根据当前用户名查出这个案件的领导，并将其设置为下一个任务的assignee
		String leaderUserName = auditCaseService.getLeaderUserName(toCaseParticipant);	
		System.out.println(leaderUserName);
	}
	@Test
	public void CommitTaskQueryCaseRecv() throws Exception {
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		String caseCode="ZY-TJ-2017080033";
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
//		taskQuery.setTaskDefinitionKey("usertask17");
		taskQuery.setProcessInstanceId("1192606");
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		System.out.println(listTasks.toString());
		//提交任务
		if (listTasks.getData().size() > 0) {
			TaskVo taskVo = (TaskVo) listTasks.getData().get(0);
			System.out.println("getProcessInstanceId:" + taskVo.getProcessInstanceId());
			ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
			/* 流程引擎相关 */
			List<RestVariable> variables = new ArrayList<RestVariable>();
			RestVariable restVariable = new RestVariable();
			restVariable.setName("auditManagerAssignee");
			ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
			toCaseParticipant.setCaseCode(caseCode);
			toCaseParticipant.setUserName("humin.tj");
			//根据当前用户名查出这个案件的领导，并将其设置为下一个任务的assignee
			String leaderUserName = auditCaseService.getLeaderUserName(toCaseParticipant);				
			restVariable.setValue(leaderUserName);
			variables.add(restVariable);
			Boolean flag = workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
					toCase.getLeadingProcessId(), caseCode);
		}
	}
	@Test
	public void taskQueryCaseRecv() throws Exception {
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		String caseCode="ZY-TJ-2017080033";
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
//		taskQuery.setTaskDefinitionKey("usertask17");
		taskQuery.setProcessInstanceId("1192698");
//		taskQuery.setProcessDefinitionKey("1192606");1192613
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		System.out.println(listTasks.toString());
	}
	@Test
	public void taskCommitTaskQuery() throws Exception {
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		String caseCode="ZY-TJ-2017080030";
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
		taskQuery.setProcessInstanceId("1192698");
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		System.out.println(listTasks.toString());
	}
	@Test
	public void taskCommit() throws Exception {
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		String caseCode="ZY-TJ-2017080030";
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
//		taskQuery.setProcessInstanceBusinessKey("1192698");
		taskQuery.setProcessInstanceId("1192698");
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		TaskVo taskVo=(TaskVo)listTasks.getData().get(0);
		System.out.println(listTasks.toString());
		
		ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
		List<RestVariable> variables = new ArrayList<RestVariable>();
		RestVariable restVariable = new RestVariable();
		restVariable.setName("auditManagerAssignee");
		ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
		toCaseParticipant.setCaseCode(caseCode);
		toCaseParticipant.setUserName("shangfei");
		//根据当前用户名查出这个案件的领导，并将其设置为下一个任务的assignee
		String leaderUserName = auditCaseService.getLeaderUserName(toCaseParticipant);				
		restVariable.setValue(leaderUserName);
		variables.add(restVariable);

		workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), "1192698", toCase.getLeadingProcessId(), caseCode);
	}
	@Test
	public void testStartQueryCommit() throws Exception {
		String caseCode="ZY-TJ-2017080030";
		Map<String,Object>vars=new HashMap<>();
	    vars.put("caseOwner", "mytest123");
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
		//开启流程
	    StartProcessInstanceVo pIVo =processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum
				  .WBUSSKEY.getCode()), caseCode, vars);
		System.out.println("===================开启任务结束===================================");
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
		System.out.println("===================查询任务结束===================================");
		//提交任务
		if (listTasks.getData().size() > 4) {
			TaskVo taskVo = (TaskVo) listTasks.getData().get(4);
			System.out.println("getProcessInstanceId:" + taskVo.getProcessInstanceId());
			ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
			/* 流程引擎相关 */
			List<RestVariable> variables = new ArrayList<RestVariable>();
			RestVariable restVariable = new RestVariable();
			restVariable.setName("auditManagerAssignee");
			ToCaseParticipant toCaseParticipant = new ToCaseParticipant();
			toCaseParticipant.setCaseCode(caseCode);
			toCaseParticipant.setUserName("shangfei");
			//根据当前用户名查出这个案件的领导，并将其设置为下一个任务的assignee
			String leaderUserName = auditCaseService.getLeaderUserName(toCaseParticipant);				
			restVariable.setValue(leaderUserName);
			variables.add(restVariable);
			Boolean flag = workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
					toCase.getLeadingProcessId(), caseCode);
			System.out.println("aa");
		}
		System.out.println("===================提交任务结束===================================");
		

	}
	@Test
	public void StartProcessInstanceVo1() throws Exception {
		String caseCode="ZY-TJ-2017080030";
		Map<String,Object>vars=new HashMap<>();
	    vars.put("caseOwner", "mytest123");
		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
	    StartProcessInstanceVo pIVo =processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum
				  .WBUSSKEY.getCode()), caseCode, vars);
		System.out.println("aa");
		

	}
	

}
