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
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseParticipantMapper;
import com.centaline.trans.cases.repository.ToCaseRecvMapper;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToCcaiAttachment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToCcaiAttachmentMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.AuditCaseMapper;

import cn.author.test.entity.TestCase;

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
public class StartWorkFlowJunitTest {
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired
    private ToCcaiAttachmentMapper toCcaiAttachmentMapper;
	@Autowired
    private TgGuestInfoMapper tgGuestInfoMapper;
	@Autowired
    private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
    private ToCaseInfoService toCaseInfoService;	
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
	private UamBasedataService dictService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private ProcessInstanceService processInstanceService;
    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Test
	public void startWorkFlowJunitTest() throws Exception {
    	for(int i=0;i<6;i++){
    		
//    	导入测试案件
    		String caseOwner=null;
    		TestCase testCase = new TestCase();
    		ToWorkFlow toWorkFlow = new ToWorkFlow();
    		ToCase toCase = new ToCase();
    		toCase.setCaseProperty(CasePropertyEnum.TPZT.getCode());
    		toCase.setCaseCode(testCase.getCaseCode());
    		toCase.setCtmCode(testCase.getCcaiCode());
    		toCase.setStatus("30001001");
    		toCase.setCreateTime(new Date());
    		toCase.setOrgId("A05D3E9C1ED343118F2286EC7E3D2637");
    		toCase.setCity(testCase.getCity());
    		toCase.setCaseOrigin("CCAI");
    		toCase.setDistrictId("3FE5DB2417344D87B6C9FC002BD7D4CC");
    		
//    	caseInfo
    		ToCaseInfo toCaseInfo = new ToCaseInfo();
    		toCaseInfo.setCaseCode(testCase.getCaseCode());
    		toCaseInfo.setAgentCode("004E54563A484C27B19B85354D1D1DE5");
    		toCaseInfo.setRequireProcessorId("0EEE36FF103841A882F2AFAF9CE2BC24");
    		toCaseInfo.setPayType(testCase.getPayType());
    		toCaseInfo.setImportTime(new Date());
//    	participants
    		List<ToCaseParticipant> participants = testCase.getParticipants();
    		for (ToCaseParticipant toCaseParticipant : participants) {
    			toCaseParticipantMapper.insertSelective(toCaseParticipant);
    			if(toCaseParticipant.getPosition().contains("warrant")){
    				toCase.setLeadingProcessId(toCaseParticipant.getUserName());
    				toCaseInfo.setRequireProcessorId(toCaseParticipant.getGrpMgrUsername());
    				toWorkFlow.setProcessOwner(toCaseParticipant.getGrpMgrUsername());
    				caseOwner=toCaseParticipant.getUserName();
    			}
    			if(toCaseParticipant.getPosition().contains("agent")){
    				toCaseInfo.setAgentName(toCaseParticipant.getRealName());
    				toCaseInfo.setAgentUserName(toCaseParticipant.getUserName());
    				toCaseInfo.setAgentPhone(toCaseParticipant.getMobile());
    			}
    		}
    		//guests
    		List<TgGuestInfo> guests = testCase.getGuests();
    		for (TgGuestInfo tgGuestInfo : guests) {
    			tgGuestInfoMapper.insertSelective(tgGuestInfo);
    		}
//    	attachments
    		List<ToCcaiAttachment> attachments = testCase.getAttachments();
    		for (ToCcaiAttachment toCcaiAttachment : attachments) {
    			toCcaiAttachmentMapper.insertSelective(toCcaiAttachment);
    		}
    		
    		toPropertyInfoMapper.insertSelective(testCase.getToPropertyInfo());
    		toCaseMapper.insertSelective(toCase);
    		toCaseInfoService.insertSelective(toCaseInfo);
    		Thread.sleep(100);
//    	开启工作流
    		
    		String caseCode=testCase.getCaseCode();
    		Map<String,Object>vars=new HashMap<>();
//		设置变量，有贷款业务需要贷款权证接单跟进，无贷款业务及自办贷款需要过户权证接单跟进，这里先由过户跟进
    		vars.put("caseOwner", caseOwner);
    		System.out.println(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
    		StartProcessInstanceVo pIVo =processInstanceService.startWorkFlowByDfId(propertyUtilsService.getProcessDfId(WorkFlowEnum
    				.WBUSSKEY.getCode()), caseCode, vars);
    		System.out.println("开启的流程定义ID:"+pIVo.getId());
    		System.out.println("CASE_CODE:"+testCase.getCaseCode());
    		
    		toWorkFlow.setInstCode(pIVo.getId());
    		toWorkFlow.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
    		toWorkFlow.setProcessDefinitionId(pIVo.getProcessDefinitionId());
    		toWorkFlow.setCaseCode(toCase.getCaseCode());
    		toWorkFlow.setBizCode(toCase.getCaseCode());
    		toWorkFlow.setStatus(WorkFlowStatus.ACTIVE.getCode());
    		
    		toWorkFlowService.insertSelective(toWorkFlow);
    		
    	}
    	
    }
    
    
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
