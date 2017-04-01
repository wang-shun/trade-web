package com.centaline.trans.cases.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.cases.service.CaseResetService;
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

@Controller
@RequestMapping(value = "/case")
public class CaseRestController {

	@Autowired
	private CaseResetService caseResetService;
	
	@Autowired
	private ToAttachmentService toAttachmentService;
	
	@RequestMapping(value = "/reset")
	@ResponseBody
	public AjaxResponse<StartProcessInstanceVo> reset(Model model, CaseResetVo vo) {
		
		//新增判断 如果审批通过 则将对应casecode附件表 可用字段置为N
		if(vo != null){			
				 toAttachmentService.updateToAttachmentByCaseCode(vo.getCaseCode() == null ? "":vo.getCaseCode());
		}
		caseResetService.reset(vo);
		AjaxResponse<StartProcessInstanceVo> resp = new AjaxResponse<>();
		return resp;
	} 
}
