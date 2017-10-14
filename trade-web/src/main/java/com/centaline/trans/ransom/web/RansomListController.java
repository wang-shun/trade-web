package com.centaline.trans.ransom.web;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.web.ResultNew;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.ransom.vo.ToRansomVo;

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
			Calendar ca = Calendar.getInstance();
			int month = ca.get(Calendar.MONTH);// 获取月份
			int day = ca.get(Calendar.DATE);// 获取日
			int minute = ca.get(Calendar.MINUTE);// 分
			int hour = ca.get(Calendar.HOUR);// 小时
			int second=ca.get(Calendar.SECOND);//秒
		      
			List<ToRansomFormVo> list = JSONObject.parseArray(jsonStr, ToRansomFormVo.class);
			
			for (ToRansomFormVo arf : list) {
				arf.setRansomCode("TJ-ZH-" + month + day + minute + hour + second); //赎楼单编号
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
			trco = ransomListFormService.getRansomCase(caseCode);
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
			ToRansomDetailVo detailVo = ransomService.getRansomDetail(caseCode);
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
			
			request.setAttribute("detailVo", detailVo);
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("signVo", signVo);
			request.setAttribute("applyVo", applyVo);
			request.setAttribute("mortgageVo", mortgageVo);
			request.setAttribute("cancelVo", cancelVo);
			request.setAttribute("permitVo", permitVo);
			request.setAttribute("paymentVo", paymentVo);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "ransom/ransomDetail";
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
			TsFinOrg finOrg = (TsFinOrg) list.get(3);
			
			request.setAttribute("tailinsVo", tailinsVo);
			request.setAttribute("guestInfo", guestInfo);
			request.setAttribute("caseVo", caseVo);
			request.setAttribute("finOrg", finOrg);
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
				caseVo.setBorroMoney(ransomVo.getBorrowerMoney() * 10000);
				caseVo.setBorrowerTel(ransomVo.getBorrowerPhone());
				caseVo.setUpdateUser(user.getUsername());
				caseVo.setUpdateTime(new Date());
				
				tailinsVo.setRansomCode(ransomVo.getRansomCode());
				tailinsVo.setFinOrgCode(ransomVo.getFinOrgCode());
				tailinsVo.setMortgageType(ransomVo.getMortgageType());
				tailinsVo.setDiyaType(ransomVo.getDiyaType());
				tailinsVo.setLoanMoney(ransomVo.getLoanMoney() * 10000);
				tailinsVo.setRestMoney(ransomVo.getRestMoney() * 10000);
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
		
		ToRansomVo ransomVo = ransomListFormService.getRansomPlanInfo(ransomCode);
		request.setAttribute("ransomVo", ransomVo);
		
		return "ransom/ransomPlanTime";
	}
	
	/**
	 * 计划时间信息更新
	 * @param ransomVo
	 * @return
	 */
	@RequestMapping(value="updateRansomPlanTime",method = RequestMethod.POST)
	@ResponseBody
	public String updateRansomPlanTimeInfo(ToRansomVo ransomVo) {
		
		try {
			
			ransomListFormService.updateRansomApplyInfo(ransomVo);
			ransomListFormService.updateRansomInterviewInfo(ransomVo);
			ransomListFormService.updateRansomRepayInfo(ransomVo);
			ransomListFormService.updateRansomCancelInfo(ransomVo);
			ransomListFormService.updateRansomRedeemInfo(ransomVo);
			ransomListFormService.updateRansomPaymentInfo(ransomVo);
			
			String status = "赎楼计划时间修改成功！";
			rs.setMessage(status);
			return JSONObject.toJSONString(rs);
		} catch (Exception e) {
			logger.error("",e);
			rs.setMessage(e.getMessage());
			return  JSONObject.toJSONString(rs);
		}
	}
}
