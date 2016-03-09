package com.centaline.trans.cases.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.vo.EditCaseDetailVO;

@Controller
@RequestMapping(value="/case/edit")
public class EditCaseDetailController {

	@Autowired
	private EditCaseDetailService editCaseDetailService;
	
	@RequestMapping(value="editCaseDetail")
	public String editCaseDetail(HttpServletRequest request, String caseCode) {
    	request.setAttribute("caseCode", caseCode);
		request.setAttribute("editCaseDetailVO", editCaseDetailService.queryCaseDetai(caseCode));
		return "case/editCaseDetail";
	}
	
	@RequestMapping(value="saveCaseDetai")
	@ResponseBody
	public Boolean saveCaseDetai(HttpServletRequest request, EditCaseDetailVO editCaseDetailVO) {
		editCaseDetailService.saveCaseDetai(editCaseDetailVO);
		return true;
	}
	
}
