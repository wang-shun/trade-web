package com.centaline.trans.ransom.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.ransom.entity.ToRansomApplyVo;
import com.centaline.trans.ransom.entity.ToRansomCancelVo;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.entity.ToRansomMortgageVo;
import com.centaline.trans.ransom.entity.ToRansomPaymentVo;
import com.centaline.trans.ransom.entity.ToRansomPermitVo;
import com.centaline.trans.ransom.entity.ToRansomPlanVo;
import com.centaline.trans.ransom.entity.ToRansomSignVo;
import com.centaline.trans.ransom.entity.ToRansomSubmitVo;
import com.centaline.trans.ransom.service.AddRansomFormService;
import com.centaline.trans.ransom.service.RansomChangeService;
import com.centaline.trans.ransom.service.RansomListFormService;
import com.centaline.trans.ransom.service.RansomService;

/**
 * 赎楼单前往修改Controller
 * @author wbwumf
 *
 *2017年10月25日
 */
@Controller
@RequestMapping(value = "/ransomChange")
public class RansomChangeController {

private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	private AddRansomFormService addRansomFormService;
	@Autowired(required = true)
	private RansomListFormService ransomListFormService;
	@Autowired(required = true)
	private RansomChangeService ransomChangeService;
	@Autowired(required=true)
	private  RansomService ransomService;
	@Autowired(required = true)
    UamUserOrgService uamUserOrgService;
	@Autowired
	private UamSessionService uamSessionService;
	
	/**
	 * 赎楼申请
	 * @param ransomCode
	 * @param partCode
	 * @param request
	 * @return
	 */
	@RequestMapping("applyView")
	public String toApplyView(String ransomCode,String partCode,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		ToRansomApplyVo applyVo = ransomService.getApplyInfo(ransomCode);
		
		request.setAttribute("detailVo", detailVo);
		request.setAttribute("applyVo", applyVo);
		request.setAttribute("partCode", partCode);
		
		return "ransom/ransomApplyUpdate";
	}
	
	/**
	 * 赎楼申请修改
	 * @param applyVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveApply")
	@ResponseBody
	public boolean updateApply(ToRansomApplyVo applyVo, ServletRequest request) {
		
		try{
			ransomChangeService.updateRansomApplyInfo(applyVo);
			return true;
		}catch(Exception e){
			logger.error("",e);
			return false;
		}
	}
	
	/**
	 * 面签
	 * @param ransomCode
	 * @param partCode
	 * @param request
	 * @return
	 */
	@RequestMapping("signView")
	public String toSignView(String ransomCode,String partCode,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		ToRansomSignVo signVo = ransomService.getInterviewInfo(ransomCode);
		
		request.setAttribute("detailVo", detailVo);
		request.setAttribute("signVo", signVo);
		request.setAttribute("partCode", partCode);
		
		return "ransom/ransomInterviewChange";
	}
	
	/**
	 * 赎楼面签修改
	 * @param applyVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveSign")
	@ResponseBody
	public boolean updateSign(ToRansomSignVo signVo, ServletRequest request) {
		
		//数据更新,申请表,赎楼表
		try{
			ransomChangeService.updateRansomSignInfo(signVo);
			return true;
		}catch(Exception e){
			logger.error("",e);
			return false;
		}
	}
	
	/**
	 * 面签
	 * @param ransomCode
	 * @param partCode
	 * @param request
	 * @return
	 */
	@RequestMapping("payloanView")
	public String toPayloanView(String ransomCode,String partCode,@RequestParam("count") Integer count,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		ToRansomMortgageVo payloanVo = ransomService.getMortgageInfo(ransomCode,count);
		List<ToRansomPlanVo> planVoList = ransomService.getPlanTimeInfo(ransomCode);
		
		Map<String,ToRansomPlanVo> planVoMap = new HashMap<String, ToRansomPlanVo>();
		for (ToRansomPlanVo ransomPlanVo : planVoList) {
			planVoMap.put(ransomPlanVo.getPartCode(), ransomPlanVo);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("partCode", partCode);
		request.setAttribute("detailVo", detailVo);
		request.setAttribute("payloanVo", payloanVo);
		request.setAttribute("planVoMap", planVoMap);
		
		return "ransom/ransomMortgageChange";
	}
	
	/**
	 * 陪同还贷修改
	 * @param applyVo
	 * @param request
	 * @return
	 */
	@RequestMapping(value="saveMortagage")
	@ResponseBody
	public boolean updatePayloan(ToRansomSubmitVo subVo, ServletRequest request) {
		
		//数据更新,申请表,赎楼表
		try{
			ransomChangeService.updatePayloanInfo(subVo);
			return true;
		}catch(Exception e){
			logger.error("",e);
			return false;
		}
	}
	
	/**
	 * 注销抵押
	 * @param ransomCode
	 * @param partCode
	 * @param count 0:一抵 1:二抵
	 * @param request
	 * @return
	 */
	@RequestMapping("cancelView")
	public String toCancelView(String ransomCode,String partCode,@RequestParam("count") Integer count,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
//		ToRansomCancelVo cancelVo = ransomService.getCancelInfo(ransomCode);
		
		request.setAttribute("partCode", partCode);
		request.setAttribute("count", count);
		request.setAttribute("detailVo", detailVo);
//		request.setAttribute("cancelVo", cancelVo);
		
		return "ransom/ransomCancelChange";
	}
	
	/**
	 * 领取产证
	 * @param ransomCode
	 * @param partCode
	 * @param count 0:一抵 1:二抵
	 * @param request
	 * @return
	 */
	@RequestMapping("permitView")
	public String toPermitView(String ransomCode,String partCode,@RequestParam("count") Integer count,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
//		ToRansomPermitVo permitVo = ransomService.getPermitInfo(ransomCode);
		
		request.setAttribute("partCode", partCode);
		request.setAttribute("count", count);
		request.setAttribute("detailVo", detailVo);
//		request.setAttribute("permitVo", permitVo);
		
		return "ransom/ransomPermitChange";
	}
	
	/**
	 * 回款结清
	 * @param ransomCode
	 * @param partCode
	 * @param request
	 * @return
	 */
	@RequestMapping("paymentView")
	public String toPaymentView(String ransomCode,String partCode,ServletRequest request) {
		
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		ToRansomPaymentVo paymentVo = ransomService.getPaymentInfo(ransomCode);
		
		request.setAttribute("detailVo", detailVo);
		request.setAttribute("paymentVo", paymentVo);
		request.setAttribute("partCode", partCode);
		
		return "ransom/ransomPaymentChange";
	}
	
}
