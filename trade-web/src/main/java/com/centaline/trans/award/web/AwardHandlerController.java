package com.centaline.trans.award.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.quickQuery.bo.JQGridParam;
import com.aist.common.quickQuery.service.QuickGridService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.TransJobs;

@Controller
@RequestMapping(value = "/awards")
public class AwardHandlerController {

	@Resource(name = "quickGridService")
	private QuickGridService quickGridService;

	/**
	 * 个人绩效奖金数据展示
	 * @return
	 */
	@RequestMapping(value = "personalAward")
	public String personalAward() {
		return "award/personalAwardDetail2";
	}
/*	@RequestMapping(value = "personalAward2")
	public String personalAward2() {
		return "award/personalAwardDetail2";
	}*/

	/**
	 * 所有人绩效奖金数据展示
	 * @return
	 */
	@RequestMapping(value = "allAward")
	public String allAward() {
		return "award/allAward2";
	}
/*	@RequestMapping(value = "allAward2")
	public String allAward2() {
		return "award/allAward2";
	}*/

	/**
	 * 可计件奖金案件展示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "kjjianCase")
	public String kjjianCase(HttpServletRequest request) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 默认显示上个月的数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		request.setAttribute("guohuStart", sdf.format(c1.getTime()));//上个月第一天
		
		c2.set(Calendar.DAY_OF_MONTH, 1); 
		c2.add(Calendar.DATE, -1);
		request.setAttribute("guohuEnd", sdf.format(c2.getTime()));//上个月最后 一天 

		return "award/kjjianCase2";
	}
	@RequestMapping(value = "kjjianCase2")
	public String kjjianCase2(HttpServletRequest request) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 默认显示上个月的数据
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -1);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		request.setAttribute("guohuStart", sdf.format(c1.getTime()));//上个月第一天
		
		c2.set(Calendar.DAY_OF_MONTH, 1); 
		c2.add(Calendar.DATE, -1);
		request.setAttribute("guohuEnd", sdf.format(c2.getTime()));//上个月最后 一天 
		
		return "award/kjjianCase2";
	}

	@RequestMapping(value = "allBaseAwardCount")
	@ResponseBody
	public Map<String, String> allBaseAwardCount(String paidTime, String caseCode, String propertyAddr, String dtBegin,
			String dtEnd) {

		String countMsg = "";
		JQGridParam gp = new JQGridParam();
		gp.setPagination(false);
		gp.put("paidTime", paidTime);
		gp.put("caseCode", caseCode);
		gp.put("propertyAddr", propertyAddr);
		gp.put("dtBegin", dtBegin);
		gp.put("dtEnd", dtEnd);

		gp.setQueryId("totalCount");
		Page<Map<String, Object>> result = quickGridService.findPageForSqlServer(gp);
		Object srvPartInObj = result.getContent().get(0).get("SRV_PART_IN_COUNT");
		String srvPartInCount = null == srvPartInObj ? "0" : String.format("%.2f ", srvPartInObj);

		Object obj = result.getContent().get(0).get("CASE_CODE_COUNT");
		String caseCodeCount = null == obj ? "0" : String.valueOf(obj);
		countMsg = " 环节总数: " + srvPartInCount + ",交易单数: " + caseCodeCount;

		Map<String, String> model = new HashMap<String, String>();
		model.put("countMsg", countMsg);
		return model;
	}

}
