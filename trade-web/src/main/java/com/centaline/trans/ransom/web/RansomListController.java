package com.centaline.trans.ransom.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.RansomPartEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.ransom.vo.ToRansomLinkVo;
import com.centaline.trans.ransom.vo.ToRansomVo;
import com.centaline.trans.ransom.vo.VRansomChangeUserVo;
import com.centaline.trans.utils.DateUtil;

/**
 * 赎楼单列表控制器
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "/ransomList")
public class RansomListController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	private AddRansomFormService addRansomFormService;
	@Autowired(required = true)
	private RansomListFormService ransomListFormService;
	@Autowired(required=true)
	private RansomService ransomService;
	@Autowired(required = true)
    UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	ResultNew rs=new ResultNew();
	
	
	/**
	 * 页面跳转 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="*/{keyFlag}")
	public String caseProcess(Model model, ServletRequest request,@PathVariable String keyFlag){
		model.addAttribute("flag",keyFlag);
		return "ransom/" + keyFlag;
	}
	
	/**
	 * 新建赎楼单
	 * @param model
	 * @param jsonStr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="addRansom")
	@ResponseBody
	public String addRansom(@RequestParam String jsonStr){
		
		String result = "-1";
		SessionUser user= uamSessionService.getSessionUser();
		
		try {
			List<ToRansomFormVo> list = JSONObject.parseArray(jsonStr, ToRansomFormVo.class);
			String ransomCode = new StringBuffer().append("TJ-SL-").append(getCalendarTime()).toString(); //赎楼单编号
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
				ransomFormVo.setLoanMoney((list.get(i).getLoanMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				ransomFormVo.setRestMoney((list.get(i).getRestMoney().multiply(new BigDecimal(Double.toString(10000.00)))));
				ransomFormVo.setCreateTime(new Date());
				ransomFormVo.setCreateUser(user.getId());
				ransomFormVo.setUpdateTime(new Date());
				ransomFormVo.setUpdateUser(user.getId());
				
				ransomList.add(ransomFormVo);
			}
			
			addRansomFormService.addRansomForm(ransomList);
			result = "0";
			if("0".equals(result)){
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);

				ToRansomCaseVo trco = new ToRansomCaseVo();
				//赎楼列表单插入数据
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
			}else{
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
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomLinkInfo")
	public String ransomLinkInfo(String caseCode,ServletRequest request) {
		ToRansomLinkVo ransomLinkVo= ransomService.getRansomLinkInfo(caseCode);
		
		request.setAttribute("ransomLinkVo", ransomLinkVo);
		return "ransom/addRansom";
	}
	
	
	/**
	 * 查询赎楼案件是否和caseCode关联
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value="queryRansomCode")
	@ResponseBody
	public String queryRansomByCasecode(String caseCode) {
		
		ResultNew rs=new ResultNew();
		try {
			ToRansomCaseVo trco = new ToRansomCaseVo();
			trco = ransomListFormService.getRansomCase(caseCode, null);
			String result = "-1";
			//如果赎楼信息不为空说明已有案件编号与赎楼编号相关联
			if(trco != null) {
				String message = "案件关联已被关联，请重新选择！";
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(message);
			}else {
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
	 * @param caseCode 案件编号
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomDetail")
	public String ransomDetail(String ransomCode, ServletRequest request){
		
		try {
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
			//案件详情信息
			ToRansomCaseVo caseVo = ransomService.getRansomCaseInfo(detailVo.getCaseCode());
			//赎楼详情信息
			
			//新建赎楼单即是受理状态
			ToRansomTailinsVo tailinsVo = ransomService.getTailinsInfoByCaseCode(detailVo.getCaseCode());
			//申请
			ToRansomApplyVo applyVo = ransomService.getApplyInfo(ransomCode);
			//面签
			ToRansomSignVo signVo = ransomService.getInterviewInfo(ransomCode);
			//陪同还贷
			ToRansomMortgageVo mortgageVo =  ransomService.getMortgageInfo(ransomCode);
			//注销抵押
			ToRansomCancelVo cancelVo = ransomService.getCancelInfo(ransomCode);
			//领取产证
			ToRansomPermitVo permitVo = ransomService.getPermitInfo(ransomCode);
			//回款结清
			ToRansomPaymentVo paymentVo = ransomService.getPaymentInfo(ransomCode);
			//计划时间信息
			List<ToRansomPlanVo> planVo = ransomService.getPlanTimeInfoByRansomCode(ransomCode);
			Date appTime = planVo.get(0).getEstPartTime();
			Date signTime = planVo.get(1).getEstPartTime();
			Date morTime = planVo.get(2).getEstPartTime();
			Date canTime = planVo.get(3).getEstPartTime();
			Date perTime = planVo.get(4).getEstPartTime();
			Date payTime = planVo.get(5).getEstPartTime();
			
			request.setAttribute("caseVo", caseVo);
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("signVo", signVo);
			request.setAttribute("applyVo", applyVo);
			request.setAttribute("mortgageVo", mortgageVo);
			request.setAttribute("cancelVo", cancelVo);
			request.setAttribute("permitVo", permitVo);
			request.setAttribute("paymentVo", paymentVo);
			
			request.setAttribute("appTime", appTime);
			request.setAttribute("signTime", signTime);
			request.setAttribute("morTime", morTime);
			request.setAttribute("canTime", canTime);
			request.setAttribute("perTime", perTime);
			request.setAttribute("payTime", payTime);
			return "ransom/ransomDetail";
		} catch (Exception e) {
			logger.error("",e);
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
		List<ToRansomPlanVo> planVo = ransomListFormService.getRansomPlanTimeInfo(ransomCode);
		request.setAttribute("ransomCode", ransomCode);
		request.setAttribute("planVo", planVo);
		return "ransom/ransomChangeRecord";
	}
	
	/**
	 * 查询赎楼单信息
	 * @param caseCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value="updateRansomInfo")
	public String queryUpdateRansomByCaseCode(String caseCode, ServletRequest request) {
		
		try {
			List list = ransomListFormService.getUpdateRansomInfo(caseCode);
			
			ToRansomTailinsVo tailinsVo = (ToRansomTailinsVo) list.get(0);
			List<TgGuestInfo> guestInfo = (List<TgGuestInfo>) list.get(1);
			ToRansomCaseVo caseVo = (ToRansomCaseVo) list.get(2);
			
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("guestInfo", guestInfo);
			request.setAttribute("caseVo", caseVo);
			return "ransom/ransomDetailUpdate";
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}
	
	/**
	 * 修改赎楼单
	 * @return
	 */
	@RequestMapping(value="updateRansom",method = RequestMethod.POST)
	@ResponseBody
	public String updateRansom(ToRansomVo ransomVo) {
		
		boolean flag = false;
		SessionUser user= uamSessionService.getSessionUser();
		
		try {
			
			ToRansomCaseVo caseVo = new ToRansomCaseVo();
			ToRansomTailinsVo tailinsVo = new ToRansomTailinsVo();
			
			if(ransomVo != null) {
				caseVo.setRansomCode(ransomVo.getRansomCode());
				caseVo.setBorrowerName(ransomVo.getBorrowerName());
				caseVo.setBorroMoney((ransomVo.getBorrowerMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				caseVo.setBorrowerTel(ransomVo.getBorrowerPhone());
				caseVo.setUpdateUser(user.getId());
				caseVo.setUpdateTime(new Date());
				
				tailinsVo.setRansomCode(ransomVo.getRansomCode());
				tailinsVo.setSignTime(DateUtil.strToFullDate(ransomVo.getSignTime()));
				tailinsVo.setFinOrgCode(ransomVo.getFinOrgCode());
				tailinsVo.setMortgageType(ransomVo.getMortgageType());
				tailinsVo.setDiyaType(ransomVo.getDiyaType());
				tailinsVo.setLoanMoney((ransomVo.getLoanMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				tailinsVo.setRestMoney((ransomVo.getRestMoney()).multiply(new BigDecimal(Double.toString(10000.00))));
				tailinsVo.setUpdateTime(new Date());
				tailinsVo.setUpdateUser(user.getId());
				
				flag = ransomListFormService.updateRansomCaseInfo(caseVo);
				flag = ransomListFormService.updateRansomTailinsInfo(tailinsVo);
				
				if(flag) {
					String status = "信息修改成功！";
					rs.setCode(status);
					rs.setMessage(status);
					rs.setStatus(status);
				}
			}
			return  JSONObject.toJSONString(rs);
		} catch (Exception e) {
			logger.error("",e);
			rs.setMessage(e.getMessage());
			return  JSONObject.toJSONString(rs);
		}
	}
	
	/**
	 * 计划时间信息
	 * @param ransomCode
	 * @param request
	 * @return
	 */
	@RequestMapping("planTime")
	public String ransomPlanTimeInfo(String ransomCode,ServletRequest request) {
		
		SessionUser user= uamSessionService.getSessionUser();
		
		try {
			
			ToRansomCaseVo caseVo = ransomService.getRansomInfoByRansomCode(ransomCode);
			List<ToRansomPlanVo> planVo = ransomListFormService.getRansomPlanTimeInfo(ransomCode);
			
			//如果计划时间信息为空，添加赎楼时间计划信息的ransomCode、更新人、更新时间
			if(planVo.isEmpty()) {
				//如果计划时间信息为空,根据赎楼单编号向时间计划添加每个环节partCode
				List<String> partCodeList = new ArrayList<String>();
				partCodeList.add("APPLY");
				partCodeList.add("SIGN");
				partCodeList.add("PAYLOAN_ONE");
				partCodeList.add("PAYLOAN_TWO");
				partCodeList.add("CANCELDIYA_ONE");
				partCodeList.add("CANCELDIYA_TWO");
				partCodeList.add("RECEIVE_ONE");
				partCodeList.add("RECEIVE_TWO");
				partCodeList.add("PAYCLEAR");
				
				
				List<ToRansomPlanVo> planList = new ArrayList<ToRansomPlanVo>();
				for(int i = 0; i < partCodeList.size(); i++ ) {
					ToRansomPlanVo ransomPlanVo = new ToRansomPlanVo();
					ransomPlanVo.setCreateTime(new Date());
					ransomPlanVo.setRansomCode(ransomCode);
					ransomPlanVo.setCreateUser(user.getId());
					ransomPlanVo.setUpdateUser(user.getId());
					ransomPlanVo.setUpdateTime(new Date());
					ransomPlanVo.setPartCode(partCodeList.get(i));
					
					planList.add(ransomPlanVo);
				}
				
				ransomListFormService.insertListRansomPlanTime(planList);
				//ransomListFormService.insertRansomPlanTimeInfo(ransomPlanVo);
				request.setAttribute("ransomCode", ransomCode);
				request.setAttribute("caseVo", caseVo);
				request.setAttribute("planVo", planList);
			}else {
				request.setAttribute("ransomCode", ransomCode);
				request.setAttribute("caseVo", caseVo);
				request.setAttribute("planVo", planVo);
			}
			
			
			return "ransom/ransomPlanTime";
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	/**
	 * 赎楼计划时间
	 * @param ransomVo
	 * @param flag
	 * @return
	 */
	@RequestMapping(value="updateRansomPlanTime",method = RequestMethod.POST)
	@ResponseBody
	public String updateRansomPlanTimeInfo(@RequestParam String ransomVo,int flag) {
		
		SessionUser user= uamSessionService.getSessionUser();
		
		try {
			List<ToRansomPlanVo> list = JSONObject.parseArray(ransomVo, ToRansomPlanVo.class);
			List<ToRansomPlanVo> ransomPlanVo = ransomListFormService.getRansomPlanTimeInfo(list.get(0).getRansomCode());
			
			List<ToRansomPlanVo> planList = new ArrayList<ToRansomPlanVo>();
			for(int i = 0; i < list.size(); i++) {
				ToRansomPlanVo ransomPlan = new ToRansomPlanVo();
				
				ransomPlan.setRansomCode(list.get(i).getRansomCode());
				ransomPlan.setPartCode(ransomPlanVo.get(i).getPartCode());
				ransomPlan.setEstPartTime(list.get(i).getEstPartTime());
				ransomPlan.setRemark(list.get(i).getRemark());
				ransomPlan.setUpdateTime(new Date());
				ransomPlan.setUpdateUser(user.getId());
				ransomPlan.setCreateTime(new Date());
				ransomPlan.setCreateUser(user.getId());
				
				planList.add(ransomPlan);
			}
			ransomListFormService.updateListPlanTimeInfo(planList );
			
			String status = "赎楼计划时间修改成功！";
			rs.setMessage(status);
			rs.setCode(String.valueOf(flag));
			return JSONObject.toJSONString(rs);
		} catch (Exception e) {
			logger.error("",e);
			rs.setMessage(e.getMessage());
			return  JSONObject.toJSONString(rs);
		}
	}
	
	/**
	 * 用户机构金融权证查询
	 * @param request
	 * @param caseCode
	 * @param operation
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getUserOrgFWUserList")
    @ResponseBody
	public List<VRansomChangeUserVo> getUserOrgFWUserList(HttpServletRequest request, String ransomCode,String operation) throws ParseException{
		
		//TODO 未写完
		List<VRansomChangeUserVo> res = new ArrayList<VRansomChangeUserVo>();
		
		// 获取当前用户
        SessionUser sessionUser = uamSessionService.getSessionUser();
        List<User> userList=new ArrayList<User>();
		
        if(operation == null && operation == "") {
        	userList = uamUserOrgService.getUserByOrgIdAndJobCode(sessionUser.getServiceDepId(),TransJobs.JRQZ.getCode());
        }
        //金融权证  id 电话 真实姓名 总单数 接单数 未处理单数
        
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
	
	public static StringBuffer getCalendarTime() {
		StringBuffer sb = new StringBuffer();
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// 获取年份
		int month = ca.get(Calendar.MONTH);// 获取月份
		int day = ca.get(Calendar.DATE);// 获取日
		int minute = ca.get(Calendar.MINUTE);// 分
		int hour = ca.get(Calendar.HOUR);// 小时
		int second=ca.get(Calendar.SECOND);//秒
		
		if(month < 10) {//+ String.valueOf("0" + month) + "-"
			 sb.append(String.valueOf(year)).append(String.valueOf(0)).append(String.valueOf(month)).append(String.valueOf("-"));
		}else {
			 sb.append(String.valueOf(year)).append(String.valueOf(month)).append(String.valueOf("-"));
			sb.append(String.valueOf(year) + String.valueOf(month) + "-");
		}
		
		if(minute < 10) {
			 sb.append(String.valueOf(0)).append(String.valueOf(minute));
		}else {
			sb.append(String.valueOf(minute));
		}
		
		if(second < 10) {
			sb.append(String.valueOf(0)).append(String.valueOf(second));
		}else {
			sb.append(String.valueOf(second));
		}
		
		return sb;
	}
	
	public static void main(String[] args) {
		for (RansomPartEnum e : RansomPartEnum.values()) {
			System.out.println(e.toString());
		}
	}
	
}
