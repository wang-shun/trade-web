package com.centaline.trans.eloan.web;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.MyCaseListService;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.OrgService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.service.ToWorkFlowService;
import com.centaline.trans.eloan.entity.ToEloanCase;
import com.centaline.trans.eloan.entity.ToEloanRel;
import com.centaline.trans.eloan.service.ToEloanCaseService;
import com.centaline.trans.eloan.service.ToEloanRelService;
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
	
	//押卡
	@RequestMapping("guarantycards")
	public String guarantycards(Long pkid, Model model) {
		getDetailByPkId(pkid, model);
		return "/eloan/guarantycards";
	}
	
	//抵押
	@RequestMapping("guarantymortgage")
	public String guarantymortgage(Long pkid, Model model) {
		getDetailByPkId(pkid, model);
		return "/eloan/guarantymortgage";
	}
	
	//强制公正
	@RequestMapping("guarantyfair")
	public String guarantyfair(Long pkid, Model model) {
		getDetailByPkId(pkid, model);
		return "/eloan/guarantyfair";
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
		}
		return model;
	}
	
}
