package com.centaline.trans.eval.web;

import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.eval.entity.ToEvaFeeRecord;
import com.centaline.trans.eval.service.ToEvaFeeRecordService;

/**
 * 
 * <p>Project: 评估费核实</p>
 * <p>Description: </p>
 * <p>Copyright (c) 2015</p>
 * @author wanggh</a>
 */
@Controller
@RequestMapping(value="/eval")
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
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="evalFeeDesign")
	public String evalFeeDesign(ServletRequest request){
		//TODO
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
		toEvaFeeRecord.setRecordTime(new Date());
		if(toEvaFeeRecord.getPkid()==null){

			ToEvaFeeRecord oldItem = toEvaFeeRecordService.findToEvaFeeRecordByCaseCode(toEvaFeeRecord.getCaseCode());
	    	if(oldItem!=null&&oldItem.getPkid()!=null)return "请不要重复提交";
			toEvaFeeRecordService.insertSelective(toEvaFeeRecord);
		}else{
			toEvaFeeRecordService.updateByPrimaryKeySelective(toEvaFeeRecord);
		}
		
		return this.evalFeeDesign(request);
	}
}
