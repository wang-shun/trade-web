/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.mgr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value = "/manage")
public class TsFinOrgController {

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
	public List<TsFinOrg> queryParentBankList(String flag) {

		List<TsFinOrg> bankList = tsFinOrgService.findParentBankList(flag);
		return bankList;
	}

	@RequestMapping(value = "queryBankListByPcode")
	@ResponseBody
	public Map<String, Object> queryBankListByPcode(String pcode, String flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TsFinOrg> bankList = tsFinOrgService.findParentBankList(flag);
		map.put("bankList", bankList);
		if (!StringUtils.isBlank(pcode)) {
			TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(pcode);
			if (tsFinOrg != null) {
				map.put("bankCode", tsFinOrg.getFaFinOrgCode());
				return map;
			}
		}
		map.put("bankCode", "");
		return map;
	}

	/**
	 * 根据分行编号查询egu或非egu支行下拉列表
	 * 
	 * @param faFinOrgCode
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "queryBankListByParentCode")
	@ResponseBody
	public List<TsFinOrg> findBankListByParentCode(String faFinOrgCode,
			String flag) {

		List<TsFinOrg> bankList = tsFinOrgService.findBankListByParentCode(
				flag, faFinOrgCode);
		return bankList;
	}

	/**
	 * 查询评估公司下拉列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryEvaCompany")
	@ResponseBody
	public List<TsFinOrg> queryEvaCompany() {

		List<TsFinOrg> evaCompanyList = tsFinOrgService.findEvaCompany();
		return evaCompanyList;
	}

	/**
	 * 查询资金监管公司下拉列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "querySpvCompany")
	@ResponseBody
	public List<TsFinOrg> querySpvCompany() {

		List<TsFinOrg> evaCompanyList = tsFinOrgService.findSpvCompany();
		return evaCompanyList;
	}

	/**
	 * 公积金贷款供应商
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryPrfLoanBank")
	@ResponseBody
	public List<TsFinOrg> queryPrfLoanBank() {

		List<TsFinOrg> evaCompanyList = tsFinOrgService.findPrfLoanBank();
		return evaCompanyList;
	}

	/**
	 * 查询支行
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryBranchBank")
	@ResponseBody
	public List<TsFinOrg> queryBranchBank() {

		List<TsFinOrg> bankList = tsFinOrgService.findBranchBank();
		return bankList;
	}

	/**
	 * 查询所有供应商
	 * 
	 * @param faFinOrgCode
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "queryAllFinOrg")
	@ResponseBody
	public List<TsFinOrg> queryAllFinOrg() {

		List<TsFinOrg> finOrgList = tsFinOrgService.findAllFinOrg();
		return finOrgList;
	}

	@RequestMapping(value = "queryParentBankInfo")
	@ResponseBody
	public AjaxResponse<String> queryParentBankInfo(String finOrgCode) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		if (StringUtils.isNotBlank(finOrgCode)) {
			TsFinOrg org = tsFinOrgService.findBankByFinOrg(finOrgCode);
			response.setContent(org.getFaFinOrgCode());
		}
		return response;
	}

	@RequestMapping(value = "queryFin")
	@ResponseBody
	public Map<String, Object> queryFin() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TsFinOrg> bankList = tsFinOrgService.findFinCompany();
		map.put("bankList", bankList);
		map.put("bankCode", "");
		return map;
	}
}
