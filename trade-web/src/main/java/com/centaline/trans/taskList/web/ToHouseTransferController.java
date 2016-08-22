package com.centaline.trans.taskList.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.api.service.SalesDealApiService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.LoanlostApproveVO;

@Controller
@RequestMapping(value="/task/ToHouseTransfer")
public class ToHouseTransferController {

	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private SalesDealApiService salesdealApiService;
	
	@Autowired
	private ToCaseInfoService tocaseInfoService;
	
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
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
		
		toAccesoryListService.getAccesoryListGuoHu(request, taskitem, caseCode);
		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		request.setAttribute("toMortgage", toMortgage);
		
		return "task/taskGuohu";
	}
	@RequestMapping(value="saveToHouseTransfer")
	@ResponseBody
	public AjaxResponse<String> saveToHouseTransfer(HttpServletRequest request, ToHouseTransfer toHouseTransfer,ToMortgage toMortgage) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toHouseTransferService.saveToHouseTransferAndMort(toHouseTransfer, toMortgage);
		}catch(Exception e){
			response.setMessage("操作失败！");
		}
		return response;
	}
	

	@RequestMapping(value="submitToHouseTransfer")
	@ResponseBody
	public Result submitToHouseTransfer(HttpServletRequest request, ToHouseTransfer toHouseTransfer,ToMortgage toMortgage,
			LoanlostApproveVO loanlostApproveVO, String taskId, String processInstanceId) {

		Result rs=new Result();
		String ctmCode=null;

		ToCaseInfo caseInfo=tocaseInfoService.findToCaseInfoByCaseCode(toHouseTransfer.getCaseCode());
		if(null!=caseInfo){
			ctmCode=caseInfo.getCtmCode();
		}
		
		if(null==ctmCode){
			rs.setMessage("ctmCode不可为空");
			return rs;
		}

		toHouseTransferService.submitToHouseTransfer(toHouseTransfer, toMortgage, loanlostApproveVO, taskId, processInstanceId);
		
		// 回写三级市场, 交易过户
		salesdealApiService.noticeSalesDeal(ctmCode);
		
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
