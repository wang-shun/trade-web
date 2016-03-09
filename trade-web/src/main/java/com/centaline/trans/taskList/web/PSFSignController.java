package com.centaline.trans.taskList.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.PSFSignService;
import com.centaline.trans.task.vo.PSFSignVO;
import com.centaline.trans.utils.NumberUtil;

@Controller
@RequestMapping(value="/task/PSFSign")
public class PSFSignController {

	@Autowired
	private PSFSignService psfSignService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@RequestMapping(value="savePSFSign")
	@ResponseBody
	public AjaxResponse<String> savePSFSign(HttpServletRequest request, PSFSignVO psfSignVO) {
		AjaxResponse<String> response = new AjaxResponse<String> ();
		psfSignVO.setMortTotalAmount(NumberUtil.multiply(psfSignVO.getMortTotalAmount(), new BigDecimal(10000)));
		psfSignService.savePSFSign(psfSignVO);
		return response;
	}
	
	@RequestMapping(value="qureyGuestList")
	@ResponseBody
	public List<TgGuestInfo> qureyGuestList(HttpServletRequest request, String caseCode) {
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(caseCode);
		tgGuestInfo.setTransPosition("30006002");
		return tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
	}

	
	@RequestMapping(value="submitPSFSign")
	@ResponseBody
	public boolean submitPSFSign(HttpServletRequest request, PSFSignVO psfSignVO) {
		psfSignVO.setMortTotalAmount(NumberUtil.multiply(psfSignVO.getMortTotalAmount(), new BigDecimal(10000)));
		psfSignService.savePSFSign(psfSignVO);
		
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		ToCase toCase = toCaseService.findToCaseByCaseCode(psfSignVO.getCaseCode());	
		return workFlowManager.submitTask(variables, psfSignVO.getTaskId(), psfSignVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), psfSignVO.getCaseCode());
	}
	
}
