package com.centaline.trans.ransom.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.web.QuickQueryController;
import com.aist.common.quickQuery.web.vo.DatagridVO;
import com.aist.common.rapidQuery.paramter.ParamterHander;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.RansomStopStatusEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.service.RansomDiscontinueService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

/**
 * <font color=red>流程引擎 任务处理</font>
 * 
 * @author wbwumf
 *
 *         2017年10月9日
 */
@Controller
@RequestMapping(value = "task/ransomDiscontinue")
public class RansomDiscontinueController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	private RansomService ransomService;
	
	@Autowired(required = true)
	private QuickQueryController quickQueryController;

	@Autowired
	private RansomListFormService ransomListFormService;

	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Autowired
	private RansomDiscontinueService ransomDiscontinueService;
	
	/**
	 * 赎楼中止申请,这个方法是为通过流程ID进入赎楼申请准备的
	 * 与stopApplyProcess1的区别在于此方法的caseCode实际上是ransomCode
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "stopApplyProcess")
	public String stopApplyProcess(String caseCode, ServletRequest request) {
		//公共信息
		getTaskBaseInfo(request, null, caseCode);
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(null, caseCode);
		if(ransomCase != null) {
			request.setAttribute("ransomCase", ransomCase);
			request.setAttribute("caseCode", ransomCase.getCaseCode());
		}
		return "ransom/ransomDiscontinue";
	}
	
	/**
	 * 赎楼中止申请
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "stopApplyProcess1")
	public String stopApplyProcess1(String caseCode, ServletRequest request) {
		//公共信息
		getTaskBaseInfo(request, caseCode, null);
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(caseCode, null);
		if(ransomCase != null) {
			request.setAttribute("ransomCase", ransomCase);
			request.setAttribute("caseCode", caseCode);
		}
		return "ransom/ransomDiscontinue";
	}
	
	/**
	 * 赎楼中止提交
	 * @param discontinueVo
	 * @param request
	 * @return
	 * update by wbshume
	 */
	@RequestMapping(value = "submitDiscontinue")
	@ResponseBody
	public boolean submitDiscontinue(ToRansomCaseVo ransomCase, ServletRequest request, ProcessInstanceVO processInstanceVO, String caseCode, String ransomCode) {
		Map<String, Object> task = null;
		String taskId = null;
		ransomCase.setRansomCode(ransomCode);
		ransomCase.setCaseCode(caseCode);
		//①挂起对应的【赎楼流程】(如果有)②开启一个【赎楼中止流程】(如果没有)③给processInstanceVO赋值
		task = getSingleRansomTaskInfo((HttpServletRequest)request, true, false, caseCode);//查询中止流程
		if((boolean)task.get("hasData")) {
			//如果存在对应中止流程，那么判断责任人
			String assignee = (String) task.get("ASSIGNEE");
			SessionUser user = uamSessionService.getSessionUser();
			if(assignee == null || !assignee.equals(user.getUsername())) {
				return false;
			}
		}else {
			//如果不存在赎楼中止流程，则继续判断赎楼流程
			task = null;
			task = getSingleRansomTaskInfo((HttpServletRequest)request, false, false, caseCode);//查询赎楼流程
			if((boolean)task.get("hasData")) {
				//如果有赎楼，无相应中止，那么表示第一次申请中止，则需要做①、②、③
				taskId = (String) task.get("INST_CODE");
				processInstanceService.activateOrSuspendProcessInstance(taskId, false);//①
			}else {
				//如果中止和赎楼都不存在，返回错误
				return false;
			}
			ransomDiscontinueService.startDiscontinueTask(caseCode, ransomCode);//②
			task = null;
			task = getSingleRansomTaskInfo((HttpServletRequest)request, true, false, caseCode);
			processInstanceVO.setTaskId((String)task.get("ID"));//③
			processInstanceVO.setProcessInstanceId((String)task.get("INST_CODE"));
			processInstanceVO.setPartCode((String)task.get("PART_CODE"));
			processInstanceVO.setCaseCode(caseCode);
			processInstanceVO.setBusinessKey(ransomCode);
		}
		return ransomDiscontinueService.submitDiscontinueApply(ransomCase, processInstanceVO);
	}
	
	/**
	 * 赎楼页面公共信息
	 * @param request
	 * @param caseCode
	 */
	private ToRansomDetailVo getTaskBaseInfo(ServletRequest request, String caseCode, String ransomCode) {
		SessionUser user =uamSessionService.getSessionUser(); 
		// 公共基本信息
		List<ToRansomDetailVo> ransomDetailVo = ransomService.getRansomDetail(caseCode);
		ToRansomDetailVo detailVo = ransomDetailVo.get(0);
		if(detailVo != null) {
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("caseCode", detailVo.getCaseCode());
			request.setAttribute("ransomCode", detailVo.getRansomCode());
		}
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user.getId());
		return detailVo;
	}
	
	
	/**
	 * 赎楼中止审批入口
	 * by wbshume
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "aprroProcess")
	public String aprroProcess(String caseCode, ServletRequest request) {
		// 公共基本信息
		getTaskBaseInfo(request, null, caseCode);
		
		//赎楼案件信息
		ToRansomCaseVo ransomCase = ransomListFormService.getRansomCase(null, caseCode);
		request.setAttribute("ransomCase", ransomCase);
		return "ransom/ransomExamine";
	}
	
	@RequestMapping(value = "isCanBeSuspend")
	@ResponseBody
	public boolean isCanSuspend(ServletRequest request, String ransomCode) {
		ToRansomCaseVo ranCase = ransomService.getRansomCaseInfo(ransomCode);
		if(ranCase != null && !RansomStopStatusEnum.STOPING.getCode().equals(ranCase.getIsstop())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 赎楼中止审批提交
	 * by wbshume
	 * @param caseCode
	 * @param request
	 * @return
	 * 三个不同审批结果：
	 * 	驳回：		流程退回 【赎楼中止申请】 到责任人，重新填写 【赎楼中止申请】
	 * 	审批通过：		结束当前 【赎楼中止】 流程和 对应的 【赎楼流程】
	 * 	审批不通过：	结束当前 【赎楼中止】 流程，将之前挂起的 【赎楼流程】重启
	 */
	@RequestMapping(value = "aprroSubmit")
	@ResponseBody
	public Boolean aprroSubmit(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String examContent, String remark, String caseCode, String ransomCode) {
		//通过：删除 [赎楼流程]
		if("pass".equals(examContent)) {
			//或者直接使用processInstanceVO.getProcessInstanceId()
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true, caseCode);
			if((boolean)task.get("hasData")) {
				processInstanceService.deleteProcess((String) task.get("INST_CODE"));
				ransomService.deleteRansomApplyByRansomCode((String) task.get("RANSOM_CODE"));
			}
		}
		//不通过：重启 [赎楼流程]
		else if("noPass".equals(examContent)) {
			Map<String, Object> task = getSingleRansomTaskInfo(request, false, true, caseCode);
			if((boolean)task.get("hasData")) {
				processInstanceService.activateOrSuspendProcessInstance((String) task.get("INST_CODE"), true);
			}
		}
		//保存审批记录
		ransomDiscontinueService.saveToApproveRecord(processInstanceVO, loanlostApproveVO, examContent, remark);
		//赎楼中止 流程下一步
		return ransomDiscontinueService.submitDiscontinueAppro(processInstanceVO, examContent, caseCode, ransomCode);
	}
	
	/**
	 * 获取单条task信息,此方法仅用于查询【赎楼流程】、【赎楼中止流程】的查询,不可扩展改造
	 * @param request
	 * @param isSuspend
	 * @param isSuspended
	 * 实际参数有caseCode,userName,isSuspend,isSuspended
	 * @return
	 */
	private Map<String ,Object> getSingleRansomTaskInfo(HttpServletRequest request, boolean isSuspend, boolean isSuspended, String caseCode) {
		Map<String, String[]> paramMap = ParamterHander.getParameters(request);
        Map<String, Object> paramObj = new HashMap<String, Object>();
        ParamterHander.mergeParamter(paramMap, paramObj);
        if(isSuspend) {//查询赎楼中止流程数据
        	String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM_SUSPEND", processDfId);
        }else {//查询赎楼流程数据
        	String processDfId = propertyUtilsService.getProcessDfId("ransom_process");
        	paramObj.put("PROCESS_DEFINITION_ID_RANSOM", processDfId);
        }
        if(isSuspended) {
        	paramObj.put("isSuspended", true);
        }else {
        	paramObj.put("isSuspended", false);
        }
        if(caseCode != null) {
        	paramObj.put("caseCode",caseCode);
        }
        SessionUser user = uamSessionService.getSessionUser();
        paramObj.put("sessionUserId", user.getId());
        JQGridParam gp = new JQGridParam();
        gp.setPage(1);
        gp.setRows(1);
        gp.putAll(paramObj);
        gp.setQueryId("queryRansomTaskListItemList");
        DatagridVO taskPage = quickQueryController.findPage(gp, request);
        Map<String, Object> map = new HashMap<>();
		if(taskPage != null && taskPage.getRows() != null && taskPage.getRows().size() > 0) {
			map = (Map<String ,Object>)taskPage.getRows().get(0);
			map.put("hasData", true);
		}else {
			map.put("hasData", false);
		}
		return map;
	}
	
}
