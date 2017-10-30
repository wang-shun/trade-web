package com.centaline.trans.taskList.web;

import java.util.ArrayList;










import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToRatePayment;
import com.centaline.trans.task.service.ToRatePaymentService;
import com.centaline.trans.utils.UiImproveUtil;

/**
 * @author wbzhouht
 * 缴税
 */
@Controller
@RequestMapping(value="/task/ratePayment")
public class RatePaymentController {
	@Autowired
	private ToRatePaymentService ratePaymentService;
	@Autowired(required=true)
	private ToCaseService toCaseService;
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@RequestMapping(value="process")
	public String toProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		//根据交易编号查询不加锁状态下sctrans.T_TO_LOAN_AGENT表下面的数据数量
		int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
		if (cou > 0) {
			caseBaseVO.setLoanType("30004005");
		}
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		//查询是否有附件
		toAccesoryListService.getAccesoryList(request, taskitem);
		ToRatePayment ratePayment=ratePaymentService.qureyToRatePayment(caseCode);
		if(ratePayment!=null){
			User user=uamUserOrgService.getUserById(ratePayment.getAutualOperatorId());
			if (user!=null){
				ratePayment.setAutualOperatorName(user.getRealName());
			}
		}
		request.setAttribute("ratePayment",ratePayment);
		return "task" + UiImproveUtil.getPageType(request) + "/taskRatePayment";
	}

	/**
	 * 保存缴税信息
	 * @param request
	 * @param toRatePayment
	 * @return
	 */
	@RequestMapping(value="saveRatePayment")
	public String saveRatePayment(HttpServletRequest request,ToRatePayment toRatePayment){
		boolean boo=ratePaymentService.saveRatePayment(toRatePayment);
		if (boo) {
			System.out.println("添加成功");
		}
		return "task/task"+toRatePayment.getPartCode();
	}

	/**
	 * 提交缴税信息
	 * @param request
	 * @param toRatePayment
	 * @param taskId
	 * @param processInstanceId
	 * @return
	 */
	@RequestMapping(value="submitRatePayment")
	@ResponseBody
	public Result submitRatePayment(HttpServletRequest request,
			ToRatePayment toRatePayment, String taskId, String processInstanceId) {
		Result result=new Result();
		try {
			ratePaymentService.sumbitRatePayment(request,toRatePayment,taskId,processInstanceId);

		}catch (Exception b){
			b.printStackTrace();
		}
		return result;
	}
}
