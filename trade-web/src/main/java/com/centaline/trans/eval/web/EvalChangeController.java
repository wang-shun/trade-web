package com.centaline.trans.eval.web;

import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.eval.entity.ToEvaCommissionChange;
import com.centaline.trans.eval.service.ToEvaCommPersonAmountService;
import com.centaline.trans.eval.service.ToEvaCommissionChangeService;
import com.centaline.trans.eval.vo.EvalChangeCommVO;
import com.centaline.trans.task.entity.ToApproveRecord;


/**
 * @author xiefei1
 * @since 2017年9月21日 下午5:51:56 
 * @description 评估公司变更调佣金controller
 */
@Controller
@RequestMapping(value = "/eval")
public class EvalChangeController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	private UamBasedataService uamBasedataService;
	@Autowired
	private ToEvaCommissionChangeService toEvaCommissionChangeService;
	@Autowired
	private UamPermissionService uamPermissionService;
	@Autowired
	private ToEvaCommPersonAmountService toEvaCommPersonAmountService;
	/**
	 * @since:2017年10月11日 上午11:04:28
	 * @description: 提交表单
	 * @author:xiefei1
	 */
	@RequestMapping(value = "submitEvalChangeAudit")
	public AjaxResponse<String> submitInvoiceAudit(HttpServletRequest request,Model model,
			String caseCode,String partCode,String content,String status,
			String taskId, String processInstanceId) {
		SessionUser user = uamSessionService.getSessionUser();		
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setOperator(user.getId());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setPartCode(partCode);
		toApproveRecord.setCaseCode(caseCode);
		toApproveRecord.setContent(content);
		toApproveRecord.setCaseCode(caseCode);
		//1是通过，0是没通过
		//notApprove为空表示通过，不为空表示没通过
		if(status.contains("1")){
			toApproveRecord.setNotApprove(null);			
		}else{
			toApproveRecord.setNotApprove("没通过");
		}
		AjaxResponse<String> response = new AjaxResponse<String>();		
		try{
			//提交任务，插入评论，回写CCAI，若taskId,processInstanceId则只插入保存toApproveRecord
			toEvaCommissionChangeService.updateEvalChangeApproveRecord(toApproveRecord,taskId,processInstanceId);
			}catch(Exception e){
		response.setSuccess(false);
		response.setMessage(e.getMessage());
		logger.error("保存失败！"+e.getCause());
	}
	return response;

	}
	/**
	 * @since:2017年10月13日 上午11:04:28
	 * @description: 表单提交
	 * @author:xiefei1
	 */
	@Deprecated
	@RequestMapping(value = "submitChangeComm")
	@ResponseBody
	public AjaxResponse<String> submitChangeComm(EvalChangeCommVO evalChangeCommVO,ToEvaCommissionChange toEvaCommissionChange, HttpServletRequest request,Model model,String caseCode) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			//只要有同名的属性都会同时分配给这两个对象
		toEvaCommPersonAmountService.saveEvalChangeCommVO(evalChangeCommVO,toEvaCommissionChange);
		}
		catch(Exception e){
    		response.setSuccess(false);
    		response.setMessage(e.getMessage());
    		logger.error("保存失败！"+e.getCause());
    	}
		return response;
	}
	
	/**
	 * 
	 * @since:2017年10月16日 下午3:31:21
	 * @description:changeEvalComAudit 调佣审批 页面跳转用这个
	 * @author:xiefei1
	 * @param request
	 * @param model
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value = "changeEvalComAudit")
	public String changeEvalComAudit(HttpServletRequest request,Model model,String caseCode) {
		EvalChangeCommVO evalChangeCommVO = toEvaCommPersonAmountService.getFullEvalChangeCommVO(caseCode);
		model.addAttribute("caseCode", caseCode);
		model.addAttribute("evalChangeCommVO", evalChangeCommVO);
		return "eval/changeEvalComAudit";
	}
	/**
	 * 
	 * @since:2017年10月16日 下午3:31:21
	 * @description:changeEvalCom 跳转评估公司变更,已经不用
	 * @author:xiefei1
	 * @param request
	 * @param model
	 * @param caseCode
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "changeEvalCom")
	public String changeEvalComDetails(HttpServletRequest request,Model model,String caseCode) {
		EvalChangeCommVO evalChangeCommVO = toEvaCommPersonAmountService.getFullEvalChangeCommVO(caseCode);
		model.addAttribute("caseCode", caseCode);
		model.addAttribute("evalChangeCommVO", evalChangeCommVO);
		return "eval/changeEvalCom";
	}
	
	@RequestMapping(value = "addEval")
	@ResponseBody
	public HashMap<String, Object> addEval(HttpServletRequest request,ToEvaCommissionChange toEvaCommissionChange) {
		int insertSelective = toEvaCommissionChangeService.insertSelective(toEvaCommissionChange);
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		if(insertSelective==1){
			resultMap.put("data", "success");
		}else{
			resultMap.put("data", "error");
		}
		return resultMap;
	}

	
	

	


}
