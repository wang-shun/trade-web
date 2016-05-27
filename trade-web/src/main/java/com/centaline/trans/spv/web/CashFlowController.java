/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.spv.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.centaline.trans.cases.service.ToCaseService;

@Controller
@RequestMapping(value="/cashFlow")
public class CashFlowController {
	
	@Autowired
	private ToCaseService toCaseService;
	 
	/**
	 * 转到资金进出账界面
	 * @param caseCode
	 * @param model
	 * @return
	 */
    @RequestMapping(value="")
    public String cashFlow(String caseCode,Model model){
    
    	return "spv/cashFlow";
    }
	
 
}
