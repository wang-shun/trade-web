package com.centaline.trans.cases.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.cases.service.CaseStopService;
import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

/**
 * 案件爆单
 * @author wbcaiyx
 *	@date 2017/10/17
 */
@Controller
@RequestMapping(value = "/caseStop")
public class CaseStopController {
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private CaseStopService caseStopService;
	
	/**
	 * 案件导爆单初始 检查及启动
	 * @return
	 */
	@RequestMapping(value = "initCheck")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> initCheck(Model model,String caseCode) {
		
		try{
			return caseStopService.checkIsCanStop(caseCode);
		}catch(Exception e){
			throw new BusinessException("案件爆单异常！",e);	 
		}
	}
	
	/**
	 * 案件爆单申请跳转
	 * @param taskId
	 * @param instCode
	 * @param caseCode
	 * @return
	 */
	@RequestMapping("apply/process")
	public String caseStopApply(String taskId, String instCode, String caseCode, HttpServletRequest request,
								@RequestParam(value="type",required=false)String type){
		if(!com.alibaba.druid.util.StringUtils.isEmpty(type)){
			request.setAttribute("taskId", taskId);
			request.setAttribute("processInstanceId", instCode);
			request.setAttribute("caseCode", caseCode);	
		}

		return "case/caseStopApply";
	}
	
	/**
	 * 案件爆单申请提交
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "applySubmit")
	@ResponseBody
	public AjaxResponse<String> apply(ServiceRestartVo vo) {
		return caseStopService.applySubmit(vo);
	}
    
	/**
	 * 案件爆单审批
	 * @param request
	 * @return
	 */
	@RequestMapping("approve/process")
	public String toApproveProcess(HttpServletRequest request) {

		return "case/caseStopApprove";
	}
	
	/**
	 * 案件爆单审批提交
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "approveSubmit")
	@ResponseBody
	public AjaxResponse<String> approve(ServiceRestartVo vo) {
		return caseStopService.approveSubmit(vo);
	}

}
