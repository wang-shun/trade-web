package com.centaline.trans.transplan.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.enums.PartCodeEnum;
import com.centaline.trans.common.enums.ToApproveRecordEnum;
import com.centaline.trans.common.enums.WorkFlowStatus;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.task.entity.ToTransPlanOrToPropertyInfo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TsTaskPlanSet;
import com.centaline.trans.transplan.entity.TsTransPlanHistory;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.repository.ToTransPlanMapper;
import com.centaline.trans.transplan.repository.TsTaskPlanSetMapper;
import com.centaline.trans.transplan.repository.TsTransPlanHistoryMapper;
import com.centaline.trans.transplan.repository.TtsReturnVisitRegistrationMapper;
import com.centaline.trans.transplan.repository.TtsTransPlanHistoryBatchMapper;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TransPlanVO;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;
import com.centaline.trans.utils.ConstantsUtil;

@Service
public class TransplanServiceFacadeImpl implements TransplanServiceFacade {

	@Autowired
	private ToTransPlanMapper toTransPlanMapper;
	@Autowired
	private TsTransPlanHistoryMapper tsTransPlanHistoryMapper;
	@Autowired
	private TtsTransPlanHistoryBatchMapper ttsTransPlanHistoryBatchMapper;
	@Resource
	private TtsReturnVisitRegistrationMapper ttsReturnVisitRegistrationMapper;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private TsTaskPlanSetMapper tsTaskPlanSetMapper;
	@Autowired
	private ToWorkFlowService toWorkFlowService;
	@Autowired
	private WorkFlowManager workFlowManager;
	@Autowired
	private LoanlostApproveService loanlostApproveService;
	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Override
	public void processRestartOrResetOperate(String caseCode,
			String changeReason) {
		List<ToTransPlan> ttps = toTransPlanMapper
				.findTransPlanByCaseCode(caseCode);
		TsTransPlanHistory tsTransPlanHistory = null;
		SessionUser sessionUser = uamSessionService.getSessionUser();
		TtsTransPlanHistoryBatch ttpb = new TtsTransPlanHistoryBatch();
		ttpb.setCaseCode(caseCode);
		if (ttps != null && ttps.size() > 0) {
			ttpb.setPartCode(ttps.get(0).getPartCode());
		}
		ttpb.setOperateFlag("1");// 流程
		ttpb.setChangeReason(changeReason);
		ttsTransPlanHistoryBatchMapper.insertSelective(ttpb);
		if (ttps != null && ttps.size() > 0) {
			// 将交易计划表的数据转移到交易计划历史表
			for (ToTransPlan ttp : ttps) {
				tsTransPlanHistory = new TsTransPlanHistory();
				tsTransPlanHistory.setCaseCode(caseCode);
				tsTransPlanHistory.setBatchId(ttpb.getPkid());
				tsTransPlanHistory.setChangeReason(changeReason);
				tsTransPlanHistory.setOldEstPartTime(ttp.getEstPartTime());
				tsTransPlanHistory.setPartCode(ttp.getPartCode());
				tsTransPlanHistory.setChangerId(sessionUser.getId());
				tsTransPlanHistory.setChangeTime(new Date());
				tsTransPlanHistoryMapper.insertSelective(tsTransPlanHistory);
			}
		}
		// 删除交易计划表该案件相关信息
		Map map = new HashMap();
		map.put("caseCode", caseCode);
		/*if (ConstantsUtil.PROCESS_RESTART.equals(changeReason)) {
			// 流程重启保留首次跟进环节信息并更新首次跟进原预计时间
			map.put("partCode", ToAttachmentEnum.FIRSTFOLLOW.getCode());
			TsTaskPlanSet tps = tsTaskPlanSetMapper
					.getTsTaskPlanSetByPartCode(ToAttachmentEnum.FIRSTFOLLOW
							.getCode());
			if (tps != null) {
				ToTransPlan plan = new ToTransPlan();
				plan.setCaseCode(caseCode);
				plan.setPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, tps.getPlanDays());
				plan.setEstPartTime(cal.getTime());
				if (toTransPlanMapper.findTransPlan(plan) != null) {
					toTransPlanMapper.updateTransPlanSelective(plan);
				}
			}

		}*/
		toTransPlanMapper.deleteTransPlansByCaseCode(map);

	}

