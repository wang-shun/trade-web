package com.centaline.trans.mgr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.SQLUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.mgr.entity.UnlocateGRP;
import com.centaline.trans.mgr.repository.UnlocateGRPMapper;
import com.centaline.trans.mgr.service.UnlocateGRPService;

@Service
public class UnlocateGRPServiceImpl implements UnlocateGRPService {
	@Autowired
	private UamPermissionService uamPermissionService;
	@Autowired
	private UamTemplateService uamTemplateService;
	@Autowired
	private UamBasedataService uamBasedataService;
	@Autowired
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	private App app;
	@Autowired
	private UnlocateGRPMapper unlocateGRPMapper;

	@Override
	public void execute() {
		if (this.app == null) {
			this.app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_TRADE.getCode());
		}

		String ctx = app.genAbsoluteUrl();
		String tempCode = "UnlocateGRPList";

		Integer count = unlocateGRPMapper.count();

		if (count != null && count > 0) {
			String subMsg = getSubMsg();
			Map<String, Object> vars = new HashMap<>();
			vars.put("grp_no", count);
			vars.put("wz_info", subMsg);
			vars.put("domain", ctx);
			String msg = uamTemplateService.mergeTemplate(tempCode, vars);
			sendMsgToManager(msg);
		}

	}

	private String getSubMsg() {
		StringBuffer subMsg = new StringBuffer();
		List<UnlocateGRP> list = unlocateGRPMapper.groupByCount();
		for (int i = 0; i < list.size(); i++) {
			UnlocateGRP unlocateGRP = list.get(i);
			subMsg.append(unlocateGRP.getOrgName()).append("区域有").append(unlocateGRP.getCount()).append("个");
			if (i == list.size() - 1) {
				subMsg.append(".");
			} else {
				subMsg.append(",");
			}
		}
		return subMsg.toString();
	}

	private void sendMsgToManager(String msg) {
		List<User> managerList = uamUserOrgService
				.getUserByJobId(uamUserOrgService.getJobByCode(TransJobs.YCADM.getCode()).getId());
		Dict d = uamBasedataService.findDictByTypeAndCode(MsgCatagoryEnum.WORK.getCode(), "3");
		List<String> userIds = new ArrayList<>();
		for (User user : managerList) {
			userIds.add(user.getId());
		}
		Message message = new Message();
		message.setTitle(d.getName());// 消息标题
		message.setType(MessageType.SITE);// 消息类型
		message.setMsgCatagory(MsgCatagoryEnum.WORK.getCode());
		message.setContent(msg);
		message.setSenderId(uamUserOrgService.getUserByUsername("admin").getId());
		try {
			uamMessageService.sendMessageByDist(message, userIds);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
