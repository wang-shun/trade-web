package com.centaline.trans.performance.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.performance.entity.Subject;
import com.centaline.trans.performance.service.PerfGoalService;
import com.centaline.trans.performance.service.SubjectService;
import com.centaline.trans.performance.vo.PerfGoalVo;

/**
 * 业绩目标设定
 * 
 * @author jjm
 *
 */

@Controller
@RequestMapping("/perf")
public class PerfGoalController {
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private PerfGoalService perfGoalService;
	@Autowired
	private SubjectService subjectService;

	/**
	 * 获得某月第一天
	 * 
	 * @param currentMonthDiff
	 * @return
	 */
	private Date getBelongMonth(int currentMonthDiff) {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.MONTH, currentMonthDiff);
		c1.set(Calendar.DAY_OF_MONTH, 1);
		return c1.getTime();
	}
	
	@RequestMapping("/verificationTime")
	@ResponseBody
	private AjaxResponse verificationTime(PerfGoalVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		vo.setOrgId(user.getServiceDepId());
		return AjaxResponse.successContent(perfGoalService.getMainStatus(vo));
		  
	}

	/**
	 * 业绩目标设定页面
	 * 
	 * @return
	 */
	@RequestMapping("/perfGoal")
	public String perfGoal(Model model, PerfGoalVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		/// 总监所带的组
		List<Org> orgs = uamUserOrgService.getOrgByParentId(user.getServiceDepId());

		if (vo.getBelongMonth() == null) {
			vo.setBelongMonth(getBelongMonth(0));
		}
		String mainStatus = perfGoalService.getMainStatus(vo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		model.addAttribute("orgs", orgs);
		model.addAttribute("belongMonth", sdf.format(vo.getBelongMonth()));
		model.addAttribute("mainStatus", mainStatus);// 主表状态
		return "performance/perfGoal";
	}
	
	@RequestMapping(value = "receivablePerfDetail")
	public String receivablePerfDetail(String caseCode , ServletRequest request,Model model) {
		SessionUser user =uamSessionService.getSessionUser();
		String orgId=user.getServiceDepId();
		String jobCode=user.getServiceJobCode();
		List<Subject> queryPeceivablePerfList = subjectService.querySubjectList();
		
		model.addAttribute("viewObject", request.getParameter("viewObject"));
		model.addAttribute("viewObjectId", request.getParameter("viewObjectId"));
		model.addAttribute("subjectList", queryPeceivablePerfList);
		return "performance/receivablePerf";
	}
	

	/**
	 * 目标设定方法
	 * 
	 * @return
	 */
	@RequestMapping("setPerfGoal")
	@ResponseBody
	public AjaxResponse setPerfGoal(PerfGoalVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		vo.setOrgId(user.getServiceDepId());
		perfGoalService.setPerfGoal(vo);
		return AjaxResponse.success();
	}

	/**
	 * 提交业绩目标
	 * 
	 * @return
	 */
	@RequestMapping("commitPerfGoal")
	@ResponseBody
	public AjaxResponse commitPerfGoal(PerfGoalVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		vo.setOrgId(user.getServiceDepId());
		int result = perfGoalService.commitPerfGoal(vo);
		if (result <= 0) {
			return AjaxResponse.fail("提交失败，请刷新后重试");
		}
		return AjaxResponse.success();
	}

	/**
	 * 获得未设定人数
	 * 
	 * @return
	 */
	@RequestMapping("getNotSetCount")
	@ResponseBody
	public AjaxResponse<String> getNotSetCount(PerfGoalVo vo) {
		SessionUser user = uamSessionService.getSessionUser();
		vo.setOrgId(user.getServiceDepId());
		int result = perfGoalService.getNotSetCount(vo);
		return AjaxResponse.successContent(result + "");
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("perfGoalAttainment")
	public String perfGoalAttainment(Model model) {
		SessionUser user =uamSessionService.getSessionUser();
		String orgId=user.getServiceDepId();
		String jobCode=user.getServiceJobCode();
		String dataView; //C:整个公司 D:贵宾服务中心 T:组 P:人员
		if(TransJobs.TZJL.getCode().equals(jobCode)){//总经理
			dataView="C";
		}else if (TransJobs.TZJ.getCode().equals(jobCode)){//总监
			dataView="D";
		}else if (TransJobs.TSJYZG.getCode().equals(jobCode) || TransJobs.TJYZG.getCode().equals(jobCode)){//主管，高级主管
			dataView="T";
		}else{
			dataView="P";//人员
		}
		model.addAttribute("dataView", dataView);
		return "performance/perfGoalAttainment";
	}
}
