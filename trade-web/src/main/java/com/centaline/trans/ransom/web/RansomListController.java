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
			
			for (ToRansomFormVo arf : list) {
				arf.setRansomCode(new StringBuffer()
						.append("TJ-SL-").append(getCalendarTime())
						.toString()); //赎楼单编号
				arf.setLoanMoney(arf.getLoanMoney() * 10000);
				arf.setRestMoney(new BigDecimal(arf.getRestMoney().doubleValue() * 1000));
				arf.setCreateTime(new Date());
				arf.setCreateUser(user.getUsername());
				arf.setUpdateTime(new Date());
				arf.setUpdateUser(user.getUsername());
			}
			
			addRansomFormService.addRansomForm(list);
			result = "0";
			if("0".equals(result)){
				rs.setStatus(result);
				rs.setCode(result);
				rs.setMessage(result);

				ToRansomCaseVo trco = new ToRansomCaseVo();
				//赎楼列表单插入数据
				trco.setRansomCode(list.get(0).getRansomCode());
				trco.setCaseCode(list.get(0).getCaseCode());
				trco.setRansomStatus("RANSOMDEAL");
				trco.setRansomProperty("DEAL");
				trco.setBorrowerName(list.get(0).getBorrowerName());
				trco.setBorroMoney(list.get(0).getBorroMoney());
				trco.setAcceptTime(list.get(0).getPlanTime());
				trco.setCreateTime(new Date());
				trco.setCreateUser(user.getUsername());
				trco.setUpdateTime(new Date());
				trco.setUpdateUser(user.getUsername());
				
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
	public String ransomDetail(String caseCode, ServletRequest request){
		
		try {
			//案件详情信息
			ToRansomCaseVo caseVo = ransomService.getRansomCaseInfo(caseCode);
			//赎楼详情信息
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode, null);
			//新建赎楼单即是受理状态
			ToRansomTailinsVo tailinsVo = ransomService.getTailinsInfoByCaseCode(caseCode);
			//查询赎楼编号
			String ransomCode = tailinsVo.getRansomCode();
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
			
			request.setAttribute("caseVo", caseVo);
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("signVo", signVo);
			request.setAttribute("applyVo", applyVo);
			request.setAttribute("mortgageVo", mortgageVo);
			request.setAttribute("cancelVo", cancelVo);
			request.setAttribute("permitVo", permitVo);
			request.setAttribute("paymentVo", paymentVo);
			return "ransom/ransomDetail";
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
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
			//TsFinOrg finOrg = (TsFinOrg) list.get(3);
			
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("guestInfo", guestInfo);
			request.setAttribute("caseVo", caseVo);
			//request.setAttribute("finOrg", finOrg);
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
				caseVo.setBorroMoney(ransomVo.getBorrowerMoney().multiply(new BigDecimal(10000.00)));
				caseVo.setBorrowerTel(ransomVo.getBorrowerPhone());
				caseVo.setUpdateUser(user.getUsername());
				caseVo.setUpdateTime(new Date());
				
				tailinsVo.setRansomCode(ransomVo.getRansomCode());
				tailinsVo.setSignTime(DateUtil.strToFullDate(ransomVo.getSignTime()));
				tailinsVo.setFinOrgCode(ransomVo.getFinOrgCode());
				tailinsVo.setMortgageType(ransomVo.getMortgageType());
				tailinsVo.setDiyaType(ransomVo.getDiyaType());
				tailinsVo.setLoanMoney(ransomVo.getLoanMoney().multiply(new BigDecimal(10000.00)));
				tailinsVo.setRestMoney(ransomVo.getRestMoney().multiply(new BigDecimal(10000.00)));
				tailinsVo.setUpdateTime(new Date());
				tailinsVo.setUpdateUser(user.getUsername());
				
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
			ToRansomApplyVo applyVo = ransomService.getApplyInfo(ransomCode);
			ToRansomSignVo signVo = ransomService.getInterviewInfo(ransomCode);
			ToRansomMortgageVo mortgageVo = ransomService.getMortgageInfo(ransomCode);
			ToRansomCancelVo cancelVo = ransomService.getCancelInfo(ransomCode);
			ToRansomPermitVo permitVo = ransomService.getPermitInfo(ransomCode);
			ToRansomPaymentVo paymentVo = ransomService.getPaymentInfo(ransomCode);
			ToRansomCaseVo caseVo = ransomService.getRansomCaseInfo(ransomCode);
			List<ToRansomPlanVo> planVo = ransomListFormService.getRansomPlanTimeInfo(ransomCode);
			
			//如果计划时间信息为空，添加赎楼时间计划信息的ransomCode、更新人、更新时间
			if(planVo == null) {
				ToRansomPlanVo ransomPlanVo = new ToRansomPlanVo();
				ransomPlanVo.setCreateTime(new Date());
				ransomPlanVo.setRansomCode(ransomCode);
				ransomPlanVo.setCreateUser(user.getUsername());
				ransomPlanVo.setUpdateUser(user.getUsername());
				ransomPlanVo.setUpdateTime(new Date());
				ransomListFormService.insertRansomPlanTimeInfo(ransomPlanVo);
			}
			
			request.setAttribute("applyVo", applyVo);
			request.setAttribute("signVo", signVo);
			request.setAttribute("mortgageVo", mortgageVo);
			request.setAttribute("cancelVo", cancelVo);
			request.setAttribute("permitVo", permitVo);
			request.setAttribute("paymentVo", paymentVo);
			request.setAttribute("caseVo", caseVo);
			request.setAttribute("ransomCode", ransomCode);
			
			return "ransom/ransomPlanTime";
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}
	
	
	@RequestMapping(value="updateRansomPlanTime",method = RequestMethod.POST)
	@ResponseBody
	public String updateRansomPlanTimeInfo(@RequestParam String ransomVo,int flag) {
		
		SessionUser user= uamSessionService.getSessionUser();
		
		try {
			List<ToRansomPlanVo> list = JSONObject.parseArray(ransomVo, ToRansomPlanVo.class);
			List<ToRansomPlanVo> ransomPlanVo = ransomListFormService.getRansomPlanTimeInfo(list.get(0).getRansomCode());
			ToRansomPlanVo ransomPlan = new ToRansomPlanVo();
			
			List<String> partCode = new ArrayList<String>();
			
			for(int i = 0; i < ransomPlanVo.size(); i++) {
				 partCode.add(ransomPlanVo.get(i).getPartCode());
			}
			
			for (ToRansomPlanVo planVo : list) {
				
				ransomPlan.setRansomCode(planVo.getRansomCode());
				ransomPlan.setPartCode(planVo.getPartCode());
				ransomPlan.setEstPartTime(planVo.getEstPartTime());
				ransomPlan.setRemark(planVo.getRemark());
				ransomPlan.setUpdateTime(new Date());
				ransomPlan.setUpdateUser(user.getUsername());
				ransomPlan.setCreateTime(new Date());
				ransomPlan.setCreateUser(user.getUsername());
				
				//如果查询存在此环节有记录则进行修改，没有进行插入
				if (partCode.contains("APPLY") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				} else if(partCode.contains("SIGN") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("PAYLOAN_ONE") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("PAYLOAN_TWO") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("CANCELDIYA_ONE") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("CANCELDIYA_TWO") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("RECEIVE_ONE") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("RECEIVE_TWO") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else if(partCode.contains("PAYCLEAR") && partCode.contains(planVo.getPartCode())) {
					ransomListFormService.updateRansomPlanTimeInfo(ransomPlan);
				}else {
					ransomListFormService.insertRansomPlanTimeInfo(ransomPlan);
				}
			}
			
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
	
	
}
