package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToAppRecordInfoService;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.KnotCommissionVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.utils.UiImproveUtil;


/**
 * @author wbzhouht
 * 允许结佣
 */
@Controller
@RequestMapping(value="/task/KnotCommission")
public class KnotCommissionController {
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private FlowApiService flowApiService;

	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService;

	@Autowired
	private ToAppRecordInfoService toAppRecordInfoService;

	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId){
		System.out.println("================进来了！=================");
		//根据交易单编号，查询案件相关信息
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		//自办贷款信息查询
		ToSelfAppInfo toSelfAppInfo = toSelfAppInfoService.getAppInfoByCaseCode(caseCode);
		//审批记录信息查询
		ToAppRecordInfo toAppRecordInfo = toAppRecordInfoService.getAppRedordByAppInfoId(toSelfAppInfo.getPkid());
		if(toSelfAppInfo!=null){
			request.setAttribute("toSelfAppInfo", toSelfAppInfo);
		}
		if(toAppRecordInfo!=null){
			request.setAttribute("toAppRecordInfo", toAppRecordInfo);
		}
		return "task" + UiImproveUtil.getPageType(request) + "/taskKnotCommission";
	}
	@RequestMapping(value="submitKnotCommission")
	@ResponseBody
	public Result sumbitKnotCommission(HttpServletRequest request,KnotCommissionVO knotCommissionVO){
		Result rs=new Result();
		/**
		 * 与ccai交互
		 */
		SessionUser sessionUser=uamSessionService.getSessionUser();
		FlowFeedBack info=new FlowFeedBack(sessionUser, CcaiFlowResultEnum.SUCCESS,"进入结案归档环节");
		ApiResultData apiResultData=flowApiService.tradeFeedBackCcai(knotCommissionVO.getCaseCode(), CcaiTaskEnum.TRADE_WARRANT_TRANSFER,info);
		if(apiResultData.isSuccess()){

			/* 流程引擎相关 */

			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService
					.findToCaseByCaseCode(knotCommissionVO.getCaseCode());
			if(workFlowManager.submitTask(variables, knotCommissionVO.getTaskId(), knotCommissionVO.getProcessInstanceId(),
					toCase.getLeadingProcessId(),
					knotCommissionVO.getCaseCode())){
				//修改案件状态为允许结佣
				ToCase ca  = toCaseService.findToCaseByCaseCode(knotCommissionVO.getCaseCode());
				ca.setStartDate(CaseStatusEnum.YGH.getCode());
				toCaseService.updateByCaseCodeSelective(ca);
				rs.setData(true);
				rs.setMessage("提交成功！");
				System.out.println(apiResultData.toString());
			}
		}else {
			rs.setData(false);
			throw new BusinessException(apiResultData.getMessage());
		}
		return rs;
	}
	
}
