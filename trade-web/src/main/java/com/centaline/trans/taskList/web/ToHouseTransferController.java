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
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.api.service.SalesApiResponse;
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
	private UamUserOrgService uamUserOrgService;
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

		/**
		 * 思路： 1 用户点击提交按钮, 先调用checkCanHouseTransfer接口。
		 * 	   2  调用checkCanHouseTransfer接口成功[或者是不等于虹口杨浦贵宾服务部 6114AB949B4445828D4383977C4FAC71] -> 执行交易系统的代码 -> 调用noticeSalesDeal接口 -> 不论调用 noticeSalesDeal接口成功与否都需要记录到数据库中。
		 *     3 调用checkCanHouseTransfer接口失败 -> 提示失败信息即可。
		 */
		Result rs=new Result();
		String ctmCode=null;

		ToCaseInfo caseInfo=tocaseInfoService.findToCaseInfoByCaseCode(toHouseTransfer.getCaseCode());
		if(null!=caseInfo){
			ctmCode=caseInfo.getCtmCode();
		}
		
		// 根据 caseCode 到 T_TO_CASE 表中查询出 orgId, 然后再根据orgId 到 SYS_ORG 表中查询 PARENT_ID  
		String orgId=null;
		ToCase tocase=toCaseService.findToCaseByCaseCode(toHouseTransfer.getCaseCode());
		if(null!=tocase){
			orgId=tocase.getOrgId();
		}
		String pid="6114AB949B4445828D4383977C4FAC71";  // 虹口杨浦贵宾服务部
		String parentId=null;
		Org oo=uamUserOrgService.getOrgById(orgId);
		if(null!=oo){
			parentId=oo.getParentId();
		}
		
		if(null==ctmCode){
			rs.setMessage("ctmCode不可为空");
			return rs;
		}
		// 1 调用validCtmDeal接口
		//SalesApiResponse canHouseTransResult=salesdealApiService.checkCanHouseTransfer(ctmCode);
		//if(canHouseTransResult.getSuccess() || !parentId.equals(pid)){// 成功
			
			//2
			toHouseTransferService.submitToHouseTransfer(toHouseTransfer, toMortgage, loanlostApproveVO, taskId, processInstanceId);
			
			// 3 调用noticeSalesDeal接口
			if(parentId.equals(pid)){  // 虹口杨浦贵宾服务部
				salesdealApiService.noticeSalesDeal(ctmCode);
			}
			
			/**
			 * 功能: 给客户发送短信
			 * 作者：zhangxb16
			 */
			int result=tgGuestInfoService.sendMsgHistory(toHouseTransfer.getCaseCode(), toHouseTransfer.getPartCode());
			
			if(result<=0){
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		//}
		//else{ // 失败
			//rs.setMessage("房源过户检查失败，原因为："+canHouseTransResult.getMessage());
		//}
			
		return rs;
	}
	
	
}
