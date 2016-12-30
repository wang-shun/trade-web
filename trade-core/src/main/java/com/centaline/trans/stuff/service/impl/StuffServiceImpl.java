package com.centaline.trans.stuff.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.service.ToCaseCommentService;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.engine.annotation.TaskOperate;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.stuff.enums.CommentType;
import com.centaline.trans.stuff.service.StuffAssigneeGetService;
import com.centaline.trans.stuff.service.StuffService;

@Service
public class StuffServiceImpl implements StuffService {
	private Logger logger = LoggerFactory.getLogger(StuffServiceImpl.class);
	@Autowired
	private ToCaseCommentService toCaseCommentService;
	@Autowired
	private ProcessInstanceService processService;
	@Autowired
	private PropertyUtilsService propertyUtilsService;
	@Autowired
	private StuffAssigneeGetService stuffAssigneeGetService;
	@Autowired
	private ToWorkFlowService workFlowService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Override
	public void reqStuff(ToCaseComment stuffComment, Boolean isNotifyCustoemr) {
		/// toCaseCommentService.insertToCaseComment(stuffComment);这里只处理流程相关
		if (CommentType.BUJIAN.getCode().equals(stuffComment.getType())) {
			Map<String, Object> vars = new HashMap<>();
			User assigneeUser = stuffAssigneeGetService.getAssignee(stuffComment);
			vars.put("assignee", assigneeUser.getUsername());
			StartProcessInstanceVo vo = processService.startWorkFlowByDfId(
					propertyUtilsService.getProcessDfId(WorkFlowEnum.REQ_STUFF.getName()), stuffComment.getPkid() + "",
					vars);
			ToWorkFlow wf = new ToWorkFlow();
			wf.setBusinessKey(WorkFlowEnum.WBUSSKEY.getCode());
			wf.setCaseCode(stuffComment.getCaseCode());
			wf.setBizCode(stuffComment.getPkid() + "");
			wf.setProcessOwner(assigneeUser.getId());
			wf.setProcessDefinitionId(propertyUtilsService.getProcessDfId(WorkFlowEnum.WBUSSKEY.getCode()));
			wf.setInstCode(vo.getId());
			workFlowService.insertSelective(wf);
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("StuffService.reqStuff() comment type not BJJIAN only insert comment!");
			}
		}
	}

	@Override
	public ToCaseComment getCommentParentByBizCode(String bizCode) {
		ToCaseComment comment = toCaseCommentService.getCommentParentByBizCode(bizCode);
		User user = uamUserOrgService.getUserById(comment.getCreateBy());
		Org org = uamUserOrgService.getOrgById(comment.getCreatorOrgId());
		if (user != null) {
			comment.setCreateByShow(user.getRealName());
		}
		if (org != null) {
			comment.setCreatorOrgIdShow(org.getOrgName());
		}
		return comment;
	}

	@TaskOperate
	@Override
	public void submit(ToCaseComment stuffComment, String taskId) {
		// 每有业务要处理
	}

	@Override
	public ToCaseComment findCommentById(String pkid) {
		ToCaseComment comment = toCaseCommentService.findCommentById(Long.valueOf(pkid));
		User user = uamUserOrgService.getUserById(comment.getCreateBy());
		Org org = uamUserOrgService.getOrgById(comment.getCreatorOrgId());
		if (user != null) {
			comment.setCreateByShow(user.getRealName());
		}
		if (org != null) {
			comment.setCreatorOrgIdShow(org.getOrgName());
		}
		return comment;
	}
}
