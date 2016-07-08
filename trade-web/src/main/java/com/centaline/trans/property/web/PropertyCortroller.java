package com.centaline.trans.property.web;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.service.ToWorkFlowService;


/**
 * 产调
 * @author aisliahail
 *
 */
@Controller
@RequestMapping(value="/property")
public class PropertyCortroller {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	ToCaseInfoService toCaseInfoService;
	@Autowired(required = true)
	ToCaseService toCaseService;
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	@Autowired(required = true)
	ToAttachmentService toAttachmentService;
	@Autowired
    UamPermissionService uamPermissionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	/**
	 * 进入 待处理产调
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="processWaitList")
	public String processWaitList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		Org orgProWait = uamUserOrgService.getOrgById(user.getServiceDepId());
		if(orgProWait.getDepHierarchy().equals(DepTypeEnum.TYCTEAM.getCode())){
			model.addAttribute("prDistrictId",orgProWait.getParentId());
		}else{
			model.addAttribute("prDistrictId",orgProWait.getId());
		}
		return "property/processWaitList";
	}
	
	/**
	 * 进入 已受理产调
	 * @param model
	 * @param request
	 * @return  
	 */    
	@RequestMapping(value="processingList")
	public String processingList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		Org orgPro = uamUserOrgService.getOrgById(user.getServiceDepId());
		if(orgPro.getDepHierarchy().equals(DepTypeEnum.TYCTEAM.getCode())){
			model.addAttribute("prDistrictId",orgPro.getParentId());
		}else{
			model.addAttribute("prDistrictId",orgPro.getId());
		}
		
		model.addAttribute("serviceDepId", user.getServiceDepId());
		
		return "property/processingList";
	}
	/**
	 * 进入 已完成产调
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="successList")
	public String successList(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		Org orgSuc = uamUserOrgService.getOrgById(user.getServiceDepId());
		if(orgSuc.getDepHierarchy().equals(DepTypeEnum.TYCTEAM.getCode())){
			model.addAttribute("prDistrictId",orgSuc.getParentId());
		}else{
			model.addAttribute("prDistrictId",orgSuc.getId());
		}
		
		model.addAttribute("serviceDepId", user.getServiceDepId());
		
		return "property/successList";
	}
	
	/**
	 * 进入 上传附件页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="box/addFiles")
	public String addFiles(Model model, ServletRequest request,String id,String prCode){
		List<ToAttachment>	toAttachmentList = toAttachmentService.findToAttachmentByCaseCode(prCode);
		//List<ToAttachment>	toAttachmentList = toAttachmentService.findToAttachmentByIdAndCaseCode(id,caseCode);
		App regApp = uamPermissionService.getAppByAppName("shcl-image-web");
        String imgHost = regApp.genAbsoluteUrl();
		model.addAttribute("toAttachmentList", toAttachmentList);
		model.addAttribute("toAttachmentListLength", toAttachmentList.size());
		model.addAttribute("imgHost", imgHost);
		model.addAttribute("prCode", prCode);
		return "property/addFiles";
	}
	/**
	 * 进入无效标记页面
	 * @param model
	 * @param request
	 * @param id
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value="box/nullityTag")
	public String nullityTag(Model model, ServletRequest request,String id,String caseCode){
		
		model.addAttribute("id", id);
		model.addAttribute("caseCode", caseCode);
		return "property/nullityTag";
	}
	/**
	 * 产调处理结果
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="resultGet")
	public String resultGet(Model model, ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("userId",user.getId());
		return "property/resultGetList";
	}

	/**
	 * 进入查看产调结果
	 * @param model
	 * @param request
	 * @param caseCode
	 * @return
	 */
	@RequestMapping(value="box/resultGetPic")
	public String resultGet(Model model, ServletRequest request,String caseCode){
		List<ToAttachment>	toAttachmentList = toAttachmentService.findToAttachmentByCaseCode(caseCode);
		//List<ToAttachment>	toAttachmentList = toAttachmentService.findToAttachmentByIdAndCaseCode(id,caseCode);
		App regApp = uamPermissionService.getAppByAppName("aist-filesvr-web");
        String imgHost = regApp.genAbsoluteUrl();
		model.addAttribute("toAttachmentList", toAttachmentList);
		model.addAttribute("toAttachmentListLength", toAttachmentList.size());
		model.addAttribute("imgHost", imgHost);
		model.addAttribute("caseCode", caseCode);
		return "property/resultGet";
	}
	
	/**
	 * 移动端产调上传
	 * @param model
	 * @return
	 */
	@RequestMapping(value="mobile/propertyList")
	public String propertyList(Model model){
		SessionUser user = uamSessionService.getSessionUser();
		model.addAttribute("orgId",user.getServiceDepId());
		return "mobile/property/propertyResearch";
	}
}








