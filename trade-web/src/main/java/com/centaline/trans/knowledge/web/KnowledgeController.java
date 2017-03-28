package com.centaline.trans.knowledge.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.knowledge.entity.KnowledgeRepo;
import com.centaline.trans.knowledge.service.KnowledgeRepoService;
import com.centaline.trans.knowledge.vo.KnowledgeRepoAddVO;

/**
 * 知识库查询、 跳转 Controller 创建时间：2015-09-18
 * 
 * @author yumin1
 */
@Controller
@RequestMapping(value = "/knowledge")
public class KnowledgeController {

	@Autowired(required = true)
	private UamPermissionService uamPermissionService;
	@Autowired(required = true)
	KnowledgeRepoService knowledgeRepoService;
	@Autowired(required = true)
	UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@RequestMapping(value = "read")
	public String read() {
		return "knowledge/read";
	}

	// 点赞或者取消点赞
	@RequestMapping(value = "doLike")
	public AjaxResponse likeOrUnlikeKnowledgeRepo(Long pkid, String like) {
		String userId = uamSessionService.getSessionUser().getId();
		return new AjaxResponse<>(
				knowledgeRepoService.likeOrUnlikeKnowledgeRepo(pkid, userId,
						like) == 1);
	}

	@RequestMapping(value = "detail")
	public String detail(Model model, Long id) {
		KnowledgeRepoAddVO knowledgeRepoAddVO = knowledgeRepoService
				.selectDetailByPkid(id);
		KnowledgeRepo kr = knowledgeRepoAddVO.getKnowledgeRepo();
		User u = uamUserOrgService.getUserById(kr.getPublisher());
		if (u != null) {
			kr.setPubiisherCode(u.getEmployeeCode());
			kr.setPubiisherName(u.getRealName());
		}
		knowledgeRepoService.knowledeClickCount(id);// 点击量计数
		model.addAttribute("data", knowledgeRepoAddVO);
		return "knowledge/detail";
	}

	/**
	 * 跳转知识库列表
	 * 
	 * @author yumin1
	 * @param model
	 * @return 跳转jsp页面
	 */
	@RequestMapping(value = "release")
	public String toKnowledgeList(Model model) {
		model.addAttribute("userId", uamSessionService.getSessionUser().getId());
		return "knowledge/knowledgeList";
	}

	/**
	 * 知识库 添加按钮跳转
	 * 
	 * @author yumin1
	 * @param model
	 * @param knowledgeRepo
	 *            知识库对象
	 * @param knowledgeRepoAttachments
	 *            知识库附件集合
	 * @return 跳转jsp页面
	 */
	@RequestMapping(value = "box/toKnowledgeAdd")
	public String toKnowledgeAdd(Model model) {

		return "knowledge/knowledgeAdd";
	}

	/**
	 * 知识库 详情按钮跳转
	 * 
	 * @author yumin1
	 * @param model
	 * @return 跳转jsp页面
	 */
	@RequestMapping(value = "box/toKnowledgeDetail")
	public String toKnowledgeDetail(Model model, String knowledgePkid) {
		model.addAttribute("knowledgePkid", knowledgePkid); // ID
		// 图片服务器的路径
		App app = uamPermissionService.getAppByAppName("shcl-filesvr-web");
		model.addAttribute("filesvr", app.genAbsoluteUrl());

		return "knowledge/knowledgeDetail";
	}

	/**
	 * 知识库 修改按钮跳转
	 * 
	 * @author yumin1
	 * @param model
	 * @return 跳转jsp页面
	 */
	@RequestMapping(value = "box/toKnowledgeUpdate")
	public String toKnowledgeUpdate(Model model, String knowledgePkid) {

		model.addAttribute("knowledgePkid", knowledgePkid); // ID

		// 图片服务器的路径
		App app = uamPermissionService.getAppByAppName("shcl-image-web");
		model.addAttribute("imgweb", app.genAbsoluteUrl());

		return "knowledge/knowledgeUpdate";
	}

}
