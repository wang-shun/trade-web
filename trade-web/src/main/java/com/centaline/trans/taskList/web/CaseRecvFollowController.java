package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseParticipant;
import com.centaline.trans.cases.entity.ToCaseRecv;
import com.centaline.trans.cases.service.AuditCaseService;
import com.centaline.trans.cases.service.CaseRecvService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseRecvVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.bean.TaskQuery;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.PageableVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;

@Controller
@RequestMapping(value = "/task/caseRecvFollow")
public class CaseRecvFollowController {
	@Autowired
	private CaseRecvService caseRecvService;
	@Autowired
	private WorkFlowManager workFlowManager;
    @Autowired(required = true)
    private ToCaseService toCaseService;
	@Autowired
    private AuditCaseService auditCaseService;
    @Autowired(required = true)
    private UamSessionService uamSessionService;/* 用户信息 */
	@Autowired
	private UamPermissionService uamPermissionService;
	
	@RequestMapping(value = "submit")
	@ResponseBody
	public HashMap<String, String> submit(HttpServletRequest request, String caseCode, ToPropertyInfo toPropertyInfo, ToSign toSign,
			ToCaseRecv toCaseRecv, String payType, ToTax toTax, ToCaseComment toCaseComment, String content,
			String businessLoanWarn) {
		SessionUser user = uamSessionService.getSessionUser();
//		提交保存
		HashMap<String, String> hashMap = new HashMap<String,String>();
		CaseRecvVO caseRecvVO = new CaseRecvVO();
		if(null!=caseCode&&caseCode!=""){
		caseRecvVO.setPayType(payType);
		caseRecvVO.setToCaseComment(toCaseComment);
		caseRecvVO.setToCaseRecv(toCaseRecv);
		caseRecvVO.setToPropertyInfo(toPropertyInfo);
		caseRecvVO.setToSign(toSign);
		caseRecvVO.setToTax(toTax);
		BizWarnInfo bizWarnInfo = new BizWarnInfo();
		bizWarnInfo.setContent(content);
		bizWarnInfo.setStatus(businessLoanWarn);
		caseRecvVO.setBizWarnInfo(bizWarnInfo);
		
		//统一设置caseCode,建议设置完field之后再调用该方法来避免空指针异常,因为刚new出来时他的field都是null;
		caseRecvVO.setCaseCode(caseCode);
		}else{
			hashMap.put("message", "lackCaseCode");
		}
		caseRecvService.insertSelective(caseRecvVO);
		
		hashMap.put("message", "success");
		
		//根据caseCode查出taskVo和ProcessInstanceId,因为设置案件审核的assignee(auditManagerAssignee)需要ProcessInstanceId；
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setProcessInstanceBusinessKey(caseCode);
		PageableVo listTasks = workFlowManager.listTasks(taskQuery);
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
			toCaseParticipant.setUserName(user.getUsername());
			String leaderUserName = auditCaseService.getLeaderUserName(toCaseParticipant);				
			restVariable.setValue(leaderUserName);
			variables.add(restVariable);
			Boolean flag = workFlowManager.submitTask(variables, String.valueOf(taskVo.getId()), taskVo.getProcessInstanceId(),
					toCase.getLeadingProcessId(), caseCode);
			if(!flag){
				hashMap.put("message", "提交任务失败！");
			}
		}
		return hashMap;
	}
	
	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,Model model,String caseCode){
		if(null!=caseCode&&caseCode!=""){
			CaseRecvVO caseRecvVO = caseRecvService.selectFullCaseRecvVO(caseCode);
			model.addAttribute("caseRecvVO", caseRecvVO);			
		}
		
		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		String ctx = app.genAbsoluteUrl();
		request.setAttribute("ctx", ctx);
		return "task/taskCaseRecvFollow";
	}
	
	@RequestMapping(value = "save")
	@ResponseBody
	public HashMap<String, String> saveFirstFollow(HttpServletRequest request,
			String caseCode,ToPropertyInfo toPropertyInfo,ToSign toSign,
			ToCaseRecv toCaseRecv,String payType,ToTax toTax,ToCaseComment toCaseComment,
			String content,String businessLoanWarn) {
		HashMap<String, String> hashMap = new HashMap<String,String>();
		CaseRecvVO caseRecvVO = new CaseRecvVO();
		if(null!=caseCode&&caseCode!=""){
		caseRecvVO.setPayType(payType);
		caseRecvVO.setToCaseComment(toCaseComment);
		caseRecvVO.setToCaseRecv(toCaseRecv);
		caseRecvVO.setToPropertyInfo(toPropertyInfo);
		caseRecvVO.setToSign(toSign);
		caseRecvVO.setToTax(toTax);
		BizWarnInfo bizWarnInfo = new BizWarnInfo();
		bizWarnInfo.setContent(content);
		bizWarnInfo.setStatus(businessLoanWarn);
		caseRecvVO.setBizWarnInfo(bizWarnInfo);
		//统一设置caseCode,建议设置完field之后再调用该方法来避免空指针异常,因为刚new出来时他的field都是null;
		caseRecvVO.setCaseCode(caseCode);
		}else{
			hashMap.put("message", "lackCaseCode");
		}
		caseRecvService.insertSelective(caseRecvVO);
		
		hashMap.put("message", "success");
		return hashMap;
	}
}
