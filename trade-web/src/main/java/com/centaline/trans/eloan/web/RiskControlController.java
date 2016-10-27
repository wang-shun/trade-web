package com.centaline.trans.eloan.web;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.AttachmentPartCodeEnum;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.eloan.entity.RcRiskControl;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.entity.ToEloanRel;
import com.centaline.trans.eloan.enums.RiskTypeEnum;
import com.centaline.trans.eloan.service.RcRiskControlService;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanRelService;
import com.centaline.trans.eloan.service.ToRcForceRegisterService;
import com.centaline.trans.eloan.service.ToRcMortgageService;
import com.centaline.trans.eloan.vo.ToRcForceRegisterVO;
import com.centaline.trans.eloan.vo.ToRcMortgageCardVO;
import com.centaline.trans.eloan.vo.ToRcMortgageVO;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.loan.service.LoanAgentService;
import com.centaline.trans.mgr.service.TsFinOrgService;

@Controller
@RequestMapping(value="/riskControl")
public class RiskControlController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	
	@Autowired
	LoanAgentService loanAgentService;
	@Autowired
	OrgService orgService;
	@Autowired
	TsFinOrgService finorgService;
	@Autowired
	ToEloanCaseService toEloanCaseService;
	@Autowired
	ToEloanRelService toEloanRelService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired
	private MyCaseListService myCaseListService;
	@Autowired
	ToWorkFlowService flowService;
	@Autowired
	ProcessInstanceService processInstanceService;
	@Autowired
	ToRcMortgageService toRcMortgageService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	@Autowired
	ToRcForceRegisterService toRcForceRegisterService;
	@Autowired
	RcRiskControlService rcRiskControlService;
	
	//押卡
	@RequestMapping("guarantycards")
	public String guarantycards(Long pkid, HttpServletRequest request,Model model) {
		getDetailByPkId(pkid, model);
		getAccesoryList(request, AttachmentPartCodeEnum.RISKCONTROL_CARD.getCode());
		ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcMortgageCardVO toRcMortgageCardVO = toRcMortgageService.getRcMortgageCardInfoByProperty("card", eloanCase.getEloanCode());
		model.addAttribute("toRcMortgageCardVO", toRcMortgageCardVO);
		return "/eloan/guarantycards";
	}
	
	//只读押卡信息
	@RequestMapping("guarantycardsvonly")
	public String guarantycardsvonly(Long pkid, Model model) {
		getDetailByPkId(pkid, model);
		ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcMortgageCardVO toRcMortgageCardVO = toRcMortgageService.getRcMortgageCardInfoByProperty("card", eloanCase.getEloanCode());
		model.addAttribute("toRcMortgageCardVO", toRcMortgageCardVO);
		return "/eloan/guarantycardsvonly";
	}
	
	//抵押
	@RequestMapping("guarantymortgage")
	public String guarantymortgage(Long pkid, HttpServletRequest request,Model model) {
		getDetailByPkId(pkid, model);
		
		getAccesoryList(request, AttachmentPartCodeEnum.RISKCONTROL_MORTGAGE.getCode());
		ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcMortgageVO toRcMortgageVO = toRcMortgageService.getMortgageByProperty("mortgage", eloanCase.getEloanCode());
		model.addAttribute("toRcMortgageVO", toRcMortgageVO);
		return "/eloan/guarantymortgage";
	}
	
	@RequestMapping("validateRiskControlType")
	public AjaxResponse<String> validateRiskControlType(String type,String eloanCode, Model model) {
		List<RcRiskControl> rcRiskControlList = rcRiskControlService.getRcRiskControlByProperty(type, eloanCode);
		if(CollectionUtils.isEmpty(rcRiskControlList)) {
			return AjaxResponse.fail("不存在此风控类型");
		} else {
			return AjaxResponse.success("存在此风控类型");
		}
	}

	//只读抵押信息
	@RequestMapping("guarantymortgagevonly")
	public String guarantymortgagevonly(Long pkid, Model model) {
		getDetailByPkId(pkid, model);
		ToEloanCase eloanCase = toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcMortgageVO toRcMortgageVO = toRcMortgageService.getMortgageByProperty("mortgage", eloanCase.getEloanCode());
		model.addAttribute("toRcMortgageVO", toRcMortgageVO);
		return "/eloan/guarantymortgagevonly";
	}

	//强制公正
	@RequestMapping("guarantyfair")
	public String guarantyfair(Long pkid,HttpServletRequest request, Model model) {
		getDetailByPkId(pkid, model);
		//查找强制公证需要上传那些附件
		getAccesoryList(request, "RiskControl");
		
		ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcForceRegisterVO toRcForceRegisterVO = toRcForceRegisterService.getRcForceRegisterByProperty("forceRegister", eloanCase.getEloanCode());
		model.addAttribute("toRcForceRegister", toRcForceRegisterVO.getToRcForceRegister());
		model.addAttribute("rcRiskControl", toRcForceRegisterVO.getRcRiskControl());
		return "/eloan/guarantyfair";
	}

	//只读强制公正信息
	@RequestMapping("guarantyfairvonly")
	public String guarantyfairvonly(Long pkid, HttpServletRequest request, Model model) {
		getDetailByPkId(pkid, model);
		// 查找强制公证需要上传那些附件
		getAccesoryList(request, "RiskControl");

		ToEloanCase eloanCase = toEloanCaseService.getToEloanCaseByPkId(pkid);
		ToRcForceRegisterVO toRcForceRegisterVO = toRcForceRegisterService.getRcForceRegisterByProperty("forceRegister",
				eloanCase.getEloanCode());
		model.addAttribute("toRcForceRegister", toRcForceRegisterVO.getToRcForceRegister());
		model.addAttribute("rcRiskControl", toRcForceRegisterVO.getRcRiskControl());
		return "/eloan/guarantyfairvonly";
	}
	
	@RequestMapping("deleteRiskControl")
	public String deleteRiskControl(Long pkid,String riskType,Long eloanPkId, HttpServletRequest request, Model model) {
		rcRiskControlService.deleteReferRiskControlByProperty(RiskTypeEnum.getCode(riskType),pkid);
		return "redirect:/eloan/getEloanCaseDetails?pkid="+eloanPkId;
	}

	/**
	 * 读取上传附件备件表
	 * 
	 * @param request
	 * @param taskitem
	 */
    private void getAccesoryList(HttpServletRequest request, String taskitem) {
		ToAccesoryList toAccesoryList = new ToAccesoryList();
		toAccesoryList.setPartCode(taskitem);
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		if(list != null && list.size() > 0) {
			int size = list.size();
			request.setAttribute("accesoryList", list);
			List<Long> idList = new ArrayList<Long>(size);
			for(int i=0; i<size; i++) {
				idList.add(list.get(i).getPkid());
			}
			request.setAttribute("idList", idList);
		}
    }
	
	@RequestMapping(value="saveRcMortgageCard")
	@ResponseBody
	public AjaxResponse<String> saveRcMortgageCard(Model model,@RequestBody ToRcMortgageCardVO toRcMortgageCardVO){
		try {
			toRcMortgageService.saveRcMortgageCard(toRcMortgageCardVO);
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {	
			logger.debug("保存失败", e);
			return AjaxResponse.fail("操作放款失败");
		}
	}
	
	@RequestMapping(value="saveRcMortgage")
	@ResponseBody
	public AjaxResponse<String> saveRcMortgage(Model model,@RequestBody ToRcMortgageVO toRcMortgageVO){
		try {
			toRcMortgageService.saveRcMortgage(toRcMortgageVO);
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存失败", e);
			return AjaxResponse.fail("操作放款失败");
		}
	}
	
	@RequestMapping(value="saveToRcForceRegister")
	@ResponseBody
	public AjaxResponse<String> saveToRcForceRegister(Model model,@RequestBody ToRcForceRegisterVO toRcForceRegisterVO){
		try {
			toRcMortgageService.saveToRcForceRegister(toRcForceRegisterVO);
			return AjaxResponse.success("操作成功");
		} catch(Exception e) {
			logger.debug("保存失败", e);
			return AjaxResponse.fail("操作放款失败");
		}
	}
	
	private Model getDetailByPkId(Long pkid, Model model) {
		if (pkid != null) {
			ToEloanCase eloanCase= toEloanCaseService.getToEloanCaseByPkId(pkid);
			ToCase toCase= toCaseService.findToCaseByCaseCode(eloanCase.getCaseCode());
			//人物信息
			User jingban =uamUserOrgService.getUserById(toCase.getLeadingProcessId());
			User excutor =uamUserOrgService.getUserById(eloanCase.getExcutorId());
			Map<String,Object> object = new HashMap<String,Object>();
			if(excutor!=null){
			object.put("excutorName", excutor.getRealName());
			object.put("excutorPhone", excutor.getMobile());
			}
			object.put("jingbanName", jingban.getRealName());
			object.put("jingbanPhone",jingban.getMobile());
			// 物业信息
			ToPropertyInfo toPropertyInfo = toPropertyInfoService.findToPropertyInfoByCaseCode(toCase.getCaseCode());
			object.put("propertyAddr", toPropertyInfo.getPropertyAddr());
			//放款信息
			BigDecimal releaseAmount=new BigDecimal(0);
			List<ToEloanRel> eloanRels= toEloanRelService.getEloanRelByEloanCode(eloanCase.getEloanCode());
			for (ToEloanRel toEloanRel : eloanRels) {
				if(toEloanRel.getConfirmStatus().equals("1")){
					releaseAmount=releaseAmount.add(toEloanRel.getReleaseAmount());
				
				}
			}
			object.put("releaseAmount",releaseAmount);
			//状态
			String status="";
			if(eloanCase.getApplyTime()!=null){
				status="apply";
			}
			if(eloanCase.getApplyConfTime()!=null){
				status="confirmApply";
			}
			if(eloanCase.getSignTime()!=null){
				status="sign";
			}
		    if(eloanCase.getSignConfTime()!=null){
				status="confirmSign";
			}
			if(eloanRels.size()>0){
				status="release";
			}
			object.put("status",status);
			//申请时间
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String applyTime=dateFormat.format(eloanCase.getApplyTime());
			object.put("applyTime",applyTime );
			//合作机构查询
			String finOrgName=finorgService.findBankByFinOrg(eloanCase.getFinOrgCode()).getFinOrgName();
			object.put("finOrgName",finOrgName );
			model.addAttribute("info", object);
			model.addAttribute("eloanRelList", eloanRels);
			model.addAttribute("eloanCase", eloanCase);
			model.addAttribute("pkid", pkid);
		}
		return model;
	}
	
}
