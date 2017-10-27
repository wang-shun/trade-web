package com.centaline.api.ccai.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.api.ccai.listener.EvalFlowWorkListener;
import com.centaline.api.ccai.service.CcaiEvalService;
import com.centaline.api.ccai.vo.*;
import com.centaline.api.common.vo.CcaiServiceResult;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.service.ToCaseInfoService;
import com.centaline.trans.common.enums.*;
import com.centaline.trans.eloan.entity.ToSelfAppInfo;
import com.centaline.trans.eloan.service.ToSelfAppInfoService;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.ExecuteAction;
import com.centaline.trans.engine.core.WorkFlowEngine;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.vo.ExecutionVo;
import com.centaline.trans.eval.entity.ToEvaRefund;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.service.ToEvaRefundService;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.message.activemq.vo.MQCaseMessage;
import com.centaline.trans.message.activemq.vo.MQEvalMessage;
import com.centaline.trans.task.entity.ActRuEventSubScr;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.repository.ActRuEventSubScrMapper;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ccai评估相关导入实现类
 *
 * @author yinchao
 */
@Service
@Transactional
public class CcaiEvalServiceImpl implements CcaiEvalService {
	private static final String REBATE_APPROVE_TYPE = "18";
	private static final String SELFDO_LOAN_TYPE = "20";
	private static final String SELFDO_ASSE_TYPE = "21";
	private static final String BANK_REBATE_APPROVE_TYPE = "22";//银行返利 审批类型
	@Autowired
	ToEvalRebateService toEvalRebateService;

	@Autowired
	ToCaseInfoService toCaseInfoService;

	@Autowired
	ToEvalReportProcessService toEvalReportProcessService;

	@Autowired
	ToApproveRecordService toApproveRecordService;

	@Autowired
	UamUserOrgService uamUserOrgService;

	@Autowired
	private ToEvaRefundService toEvaRefundService;

	@Autowired
	private ToSelfAppInfoService toSelfAppInfoService;
	
	
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;

	@Autowired
	private ToBankRebateService toBankRebateService;
	
	@Autowired
	private JmsMessagingTemplate jmsTemplate; //activemq 消息队列
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();//Hibernate校验工具

