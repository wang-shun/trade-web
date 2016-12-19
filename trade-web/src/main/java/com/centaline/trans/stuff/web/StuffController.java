package com.centaline.trans.stuff.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.stuff.enums.CommentSource;
import com.centaline.trans.stuff.enums.CommentType;
import com.centaline.trans.stuff.service.StuffService;

@Controller
@RequestMapping("/stuff")
public class StuffController {
	@Autowired
	private StuffService stuffService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToCaseService toCaseService;

	@RequestMapping("startReqStuff")
	public String startReqStuff() {
		ToCaseComment comment = new ToCaseComment();
		comment.setBizCode("ZY-SH-201612-0007");
		comment.setCaseCode("ZY-SH-201612-0007");
		comment.setComment("my test");
		comment.setCreatorOrgId(uamSessionService.getSessionUser().getServiceDepId());
		comment.setSource(CommentSource.MORT.getCode());
		comment.setSrvCode("reqStuff");
		comment.setType(CommentType.BUJIAN.getCode());
		stuffService.reqStuff(comment, false);
		return "scuess";
	}

	@RequestMapping("process")
	public String process(HttpServletRequest request, HttpServletResponse response, String businessKey, String source,
			String taskitem, String processInstanceId) {
		ToCaseComment pComment = stuffService.getCommentParentByBizCode(businessKey);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(pComment.getCaseCode());
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("pComment", pComment);
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
