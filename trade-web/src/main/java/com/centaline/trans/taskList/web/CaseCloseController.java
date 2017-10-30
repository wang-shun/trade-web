package com.centaline.trans.taskList.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.common.enums.OldActivitiFormKey;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.CaseCloseService;
import com.centaline.trans.task.service.ToApproveRecordService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value="/task/CaseClose")
public class CaseCloseController {

	@Autowired(required = true)
	private ToCaseService toCaseService;
	/*发送消息*/
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	@Autowired
	private EditCaseDetailService editCaseDetailService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private CaseCloseService caseCloseService;
	@RequestMapping("process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response,String caseCode,String source){
		SessionUser user=uamSessionService.getSessionUser();
		request.setAttribute("source", source);
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "3");
		request.setAttribute("operator", user != null ? user.getId():"");
		toAccesoryListService.getAccesoryListCaseClose(request, caseCode);
		EditCaseDetailVO editCaseDetailVO=editCaseDetailService.queryCaseDetai(caseCode);
		if(editCaseDetailVO != null && StringUtils.isNotBlank(String.valueOf(editCaseDetailVO.getMpkid()))) {
			ToApproveRecord tar = new ToApproveRecord();
			tar.setCaseCode(caseCode);
			tar.setPartCode(OldActivitiFormKey.CaseCloseFirstApprove.getTaskDefinitionKey());
			tar.setProcessInstance(String.valueOf(request.getAttribute("processInstanceId")));
			List<ToApproveRecord> tarList = toApproveRecordService.queryToApproveRecords(tar);
			if(tarList != null && tarList.size() > 0) {
				request.setAttribute("notFirstTimeSubmit", 1);
			}
		}
		request.setAttribute("editCaseDetailVO", editCaseDetailVO);
		request.setAttribute("loanReq", editCaseDetailVO.getLoanReq());
		return "task" + UiImproveUtil.getPageType(request) + "/taskCaseClose";
	}

	
	
	
	@RequestMapping(value="saveCaseClose")
	@ResponseBody
	public Boolean saveCaseDetai(HttpServletRequest request, EditCaseDetailVO editCaseDetailVO) {
		try{
			editCaseDetailService.saveCaseCloseDetai(editCaseDetailVO);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@RequestMapping(value="submitCaseClose")
	@ResponseBody
	public boolean submitCaseClose(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO,EditCaseDetailVO editCaseDetailVO) throws Exception {
		return caseCloseService.submitCaseClose(request, processInstanceVO, loanlostApproveVO, editCaseDetailVO);
	}
	
}
