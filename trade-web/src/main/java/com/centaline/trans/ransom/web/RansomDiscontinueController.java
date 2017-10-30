package com.centaline.trans.ransom.web;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
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
	@Autowired(required = true)
	private RansomService ransomService;
	@Autowired
	private RansomListFormService ransomListFormService;
	@Autowired
	private UamSessionService uamSessionService;
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
	public boolean submitDiscontinue(ToRansomCaseVo ransomCase, HttpServletRequest request, ProcessInstanceVO processInstanceVO, String caseCode, String ransomCode) {
		boolean b = true;
		try {
			b = ransomDiscontinueService.submitDiscontinue(ransomCase, request, processInstanceVO, caseCode, ransomCode);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return b;
	}
	
	/**
	 * 赎楼页面公共信息
	 * @param request
	 * @param caseCode
	 */
	private ToRansomDetailVo getTaskBaseInfo(ServletRequest request, String caseCode, String ransomCode) {
		SessionUser user =uamSessionService.getSessionUser(); 
		// 公共基本信息
		ToRansomDetailVo ransomDetailVo = ransomService.getRansomDetail(caseCode, ransomCode);
		ToRansomDetailVo detailVo = ransomDetailVo;
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
		boolean b = true;
		try {
			b = ransomDiscontinueService.isCanSuspend(request, ransomCode);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return b;
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
		boolean b = true;
		try {
			b = ransomDiscontinueService.aprroSubmit(request, processInstanceVO, loanlostApproveVO, examContent, remark, caseCode, ransomCode);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return b;
	}
	
}
