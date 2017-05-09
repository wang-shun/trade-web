package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.Result2;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.satisfaction.service.SatisfactionService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.TransSignVO;
import com.centaline.trans.utils.UiImproveUtil;

@Controller
@RequestMapping(value = "/task/sign")
public class SignController {

	@Autowired
	private SignService signService;

	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private TgGuestInfoService tgGuestInfoService;

	@Qualifier("uamMessageServiceClient")
	@Autowired
	private UamMessageService uamMessageService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;

	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private UamPermissionService uamPermissionService;

	@RequestMapping("process")
	public String toLoanLostApproveManagerProcess(HttpServletRequest request,
			HttpServletResponse response, String caseCode, String source,
			String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);

		toAccesoryListService.getAccesoryList(request, taskitem);
		request.setAttribute("transSign", signService.qureyGuestInfo(caseCode));
		request.setAttribute("houseTransfer",
				toHouseTransferService.findToGuoHuByCaseCode(caseCode));

		App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR
				.getCode());
		request.setAttribute("imgweb", app.genAbsoluteUrl());
		return "task" + UiImproveUtil.getPageType(request) + "/taskTransSign";
	}

	@RequestMapping(value = "/saveSign")
	public String saveSign(HttpServletRequest request, TransSignVO transSignVO) {
		signService.insertGuestInfo(transSignVO);

		boolean flag = true;
		// 同时需要修改贷款表里面的 主贷人信息
		ToMortgage toMortgage = new ToMortgage();
		List<Long> pkidDownList = new ArrayList<Long>();
		List<ToMortgage> toMortgageList = new ArrayList<ToMortgage>();
		if (null != transSignVO) {
			toMortgage.setCaseCode(transSignVO.getCaseCode() == null ? ""
					: transSignVO.getCaseCode());
			pkidDownList = transSignVO.getPkidDown();

			for (int i = 0; i < pkidDownList.size(); i++) {				
				toMortgage.setCustCode(String.valueOf(pkidDownList.get(i)));
				List<ToMortgage> getMortgageByCodeList = toMortgageService.findToMortgageByCaseCodeAndCustcode(toMortgage);					
				if(null == getMortgageByCodeList || getMortgageByCodeList.size() <= 0){
					continue;
				}
				
				for(int k=0; k < getMortgageByCodeList.size();k++){
					toMortgageList.add(getMortgageByCodeList.get(k));
				}					
								
			}

			for (int i = 0; i < toMortgageList.size(); i++) {
				ToMortgage toMortgageItem = toMortgageList.get(i);
				if(toMortgageItem == null){
					continue;
				}
				String custCode = toMortgageItem.getCustCode();
				if(custCode == null){
					continue;
				}
				
				for (Long longPkid : pkidDownList) {
					String strPkid = longPkid.toString();
					if (custCode.equals(strPkid)) {
						// 签约修改下家信息时，更新主贷人
						ToMortgage toMortgageForUpdate = new ToMortgage();
						toMortgageForUpdate.setCaseCode(transSignVO.getCaseCode() == null ? "": transSignVO.getCaseCode());
						toMortgageForUpdate.setCustCode(strPkid);
						TgGuestInfo tgGuestInfo = tgGuestInfoService.findTgGuestInfoById(longPkid);
						if (tgGuestInfo != null) {
							toMortgageForUpdate.setCustName(tgGuestInfo.getGuestName() == null ? "": tgGuestInfo.getGuestName());
						}
						toMortgageService.updateToMortgageBySign(toMortgageForUpdate);
					}
				}
			}
			if (toMortgageList.size() > 0) {
				for (int m = 0; m < toMortgageList.size(); m++) {
					if (toMortgageList.get(m) != null) {
						flag = false;
						break;
					}
				}
			}
			if (flag) {
				toMortgageService.updateToMortgageByCode(transSignVO.getCaseCode() == null ? ""	: transSignVO.getCaseCode());
			}

		}

		return "task/task" + transSignVO.getPartCode();
	}

	@RequestMapping(value = "submitSign")
	@ResponseBody
	public Result2 submitSign(TransSignVO transSignVO) {
		return signService.submitSign(transSignVO);
	}

	@RequestMapping(value = "/removeGuest")
	@ResponseBody
	public Boolean removeGuest(HttpServletRequest request, Long pkid) {
		tgGuestInfoService.removeGuestInfo(pkid);
		return true;
	}

	@RequestMapping(value = "queryGuestInfo")
	@ResponseBody
	public List<TgGuestInfo> queryGuestInfo(String caseCode,
			String transPosition) {
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(caseCode);
		tgGuestInfo.setTransPosition(transPosition);
		List<TgGuestInfo> list = tgGuestInfoService
				.findTgGuestInfosByCaseCode(tgGuestInfo);
		return list;
	}
}
