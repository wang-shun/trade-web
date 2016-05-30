package com.centaline.trans.cases.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.CaseResetService;
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

@Controller
@RequestMapping(value = "/case")
public class CaseRestController {

	@Autowired
	private CaseResetService caseResetService;
	@RequestMapping(value = "/reset")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> reset(Model model, CaseResetVo vo) {
		
		caseResetService.reset(vo);
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		return resp;
	} 
}
