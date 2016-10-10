package com.centaline.trans.eval.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;
import com.centaline.trans.workspace.entity.WorkSpace;
import com.ctc.wstx.util.StringUtil;

/**
 * 
 * <p>
 * Project: 评估费核实
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright (c) 2015
 * </p>
 * 
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value = "/eval")
public class EvalListController {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	UamBasedataService uamBasedataService;
	@Autowired(required = true)
	MyCaseListService myCaseListService;
	@Autowired(required = true)
	ToEvaFeeRecordService toEvaFeeRecordService;

	/**
	 * 页面初始化
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "evalFeeDesign")
	public String evalFeeDesign(ServletRequest request) {
		// TODO
		SessionUser user = uamSessionService.getSessionUser();
		String userOrgId = user.getServiceDepId();

		request.setAttribute("queryOrg", userOrgId);

		return "eval/evalFeeDesign";
	}

	/**
	 * 
	 * 更新评估费
	 * 
	 * @param busIC
	 * @param request
	 * @return 描述
	 * 
	 */
	@RequestMapping("/saveEvalItem")
	public String saveEvalItem(ToEvaFeeRecord toEvaFeeRecord, HttpServletRequest request) {
		
		/* 删除评估费清空评估费金额及最后收取时间 */
		if(toEvaFeeRecord.getEvalFee()!=null){
			toEvaFeeRecord.setRecordTime(new Date());
		}
		
		if (toEvaFeeRecord.getPkid() == null) {

			ToEvaFeeRecord oldItem = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(toEvaFeeRecord.getCaseCode());
			if (oldItem != null && oldItem.getPkid() != null)
				return "请不要重复提交";
			toEvaFeeRecordService.insertSelective(toEvaFeeRecord);
		} else {
			toEvaFeeRecordService.updateByPrimaryKeySelective(toEvaFeeRecord);
		}

		return this.evalFeeDesign(request);
	}

	@RequestMapping("/evalListStatistics")
	public String toEvalListStatistics(String source, String mo, String serachId, HttpServletRequest request) {
		SessionUser sessionUser = uamSessionService.getSessionUser();
		String jobCode = sessionUser.getServiceJobCode();
		if ("dashboard".equals(source)) {
			if (!StringUtils.isBlank(serachId)) {
				if (TransJobs.TJYZG.getCode().equals(jobCode) || TransJobs.TSJYZG.getCode().equals(jobCode)) {
					request.setAttribute("serUserId", serachId);
					request.setAttribute("userInfo", getUserInfo(serachId));
				} else {
					request.setAttribute("serOrgId", serachId);
					Org org=uamUserOrgService.getOrgById(serachId);
					request.setAttribute("serOrgName", org.getOrgName());
				}
			}
			if (TransJobs.TJYGW.getCode().equals(jobCode)) {
				request.setAttribute("isConsultant", true);
				request.setAttribute("serUserId", sessionUser.getId());
			}

			Calendar cal = Calendar.getInstance();
			if (!StringUtils.isBlank(mo)) {
				cal.set(Calendar.MONTH, Integer.valueOf(mo)-1);
			}
			cal.set(Calendar.DAY_OF_MONTH, 1);
			request.setAttribute("sTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			cal.roll(Calendar.DAY_OF_MONTH, -1);
			request.setAttribute("eTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));

		}
		request.setAttribute("serviceDepId", sessionUser.getServiceDepId());
		return "eval/evalListStatistics";
	}

	private String getUserInfo(String userId) {
		if (StringUtils.isBlank(userId)) {
			return userId;
		}
		List<UserOrgJob> uojs = uamUserOrgService.getUserOrgJobByUserId(userId);
		if (uojs != null && !uojs.isEmpty()) {
			UserOrgJob uoj = null;
			if (uojs.size() == 1) {
				uoj = uojs.get(0);
			} else {
				for (UserOrgJob userOrgJob : uojs) {
					if ("1".equals(userOrgJob.getIsmain())) {
						uoj = userOrgJob;
						break;
					}
				}
			}
			if (uoj == null) {
				uoj = uojs.get(0);
			}
			User user = uamUserOrgService.getUserById(userId);
			return user.getRealName() ;
		}
		return null;
	}
}
