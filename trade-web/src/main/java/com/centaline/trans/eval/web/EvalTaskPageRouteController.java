package com.centaline.trans.eval.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.task.entity.ToApproveRecord;

/**
 * @Description:TODO
 * @author：jinwl6
 * @date:2017年10月12日
 * @version:
 */
@Controller
@RequestMapping(value="/eval/task")
public class EvalTaskPageRouteController {
	     @Autowired
	     ToWorkFlowService  toWorkFlowService;
	     @Autowired
	     UamSessionService uamSessionService;
	
	    @RequestMapping(value="/route/{taskitem}")   
	    public String taskPageRoute(Model model, HttpServletRequest request,@PathVariable String taskitem,
	    		String caseCode,String evalCode, String taskId, String instCode,String source) {
		    request.setAttribute("taskId", taskId);
	    	request.setAttribute("processInstanceId", instCode);
			request.setAttribute("caseCode", caseCode);
			request.setAttribute("taskitem", taskitem);
			request.setAttribute("source", source);
			
			if("evalServiceRestartApply".equals(taskitem) || "evalServiceRestartApprove".equals(taskitem)){ //天津评估流程
				initApproveRecord(request, caseCode, "10");
	     		if(instCode == null && caseCode != null) {
	        		ToWorkFlow toWorkFlow = new ToWorkFlow();
	        		toWorkFlow.setBizCode(request.getParameter("evalCode"));
	        		toWorkFlow.setBusinessKey(WorkFlowEnum.SRV_BUSSKEY.getCode());
	        		instCode = toWorkFlowService.queryToWorkFlowByCaseCodeBusKey(toWorkFlow).getInstCode();
	        		request.setAttribute("processInstanceId", instCode);
	        	}
			}
			
			return "eval/task"+taskitem;
	 }
	    
	    /**
	     * 审批记录初始化
	     * @param request
	     * @param taskitem
	     * @param caseCode
	     * @param approveType
	     */
	    private void initApproveRecord(HttpServletRequest request, String caseCode, String approveType) {
			SessionUser user = uamSessionService.getSessionUser();
	    	ToApproveRecord toApproveRecord = new ToApproveRecord();
			toApproveRecord.setCaseCode(caseCode);
			toApproveRecord.setApproveType(approveType);
			toApproveRecord.setOperator(user.getId());
			request.setAttribute("approveType", approveType);
			request.setAttribute("operator", user != null ? user.getId():"");
	    }
                 
}
