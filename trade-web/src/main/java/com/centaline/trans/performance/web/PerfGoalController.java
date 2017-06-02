package com.centaline.trans.performance.web;

import java.util.Date;
import java.util.List;

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
import com.centaline.trans.performance.service.PerfGoalService;
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

	/**
	 * 业绩目标设定页面
	 * 
	 * @return
	 */
	@RequestMapping("/perfGoal")
	public String perfGoal(Model model,Date belongMonth) {
		SessionUser user = uamSessionService.getSessionUser();
		/// 总监所带的组
		List<Org> orgs = uamUserOrgService.getOrgByParentId(user.getServiceDepId());
		model.addAttribute("orgs", orgs);
		if(belongMonth==null){
			belongMonth=new Date();
		}
		model.addAttribute("belongMonth", belongMonth);
		return "performance/perfGoal";
	}

	/**
	 * 目标设定方法
	 * 
	 * @return
	 */
	@RequestMapping("setPerfGoal")
	@ResponseBody
	public AjaxResponse setPerfGoal(PerfGoalVo vo) {
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
	public AjaxResponse getNotSetCount(PerfGoalVo vo) {
		int result = perfGoalService.getNotSetCount(vo);
		return AjaxResponse.success(result + "");
	}
}
