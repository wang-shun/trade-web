package com.centaline.parportal.mobile.taskflow.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value = "/bank")
public class BankController {

	@Autowired
	private TsFinOrgService tsFinOrgService;

	/**
	 * 查询所有银行
	 * 
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "queryBankList")
	@ResponseBody
	public Object queryBankList() {
		List<TsFinOrg> mainbankList = tsFinOrgService.getMainBankListInTempBankReport();
		List<TsFinOrg> branchList = tsFinOrgService.findBranchBank();
		branchList.addAll(mainbankList);
		return branchList;
	}

}
