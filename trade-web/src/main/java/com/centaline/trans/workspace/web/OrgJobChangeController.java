package com.centaline.trans.workspace.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.UserOrgJob;

@Controller
@RequestMapping("/workbench")
public class OrgJobChangeController {
	@Autowired
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageServiceClient;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UamSessionService uamSessionService;

	@Autowired
	private UamUserOrgService uamUserOrgService;

	@RequestMapping("/listUserOrgJob.json")
	public @ResponseBody
	List<UserOrgJob> listUserOrgJob() {
		SessionUser sessionUser = uamSessionService.getSessionUser();
		List<UserOrgJob> uojs = uamUserOrgService
				.getUserOrgJobByUserId(sessionUser.getId());
		return uojs;
	}

	@RequestMapping("/sessionUser.json")
	public @ResponseBody
	SessionUser getSessionUser() {
		SessionUser sessionUser = uamSessionService.getSessionUser();
		return sessionUser;
	}

	@RequestMapping("/changUserOrgJob.json")
	public @ResponseBody
	AjaxResponse changUserOrgJob(String orgId, String jobId) {
		try {
			uamSessionService.changSessionOrgAndJob(orgId, jobId);
			return AjaxResponse.success();
		} catch (RuntimeException e) {
			logger.debug("Fail to change UserOrgJob:", e);
			return AjaxResponse.fail(e.getMessage());
		}
	}

}
