package com.centaline.trans.stuff.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.comment.entity.ToCaseComment;

import com.centaline.trans.stuff.service.StuffService;

@Controller
@RequestMapping("/stuff")
public class StuffController {
	@Autowired
	private StuffService stuffService;

	@Autowired
	private ToCaseService toCaseService;


	
	/*public String startReqStuff(String caseCode) {
		ToCaseComment comment = new ToCaseComment();
		comment.setBizCode(caseCode);
		comment.setCaseCode(caseCode);
		comment.setComment("my test");
		comment.setCreatorOrgId(uamSessionService.getSessionUser().getServiceDepId());
		comment.setSource(CommentSource.MORT.getCode());
		comment.setSrvCode("reqStuff");
		comment.setType(CommentType.BUJIAN.getCode());
		caseCommentService.insertToCaseComment(comment);
		stuffService.reqStuff(comment, false);
		return "scuess";
	}*/

	@RequestMapping("process")
	public String process(HttpServletRequest request, HttpServletResponse response, String businessKey, String source,
			String taskitem, String processInstanceId) {
		ToCaseComment pComment = stuffService.findCommentById(businessKey);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(pComment.getCaseCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("pComment", pComment);
		request.setAttribute("caseCode", pComment.getCaseCode());
		return "/stuff/stuffProcess";
	}
	@RequestMapping("submit")
	@ResponseBody
	public AjaxResponse submit(ToCaseComment caseComment, String taskId) {
		try {
			stuffService.submit(caseComment, taskId);
			return AjaxResponse.success();
		} catch (Exception e) {
			e.printStackTrace();
			return AjaxResponse.fail("处理失败！");
		}
	}
}
