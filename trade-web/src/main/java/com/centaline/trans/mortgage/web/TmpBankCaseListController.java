package com.centaline.trans.mortgage.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/tmpBank")
public class TmpBankCaseListController {

	@RequestMapping(value="list")
	public String list(Model model, HttpServletRequest request){
		
		return "tmpBank/list";
	}

}
