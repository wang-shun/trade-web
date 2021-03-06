package com.centaline.trans.bizwarn.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.bizwarn.entity.BizWarnInfo;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.product.repository.ProductMapper;
import com.centaline.trans.task.vo.BizwarnForm;

/**
 * 商贷预警信息控制器
 * 
 * @author yinjf2
 * @date 2016年8月5日
 */
@Controller
@RequestMapping(value = "/bizwarn")
public class BizWarnInfoController {

	@Autowired
	private BizWarnInfoService bizWarnInfoService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private UamSessionService uamSessionService;

	@Autowired
	private ToCaseService toCaseService;

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 修改商贷预警信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "editBizWarnInfo")
	@ResponseBody
	public AjaxResponse editBizWarnInfo(ServletRequest request,
			BizwarnForm bizwarnForm) {
		AjaxResponse result = new AjaxResponse();

		BizWarnInfo bizWarnInfo = bizWarnInfoService
				.getBizWarnInfoByCaseCode(bizwarnForm.getCaseCode());
		bizWarnInfo.setContent(bizwarnForm.getContent());

		int count = bizWarnInfoService.updateByCaseCode(bizWarnInfo);

		if (count > 0) {
			return result.success("修改成功！");
		} else {
			return result.fail("修改失败！");
		}
	}

	/**
	 * 获取商贷预警信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getBizWarnInfo")
	@ResponseBody
	public Object getBizWarnInfo(ServletRequest request, BizwarnForm bizwarnForm) {
		BizWarnInfo bizWarnInfo = bizWarnInfoService
				.getBizWarnInfoByCaseCode(bizwarnForm.getCaseCode());

		return bizWarnInfo;
	}

	/**
	 * 添加商贷预警信息
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addBizWarnInfo")
	@ResponseBody
	public AjaxResponse addBizWarnInfo(ServletRequest request,
			BizwarnForm bizwarnForm) {
		AjaxResponse result = new AjaxResponse();

		SessionUser currentUser = uamSessionService.getSessionUser();
		ToCase toCase = toCaseService.findToCaseByCaseCode(bizwarnForm
				.getCaseCode());

		BizWarnInfo bizWarnInfo = new BizWarnInfo();

		bizWarnInfo.setCaseCode(bizwarnForm.getCaseCode());
		bizWarnInfo.setContent(bizwarnForm.getContent());
		bizWarnInfo.setWarnType("LOANLOSS");
		bizWarnInfo.setCreateBy(currentUser.getId());
		bizWarnInfo.setParticipant(toCase.getLeadingProcessId());
		bizWarnInfo.setTeamId(toCase.getOrgId());
		bizWarnInfo.setStatus("0");
		bizWarnInfo.setCreateTime(new Date());
		bizWarnInfo.setWarnTime(new Date());

		Org currentOrg = uamUserOrgService.getOrgById(toCase.getOrgId());
		Org parentOrg = uamUserOrgService.getOrgById(currentOrg.getParentId());

		bizWarnInfo.setDistrictId(parentOrg.getId());

		int count = bizWarnInfoService.insertSelective(bizWarnInfo);

		if (count > 0) {
			return result.success("保存成功！");
		} else {
			return result.fail("保存失败！");
		}
	}

	/**
	 * 进入商贷预警展示列表
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(Model model, ServletRequest request) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		String status = request.getParameter("status");
		if (status != null && !"".equals(status)) {
			request.setAttribute("status", status);
		}

		request.setAttribute("currentUser", currentUser);

		SessionUser user = uamSessionService.getSessionUser();
		String userJob = user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;
		int userJobCode = -1;

		StringBuffer reBuffer = new StringBuffer();
		// 如果不是交易顾问
		if (!userJob.equals(TransJobs.TJYGW.getCode())) {
			// 下面有组别
			queryOrgFlag = true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			if (depString.equals(DepTypeEnum.TYCTEAM.getCode())) {
				reBuffer.append(userOrgIdString);
				userJobCode = 2;
			} else if (depString.equals(DepTypeEnum.TYCQY.getCode())) {
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(
						userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for (Org org : orgList) {
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length() - 1);
				userJobCode = 1;
			} else {
				isAdminFlag = true;
				userJobCode = 0;
			}
		} else {
			userJobCode = 3;
		}

		request.setAttribute("queryOrgs", reBuffer.toString());
		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		request.setAttribute("userJobCode", userJobCode);
		request.setAttribute("serviceDepId", user.getServiceDepId());// 登录用户的org_id

		return "bizwarn/list";
	}

	/**
	 * 解除
	 * 
	 * @param caseCode
	 *            案件编号
	 * @return
	 */
	@RequestMapping(value = "relieve")
	@ResponseBody
	public AjaxResponse relieve(String caseCode) {
		AjaxResponse result = new AjaxResponse();
		//新增warnType  确认案件的唯一性
		BizWarnInfo bizWarnInfo = bizWarnInfoService.getBizWarnInfoByCaseCode(caseCode);
		bizWarnInfo.setStatus("1");
		bizWarnInfo.setRelieveTime(new Date());

		int count = bizWarnInfoService.updateStatusByCaseCode(bizWarnInfo);

		if (count > 0) {
			return result.success("解除成功！");
		} else {
			return result.fail("解除失败！");
		}
	}
	
	
	
	
	
	/**
	 * @author zhuody
	 * @Date 2017-03-15
	 *  
	 * 预警解除 
	 * @param caseCode 案件编号
	 * @param warnType 预警类型
	 * @return
	 */
	@RequestMapping(value = "relieveWarn")
	@ResponseBody
	public AjaxResponse<String> relieveWarn(String caseCode,String warnType) {
		AjaxResponse<String> result = new AjaxResponse<String>();
		//新增warnType  确认案件的唯一性
		if(null == caseCode || "".equals(caseCode) && (null == warnType || "".equals(warnType))){
			throw new BusinessException("查询预警案件信息请求参数有误！");
		}		
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("caseCode", caseCode);
		paraMap.put("warnType", warnType);
		int k = 0;
		
		try{
			k = bizWarnInfoService.relieveWarn(paraMap);
		}catch(BusinessException e){
			throw new BusinessException("案件取消预警异常！");
		}		

		if (k > 0) {			
			result.setSuccess(true);
		} else {
			result.setSuccess(false);			
		}
		return result;
	}
}
