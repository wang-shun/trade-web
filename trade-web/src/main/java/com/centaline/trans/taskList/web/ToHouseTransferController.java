package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.task.service.ToMortgageTosaveService;
import com.centaline.trans.task.vo.MortgageToSaveVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.apilog.service.SalesDealApiService;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.utils.UiImproveUtil;

import java.math.BigDecimal;

@Controller
@RequestMapping(value="/task/ToHouseTransfer")
public class ToHouseTransferController {

	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	/*原ccai回写接口，现已废弃*/
	@Autowired
	private SalesDealApiService salesdealApiService;
	//现与ccai交互接口
	@Autowired
	private FlowApiService flowApiService;

	@Autowired
	private ToCaseInfoService tocaseInfoService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private UamBasedataService uamBasedataService;

	@Autowired
	private ToMortgageTosaveService toMortgageTosaveService;
	/**
	 * 过户
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
		SessionUser user= uamSessionService.getSessionUser();
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		request.setAttribute("approveType", "2");
		request.setAttribute("operator", user != null ? user.getId() : "");

		Dict dict = uamBasedataService.findDictByType("accompany_reason");

		if(dict!=null){
			request.setAttribute("accompanyReason", dict.getChildren());
		}
		toAccesoryListService.getAccesoryListGuoHu(request, taskitem, caseCode);
		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		request.setAttribute("toMortgage", toMortgage);
		/*确认是否已经是贷款流失*/
		MortgageToSaveVO mortgageToSaveVO=toMortgageTosaveService.selectByCaseCode(caseCode);
		mortgageToSaveVO.setLoanLossAmount(mortgageToSaveVO.getLoanLossAmount()!=null?mortgageToSaveVO
				.getLoanLossAmount().divide(new BigDecimal(10000)):null);
		request.setAttribute("mortgageToSaveVO",mortgageToSaveVO);
		return "task" + UiImproveUtil.getPageType(request) + "/taskGuohu";
	}
	@RequestMapping(value="saveToHouseTransfer")
	@ResponseBody
	public AjaxResponse<String> saveToHouseTransfer(HttpServletRequest request, ToHouseTransfer toHouseTransfer,MortgageToSaveVO toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toHouseTransferService.savaToHouseTransferAndMortageToVO(toHouseTransfer, toMortgage);
		}catch(Exception e){
			response.setMessage("操作失败！");
		}
		return response;
	}


	@RequestMapping(value="submitToHouseTransfer")
	@ResponseBody
	public Result submitToHouseTransfer(HttpServletRequest request, ToHouseTransfer toHouseTransfer,MortgageToSaveVO toMortgage,
										LoanlostApproveVO loanlostApproveVO, String taskId, String processInstanceId) {

		Result rs=new Result();
		String ccaiCode=null;

		ToCaseInfo caseInfo=tocaseInfoService.findToCaseInfoByCaseCode(toHouseTransfer.getCaseCode());
		if(null!=caseInfo){
			if(caseInfo.getCtmCode()!=null){
				ccaiCode=caseInfo.getCtmCode();
			}else {
				ccaiCode=caseInfo.getCcaiCode();
			}
		}

		if(null==ccaiCode){
			rs.setMessage("ccaiCode不可为空");
			return rs;
		}

		toHouseTransferService.submitToHouseTransfer(toHouseTransfer, toMortgage, loanlostApproveVO, taskId, processInstanceId);
		/*原ccai回写接口，现已废弃*/
		// 回写三级市场, 交易过户
		//salesdealApiService.noticeSalesDeal(ccaiCode);

		/**
		 * 与ccai交互
		 */
		//获取用户登录信息
		SessionUser sessionUser=uamSessionService.getSessionUser();
		//获取审批结果信息
		FlowFeedBack info=new FlowFeedBack(sessionUser, CcaiFlowResultEnum.SUCCESS,"进入过户审批环节");
		//获取审批状态
		ApiResultData apiResultData=flowApiService.tradeFeedBackCcai(toHouseTransfer.getCaseCode(), CcaiTaskEnum.TRADE_WARRANT_TRANSFER,info);
		if(apiResultData.isSuccess()){
			//修改案件状态为已过户
			ToCase ca  = toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
			ca.setStartDate(CaseStatusEnum.YGH.getCode());
			toCaseService.updateByCaseCodeSelective(ca);
			rs.setMessage("交互成功！");
		}else {
			rs.setMessage("交互失败！请联系过户权证！"+apiResultData.toString());
		}

		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		int result=tgGuestInfoService.sendMsgHistory(toHouseTransfer.getCaseCode(), toHouseTransfer.getPartCode());

		if(result<=0){
			rs.setMessage("短信发送失败, 请您线下手工再次发送！");
		}


		return rs;
	}


}
