package com.centaline.trans.transplan.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.ToAttachmentEnum;
import com.centaline.trans.task.entity.TsTaskPlanSet;
import com.centaline.trans.task.entity.TsTransPlanHistory;
import com.centaline.trans.task.repository.TaskPlanSetMapper;
import com.centaline.trans.task.repository.TsTransPlanHistoryMapper;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.entity.TtsTransPlanHistoryBatch;
import com.centaline.trans.transplan.repository.ReturnVisitRegistrationMapper;
import com.centaline.trans.transplan.repository.ToTransPlanMapper;
import com.centaline.trans.transplan.repository.TtsTransPlanHistoryBatchMapper;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
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
	private ReturnVisitRegistrationMapper returnVisitRegistrationMapper;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private TaskPlanSetMapper taskPlanSetMapper;
	
	@Override
	public void processRestartOrResetOperate(String caseCode,String changeReason) {
		List<ToTransPlan> ttps = toTransPlanMapper.findTransPlanByCaseCode(caseCode);
		TsTransPlanHistory tsTransPlanHistory = null;
		SessionUser sessionUser = uamSessionService.getSessionUser();
		TtsTransPlanHistoryBatch ttpb = new TtsTransPlanHistoryBatch();
		ttpb.setCaseCode(caseCode);
		if(ttps!=null && ttps.size()>0){
			ttpb.setPartCode(ttps.get(0).getPartCode());
		}
		ttpb.setOperateFlag("1");//流程
		ttpb.setChangeReason(changeReason);
		ttsTransPlanHistoryBatchMapper.insertSelective(ttpb);
		if(ttps!=null && ttps.size()>0){
			//将交易计划表的数据转移到交易计划历史表
			for(ToTransPlan ttp:ttps){
				tsTransPlanHistory=new TsTransPlanHistory();
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
		//删除交易计划表该案件相关信息
		Map map = new HashMap();
		map.put("caseCode", caseCode);
		if(ConstantsUtil.PROCESS_RESTART.equals(changeReason)){
			//流程重启保留首次跟进环节信息并更新首次跟进原预计时间
			map.put("partCode", ToAttachmentEnum.FIRSTFOLLOW.getCode());
			TsTaskPlanSet tps = taskPlanSetMapper.getAutoTsTaskPlanSetByPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
			if (tps != null){
				ToTransPlan plan = new ToTransPlan();
				plan.setCaseCode(caseCode);
				plan.setPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, tps.getPlanDays());
				plan.setEstPartTime(cal.getTime());
				if(toTransPlanMapper.findTransPlan(plan) != null) {
					toTransPlanMapper.updateTransPlanSelective(plan);
				} 
			}
			
		}
		toTransPlanMapper.deleteTransPlansByCaseCode(map);
		
	}

	@Override
	public int insertTtsTransPlanHistoryBatch(TtsTransPlanHistoryBatch ttsTransPlanHistoryBatch) {
		return ttsTransPlanHistoryBatchMapper.insertSelective(ttsTransPlanHistoryBatch);
	}
	
	@Override
	public List<TtsReturnVisitRegistration> queryReturnVisitRegistrations(long batchId) {
		return returnVisitRegistrationMapper.queryReturnVisitRegistrations(batchId);
	}

	@Override
	public int addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration) {
		//更新历史批次表最新回访跟进信息
		TtsTransPlanHistoryBatch record = new TtsTransPlanHistoryBatch();
		record.setPkid(ttsReturnVisitRegistration.getBatchId());
		record.setLastVisitRemark(ttsReturnVisitRegistration.getVisitRemark());
		record.setLastContent(ttsReturnVisitRegistration.getContent());
		ttsTransPlanHistoryBatchMapper.updateTtsTransPlanHistoryBatchMapper(record);
		return returnVisitRegistrationMapper.insertReturnVisitRegistration(ttsReturnVisitRegistration);
	}

	@Override
	public List<TsTransPlanHistoryVO> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO) {
		return tsTransPlanHistoryMapper.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
	}


}
