package com.centaline.trans.sys.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.team.service.TsTeamScopeTargetService;

@Controller
@RequestMapping(value = "/caseModify")
public class CaseModifyController {

	@Autowired
	private ToCaseInfoService toCaseInfoService;

	@Autowired
	private TsTeamScopeTargetService tsTeamScopeTargetService;
	
	// 列表页面
	@RequestMapping("toCaseModify")
	public String spvList() {
		return "sys/caseModify";
	}

	/**
	 * 修改TagergetCode
	 * 
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "updateTagetCode.json")
	@ResponseBody
	public AjaxResponse updateTagetCode(@RequestParam(required = true) String ctmCode,
			@RequestParam(required = true) String targetCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ctmCode", ctmCode);
		map.put("targetCode", targetCode);
		Integer result = toCaseInfoService.updateByTargetCode(map);
		return new AjaxResponse(result == 1);
	}
	
	/**
	 * 导入ctm案件
	 * 
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "exportCTMCase.json")
	@ResponseBody
	public AjaxResponse exportCTMCase(@RequestParam(required = true) String ctmCode){
		Integer result = toCaseInfoService.exportCTMCase(ctmCode);
		return new AjaxResponse(result == 1);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "addCaseMapping.json")
	@ResponseBody
	public AjaxResponse addCaseMapping(@RequestParam(required = true) String salesOrgId,
			@RequestParam(required = true) String yuTeamCode){
		
		String msg = tsTeamScopeTargetService.addCaseMapping(salesOrgId, yuTeamCode);
		if(StringUtils.isBlank(msg)){
			return new AjaxResponse();
		}
		return new AjaxResponse(false,msg);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "checkCaseMapping.json")
	@ResponseBody
	public AjaxResponse checkCaseMapping(@RequestParam(required = true) String salesOrgId){
		
		Boolean result = tsTeamScopeTargetService.checkCaseMapping(salesOrgId);
		return new AjaxResponse(result);
	}
}
