package com.centaline.trans.knowledge.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.centaline.trans.knowledge.entity.KnowledgeRepo;
import com.centaline.trans.knowledge.entity.KnowledgeRepoAttachment;
import com.centaline.trans.knowledge.service.KnowledgeRepoService;
import com.centaline.trans.knowledge.vo.KnowledgeRepoAddVO;


/**
 * 知识库增、删、改操作 MtnController
 * 创建时间：2015-09-18
 * @author yumin1
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/knowledgemtn") 
public class KnowledgeMtnController {

	@Autowired(required = true)
    private KnowledgeRepoService knowledgeRepoService;
	
	@Autowired(required = true)
	private UamSessionService uamSessionService;	//当前登录用户信息
	
	
	
	/**
	 * 知识库信息发布 
	 * @author yumin1
	 * @return 是否添加成功
	 */
	@RequestMapping(value="knowledgeAdd")  
	@ResponseBody
	public AjaxResponse knowledgeAdd(@RequestBody KnowledgeRepoAddVO knowledgeRepoAddVO ) {
		
		SessionUser longinUser = uamSessionService.getSessionUser();
		
		if(knowledgeRepoAddVO != null ){
			boolean bool = knowledgeRepoService.insert(knowledgeRepoAddVO,longinUser);
			if(bool){
				return AjaxResponse.success("知识发布成功！");
			}
		}
		
		return  AjaxResponse.fail("知识发布失败");
	}
	
	/**
	 * 知识库信息详情  查询
	 * @author yumin1
	 * @param knowledgeRepoAddVO  响应结果
	 * @return
	 */
	@RequestMapping(value="knowledgeDetail")  
	@ResponseBody
	public KnowledgeRepoAddVO knowledgeDetail( long knowledgePkid ) {
		
		SessionUser longinUser = uamSessionService.getSessionUser();
		KnowledgeRepoAddVO knowledgeRepoAddVO = knowledgeRepoService.selectDetailByPkid(knowledgePkid);
		
		if(knowledgeRepoAddVO != null ){
			
			return knowledgeRepoAddVO;
		}
		
		  

		
		return  null;
	}
	
	
	/**
	 * 知识库信息删除 
	 * @author yumin1
	 * @return 是否删除成功
	 */
	@RequestMapping(value="knowledgeDelete")  
	@ResponseBody
	public AjaxResponse knowledgeDelete(long knowledgePkid ,String knowledgeCode) {
		
		if(knowledgePkid != 0 ){
			knowledgeRepoService.delete(knowledgePkid,knowledgeCode);
			return AjaxResponse.success("知识删除成功！");
		}
		return AjaxResponse.fail("知识删除失败！");
	}
	
	/**
	 * 知识库信息修改 
	 * @author yumin1
	 * @return 是否修改成功
	 */
	@RequestMapping(value="knowledgeUpdate")  
	@ResponseBody
	public AjaxResponse knowledgeUpdate(@RequestBody KnowledgeRepoAddVO knowledgeRepoAddVO ) {
		SessionUser longinUser = uamSessionService.getSessionUser();
		if(knowledgeRepoAddVO != null ){
			
			boolean bool = knowledgeRepoService.update(knowledgeRepoAddVO,longinUser);
			if(bool){
				return AjaxResponse.success("知识修改成功！");
			}
		}
		
		return AjaxResponse.fail("知识修改失败！");
	}
	/**
	 * 删除图片
	 * @param pkid
	 * @return AjaxResponse
	 */
	@RequestMapping(value="delFile")  
	public AjaxResponse delFile(@RequestBody String pkid) {
		SessionUser longinUser = uamSessionService.getSessionUser();
		int bool = knowledgeRepoService.delFile(Long.parseLong(pkid));
		if(bool>0){
			return AjaxResponse.success(Integer.toString(bool));
		}
		return AjaxResponse.success(Integer.toString(bool));
	}
	/**
	 * 更改 是否置顶
	 * @param pkid
	 * @return
	 */
	@RequestMapping(value="isTop")  
	public AjaxResponse isTop(String pkid,String isTopNum) {
		AjaxResponse result = new AjaxResponse();
		SessionUser longinUser = uamSessionService.getSessionUser();
		int bool = knowledgeRepoService.updateIsTop(pkid,isTopNum);
		if(bool>0){
			result.setSuccess(true);
			result.setMessage("操作成功!");
		}else{
			result.setSuccess(false);
			result.setMessage("操作失败,请刷新后重试!");
		}
		return result;
	}
	/**
	 * 更改 是否推荐
	 * @param pkid
	 * @param isTopNum
	 * @return
	 */
	@RequestMapping(value="isRecommand")  
	public AjaxResponse isRecommand(String pkid,String isRecomNum) {
		AjaxResponse result = new AjaxResponse();
		SessionUser longinUser = uamSessionService.getSessionUser();
		int bool = knowledgeRepoService.updateIsRecommand(pkid,isRecomNum);
		if(bool>0){
			result.setSuccess(true);
			result.setMessage("操作成功!");
		}else{
			result.setSuccess(false);
			result.setMessage("操作失败,请刷新后重试!");
		}
		return result;
	}
	
	
}
