package com.centaline.trans.taskList.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.SelfLoanWarrantManagerApproService;
import com.centaline.trans.task.vo.ToAppRecordInfoVO;

/**
 * 自办单款权证经理审批
 * @author wblujian
 *
 */
@Controller
@RequestMapping(value="/task/warrantManagerAppro")
public class SelfLoanWarrantManagerApproController {
	
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService;
	
	private SelfLoanWarrantManagerApproService selfLoanWarrantManagerApproService;
	
	/**
	 * 跳转权证经理审批页面
	 * @param request
	 * @param response
	 * @param caseCode
	 * @param source
	 * @param taskitem
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);	
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToSelfAppInfo toSelfAppInfo = toSelfAppInfoService.getAppInfoByCaseCode(caseCode);
		request.setAttribute("toSelfAppInfo", toSelfAppInfo);
		return "task/taskWarrantManagerAppro";
	}
	
	/**
	 * 获取审批信息
	 * @param appInfoId
	 * @return
	 */
	@RequestMapping("getAppRecordInfo")
	@ResponseBody
	public List<ToAppRecordInfo> getAppRecordInfo(String appInfoId){
		return toSelfAppInfoService.getAppRecordInfo(appInfoId);
	};
	
	@ResponseBody
	@RequestMapping(value = "submit")
	public boolean submit(ToAppRecordInfoVO vo){
		return selfLoanWarrantManagerApproService.saveAndSubmit(vo);
	}
	
}
