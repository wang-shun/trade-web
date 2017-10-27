package com.centaline.trans.ransom.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;
import com.centaline.trans.ransom.vo.ToRansomMoneyVo;
import com.centaline.trans.ransom.vo.ToRansomVo;
import com.centaline.trans.ransom.vo.VRansomChangeUserVo;
import com.centaline.trans.ransom.vo.VRansomFinishTaskVo;
import com.centaline.trans.utils.DateUtil;

/**
 * 赎楼单列表控制器
 * 
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "/ransomList")
public class RansomListController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 赎楼编码前缀
	 */
	private static String EVA_CODE_PRE = "SL-TJ-";
	
	@Autowired
	private ToCaseMapper tocaseMapper;
	@Autowired(required = true)
	private AddRansomFormService addRansomFormService;
	@Autowired(required = true)
	private RansomListFormService ransomListFormService;
	@Autowired(required = true)
	private RansomService ransomService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;

	ResultNew rs = new ResultNew();

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "*/{keyFlag}")
	public String caseProcess(Model model, ServletRequest request, @PathVariable String keyFlag) {
		model.addAttribute("flag", keyFlag);
		return "ransom/" + keyFlag;
	}

	/**
	 * 新建赎楼单
	 * 
	 * @param model
	 * @param jsonStr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addRansom")
	@ResponseBody
	public String addRansom(@RequestParam String jsonStr) {

		String result = "-1";
		SessionUser user = uamSessionService.getSessionUser();

		try {
			List<ToRansomFormVo> list = JSONObject.parseArray(jsonStr, ToRansomFormVo.class);
			String ransomCode = generateEvaCode(); // 赎楼单编号
			String caseCode = list.get(0).getCaseCode();
			List<ToRansomFormVo> ransomList = new ArrayList<ToRansomFormVo>();

			for (int i = 0; i < list.size(); i++) {
				ToRansomFormVo ransomFormVo = new ToRansomFormVo();

				ransomFormVo.setCaseCode(caseCode);
				ransomFormVo.setRansomCode(ransomCode);
				ransomFormVo.setSignTime(list.get(0).getSignTime());
				ransomFormVo.setPlanTime(list.get(0).getPlanTime());
				ransomFormVo.setFinOrgCode(list.get(i).getFinOrgCode());
				ransomFormVo.setMortgageType(list.get(i).getMortgageType());
				ransomFormVo.setDiyaType(list.get(i).getDiyaType());
				ransomFormVo
						.setLoanMoney((list.get(i).getLoanMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				ransomFormVo
						.setRestMoney((list.get(i).getRestMoney().multiply(new BigDecimal(Double.toString(10000.00)))));
				ransomFormVo.setCreateTime(new Date());
				ransomFormVo.setCreateUser(user.getId());
				ransomFormVo.setUpdateTime(new Date());
				ransomFormVo.setUpdateUser(user.getId());

				ransomList.add(ransomFormVo);
			}

			addRansomFormService.addRansomForm(ransomList);

			ToRansomPlanVo planVo = new ToRansomPlanVo();
			planVo.setRansomCode(ransomCode);
			planVo.setPartCode("APPLY");
			planVo.setEstPartTime(list.get(0).getPlanTime());
			planVo.setCreateUser(user.getId());
			planVo.setCreateTime(new Date());
			planVo.setUpdateTime(new Date());
			planVo.setUpdateUser(user.getId());
			ransomListFormService.insertRansomPlanTimeInfo(planVo);

			result = "0";
			if ("0".equals(result)) {
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);

				ToRansomCaseVo trco = new ToRansomCaseVo();
				// 赎楼列表单插入数据
				trco.setRansomCode(ransomCode);
				trco.setCaseCode(caseCode);
				trco.setRansomStatus("1");
				trco.setRansomProperty("DEAL");
				trco.setBorrowerName(list.get(0).getBorrowerName());
				trco.setBorrowerTel(list.get(0).getBorrowerTel());
				trco.setBorroMoney((list.get(0).getBorroMoney().multiply(new BigDecimal(Double.toString(10000.00)))));
				trco.setAcceptTime(list.get(0).getPlanTime());
				trco.setCreateTime(new Date());
				trco.setCreateUser(user.getId());
				trco.setUpdateTime(new Date());
				trco.setUpdateUser(user.getId());

				ransomListFormService.addRansomDetail(trco);
			} else {
				String message = "新增案件失败，请刷新后再次尝试！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			}

			return JSONObject.toJSONString(rs);
		} catch (BusinessException ex) {
			logger.error("", ex);
			rs.setStatus("-1");
			rs.setMessage(ex.getMessage());
			return JSONObject.toJSONString(rs);
		}
	}

	/**
	 * 案件关联信息查询
	 * 
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ransomLinkInfo")
	public String ransomLinkInfo(String caseCode, ServletRequest request) {
		ToRansomLinkVo ransomLinkVo = ransomService.getRansomLinkInfo(caseCode);

		request.setAttribute("ransomLinkVo", ransomLinkVo);
		return "ransom/addRansom";
	}

	/**
	 * 查询赎楼案件是否和caseCode关联
	 * 
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value = "queryRansomCode")
	@ResponseBody
	public String queryRansomByCasecode(String caseCode) {

		ResultNew rs = new ResultNew();
		try {
			ToRansomCaseVo trco = new ToRansomCaseVo();
			trco = ransomListFormService.getRansomCase(caseCode, null);
			String result = "-1";
			// 如果赎楼信息不为空说明已有案件编号与赎楼编号相关联
			if (trco != null) {
				String message = "案件关联已被关联，请重新选择！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			} else {
				result = "0";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);
			}
			return JSONObject.toJSONString(rs);
		} catch (BusinessException ex) {
			logger.error("", ex);
			rs.setStatus("0");
			rs.setMessage(ex.getMessage());
			return JSONObject.toJSONString(rs);
		}
	}

	/**
	 * 赎楼详情画面
	 * 
	 * @param caseCode
	 *            案件编号
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ransomDetail")
	public String ransomDetail(String ransomCode, ServletRequest request) {

		try {
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
			// 案件详情信息
			// ToRansomCaseVo caseVo = ransomService.getRansomInfoByRansomCode(ransomCode);
			Map<String, String> actTasks = ransomService.getActTasks(ransomCode);
			ToRansomMoneyVo moneyVo = ransomListFormService.getRansomDetailMoneyInfo(ransomCode);
			// 新建赎楼单即是受理状态
			List<ToRansomTailinsVo> ransomTailinsVo = ransomService.getTailinsInfoByRansomCode(ransomCode);
			ToRansomTailinsVo tailinsVo = ransomTailinsVo.get(0);
			// 申请
			ToRansomApplyVo applyVo = ransomService.getApplyInfo(ransomCode);
			// 面签
			ToRansomSignVo signVo = ransomService.getInterviewInfo(ransomCode);
			// 陪同还贷
			Map<String, Date> mortgageMap = ransomService.getMortgageInfoByRansomCode(ransomCode);
			// 注销抵押
			Map<String, Date> cancelMap = ransomService.getCancelInfo(ransomCode);
			// 领取产证
			Map<String, Date> permitMap = ransomService.getPermitInfo(ransomCode);
			// 回款结清
			ToRansomPaymentVo paymentVo = ransomService.getPaymentInfo(ransomCode);
			// 计划时间信息
			List<ToRansomPlanVo> planVo = ransomService.getPlanTimeInfoByRansomCode(ransomCode);

			if (!planVo.isEmpty()) {
				Date appTime = tailinsVo.getPlanTime();
				Date signTime = null;
				Date morTime = null;
				Date canTime = null;
				Date perTime = null;
				Date payTime = null;
				for (int i = 0; i < planVo.size(); i++) {
					if (i == 0) {
						signTime = planVo.get(0).getEstPartTime();
					}
					if (i == 1) {
						morTime = planVo.get(1).getEstPartTime();
					}
					if (i == 2) {
						canTime = planVo.get(2).getEstPartTime();
					}
					if (i == 3) {
						perTime = planVo.get(3).getEstPartTime();
					}
					if (i == 4) {
						payTime = planVo.get(4).getEstPartTime();
					}
				}

				request.setAttribute("appTime", appTime);
				request.setAttribute("signTime", signTime);
				request.setAttribute("morTime", morTime);
				request.setAttribute("canTime", canTime);
				request.setAttribute("perTime", perTime);
				request.setAttribute("payTime", payTime);
			}

			// request.setAttribute("caseVo", caseVo);
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("moneyVo", moneyVo);
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("signVo", signVo);
			request.setAttribute("applyVo", applyVo);
			request.setAttribute("mortgageMap", mortgageMap);
			request.setAttribute("cancelMap", cancelMap);
			request.setAttribute("permitMap", permitMap);
			request.setAttribute("paymentVo", paymentVo);
			request.setAttribute("actTasks", actTasks);

			return "ransom/ransomDetail";
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}

	}

	/**
	 * 跳转赎楼变更详情页面
	 * @param ransomCode
	 * @param request
	 * @return
	 */
	@RequestMapping("ransomChangeRecord")
	public String changeRecord(String ransomCode, ServletRequest request) {
		
		List<ToRansomPlanVo> plans 	= ransomListFormService.getRansomPlanChangeRecordByRansomCode(ransomCode);

		request.setAttribute("ransomCode", ransomCode);
		request.setAttribute("plans", plans);
		return "ransom/ransomChangeRecord";
	}

	/**
	 * 查询赎楼单信息
	 * 
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateRansomInfo")
	public String queryUpdateRansomByCaseCode(String caseCode, ServletRequest request) {

		try {

			List<ToRansomTailinsVo> tailinsVoList = ransomListFormService.getTailinsInfoByCaseCode(caseCode);
			List<TgGuestInfo> guestInfo = ransomListFormService.getGuestInfo(caseCode);
			ToRansomCaseVo caseVo = ransomListFormService.getRansomCase(caseCode);
			VRansomFinishTaskVo taskVo = ransomListFormService.getRansomTaskInfoByRansomCode(caseVo.getRansomCode());
			int count = ransomService.queryErdiByRansomCode(caseVo.getRansomCode());

			request.setAttribute("tailinsVoList", tailinsVoList);
			request.setAttribute("guestInfo", guestInfo);
			request.setAttribute("caseVo", caseVo);
			request.setAttribute("taskVo", taskVo);
			request.setAttribute("count", count);
			return "ransom/ransomDetailUpdate";
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * 修改赎楼单
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateRansom", method = RequestMethod.POST)
	@ResponseBody
	public String updateRansom(@RequestParam String ransomVo) {

		boolean flag = true;
		SessionUser user = uamSessionService.getSessionUser();

		try {
			List<ToRansomVo> ransomVoList = JSONObject.parseArray(ransomVo, ToRansomVo.class);

			ToRansomCaseVo caseVo = new ToRansomCaseVo();

			if (!ransomVoList.isEmpty()) {

				for (ToRansomVo vo : ransomVoList) {
					ToRansomTailinsVo tailinsVo = new ToRansomTailinsVo();
					tailinsVo.setRansomCode(vo.getRansomCode());
					tailinsVo.setSignTime(DateUtil.strToFullDate(vo.getSignTime()));
					tailinsVo.setFinOrgCode(vo.getFinOrgCode());
					tailinsVo.setMortgageType(vo.getMortgageType());
					tailinsVo.setDiyaType(vo.getDiyaType());
					tailinsVo.setLoanMoney(vo.getLoanMoney());
					tailinsVo.setRestMoney(vo.getRestMoney());
					tailinsVo.setUpdateUser(user.getId());
					flag = flag && ransomListFormService.updateRansomTailinsInfo(tailinsVo);
				}
				caseVo.setRansomCode(ransomVoList.get(0).getRansomCode());
				caseVo.setBorrowerName(ransomVoList.get(0).getBorrowerName());
				caseVo.setBorroMoney(ransomVoList.get(0).getBorrowerMoney());
				caseVo.setBorrowerTel(ransomVoList.get(0).getBorrowerPhone());
				caseVo.setUpdateUser(user.getId());
				caseVo.setUpdateTime(new Date());
				flag = flag && ransomListFormService.updateRansomCaseInfo(caseVo);

				if (flag) {
					String status = "信息修改成功！";
					rs.setCode(status);
					rs.setMessage(status);
					rs.setStatus(status);
				}
			}
			return JSONObject.toJSONString(rs);
		} catch (Exception e) {
			logger.error("", e);
			rs.setMessage(e.getMessage());
			return JSONObject.toJSONString(rs);
		}
	}

	/**
	 * 计划时间信息
	 * 
	 * @param ransomCode
	 * @param request
	 * @return
	 */
	@RequestMapping("planTime")
	public String ransomPlanTimeInfo(String ransomCode, ServletRequest request) {

		/*
		 * try { SessionUser user= uamSessionService.getSessionUser(); ToRansomCaseVo
		 * caseVo = ransomService.getRansomInfoByRansomCode(ransomCode);
		 * List<ToRansomPlanVo> toRansomPlanVoList =
		 * ransomListFormService.getRansomPlanTimeInfo(ransomCode); //将List 转为Map
		 * 便于页面通过partCode获取数据 Map<String,ToRansomPlanVo> planMap = new HashMap<>();
		 * for(ToRansomPlanVo plan : toRansomPlanVoList) {
		 * planMap.put(plan.getPartCode(), plan); } int count =
		 * ransomService.queryErdiByRansomCode(ransomCode);
		 * 
		 * request.setAttribute("ransomCode", ransomCode);
		 * request.setAttribute("caseVo", caseVo); request.setAttribute("planMap",
		 * planMap); request.setAttribute("count", count);
		 * 
		 * return "ransom/ransomPlanTime"; } catch (Exception e) { logger.error("",e);
		 * return null; }
		 */

		request.setAttribute("ransomCode", ransomCode);

		return "ransom/ransomPlanTime";
	}

	/**
	 * 获取计划时间数据
	 * 
	 * @param ransomCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getPlanTimeDate")
	@ResponseBody
	public AjaxResponse<ToRansomFormVo> getPlanTimeDate(String ransomCode) {
		// 计划时间数据
		ToRansomFormVo planVo = ransomListFormService.getRansomPlanTimeInfo(ransomCode);

		return AjaxResponse.successContent(planVo);
	}

	/**
	 * 赎楼计划时间保存
	 * @author wbcaiyx
	 * @param ransomVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="updateRansomPlanTime",method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse<String> updateRansomPlanTimeInfo(String ransomVo) {
		
		ToRansomFormVo vo = JSONObject.parseObject(ransomVo, ToRansomFormVo.class);
		AjaxResponse<String> result = null;
		try{
			result = ransomListFormService.updateRansomPlanTimeInfo(vo);
		}catch (Exception e){
			
			return AjaxResponse.failException(e);
		}
		return result;
	}

	/**
	 * 用户机构金融权证查询
	 * 
	 * @param request
	 * @param caseCode
	 * @param operation
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getUserOrgFWUserList")
	@ResponseBody
	public List<VRansomChangeUserVo> getUserOrgFWUserList(HttpServletRequest request, String ransomCode,
			String operation) throws ParseException {

		List<VRansomChangeUserVo> res = new ArrayList<VRansomChangeUserVo>();

		// 获取当前用户
		SessionUser sessionUser = uamSessionService.getSessionUser();
		List<User> userList = new ArrayList<User>();

		if (operation == null && operation == "") {
			userList = uamUserOrgService.getUserByOrgIdAndJobCode(sessionUser.getServiceDepId(),
					TransJobs.JRQZ.getCode());
		}
		// 金融权证 id 电话 真实姓名 总单数 接单数 未处理单数

		for (int i = 0; i < userList.size(); i++) {
			VRansomChangeUserVo userVo = new VRansomChangeUserVo();

			User user = userList.get(i);
			userVo.setId(user.getId());
			userVo.setMobile(user.getMobile());
			userVo.setRealName(user.getRealName());

			int userRansomCount = ransomListFormService.queryCountRansomsByUserId(user.getId());
			int userRansomMonthCount = ransomListFormService.queryCountMonthRansomsByUserId(user.getId());

			userVo.setUserCaseCount(userRansomCount);
			userVo.setUserCaseMonthCount(userRansomMonthCount);

			res.add(userVo);
		}

		return res;
	}

	/**
	 * 自生成赎楼编号
	 * @return
	 */
	private String generateEvaCode(){	
		StringBuilder s = new StringBuilder();
		s.append(EVA_CODE_PRE);
		s.append(DateUtil.getFormatDate(new Date(), "yyyyMM"));
		s.append(tocaseMapper.nextCaseCodeNumber());
		return s.toString();
	}

}
