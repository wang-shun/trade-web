package com.centaline.trans.common.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.entity.ToAccesoryList;
import com.centaline.trans.common.entity.ToAttachment;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.common.service.ToAttachmentService;
import com.centaline.trans.common.vo.FileUploadVO;

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
	private UamBasedataService uamBasedataService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private UamPermissionService uamPermissionService;
	
	private static String url = null;

	@RequestMapping(value = "saveAttachment")
	@ResponseBody
	public AjaxResponse<String> saveAttachment(HttpServletRequest request,
			FileUploadVO fileUploadVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toAttachmentService.saveAttachment(fileUploadVO);
		}catch(Exception e){
			response.setMessage("保存失败！");
		}
		return response;
	}

	@RequestMapping(value = "quereyAttachments")
	@ResponseBody
	public Map<String, Object> quereyAttachments(HttpServletRequest request,
			ToAccesoryList toAccesoryList, ToAttachment toAttachment) {
		/** 读取上传附件备件表 */
		// ToAccesoryList toAccesoryList = new ToAccesoryList();
		// toAccesoryList.setPartCode(toAttachment.getPartCode());
		if(toAccesoryList.getPartCode()!=null && toAccesoryList.getPartCode().equals("CaseClose")){
			toAccesoryList.setPartCode(null);
			toAttachment.setPartCode(null);
		}
		List<ToAccesoryList> list = toAccesoryListService
				.qureyToAccesoryList(toAccesoryList);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accList", list);
		map.put("attList", toAttachmentService.quereyAttachments(toAttachment));
		return map;
	}
	
	@RequestMapping(value = "quereyAttachment")
	@ResponseBody
	public List<ToAttachment> quereyAttachments(HttpServletRequest request, ToAttachment toAttachment) {
		List<ToAttachment> attachments = toAttachmentService.quereyAttachments(toAttachment);
		if(attachments!=null && attachments.size()>0){
			for(ToAttachment attachment:attachments){
				if(!StringUtils.isEmpty(attachment.getPreFileCode())){
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
		if(attachments!=null && attachments.size()>0){
			for(ToAttachment attachment:attachments){
				if(attachment.getPartCode().equals("property_research")){
					attachments.remove(attachment);
					continue;
				}
				if(!StringUtils.isEmpty(attachment.getPreFileCode())){
					attachment.setPreFileName(toAccesoryListService.findAccesoryNameByCode(attachment.getPreFileCode()));
					ToAccesoryList itemAccesoryList = toAccesoryListService.findAccesoryByCode(attachment.getPreFileCode());
					boolean isHave = false;
					if(CollectionUtils.isNotEmpty(list)){
						for(ToAccesoryList item:list){
							if(item.getPkid() == itemAccesoryList.getPkid()){
								isHave = true;
								break;
							}
						}
						if(!isHave){
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
	public AjaxResponse<String> delAttachment(HttpServletRequest request,
			FileUploadVO fileUploadVO) {
		AjaxResponse<String> response = new AjaxResponse<String>();
		try{
			toAttachmentService.delAttachment(fileUploadVO.getPkIdArr());
		}catch(Exception e){
			response.setMessage("删除失败！");
		}
		
		return response;
	}

	@RequestMapping(value = "/resourcelibrary/downLoadResource.do")
	public void downLoadResource(String name, String path,
			HttpServletRequest request, HttpServletResponse response) {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		try {
			if(url == null) {
				App app = uamPermissionService.getAppByAppName("shcl-image-web");
				url = app.genAbsoluteUrl();
			}
			HttpResponse httResponse = executeGet(url+"/image/"+path);

			response.reset();
			response.setHeader("Content-disposition",
					"attachment;success=true;filename =" + name);

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

	private HttpClient createHttpClient() {
		// 设置Base Auth验证信息
		CredentialsProvider provider = new BasicCredentialsProvider();
		SessionUser u = uamSessionService.getSessionUser();
		User user = uamUserOrgService.getUserById(u.getId());
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				user.getUsername(), user.getPassword());
		provider.setCredentials(AuthScope.ANY, credentials);

		// 创建HttpClient
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		return client;
	}

	private HttpResponse executeGet(String queryUrl)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		HttpGet get = new HttpGet("http://stage.vcainfo.com/v1/" + queryUrl);
		get.addHeader("vc-user-key", "11260");
		return client.execute(get);
	}
}
