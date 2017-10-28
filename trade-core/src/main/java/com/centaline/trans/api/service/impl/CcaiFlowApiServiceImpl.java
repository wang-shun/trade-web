package com.centaline.trans.api.service.impl;

import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.ApiService;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiFeedBack;
import com.centaline.trans.api.vo.CcaiFlowApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
		if(serviceIsEnable()){
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
			CcaiFlowApiResultData result = restTemplate.postForObject(url,post,CcaiFlowApiResultData.class);
			return result;
		}else{
			ApiResultData result = new CcaiFlowApiResultData();
			result.setMessage("接口服务未开启,默认true");
			result.setSuccess(true);
			return result;
		}
	}


}
