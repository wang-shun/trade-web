package com.centaline.trans.property.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.task.entity.ToPropertyResearch;

/**
 * 产调
 * 
 * @author aisliahail
 *
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/property")
public class PropertyMtnCortroller {

	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired(required = true)
	UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToPropertyService toPropertytService;

	@Autowired(required = true)
	private ToAttachmentService toAttachmentService;

	/**
	 * 处理未处理产调状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateProcessWaitListStatus")
	@ResponseBody
	public AjaxResponse<ToPropertyResearch> updateProcessWaitListStatus(Model model, ServletRequest request,
			String pkid) {
		AjaxResponse<ToPropertyResearch> result = new AjaxResponse<>();
		SessionUser user = uamSessionService.getSessionUser();
		Org orgProWait = uamUserOrgService.getOrgById(user.getServiceDepId());
		String prDistrictId = null;
		if (orgProWait.getDepHierarchy().equals(DepTypeEnum.TYCTEAM.getCode())) {
			prDistrictId = orgProWait.getParentId();
		} else {
			prDistrictId = orgProWait.getId();
		}
		List<ToPropertyResearch> list = new ArrayList<>();
		int proCount = 0;
		if (StringUtils.isBlank(pkid)) {// 根据district处理
			list = toPropertytService.getUnProcessListByDistrict(prDistrictId);
			proCount = toPropertytService.updateProcessWaitListStatus(prDistrictId);
		} else {// 处理单条
			proCount = toPropertytService.updateProcessWaitListStatusByPkId(pkid);
			ToPropertyResearch t = toPropertytService.findByPKID(Long.valueOf(pkid));
			list.add(t);
		}

		toPropertytService.sendMessage(list);
		if (proCount > 0) {
			result.setSuccess(true);
			result.setMessage("处理成功!");
		}
		return result;
	}

	/**
	 * 处理已受理产调状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateProcessingListStatus")
	@ResponseBody
	public AjaxResponse<ToPropertyResearch> updateProcessingListStatus(Model model, ServletRequest request,
			String pkidList) {
		AjaxResponse<ToPropertyResearch> result = new AjaxResponse<>();
		String[] pkidArr = pkidList.split(",");
		SessionUser u = uamSessionService.getSessionUser();
		int proCount = toPropertytService.updateProcessingListStatus(pkidArr, u.getId());
		if (proCount > 0) {
			result.setSuccess(true);
			result.setMessage("处理成功!");
		}
		return result;
	}

	/**
	 * 处理已受理产调状态
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveProcessingList")
	@ResponseBody
	public AjaxResponse<ToPropertyResearch> saveProcessingList(Model model, ServletRequest request, String pkid,
			String isScuess, String unSuccessReason, Boolean isSubmit, String executorId) {
		AjaxResponse<ToPropertyResearch> result = new AjaxResponse<>();

		SessionUser u = uamSessionService.getSessionUser();
		int proCount = toPropertytService.saveProcessingList(pkid, u.getId(), isScuess, unSuccessReason, isSubmit, executorId);
		if (proCount > 0) {
			result.setSuccess(true);
			result.setMessage("处理成功!");
		}
		return result;
	}

	/**
	 * 保存附件
	 * 
	 * @param model
	 * @param request
	 * @param disposeCodeList
	 * @return
	 */
	@RequestMapping(value = "saveFiles")
	@ResponseBody
	public AjaxResponse<ToAttachment> saveFiles(Model model, ServletRequest request,
			@RequestBody ToAttachment toAttachment) {
		AjaxResponse result = new AjaxResponse();
		int proCount = toAttachmentService.saveFiles(toAttachment);
		if (proCount > 0) {
			result.setSuccess(true);
			result.setMessage("附件上传成功!");
		}
		return result;
	}

	/**
	 * 删除图片
	 * 
	 * @param model
	 * @param request
	 * @param pkid
	 * @return
	 */
	@RequestMapping(value = "delFiles")
	@ResponseBody
	public AjaxResponse<ToAttachment> delFiles(Model model, ServletRequest request, String pkid) {
		AjaxResponse result = new AjaxResponse();
		int proCount = toAttachmentService.delFilesByPkid(Long.parseLong(pkid));
		if (proCount > 0) {
			result.setSuccess(true);
			result.setMessage("删除成功!");
		}
		return result;
	}

	/**
	 * 判断是否上传了附件
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "isExistFile")
	@ResponseBody
	public AjaxResponse<ToAttachment> isExistFile(Model model, ServletRequest request, String[] prCodeArray) {
		AjaxResponse result = new AjaxResponse();
		for (String prCode : prCodeArray) {
			List<ToAttachment> toAttachmentList = toAttachmentService.findToAttachmentByCaseCode(prCode);
			ToPropertyResearch toPropertyResearch = toPropertytService.findToPropertyResearchsByCaseCode(prCode);
			/**
			 * toPropertyResearch 为null,就说明该产调未致为无效.接着判断是否上传了附件
			 * toPropertyResearch 不为null,就说明该产调已致为无效.
			 */
			if (null == toPropertyResearch) {
				if (!CollectionUtils.isNotEmpty(toAttachmentList)) {
					result.setMessage("有要处理的产调未上传附件");
					result.setSuccess(false);
					return result;
				}
			}
		}
		return result;
	}

	/**
	 * 处理无效标记数据
	 * 
	 * @param model
	 * @param request
	 * @param caseArray
	 * @return
	 */
	@RequestMapping(value = "nullityTag")
	@ResponseBody
	public AjaxResponse<ToPropertyResearch> nullityTag(Model model, ServletRequest request,
			@RequestBody ToPropertyResearch toPropertyResearch) {
		AjaxResponse result = new AjaxResponse();
		toPropertyResearch.setIsSuccess(0);
		int succ = toPropertytService.nullityTag(toPropertyResearch);
		if (succ > 0) {
			result.setSuccess(true);
			result.setMessage("操作成功!");
		} else {
			result.setSuccess(false);
			result.setMessage("操作失败,请刷新后重试!");
		}
		return result;
	}
	
	@RequestMapping(value = "doChangePrDistrictId")
	@ResponseBody
	public AjaxResponse<ToPropertyResearch> doChangePrDistrictId(Model model, ServletRequest request,
			String pkid,String districtId ) {
		AjaxResponse result = new AjaxResponse();
		
		int succ = toPropertytService.doChangePrDistrictId(pkid,districtId);
		if (succ > 0) {
			result.setSuccess(true);
			result.setMessage("操作成功!");
		} else {
			result.setSuccess(false);
			result.setMessage("操作失败,请刷新后重试!");
		}
		return result;
	}
	

}
