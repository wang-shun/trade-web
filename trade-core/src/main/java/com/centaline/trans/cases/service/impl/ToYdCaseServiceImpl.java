
package com.centaline.trans.cases.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.message.core.remote.vo.Message;
import com.aist.message.core.remote.vo.MessageType;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.bizwarn.service.BizWarnInfoService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToCaseInfoCountVo;
import com.centaline.trans.cases.entity.ToCaseMerge;
import com.centaline.trans.cases.entity.ToOrgVo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCaseMergeMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.service.ToCloseService;
import com.centaline.trans.cases.service.ToYdCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.vo.CaseDetailProcessorVO;
import com.centaline.trans.cases.vo.CaseMergeVo;
import com.centaline.trans.cases.vo.CaseResetVo;
import com.centaline.trans.cases.vo.VCaseDistributeUserVO;
import com.centaline.trans.comment.entity.ToCaseComment;
import com.centaline.trans.comment.repository.ToCaseCommentMapper;
import com.centaline.trans.common.entity.CaseMergerParameter;
import com.centaline.trans.common.entity.TgServItemAndProcessor;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.enums.CaseMergeStatusEnum;
import com.centaline.trans.common.enums.CasePropertyEnum;
import com.centaline.trans.common.enums.CaseStatusEnum;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.MsgCatagoryEnum;
import com.centaline.trans.common.enums.MsgLampEnum;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.common.enums.TransJobs;
import com.centaline.trans.common.enums.WorkFlowEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.common.repository.TgServItemAndProcessorMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.common.service.PropertyUtilsService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.TgServItemAndProcessorService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.common.vo.AgentManagerInfo;
import com.centaline.trans.common.vo.BuyerSellerInfo;
import com.centaline.trans.engine.bean.ProcessInstance;
import com.centaline.trans.engine.bean.TaskOperate;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.engine.service.ProcessInstanceService;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;
import com.centaline.trans.engine.vo.TaskVo;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.property.service.ToPropertyService;
import com.centaline.trans.spv.service.ToSpvService;
import com.centaline.trans.task.service.TlTaskReassigntLogService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.service.UnlocatedTaskService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.utils.ConstantsUtil;

@Service
@Transactional
public class ToYdCaseServiceImpl implements ToYdCaseService {

	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private ToSpvService toSpvService;
	@Autowired
	private ToCaseInfoService toCaseInfoService;
	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private WorkFlowManager workflowManager;
	@Autowired
	private ToCloseService toCloseService;
	@Autowired
	private UamUserOrgService uamUserOrgService;
	@Autowired(required = true)
	ToPropertyService toPropertyService;
	@Autowired(required = true)
	TgGuestInfoService tgGuestInfoService;
	@Autowired(required = true)
	TgServItemAndProcessorService tgServItemAndProcessorService;
	@Autowired(required = true)
	ToPropertyInfoService toPropertyInfoService;
	@Autowired(required = true)
	private TransplanServiceFacade transplanServiceFacade;
	@Autowired(required = true)
	private PropertyUtilsService propertyUtilsService;
	@Autowired(required=true)
	private UamTemplateService uamTemplateService;
	@Autowired(required=true)
	@Qualifier("uamMessageServiceClient")
	private UamMessageService uamMessageService;
	@Autowired(required = true)
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private TlTaskReassigntLogService taskReassingtLogService;
	@Autowired
	private ProcessInstanceService processInstanceService;
	@Autowired
	private TgServItemAndProcessorMapper tgServItemAndProcessorMapper;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToCaseMergeMapper toCaseMergeMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	@Autowired
	private ToWorkFlowService workflowService;
	@Autowired
	private UnlocatedTaskService unlocatedTaskService;
	@Autowired
	private ToCaseService caseService;
	@Autowired
	private ToCaseInfoService caseInfoservice;
	@Autowired
	private BizWarnInfoService bizWarnInfoService;
	@Autowired
	private ToMortgageService toMortgageService;
	@Autowired
	private ToCaseCommentMapper toCaseCommentMapper;
	@Autowired
	private TgServItemAndProcessorMapper tgservItemAndProcessorMapper;
	
	
	
	/**
	 * 新建外单案件
	 * @author hejf10
	 * @param caseMergeVo【页面外单输入信息】
	 * @return
	 */
	public void addYDCaseSave(CaseMergeVo caseMergeVo) throws Exception{
		SessionUser user = uamSessionService.getSessionUser();
		
	}
	

}