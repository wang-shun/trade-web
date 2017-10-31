package com.centaline.trans.api.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.entity.CcaiFlowPushLog;
import com.centaline.trans.api.repository.CcaiFlowPushLogMapper;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiFeedBack;
import com.centaline.trans.api.vo.CcaiFlowApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * CCAI流程反馈实现
 * @author yinchao
 * @date 2017/9/21
 */
@Service
public class CcaiFlowApiServiceImpl extends ApiService implements FlowApiService {

	@Autowired
	ToCaseInfoMapper caseInfoMapper;

	@Autowired
	CcaiFlowPushLogMapper logMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ApiResultData tradeFeedBackCcai(String caseCode, CcaiTaskEnum task, FlowFeedBack info) {
		ToCaseInfo caseInfo = caseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		if(caseInfo!=null&& StringUtils.isNotBlank(caseInfo.getApplyid())){
			return toCcai(caseInfo.getApplyid(),task,info);
		}else{
			CcaiFlowApiResultData result = new CcaiFlowApiResultData();
			result.setCode("99");
			result.setSuccess(false);
			result.setMessage("未获取到对应的案件信息或流程ID信息");
			return result;
		}
	}

	@Override
	public ApiResultData feedBackCcai(String applyId, CcaiTaskEnum task, FlowFeedBack info) {
		if(StringUtils.isNotBlank(applyId)){
			return toCcai(applyId,task,info);
		}else{
			CcaiFlowApiResultData result = new CcaiFlowApiResultData();
			result.setCode("99");
			result.setSuccess(false);
			result.setMessage("CCAI流程实例ID不能为空");
			return result;
		}
	}

	/**
	 * 通用通讯接口
	 * 各个方法 将对应的applyId转换 获取 传入即可
	 * @param applyId CCAI流程ID
	 * @param task 流程环节信息
	 * @param info 审批信息
	 * @return
	 */
	private ApiResultData toCcai(String applyId,CcaiTaskEnum task, FlowFeedBack info){
		CcaiFlowApiResultData result ;
		if(serviceCanPush()){
			//判断是否有与CCAI交互成功的结果有则不交互
			if(!checkExistPush(applyId,task,CcaiFlowResultEnum.SUCCESS)){
				String url = getServiceAddress()+"/CCAIData/PostFlowInfo";
				SessionUser user = info.getUser();
				CcaiFeedBack post = new CcaiFeedBack();
				post.setApplyId(applyId);
				post.setUserName(user.getUsername());
				post.setRealname(user.getRealName());
				post.setGrpCode(user.getServiceDepCode());
				post.setGrpName(user.getServiceDepName());
				post.setActiveName(task.getName());
				post.setWorkFlowType(task.getParent().getCode());
				post.setResult(info.getResult().getCode());
				post.setComment(info.getComment());
				post.setApproveTime(info.getApproveTime());
				result = restTemplate.postForObject(url,post,CcaiFlowApiResultData.class);
				//增加交互日志
				CcaiFlowPushLog log = new CcaiFlowPushLog();
				log.setApplyId(applyId);
				log.setActiveName(task.getName());
				log.setWorkflowType(Integer.toString(task.getParent().getCode()));
				log.setResult(Integer.toString(info.getResult().getCode()));
				log.setApproveTime(new Date());
				logMapper.insertSelective(log);
			}else{
				result = new CcaiFlowApiResultData();
				result.setMessage("已进行过交互，默认true");
				result.setSuccess(true);
			}
		}else{
			result = new CcaiFlowApiResultData();
			result.setMessage("接口服务未开启,默认true");
			result.setSuccess(true);
		}
		return result;
	}

	/**
	 * 判断指定的applyId 对应的任务环节 以及相应的结果是否存在
	 * @param applyId
	 * @param task
	 * @param result
	 * @return true存在交互结果  false不存在
	 */
	private boolean checkExistPush(String applyId, CcaiTaskEnum task, CcaiFlowResultEnum result){
		CcaiFlowPushLog log = new CcaiFlowPushLog();
		log.setApplyId(applyId);
		log.setActiveName(task.getName());
		log.setWorkflowType(Integer.toString(task.getParent().getCode()));
		log.setResult(Integer.toString(result.getCode()));
		long count = logMapper.countLog(log);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

}
