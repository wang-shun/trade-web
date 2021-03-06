package com.centaline.trans.attachment.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.attachment.entity.ToAccesoryList;
import com.centaline.trans.attachment.entity.ToAttachment;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.attachment.service.ToAttachmentService;
import com.centaline.trans.attachment.vo.ToAttachmentVO;
import com.centaline.trans.common.vo.FileUploadVO;
import com.centaline.trans.material.entity.MmIoBatch;
import com.centaline.trans.material.entity.MmItemBatch;
import com.centaline.trans.material.service.MmIoBatchService;
import com.centaline.trans.material.service.MmItemBatchService;

@Controller
@RequestMapping(value = "/attachment")
public class AttachmentController {

	@Autowired
	private ToAttachmentService toAttachmentService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;

	@Autowired
	private UamSessionService uamSessionService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Autowired
	private UamPermissionService uamPermissionService;

	@Autowired
	private MmItemBatchService mmItemBatchService;

	@Autowired
	private MmIoBatchService mmIoBatchService;

	@Autowired
	private ToMortgageService toMortgageService;

	private static String url = null;

	@RequestMapping(value = "test1")
	public String test(HttpServletRequest request) {
		request.setAttribute("caseCode", "ZY-SH-201611-0071");
		toAccesoryListService.getAccesoryList(request, "Guohu");
		return "attachment/test1";
	}

