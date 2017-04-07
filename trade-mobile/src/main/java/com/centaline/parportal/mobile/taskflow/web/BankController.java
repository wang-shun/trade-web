package com.centaline.parportal.mobile.taskflow.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value="/bank")
public class BankController {

	@Autowired
	private TsFinOrgService tsFinOrgService;
	/**
	 * 查询egu非egu分行下拉列表
	 * 
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "queryParentBankList")
	@ResponseBody
	public Object queryParentBankList() {
		List<TsFinOrg> bankList = tsFinOrgService.findParentBankList(null,null,null);
		return bankList;
	}
}