	@Override
	public int insertTtsTransPlanHistoryBatch(
			TtsTransPlanHistoryBatch ttsTransPlanHistoryBatch) {
		return ttsTransPlanHistoryBatchMapper
				.insertSelective(ttsTransPlanHistoryBatch);
	}

	@Override
	public List<TtsReturnVisitRegistration> queryReturnVisitRegistrations(
			long batchId) {
		return ttsReturnVisitRegistrationMapper
				.queryReturnVisitRegistrations(batchId);
	}

	@Override
	public int addReturnVisit(
			TtsReturnVisitRegistration ttsReturnVisitRegistration) {
		// 更新历史批次表最新回访跟进信息
		TtsTransPlanHistoryBatch record = new TtsTransPlanHistoryBatch();
		record.setPkid(ttsReturnVisitRegistration.getBatchId());
		record.setLastVisitRemark(ttsReturnVisitRegistration.getVisitRemark());
		record.setLastContent(ttsReturnVisitRegistration.getContent());
		ttsTransPlanHistoryBatchMapper
				.updateTtsTransPlanHistoryBatchMapper(record);
		return ttsReturnVisitRegistrationMapper
				.insertReturnVisitRegistration(ttsReturnVisitRegistration);
	}

	@Override
	public List<TsTransPlanHistoryVO> queryTtsTransPlanHistorys(
			TsTransPlanHistoryVO tsTransPlanHistoryVO) {
		return tsTransPlanHistoryMapper
				.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
	}

	@Override
	public int insertSelective(ToTransPlan record) {
		return toTransPlanMapper.insertSelective(record);
	}

	@Override
	public List<ToTransPlan> queryPlansByCaseCode(String caseCode) {
		return toTransPlanMapper.findTransPlanByCaseCode(caseCode);
	}

	@Override
	public ToTransPlan selectByPrimaryKey(Long pkid) {
		return toTransPlanMapper.selectByPrimaryKey(pkid);
	}

