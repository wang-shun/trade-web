package com.centaline.trans.stuff.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.stuff.service.StuffService;

@Controller
@RequestMapping("/stuff")
public class StuffController {
	@Autowired
	private StuffService stuffService;

	@RequestMapping("process")
	public String process(HttpServletRequest request, HttpServletResponse response, String businessKey, String source,
			String taskitem, String processInstanceId) {
		// CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		return "/stuff/stuffProcess";
	}
}
