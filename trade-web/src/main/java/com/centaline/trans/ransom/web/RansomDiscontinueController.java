package com.centaline.trans.ransom.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;

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

	@Autowired(required = true)
	private RansomService ransomService;

	@Autowired
	private RansomListFormService ransomListFormService;

	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	
	@Autowired
	private ProcessInstanceService processInstanceService;
	
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	
	
	/**
	 * 赎楼中止
	 * 
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ransomStop")
	public String ransomStop(String caseCode, ServletRequest request) {

		// 公共基本信息
		getTaskBaseInfo(request, caseCode);

		return "ransom/ransomDiscontinue";
	}

	/**
	 * 赎楼中止提交
	 * @param discontinueVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitDiscontinue")
	@ResponseBody
	public boolean submitDiscontinue(ToRansomCaseVo ransomCaseVo, ServletRequest request) {
		
		try {
			//点击中止时即保存中止原因信息
			ransomListFormService.updateRansomDiscountinue(ransomCaseVo.getCaseCode());
			
			SessionUser user = uamSessionService.getSessionUser();
			
			String manager = "";
			//赎楼流程启动
			Map<String,Object> defValsMap = new HashMap<String,Object>();
			defValsMap.put("sessionUser", user.getUsername());
			defValsMap.put("manager", manager);
			String processDfId = propertyUtilsService.getProcessDfId("ransom_suspend");
			
			/** businsessKey还是用caseCode，因为在engineTask中caseCode兼容老程序,用的businessKey赋值 **/
			StartProcessInstanceVo pVo = processInstanceService.startWorkFlowByDfId(processDfId, ransomCaseVo.getCaseCode(), defValsMap);
			
			ToWorkFlow wf = new ToWorkFlow();
			wf.setBusinessKey("ransom_suspend");
			wf.setCaseCode(ransomCaseVo.getCaseCode());
			wf.setBizCode(ransomCaseVo.getRansomCode());
			wf.setProcessOwner(user.getId());
			wf.setProcessDefinitionId(processDfId);
			wf.setInstCode(pVo.getId());
			wf.setStatus(WorkFlowStatus.ACTIVE.getCode());
			toWorkFlowService.insertSelective(wf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
	/**
	 * 赎楼页面公共信息
	 * 
	 * @param request
	 * @param caseCode
	 */
	void getTaskBaseInfo(ServletRequest request, String caseCode) {
		// 公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode);
		request.setAttribute("detailVo", detailVo);
	}
}