	@Override
	public int updateByPrimaryKeySelective(ToTransPlan record) {
		return toTransPlanMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Boolean updateTransPlan(ToTransPlan toTransPlan) {
		if (toTransPlanMapper.findTransPlan(toTransPlan) != null) {
			if (toTransPlanMapper.updateTransPlanSelective(toTransPlan) > 0) {
				return true;
			}
		} else {
			if (toTransPlanMapper.insertSelective(toTransPlan) > 0) {
				return true;
			}
			;
		}
		return false;
	}

	@Override
	public ToTransPlan findTransPlan(ToTransPlan toTransPlan) {
		return toTransPlanMapper.findTransPlan(toTransPlan);
	}

	@Override
	public int deleteTransPlansByCaseCode(String caseCode) {
		Map map = new HashMap();
		map.put("caseCode", caseCode);
		return toTransPlanMapper.deleteTransPlansByCaseCode(map);
	}

	@Override
	public TransPlanVO findTransPlanByCaseCode(String caseCode) {
		List<ToTransPlan> list = toTransPlanMapper
				.findTransPlanByCaseCode(caseCode);
		TransPlanVO transPlanVO = new TransPlanVO();
		for (ToTransPlan toTransPlan : list) {

			if (PartCodeEnum.FK.getCode().equals(toTransPlan.getPartCode())) {/* 放款 */
				transPlanVO.setEstPartTimeFk(toTransPlan.getEstPartTime());
				transPlanVO.setPkidFk(toTransPlan.getPkid());
			} else if (PartCodeEnum.JS.getCode().equals(toTransPlan.getPartCode())) { /*缴税Pt*/
				transPlanVO.setEstPartTimePt(toTransPlan.getEstPartTime());
				transPlanVO.setPkidPt(toTransPlan.getPkid());
			}else if (PartCodeEnum.GH.getCode().equals(toTransPlan.getPartCode())) {/* 过户 */
				transPlanVO.setEstPartTimeGh(toTransPlan.getEstPartTime());
				transPlanVO.setPkidGh(toTransPlan.getPkid());
			}else if (PartCodeEnum.LZ.getCode().equals(toTransPlan.getPartCode())) {/* 领证 */
				transPlanVO.setEstPartTimeLz(toTransPlan.getEstPartTime());
				transPlanVO.setPkidLz(toTransPlan.getPkid());
			}else if (PartCodeEnum.SDMQ.getCode().equals(toTransPlan.getPartCode())) { /*商贷面签Cs*/
				transPlanVO.setEstPartTimeCs(toTransPlan.getEstPartTime());
				transPlanVO.setPkidCs(toTransPlan.getPkid());
			}else if (PartCodeEnum.SDCPG.getCode().equals(toTransPlan.getPartCode())) { /*商贷出评估报告Br*/
				transPlanVO.setEstPartTimeBr(toTransPlan.getEstPartTime());
				transPlanVO.setPkidBr(toTransPlan.getPkid());
			}else if (PartCodeEnum.SDPD.getCode().equals(toTransPlan.getPartCode())) {/*商贷批贷完成Cc*/
				transPlanVO.setEstPartTimeCc(toTransPlan.getEstPartTime());
				transPlanVO.setPkidCc(toTransPlan.getPkid());
			}else if (PartCodeEnum.GJJDK.getCode().equals(toTransPlan.getPartCode())) { /*公积金贷款预约申请Pa*/
				transPlanVO.setEstPartTimePa(toTransPlan.getEstPartTime());
				transPlanVO.setPkidPa(toTransPlan.getPkid());
			}else if (PartCodeEnum.GJJMQ.getCode().equals(toTransPlan.getPartCode())) { /*公积金面签Pfs*/
				transPlanVO.setEstPartTimePfs(toTransPlan.getEstPartTime());
				transPlanVO.setPkidPfs(toTransPlan.getPkid());
			}else if (PartCodeEnum.GJJPD.getCode().equals(toTransPlan.getPartCode())) { /*公积金批贷完成Pfc*/
				transPlanVO.setEstPartTimePfc(toTransPlan.getEstPartTime());
				transPlanVO.setPkidPfc(toTransPlan.getPkid());
			}
		}
		return transPlanVO;
	}

	@Override
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(
			String leadingProcessId) {
		List<ToTransPlanOrToPropertyInfo> toTransPlanList = toTransPlanMapper
				.getToTransPlanByUserId(leadingProcessId);
		return toTransPlanList;
	}

	@Override
	public boolean saveToTransPlan(TransPlanVO transPlanVO) {
		if (transPlanVO.getCaseCode() != null
				&& transPlanVO.getCaseCode().intern().length() != 0) {

			/**缴税Pt*/
			if(transPlanVO.getEstPartTimePt()!=null) {
				ToTransPlan toTransPlanPt = new ToTransPlan();
				toTransPlanPt.setEstPartTime(transPlanVO.getEstPartTimePt());
				toTransPlanPt.setCaseCode(transPlanVO.getCaseCode());
				if (transPlanVO.getPkidPt() != null) {
					toTransPlanPt.setPkid(transPlanVO.getPkidPt());
					toTransPlanPt.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanPt);
				} else {
					toTransPlanPt.setPartCode(PartCodeEnum.JS.getCode());
					if (toTransPlanMapper.findTransPlan(toTransPlanPt) == null) {
						toTransPlanMapper.insertSelective(toTransPlanPt);
					}
				}
			}
			/** 过户 */
			if(transPlanVO.getEstPartTimeGh()!=null){
			ToTransPlan toTransPlanGh = new ToTransPlan();
			toTransPlanGh.setEstPartTime(transPlanVO.getEstPartTimeGh());
			toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidGh() != null) {
				toTransPlanGh.setPkid(transPlanVO.getPkidGh());
				toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanGh);
			} else {
				toTransPlanGh.setPartCode(PartCodeEnum.GH.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanGh) == null) {
					toTransPlanMapper.insertSelective(toTransPlanGh);
				}
			}
			}
			/** 领证 */
			if(transPlanVO.getEstPartTimeLz()!=null){
			ToTransPlan toTransPlanLz = new ToTransPlan();
			toTransPlanLz.setEstPartTime(transPlanVO.getEstPartTimeLz());
			toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidLz() != null) {
				toTransPlanLz.setPkid(transPlanVO.getPkidLz());
				toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanLz);
			} else {
				toTransPlanLz.setPartCode(PartCodeEnum.LZ.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanLz) == null) {
					toTransPlanMapper.insertSelective(toTransPlanLz);
				}
			}
			}
			/**商贷面签Cs*/
			if (transPlanVO.getEstPartTimeCs()!=null){
			ToTransPlan toTransPlanCs = new ToTransPlan();
			toTransPlanCs.setEstPartTime(transPlanVO.getEstPartTimeCs());
			toTransPlanCs.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidCs() != null) {
				toTransPlanCs.setPkid(transPlanVO.getPkidCs());
				toTransPlanCs.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanCs);
			} else {
				toTransPlanCs.setPartCode(PartCodeEnum.SDMQ.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanCs) == null) {
					toTransPlanMapper.insertSelective(toTransPlanCs);
				}
			}
			}
			/**商贷出评估报告Br*/
			if(transPlanVO.getEstPartTimeBr()!=null){
			ToTransPlan toTransPlanBr = new ToTransPlan();
			toTransPlanBr.setEstPartTime(transPlanVO.getEstPartTimeBr());
			toTransPlanBr.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidBr() != null) {
				toTransPlanBr.setPkid(transPlanVO.getPkidBr());
				toTransPlanBr.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanBr);
			} else {
				toTransPlanBr.setPartCode(PartCodeEnum.SDCPG.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanBr) == null) {
					toTransPlanMapper.insertSelective(toTransPlanBr);
				}
			}
			}
			/**商贷批贷完成Cc*/
			if(transPlanVO.getEstPartTimeCc()!=null){
			ToTransPlan toTransPlanCc = new ToTransPlan();
			toTransPlanCc.setEstPartTime(transPlanVO.getEstPartTimeCc());
			toTransPlanCc.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidCc() != null) {
				toTransPlanCc.setPkid(transPlanVO.getPkidCc());
				toTransPlanCc.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanCc);
			} else {
				toTransPlanCc.setPartCode(PartCodeEnum.SDPD.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanCc) == null) {
					toTransPlanMapper.insertSelective(toTransPlanCc);
				}
			}
			}
			 /**公积金贷款预约申请Pa*/
			 if(transPlanVO.getEstPartTimePa()!=null){
			ToTransPlan toTransPlanPa = new ToTransPlan();
			toTransPlanPa.setEstPartTime(transPlanVO.getEstPartTimePa());
			toTransPlanPa.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidPa() != null) {
				toTransPlanPa.setPkid(transPlanVO.getPkidPa());
				toTransPlanPa.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanPa);
			} else {
				toTransPlanPa.setPartCode(PartCodeEnum.GJJDK.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanPa) == null) {
					toTransPlanMapper.insertSelective(toTransPlanPa);
				}
			}
			 }
			/**公积金面签Pfs*/
			if(transPlanVO.getEstPartTimePfc()!=null){
			ToTransPlan toTransPlanPfs = new ToTransPlan();
			toTransPlanPfs.setEstPartTime(transPlanVO.getEstPartTimePfs());
			toTransPlanPfs.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidPfs() != null) {
				toTransPlanPfs.setPkid(transPlanVO.getPkidPfs());
				toTransPlanPfs.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanPfs);
			} else {
				toTransPlanPfs.setPartCode(PartCodeEnum.GJJMQ.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanPfs) == null) {
					toTransPlanMapper.insertSelective(toTransPlanPfs);
				}
			}
			}
			/**公积金批贷完成Pfc*/
			if(transPlanVO.getEstPartTimePfc()!=null){
			ToTransPlan toTransPlanPfc = new ToTransPlan();
			toTransPlanPfc.setEstPartTime(transPlanVO.getEstPartTimePfc());
			toTransPlanPfc.setCaseCode(transPlanVO.getCaseCode());
			if (transPlanVO.getPkidPfc() != null) {
				toTransPlanPfc.setPkid(transPlanVO.getPkidPfc());
				toTransPlanPfc.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanPfc);
			} else {
				toTransPlanPfc.setPartCode(PartCodeEnum.GJJPD.getCode());
				if (toTransPlanMapper.findTransPlan(toTransPlanPfc) == null) {
					toTransPlanMapper.insertSelective(toTransPlanPfc);
				}
			}
			}
			/** 放款 */
			if(transPlanVO.getEstPartTimeFk()!=null){
			ToTransPlan toTransPlanFk = new ToTransPlan();
			toTransPlanFk.setEstPartTime(transPlanVO.getEstPartTimeFk());
			toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
				if (transPlanVO.getPkidFk() != null) {
					toTransPlanFk.setPkid(transPlanVO.getPkidFk());
					toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper
							.updateByPrimaryKeySelective(toTransPlanFk);
				} else {
					toTransPlanFk.setPartCode(PartCodeEnum.FK.getCode());
					if (toTransPlanMapper.findTransPlan(toTransPlanFk) == null) {
						toTransPlanMapper.insertSelective(toTransPlanFk);
					}
				}

			}

			return true;
		}

		return false;
	}

	@Override
	public int insertTsTransPlanHistorySelective(TsTransPlanHistory record) {
		return tsTransPlanHistoryMapper.insertSelective(record);
	}

	@Override
	public List<TransPlanVO> getTransPlanVOList(TransPlanVO transPlanVO) {
		return tsTransPlanHistoryMapper.getTransPlanVOList(transPlanVO);
	}

	@Override
	public TsTaskPlanSet getAutoTsTaskPlanSetByPartCode(String tsakDfkey) {
		return tsTaskPlanSetMapper.getAutoTsTaskPlanSetByPartCode(tsakDfkey);
	}

	@Override
	public int getTsTaskPlanSetCountByProperty(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.getTsTaskPlanSetCount(tsTaskPlanSet);
	}

	@Override
	public int addTsTaskPlanSet(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.insertSelective(tsTaskPlanSet);
	}

	@Override
	public int updateByPrimaryKeySelective(TsTaskPlanSet tsTaskPlanSet) {
		return tsTaskPlanSetMapper.updateByPrimaryKeySelective(tsTaskPlanSet);
	}

	@Override
	public int deleteByPrimaryKey(Long pkid) {
		return tsTaskPlanSetMapper.deleteByPrimaryKey(pkid);
	}

	/***
	 * @author wbzhouht
	 * @param tsTransPlanHistory
	 * @return
	 */
	@Override
	public TsTransPlanHistory findTransPlanHistoryByCaseCode(TsTransPlanHistory tsTransPlanHistory) {
		List<TsTransPlanHistory>list=tsTransPlanHistoryMapper.findTransPlanHistoryByCaseCode(tsTransPlanHistory);
		if(list!=null&&list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ToTransPlan findTransPlanPKIDBycasecodeAndPartCode(TsTransPlanHistory tsTransPlanHistory) {
		return toTransPlanMapper.findTransPlanPKIDBycasecodeAndPartCode(tsTransPlanHistory);
	}

	@Override
	public int updateTransPlanHistoryByPKID(TsTransPlanHistory tsTransPlanHistory) {
		return tsTransPlanHistoryMapper.updateByPrimaryKeySelective(tsTransPlanHistory);
	}

	@Override
	public Boolean submitTransPlan(HttpServletRequest request, TransPlanVO transPlanVO) throws Exception{
		saveToTransPlan(transPlanVO);

		/* 流程引擎相关 */
		List<RestVariable> variables = new ArrayList<RestVariable>();

		ToCase toCase = toCaseService.findToCaseByCaseCode(transPlanVO
				.getCaseCode());
		return workFlowManager.submitTask(variables, transPlanVO.getTaskId(),
				transPlanVO.getProcessInstanceId(),
				toCase.getLeadingProcessId(), transPlanVO.getCaseCode());
	}

	@Override
	public AjaxResponse<?> submitTranPlanAppver(String[] pkids, String[] newDates, String[] partCodes, Boolean audit, TransPlanVO transPlanVO){
		/**
		 *
		 * 同意变更交易计划，修改交易计划表，并修改交易历史记录表的状态并提交任务，
		 * 不同意则不修改交易计划表，修改交易历史记录表的状态并提交任务
		 */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ToTransPlan tp=new ToTransPlan();
		TsTransPlanHistory ts1=new TsTransPlanHistory();
		if(pkids.length>0&&newDates.length>0&&partCodes.length>0&&transPlanVO!=null) {
			for (int i = 0; i < pkids.length; i++) {
				if (audit) {
					//通过partCode和caseCode查询交易计划表的PKID
					try {
						TsTransPlanHistory ts = new TsTransPlanHistory();
						ts.setCaseCode(transPlanVO.getCaseCode());
						ts.setPartCode(partCodes[i]);
						//根据partCode和caseCode查询交易计划
						ToTransPlan toTransPlan = findTransPlanPKIDBycasecodeAndPartCode(ts);
						if (toTransPlan != null) {
							tp.setPkid(toTransPlan.getPkid());

							tp.setEstPartTime(format.parse(newDates[i]));
							//修改交易计划
							updateByPrimaryKeySelective(tp);
							ts1.setPkid(Long.parseLong(pkids[i]));
							ts1.setAuditResult(ToApproveRecordEnum.AGREE.getCode());
							//修改交易计划历史记录为审核通过状态
							updateTransPlanHistoryByPKID(ts1);
						}else {
							return AjaxResponse.fail("未找到交易计划！");
						}
					} catch (ParseException e) {
						return AjaxResponse.fail("数据转换异常");
					}
				} else {
					ts1.setPkid(Long.parseLong(pkids[i]));
					ts1.setAuditResult(ToApproveRecordEnum.REJECT.getCode());
					//修改交易计划历史记录为审核不通过状态
					updateTransPlanHistoryByPKID(ts1);
				}
			}
			//获取登录人信息
			SessionUser sessionUser = uamSessionService.getSessionUser();
			//启动流程变量
			List<RestVariable> variables = new ArrayList<>();
			//提交任务
			Boolean boo=false;
			try {
				workFlowManager.submitTask(variables, transPlanVO.getTaskId(), transPlanVO.getProcessInstanceId(), sessionUser.getId(), transPlanVO.getCaseCode());
				//在审批记录表中插入数据
				ToApproveRecord toApproveRecord=new ToApproveRecord();
				toApproveRecord.setCaseCode(transPlanVO.getCaseCode());
				toApproveRecord.setProcessInstance(transPlanVO.getProcessInstanceId());
				toApproveRecord.setPartCode(transPlanVO.getPartCode());
				toApproveRecord.setOperator(sessionUser.getId());
				//交易计划变更审批类型
				toApproveRecord.setApproveType("5");
				toApproveRecord.setContent(audit==true?"交易变更审核通过！":"交易变更审核不通过！");
				toApproveRecord.setOperatorTime(new Date());
				loanlostApproveService.saveLoanlostApprove(toApproveRecord);
				//任务完结
				ToWorkFlow flow=new ToWorkFlow();
				flow.setBusinessKey("TransPlanAppver");
				flow.setCaseCode(transPlanVO.getCaseCode());
				flow.setBizCode(transPlanVO.getCaseCode());
				ToWorkFlow tranPlanFlow= toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(flow);
				tranPlanFlow.setStatus(WorkFlowStatus.COMPLETE.getCode());
				toWorkFlowService.updateByPrimaryKeySelective(tranPlanFlow);
			}catch (Exception e){
				e.printStackTrace();
			}
			return AjaxResponse.success("操作成功");
		}else {
			return AjaxResponse.fail("数据传递错误！");
		}
	}

}