	@Override
	public CcaiServiceResult importEvalRebate(EvalRebeatImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old==null){
			//设置案件状态
			if(StringUtils.isBlank(evalRebate.getEvaCode())){
				evalRebate.setStatus(EvalRebateStatusEnum.RELATION.getCode());
			}else{
				evalRebate.setStatus(EvalRebateStatusEnum.DOING.getCode());
			}
			toEvalRebateService.insertSelective(evalRebate);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				// 1-通过 -1-拒绝 0-驳回修改 2-修改完成
				if(task.getResult()==0){//审批未通过
					record.setNotApprove("驳回:"+task.getComment());
				}else if(task.getResult()==-1){
					record.setNotApprove("拒绝:"+task.getComment());
				}
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			MQEvalMessage message;
			//有关联的评估才发起流程
			if(StringUtils.isNotBlank(evalRebate.getEvaCode())){
				//发送启动流程消息
				message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
			}else{
				//发送消息 通知内勤有新的评估返利申请，但是无评估单
				message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.SENDMESSAGE_TYPE);
			}
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单已存在，无法进行同步!");
		}
	}

	@Override
	public CcaiServiceResult updateEvalRebate(EvalRebeatImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old!=null && EvalRebateStatusEnum.BACK.getCode().equals(old.getStatus())){
			//TODO 驳回修改后再次同步
			evalRebate.setPkid(old.getPkid());
			toEvalRebateService.updateByPrimaryKeySelective(evalRebate);
			//先删除旧的审批记录
			toApproveRecordService.deleteByCaseCodeAndType(evalRebate.getCaseCode(),REBATE_APPROVE_TYPE);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				// 1-通过 -1-拒绝 0-驳回修改 2-修改完成
				if(task.getResult()==0){//审批未通过
					record.setNotApprove("驳回:"+task.getComment());
				}else if(task.getResult()==-1){
					record.setNotApprove("拒绝:"+task.getComment());
				}
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			//发送消息 恢复流程 更改返利单状态
			MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单状态不正确，无法进行修改!");
		}
	}

	@Override
	public CcaiServiceResult importEvalRebateReport(EvalRebeatReportImport info) {
		ToEvalRebate evalRebate = validateAndConvert(info);
		ToEvalRebate old = toEvalRebateService.findToEvalRebateByCaseCode(evalRebate.getCaseCode());
		if(old!=null && EvalRebateStatusEnum.SUCCESS.getCode().equals(old.getStatus())){
			//修改原有信息 生成最终报告
			evalRebate.setPkid(old.getPkid());
			evalRebate.setStatus(EvalRebateStatusEnum.FINISH.getCode());
			evalRebate.setCreateTime(info.getCreateTime());
			evalRebate.setEvalRecept(null);//不根据CCAI传入的评估费收据修改 由于天津未保存
			toEvalRebateService.updateByPrimaryKeySelective(evalRebate);
			//先删除旧的审批记录
			toApproveRecordService.deleteByCaseCodeAndType(evalRebate.getCaseCode(),REBATE_APPROVE_TYPE);
			//导入审批记录
			for(TaskInfo task : info.getTasks()){
				ToApproveRecord record = new ToApproveRecord();
				record.setCaseCode(evalRebate.getCaseCode());
				record.setPartCode("CCAI_REBATE");
				record.setApproveType(REBATE_APPROVE_TYPE);
				record.setContent(task.getComment());
				record.setOperatorTime(task.getDealTime());
				//设置处理人
				User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
				if(u!=null){
					record.setOperator(u.getId());
				}else{
					record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
				}
				toApproveRecordService.saveToApproveRecord(record);
			}
			//发送消息 恢复流程 更改返利单状态
			MQEvalMessage message = new MQEvalMessage(evalRebate.getCaseCode(), WorkFlowEnum.EVAL_REBATE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
			jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
			return new CcaiServiceResult("00",true,"成功");
		}else{
			throw new BusinessException("返利单状态不正确，无法更改报告!");
		}
	}

	/**
	 * 将导入的评估返利信息
	 * 校验和转换为系统中的对象，并保存/更新
	 * @param erImport 导入的评估返利信息
	 * @return
	 */
	private ToEvalRebate validateAndConvert(EvalRebeatImport erImport){
		StringBuilder msg = new StringBuilder();
		buildErrorMessage(validator.validate(erImport.getTasks()),msg,"");
		if (msg.length() > 0) {
			throw new BusinessException(msg.toString());
		}
		//TODO 其他更多的校验
		String caseCode = toCaseInfoService.findcaseCodeByccaiCode(erImport.getOringinCcaiCode());
		ToEvalRebate rebate = new ToEvalRebate();
		if(StringUtils.isNotBlank(caseCode)){
			//关联评估报告改为非强制关联 有可能存在评估返利申请完成，评估单并未开始的情况
			ToEvalReportProcess eval = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
			if(eval!=null){
				rebate.setEvaCode(eval.getEvaCode());
			}
			rebate.setCaseCode(caseCode);
			rebate.setCcaiCode(erImport.getCcaiCode());
			rebate.setEvaRpocessId(erImport.getApplyId());
			rebate.setEvalPrice(erImport.getEvalPrice());
			rebate.setEvalRealCharges(erImport.getEvalRealCharges());
			rebate.setEvalDueCharges(erImport.getEvalDueCharges());
			rebate.setInputTime(erImport.getInputTime());
			rebate.setApplyRealName(erImport.getApplyRealName());
			rebate.setApplyUserName(erImport.getApplyUserName());
		}else{
			throw new BusinessException("未获取到返利申请对应的成交报告["+erImport.getOringinCcaiCode()+"]信息!");
		}
		return rebate;
	}

	/**
	 * 根据将校验信息的结果拼接到传入的msgBuilder中，
	 * 如果appendBefore不为null，则拼接到每个错误消息前
	 *
	 * @param validate
	 * @param msgBuilder
	 * @param appendBefore
	 * @param <T>
	 */
	private <T> void buildErrorMessage(Set<ConstraintViolation<T>> validates, StringBuilder msgBuilder, String appendBefore) {
		for (ConstraintViolation constraintViolation : validates) {
			msgBuilder.append(appendBefore).append(constraintViolation.getMessage()).append("\r\n");
		}
	}

	/**
	 * 导入评估退费信息
	 */
	@Override
	public CcaiServiceResult importEvalRefund(EvalRefundImport info) {
		CcaiServiceResult result = new CcaiServiceResult();
		ToEvaRefund toEvaRefund = copyEvaRefund(info);
		if(StringUtils.isBlank(toEvaRefund.getCcaiCode())){
			result.setSuccess(false);
			result.setMessage("同步失败!ccaiCode不能为空!");
			result.setCode("99");
			return result;
		}
		String caseCode = toCaseInfoMapper.findcaseCodeByCcaiCode(toEvaRefund.getCcaiCode());
		if(StringUtils.isBlank(caseCode)){
			result.setSuccess(false);
			result.setMessage("同步失败!ccaiCode没有对应的caseCode!");
			result.setCode("99");
			return result;
		}
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		if(toEvalReportProcess == null){
			result.setSuccess(false);
			result.setMessage("同步失败!caseCode没有生成对应的报告类评估信息!");
			result.setCode("99");
			return result;
		}
		toEvaRefund.setCaseCode(caseCode);
		int count = toEvaRefundService.insertSelective(toEvaRefund);
		if(count <= 0){
			result.setSuccess(false);
			result.setMessage("同步失败!审批信息保存失败!");
			result.setCode("99");
			return result;
		}
		MQEvalMessage message = new MQEvalMessage(caseCode,WorkFlowEnum.EVALREFUND_PROCESS.getCode() ,MQCaseMessage.EVAREFUND_TYPE);
		jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
		result.setSuccess(true);
		result.setMessage("同步成功！");
		result.setCode("00");
		return result;
	}
	private ToEvaRefund copyEvaRefund(EvalRefundImport info) {
		ToEvaRefund toEvaRefund = new ToEvaRefund();
		toEvaRefund.setApplyId(info.getApplyId());
		toEvaRefund.setApplyTime(info.getApplyTime());
		toEvaRefund.setCcaiCode(info.getCcaiCode());
		toEvaRefund.setCity(info.getCity());
		toEvaRefund.setEvalCompany(info.getEvalCompany());
		toEvaRefund.setFinOrgId(info.getEvalCompanyId());
		toEvaRefund.setApplyDepartCode(info.getGrpCode());
		toEvaRefund.setApplyDepart(info.getGrpName());
		toEvaRefund.setProposer(info.getRealName());
		toEvaRefund.setRefundAmount(info.getRefundAmount());
		toEvaRefund.setRefundCause(info.getRefundCause());
		toEvaRefund.setRefundKinds(info.getRefundKinds());
		toEvaRefund.setRefundTarget(info.getRefundTarget());
		toEvaRefund.setToRefundTime(info.getToRefundTime());
		toEvaRefund.setProposerId(info.getUserName());
		return toEvaRefund;
	}

	/**
	 * 导入自办贷款、评估审批信息
	 */
	@Override
	public CcaiServiceResult importSelfDo(SelfDoImport info) {
		ToSelfAppInfo toSelfAppInfo = copyProperties(info);
		String type = info.getType();
		if(type.equals("自办评估")){
			ToSelfAppInfo old = toSelfAppInfoService.getAppInfoByCaseCode(toSelfAppInfo.getCaseCode(),info.getType());//type :自办评估，自办贷款	
			if(old==null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.DOING.getCode());
				//首次同步
				toSelfAppInfoService.addSelfAppInfo(toSelfAppInfo);
				//导入审批记录
				for(TaskInfo task : info.getTasks()){
					ToApproveRecord record = new ToApproveRecord();
					record.setCaseCode(toSelfAppInfo.getCaseCode());
					record.setPartCode("CCAI_SELF_ASSE");
					record.setApproveType(SELFDO_ASSE_TYPE);
					record.setContent(task.getComment());
					record.setOperatorTime(task.getDealTime());
					//设置处理人
					User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
					if(u!=null){
						record.setOperator(u.getId());
					}else{
						record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
					}
					toApproveRecordService.saveToApproveRecord(record);
				}
				//发送消息 启动流程
				MQEvalMessage message = new MQEvalMessage(toSelfAppInfo.getCaseCode(), WorkFlowEnum.ASSE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
				jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
				return new CcaiServiceResult("00",true,"成功");
			}else{
				throw new BusinessException("自办评估信息已存在，无法进行同步!");
			}
		}else if(type.equals("自办贷款")){
			ToSelfAppInfo old = toSelfAppInfoService.getAppInfoByCaseCode(toSelfAppInfo.getCaseCode(),info.getType());//type :自办评估，自办贷款	
			if(old==null){
				toSelfAppInfo.setStatus(SelfDoStatusEnum.DOING.getCode());
				//首次同步
				toSelfAppInfoService.addSelfAppInfo(toSelfAppInfo);
				//导入审批记录
				for(TaskInfo task : info.getTasks()){
					ToApproveRecord record = new ToApproveRecord();
					record.setCaseCode(toSelfAppInfo.getCaseCode());
					record.setPartCode("CCAI_SELF_LOAN");
					record.setApproveType(SELFDO_LOAN_TYPE);
					record.setContent(task.getComment());
					record.setOperatorTime(task.getDealTime());
					//设置处理人
					User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
					if(u!=null){
						record.setOperator(u.getId());
					}else{
						record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
					}
					toApproveRecordService.saveToApproveRecord(record);
				}
				//发送消息 启动流程
				MQEvalMessage message = new MQEvalMessage(toSelfAppInfo.getCaseCode(), WorkFlowEnum.LOANANDASSE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
				jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
				return new CcaiServiceResult("00",true,"成功");
			}else{
				throw new BusinessException("自办贷款信息已存在，无法进行同步!");
			}
		}else {
			throw new BusinessException("自办贷款/评估["+type+"]不正确!");
		}

	}
	
	

	
	

	
	private ToSelfAppInfo copyProperties(SelfDoImport info) {
		String caseCode = toCaseInfoService.findcaseCodeByccaiCode(info.getCcaiCode());
		ToSelfAppInfo toSelfAppInfo = null;
		if(StringUtils.isNotBlank(caseCode)){
			toSelfAppInfo = new ToSelfAppInfo();
			toSelfAppInfo.setApplyTime(info.getApplyTime());
			toSelfAppInfo.setCcaiCode(info.getCcaiCode());
			toSelfAppInfo.setCity(info.getCity());
			toSelfAppInfo.setGrpCode(info.getGrpCode());
			toSelfAppInfo.setGrpName(info.getGrpName());
			toSelfAppInfo.setRealName(info.getRealName());
			toSelfAppInfo.setStatus(info.getStatus());
			toSelfAppInfo.setType(info.getType());
			toSelfAppInfo.setUserName(info.getUserName());
			toSelfAppInfo.setCaseCode(caseCode);
		}else{
			throw new BusinessException("未获取到自办评估/贷款["+info.getCcaiCode()+"]信息!");
		}
		return toSelfAppInfo;
	}

	
	

	@Override
	public CcaiServiceResult updateSelfDo(SelfDoImport info) {
		ToSelfAppInfo toSelfAppInfo = copyProperties(info);
		String type = info.getType();
		if(type.equals("自办评估")){
			ToSelfAppInfo old = toSelfAppInfoService.getAppInfoByCaseCode(toSelfAppInfo.getCaseCode(),info.getType());//type :自办评估，自办贷款	
			if(old!=null && SelfDoStatusEnum.BACK.getCode().equals(old.getStatus())){
				//TODO 驳回修改后再次同步
				toSelfAppInfo.setPkid(old.getPkid());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
				//先删除旧的审批记录
				toApproveRecordService.deleteByCaseCodeAndType(toSelfAppInfo.getCaseCode(),SELFDO_ASSE_TYPE);
				//导入审批记录
				for(TaskInfo task : info.getTasks()){
					ToApproveRecord record = new ToApproveRecord();
					record.setCaseCode(toSelfAppInfo.getCaseCode());
					record.setPartCode("CCAI_SELF_ASSE");
					record.setApproveType(SELFDO_ASSE_TYPE);
					record.setContent(task.getComment());
					record.setOperatorTime(task.getDealTime());
					//设置处理人
					User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
					if(u!=null){
						record.setOperator(u.getId());
					}else{
						record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
					}
					toApproveRecordService.saveToApproveRecord(record);
				}
				//发送消息 恢复流程 更改返利单状态
				MQEvalMessage message = new MQEvalMessage(toSelfAppInfo.getCaseCode(), WorkFlowEnum.ASSE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
				jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
				return new CcaiServiceResult("00",true,"成功");
			}else{
				throw new BusinessException("自办评估状态不正确，无法进行修改!");
			}
		}else if(type.equals("自办贷款")){
			ToSelfAppInfo old = toSelfAppInfoService.getAppInfoByCaseCode(toSelfAppInfo.getCaseCode(),info.getType());//type :自办评估，自办贷款	
			if(old!=null && SelfDoStatusEnum.BACK.getCode().equals(old.getStatus())){
				//TODO 驳回修改后再次同步
				toSelfAppInfo.setPkid(old.getPkid());
				toSelfAppInfoService.updateByPrimaryKeySelective(toSelfAppInfo);
				//先删除旧的审批记录
				toApproveRecordService.deleteByCaseCodeAndType(toSelfAppInfo.getCaseCode(),SELFDO_LOAN_TYPE);
				//导入审批记录
				for(TaskInfo task : info.getTasks()){
					ToApproveRecord record = new ToApproveRecord();
					record.setCaseCode(toSelfAppInfo.getCaseCode());
					record.setPartCode("CCAI_SELF_LOAN");
					record.setApproveType(SELFDO_LOAN_TYPE);
					record.setContent(task.getComment());
					record.setOperatorTime(task.getDealTime());
					//设置处理人
					User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
					if(u!=null){
						record.setOperator(u.getId());
					}else{
						record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
					}
					toApproveRecordService.saveToApproveRecord(record);
				}
				//发送消息 恢复流程 更改返利单状态
				MQEvalMessage message = new MQEvalMessage(toSelfAppInfo.getCaseCode(), WorkFlowEnum.LOANANDASSE_PROCESS.getCode(),MQEvalMessage.UPDATEFLOW_TYPE);
				jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
				return new CcaiServiceResult("00",true,"成功");
			}else{
				throw new BusinessException("自办贷款审批状态不正确，无法进行修改!");
			}
		}else{
			throw new BusinessException("自办贷款/评估["+type+"]不正确!");
		}
	}

	@Override
	public CcaiServiceResult bankRebateFeedBack(BankRebeatFeedBack feedBack) {
		ToBankRebate rebate = toBankRebateService.selectById(feedBack.getBackID());
		if(rebate!=null){
			if(BankRebateStatusEnum.SUBMIT.getCode().equals(rebate.getStatus())){
				//先删除旧的审批记录
				toApproveRecordService.deleteByCaseCodeAndType(rebate.getGuaranteeCompId(),BANK_REBATE_APPROVE_TYPE);
				//导入审批记录
				for(TaskInfo task : feedBack.getTasks()){
					ToApproveRecord record = new ToApproveRecord();
					record.setCaseCode(rebate.getGuaranteeCompId());
					record.setPartCode("CCAI_BANK_REBATE");
					record.setApproveType(BANK_REBATE_APPROVE_TYPE);
					record.setContent(task.getComment());
					record.setOperatorTime(task.getDealTime());
					//设置处理人
					User u = uamUserOrgService.getUserByUsername(task.getApplyUserName());
					if(u!=null){
						record.setOperator(u.getId());
					}else{
						record.setOperator(task.getApplyRealName()+"_"+task.getApplyUserName());
					}
					toApproveRecordService.saveToApproveRecord(record);
				}
				rebate.setCaseCode(feedBack.getApplyId());//保存CCAI流程ID
				rebate.setCompanyAccount(feedBack.getCompanyAccount());//银行账户
				if(CcaiFlowResultEnum.SUCCESS.getCode() == feedBack.getResult()){
					rebate.setStatus(BankRebateStatusEnum.FINISH.getCode());
					//保存银行返利案件关联关系
					saveBankRelation(rebate.getGuaranteeCompId(),feedBack.getRelation());
					//发起返利单 权证经理审批流程 发消息
					MQEvalMessage message = new MQEvalMessage(rebate.getPkid(), WorkFlowEnum.BANK_REBATE_PROCESS.getCode(),MQEvalMessage.STARTFLOW_TYPE);
					jmsTemplate.convertAndSend(EvalFlowWorkListener.getEvalQueueName(), message);
				}else{
					rebate.setStatus(BankRebateStatusEnum.BACK.getCode());
				}
				toBankRebateService.updateToBankRebate(rebate);
				return new CcaiServiceResult("00",true,"成功");
			}else{
				return new CcaiServiceResult("99",false,"银行返利申请状态不正确!");
			}
		}else{
			return new CcaiServiceResult("99",false,"未获取到对应的申请信息!");
		}
	}

	/**
	 * 更新成交报告与返利报告的关联关系
	 * @param compId 返利报告批次号
	 * @param relations CCAI返回的关联关系
	 */
	private void saveBankRelation(String compId,List<ReportRelation> relations){
		ToBankRebateInfoVO vo = toBankRebateService.selectRebateByGuaranteeCompId(compId);
		if(vo!=null && vo.getToBankRebate()!=null && vo.getToBankRebateInfoList() != null){
			if(relations.size() != vo.getToBankRebateInfoList().size()){
				throw new BusinessException("返利报告中，案件信息数量与申请时不一致!");
			}
			Map<String,ToBankRebateInfo> temp = new HashMap<>();
			for(ToBankRebateInfo info : vo.getToBankRebateInfoList()){
				temp.put(info.getCcaiCode(),info);
			}
			StringBuilder msg = new StringBuilder();
			for(ReportRelation relation : relations){
				ToBankRebateInfo info = temp.get(relation.getCcaiCode());
				if(info==null){
					msg.append("成交报告编号["+relation.getCcaiCode()+"]的申请信息未获取到\r\n");
				}else{
					info.setApplyId(relation.getApplyId());
					info.setReportCode(relation.getRebateReportCode());
					toBankRebateService.updateToBankRebateInfo(info);
				}
			}
			if(msg.length()>0){
				throw new BusinessException(msg.toString());
			}
		}else{
			throw new BusinessException("未获取到对应的返利报告信息!");
		}
	}
}