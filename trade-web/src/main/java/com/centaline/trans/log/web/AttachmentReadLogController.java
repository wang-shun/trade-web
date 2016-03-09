package com.centaline.trans.log.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.log.entity.TsAttachmentReadLog;
import com.centaline.trans.log.service.TsAttachmentReadLogService;

@Controller
@RequestMapping("/log")
public class AttachmentReadLogController {
	
	@Autowired
	private TsAttachmentReadLogService tsAttachmentReadLogService;
	@Autowired
	private UamSessionService sessionService;

	@SuppressWarnings("rawtypes")
	@RequestMapping("addAttachmentReadLog")
	@ResponseBody
	public AjaxResponse addAttachmentReadLog(TsAttachmentReadLog attachmentReadLog) {
		SessionUser user = sessionService.getSessionUser();
		AjaxResponse result = new AjaxResponse();
		try {
			attachmentReadLog.setReaderId(user.getId());
			attachmentReadLog.setReaderName(user.getRealName());
			attachmentReadLog.setReadTime(new Date());
			tsAttachmentReadLogService.addTsAttachmentReadLog(attachmentReadLog);
			return result.success("附件日志记录成功");
		} catch (Exception e) {
			e.printStackTrace();
			return result.fail("附件日志记录失败");
		}
	}

}
