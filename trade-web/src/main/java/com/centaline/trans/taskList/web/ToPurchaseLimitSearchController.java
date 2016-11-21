package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToPurchaseLimitSearch;
import com.centaline.trans.task.service.ToPurchaseLimitSearchService;

@Controller
@RequestMapping(value="/task/purchaseLimit")
public class ToPurchaseLimitSearchController {
	
	
	@Autowired
	private ToPurchaseLimitSearchService toPurchaseLimitSearchService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		toAccesoryListService.getAccesoryList(request, taskitem);
		request.setAttribute("purchaseLimit", toPurchaseLimitSearchService.findToPlsByCaseCode(caseCode));
		return "task/taskPurchaseLimit";
	}
	
	@RequestMapping(value="savePls")
	public String savePls(HttpServletRequest request, ToPurchaseLimitSearch toPurchaseLimitSearch) {
		toPurchaseLimitSearchService.saveToPurchaseLimitSearch(toPurchaseLimitSearch);
		return "task/task"+toPurchaseLimitSearch.getPartId();
	}

	
	@RequestMapping(value="submitPls")
	@ResponseBody
	public Result submitPls(HttpServletRequest request, ToPurchaseLimitSearch toPurchaseLimitSearch,
			String taskId, String processInstanceId) {
		toPurchaseLimitSearchService.saveToPurchaseLimitSearch(toPurchaseLimitSearch);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(toPurchaseLimitSearch.getCaseCode());
		workFlowManager.submitTask(variables, taskId, processInstanceId, 
				toCase.getLeadingProcessId(), toPurchaseLimitSearch.getCaseCode());
		
		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(toPurchaseLimitSearch.getCaseCode(), toPurchaseLimitSearch.getPartId());
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}
		
		return rs;
	}
}