	@RequestMapping(value = "saveAttachment")
	@ResponseBody
	public AjaxResponse<String> saveAttachment(HttpServletRequest request, FileUploadVO fileUploadVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			toAttachmentService.saveAttachment(fileUploadVO);
		} catch (Exception e) {
			response.setMessage("保存失败！");
		}
		return response;
	}

	@RequestMapping(value = "saveAttachmentForMaterial")
	@ResponseBody
	public AjaxResponse<String> saveAttachmentForMaterial(HttpServletRequest request, FileUploadVO fileUploadVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			String attPkid = toAttachmentService.saveAttachmentForMaterial(fileUploadVO);
			if (null != attPkid && !"".equals(attPkid)) {
				response.setSuccess(true);
				response.setMessage(attPkid);
			} else {
				response.setSuccess(false);
				response.setMessage("error");
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage("保存失败！");
		}
		return response;
	}

	@RequestMapping(value = "quereyAttachments")
	@ResponseBody
	public Map<String, Object> quereyAttachments(HttpServletRequest request, ToAccesoryList toAccesoryList, ToAttachment toAttachment) {
		/** 读取上传附件备件表 */
		// ToAccesoryList toAccesoryList = new ToAccesoryList();
		// toAccesoryList.setPartCode(toAttachment.getPartCode());
		if (toAccesoryList.getPartCode() != null && toAccesoryList.getPartCode().equals("CaseClose")) {
			toAccesoryList.setPartCode(null);
			toAttachment.setPartCode(null);
		}
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accList", list);
		map.put("attList", toAttachmentService.quereyAttachments(toAttachment));
		return map;
	}

	@RequestMapping(value = "updateAvaliableAttachmentByProperty")
	@ResponseBody
	public AjaxResponse<String> updateAvaliableAttachmentByProperty(HttpServletRequest request, ToAttachment toAttachment) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			toAttachmentService.updateAvaliableAttachmentByProperty(toAttachment);
		} catch (Exception e) {
			response.setMessage("更新状态失败！");
		}
		return response;
	}

	@RequestMapping(value = "quereyAttachmentsForMaterital")
	@ResponseBody
	public Map<String, Object> quereyAttachmentsForMaterital(HttpServletRequest request, ToAccesoryList toAccesoryList, ToAttachment toAttachment) {
		/** 读取上传附件备件表 */
		// ToAccesoryList toAccesoryList = new ToAccesoryList();
		// toAccesoryList.setPartCode(toAttachment.getPartCode());
		if (toAccesoryList.getPartCode() != null && toAccesoryList.getPartCode().equals("CaseClose")) {
			toAccesoryList.setPartCode(null);
			toAttachment.setPartCode(null);
		}
		List<ToAccesoryList> list = toAccesoryListService.qureyToAccesoryList(toAccesoryList);
		Map<String, Object> map = new HashMap<String, Object>();

		// 用toAttachment对象的pkid 存储卡证主表的pkid 来查找入库时上传附件的pkid
		if (null != toAttachment) {
			// 一个主表的pkid 可能对应多条操作记录
			List<MmItemBatch> batchList = mmItemBatchService.queryMmItemBatchList(toAttachment.getPkid());// 此处的pkid为卡证管理主表里面pkid
			if (batchList.size() > 0) {
				// 动作表中入库的pkid值最小
				int flag = 0;
				long minPkid = batchList.get(0).getBatchId();
				for (int i = 1; i < batchList.size(); i++) {
					long k = batchList.get(i).getBatchId();
					// 假如元素小于min 就把当前值赋值给min
					if (k < minPkid) {
						minPkid = k;
						flag = i;
					}
				}
				MmItemBatch mmItemBatch = batchList.get(flag);
				MmIoBatch mmIoBatchForAttaId = mmIoBatchService.queryMmIoBatchByPkid(mmItemBatch.getBatchId());// 查询入库时添加附件的pkid
				toAttachment.setPkid(mmIoBatchForAttaId.getAttachId());
			}
		}
		map.put("accList", list);
		map.put("attList", toAttachmentService.quereyAttachmentForMaterial(toAttachment));
		return map;
	}

	@RequestMapping(value = "quereyAttachment")
	@ResponseBody
	public List<ToAttachment> quereyAttachments(HttpServletRequest request, ToAttachment toAttachment) {
		List<ToAttachment> attachments = toAttachmentService.quereyAttachments(toAttachment);

		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(toAttachment.getCaseCode());
		List<ToAttachment> removeList = new ArrayList<ToAttachment>();
		if (attachments != null && attachments.size() > 0) {
			for (ToAttachment attachment : attachments) {
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					ToAccesoryList accesoryList = new ToAccesoryList();
					accesoryList.setAccessoryCode(attachment.getPreFileCode());
					accesoryList.setPartCode(attachment.getPartCode());
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByPartCode(accesoryList).getAccessoryName());
					if(toMortgage == null || toMortgage.getMortType() == null){//如果无贷款，则删除商贷利率页和抵押登记表
						if("ComLoan_rate_letter".equals(attachment.getPreFileCode())||"mortage_register_form".equals(attachment.getPreFileCode())){
							removeList.add(attachment);
						}
					}
					if(toMortgage != null&&"30016003".equals(toMortgage.getMortType())){//如果是公积金贷款
						if("ComLoan_rate_letter".equals(attachment.getPreFileCode())){
							removeList.add(attachment);
						}
					}
				}
			}
			for(ToAttachment tal:removeList) {
				attachments.remove(tal);
			}
		}
		/** 读取上传附件备件表 */
		return attachments;
	}

	@RequestMapping(value = "queryNewAttachment")
	@ResponseBody
	public ToAttachmentVO queryNewAttachment(HttpServletRequest request, ToAttachment toAttachment) {
		ToAttachmentVO toAttachmentVO = new ToAttachmentVO();
		if (StringUtils.isBlank(toAttachment.getAvailable())) {
			toAttachment.setAvailable(null);
		}

		ToAccesoryList property = new ToAccesoryList();
		property.setPartCode(toAttachment.getPartCode());
		if (!StringUtil.isBlank(toAttachment.getPreFileCode())) {
			property.setAccessoryCode(toAttachment.getPreFileCode());
		}
		List<ToAccesoryList> toAccesoryList = toAccesoryListService.qureyToAccesoryList(property, toAttachment.getCaseCode());
		toAttachmentVO.setToAccesoryList(toAccesoryList);
		toAttachment.setPreFileCode(null);
		List<ToAttachment> attachments = toAttachmentService.quereyAttachments(toAttachment);
		if (CollectionUtils.isNotEmpty(attachments)) {
			for (ToAttachment attachment : attachments) {
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
				}
			}
		}
		toAttachmentVO.setAttachmentList(attachments);

		/** 读取上传附件备件表 */
		return toAttachmentVO;
	}

	@RequestMapping(value = "quereyAttachmentForDetails")
	@ResponseBody
	public List<ToAttachment> quereyAttachmentForDetails(HttpServletRequest request, ToAttachment toAttachment) {
		List<ToAttachment> attachments = toAttachmentService.quereyAttachmentForDetails(toAttachment);
		if (attachments != null && attachments.size() > 0) {
			for (ToAttachment attachment : attachments) {
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					ToAccesoryList accesoryList = new ToAccesoryList();
					accesoryList.setAccessoryCode(attachment.getPreFileCode());
					accesoryList.setPartCode(attachment.getPartCode());
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByPartCode(accesoryList).getAccessoryName());
				}
			}
		}
		/** 读取上传附件备件表 */
		return attachments;
	}

	@RequestMapping(value = "quereyAttachmentForMaterial")
	@ResponseBody
	public List<ToAttachment> quereyAttachmentForMaterial(HttpServletRequest request, ToAttachment toAttachment) {
		List<ToAttachment> attachments = toAttachmentService.quereyAttachmentForMaterial(toAttachment);
		if (attachments != null && attachments.size() > 0) {
			for (ToAttachment attachment : attachments) {
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
				}
			}
		}
		/** 读取上传附件备件表 */
		return attachments;
	}

	@RequestMapping(value = "quereyAttachmentCaseClose")
	@ResponseBody
	public Map<String, Object> quereyAttachmentCaseClose(HttpServletRequest request, ToAttachment toAttachment) {
		List<ToAttachment> attachments = toAttachmentService.quereyAttachments(toAttachment);
		List<ToAccesoryList> list = new ArrayList<ToAccesoryList>();
		if (attachments != null && attachments.size() > 0) {
			Iterator<ToAttachment> it = attachments.iterator();
			while (it.hasNext()) {
				ToAttachment attachment = it.next();
				if (attachment.getPartCode().equals("property_research")) {
					it.remove();
					continue;
				}
				if (!StringUtils.isEmpty(attachment.getPreFileCode())) {
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
					ToAccesoryList itemAccesoryList = toAccesoryListService.findAccesory(attachment);

					boolean isHave = false;
					if (CollectionUtils.isNotEmpty(list)) {
						for (ToAccesoryList item : list) {
							if (item.getPkid() == itemAccesoryList.getPkid()) {
								isHave = true;
								break;
							}
						}
						if (!isHave) {
							list.add(itemAccesoryList);
						}
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accList", list);
		map.put("attList", attachments);
		return map;
	}

	@RequestMapping(value = "delAttachment")
	@ResponseBody
	public AjaxResponse<String> delAttachment(HttpServletRequest request, FileUploadVO fileUploadVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			toAttachmentService.delAttachment(fileUploadVO.getPkIdArr());
		} catch (Exception e) {
			response.setMessage("删除失败！");
		}

		return response;
	}

	@RequestMapping(value = "delAttachmentByFileAddress")
	@ResponseBody
	public AjaxResponse<String> delAttachmentByFileAdress(HttpServletRequest request, String preFileAdress) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try {
			toAttachmentService.deleteByFileAdress(preFileAdress);
		} catch (Exception e) {
			response.setMessage("删除失败！");
		}
		return response;
	}

	@RequestMapping(value = "/resourcelibrary/downLoadResource.do")
	public void downLoadResource(String name, String path, HttpServletRequest request, HttpServletResponse response) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		try {
			if (url == null) {
				App app = uamPermissionService.getAppByAppName("shcl-image-web");
				url = app.genAbsoluteUrl();
			}
			HttpResponse httResponse = executeGet(url + "/image/" + path);

			response.reset();
			response.setHeader("Content-disposition", "attachment;success=true;filename =" + name);

			fis = httResponse.getEntity().getContent();
			bis = new BufferedInputStream(fis);
			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			// 弹出下载对话框
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			fis.close();
			bis.close();
			fos.close();
			bos.close();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (bis != null) {
					bis.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping(value = "/fileUpload" )
	@ResponseBody
	public Object fileUpload(String fileList,String caseCode,String partCode) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		try{
			String fileId[] =fileList.split(",");
			for (String id : fileId){
				if(id.length()>2){
					ToAttachment attach=new ToAttachment();
					attach.setAvailable("Y");
					attach.setPreFileAdress(id);
					attach.setCaseCode(caseCode);
					toAttachmentService.updateToAttachmentForCaseCodeByAdres(attach);
				}
			}
			ajaxResponse.setSuccess(true);
			ajaxResponse.setMessage("保存成功");
		}catch(Exception e){
			e.printStackTrace();
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("保存失败");
		}
		return ajaxResponse;
	}

	private HttpClient createHttpClient() {
		// 设置Base Auth验证信息
		CredentialsProvider provider = new BasicCredentialsProvider();
		SessionUser u = uamSessionService.getSessionUser();
		User user = uamUserOrgService.getUserById(u.getId());
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(user.getUsername(), user.getPassword());
		provider.setCredentials(AuthScope.ANY, credentials);

		// 创建HttpClient
		HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
		return client;
	}

	private HttpResponse executeGet(String queryUrl) throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		HttpGet get = new HttpGet("http://stage.vcainfo.com/v1/" + queryUrl);
		get.addHeader("vc-user-key", "11260");
		return client.execute(get);
	}

}
