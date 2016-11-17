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
			TsTaskPlanSet tps = tsTaskPlanSetMapper.getTsTaskPlanSetByPartCode(ToAttachmentEnum.FIRSTFOLLOW.getCode());
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
		return ttsReturnVisitRegistrationMapper.queryReturnVisitRegistrations(batchId);
	}

	@Override
	public int addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration) {
		//更新历史批次表最新回访跟进信息
		TtsTransPlanHistoryBatch record = new TtsTransPlanHistoryBatch();
		record.setPkid(ttsReturnVisitRegistration.getBatchId());
		record.setLastVisitRemark(ttsReturnVisitRegistration.getVisitRemark());
		record.setLastContent(ttsReturnVisitRegistration.getContent());
		ttsTransPlanHistoryBatchMapper.updateTtsTransPlanHistoryBatchMapper(record);
		return ttsReturnVisitRegistrationMapper.insertReturnVisitRegistration(ttsReturnVisitRegistration);
	}

	@Override
	public List<TsTransPlanHistoryVO> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO) {
		return tsTransPlanHistoryMapper.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
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
		if(toTransPlanMapper.findTransPlan(toTransPlan) != null) {
			if(toTransPlanMapper.updateTransPlanSelective(toTransPlan) > 0) {
				return true;
			}
		} else {
			if(toTransPlanMapper.insertSelective(toTransPlan) > 0) {
				return true;
			};
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
		List<ToTransPlan> list = toTransPlanMapper.findTransPlanByCaseCode(caseCode);
		TransPlanVO transPlanVO = new TransPlanVO();
		for(ToTransPlan toTransPlan:list) {
			if(toTransPlan.getPartCode().equals("LoanClose")){/*还贷，贷款结清*/
				transPlanVO.setEstPartTimeHd(toTransPlan.getEstPartTime());
				transPlanVO.setPkidHd(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("HouseBookGet")){/*领证*/
				transPlanVO.setEstPartTimeLz(toTransPlan.getEstPartTime());
				transPlanVO.setPkidLz(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("TaxReview")){/*审税*/
				transPlanVO.setEstPartTimeTr(toTransPlan.getEstPartTime());
				transPlanVO.setPkidTr(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("LoanRelease")){/*放款*/
				transPlanVO.setEstPartTimeFk(toTransPlan.getEstPartTime());
				transPlanVO.setPkidFk(toTransPlan.getPkid());
			} else if(toTransPlan.getPartCode().equals("Guohu")){/*过户*/
				transPlanVO.setEstPartTimeGh(toTransPlan.getEstPartTime());
				transPlanVO.setPkidGh(toTransPlan.getPkid());
			}
		}
		return transPlanVO;
	}

	@Override
	public List<ToTransPlanOrToPropertyInfo> getToTransPlanByUserId(String leadingProcessId) {
		List<ToTransPlanOrToPropertyInfo> toTransPlanList = toTransPlanMapper.getToTransPlanByUserId(leadingProcessId);
		return toTransPlanList;
	}

	@Override
	public boolean saveToTransPlan(TransPlanVO transPlanVO) {
		if(transPlanVO.getCaseCode()!=null && transPlanVO.getCaseCode().intern().length()!=0) {
			/**还贷      贷款结清*/
			ToTransPlan toTransPlanHd = new ToTransPlan();
			if(transPlanVO.getEstPartTimeHd() != null) {
				toTransPlanHd.setEstPartTime(transPlanVO.getEstPartTimeHd()); 
				toTransPlanHd.setCaseCode(transPlanVO.getCaseCode());
				if(transPlanVO.getPkidHd() != null) {
					toTransPlanHd.setPkid(transPlanVO.getPkidHd());
					toTransPlanHd.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanHd);
				} else {
					toTransPlanHd.setPartCode("LoanClose");
					if(toTransPlanMapper.findTransPlan(toTransPlanHd) == null) {
						toTransPlanMapper.insertSelective(toTransPlanHd);
					}
				}
			}
			/**审税*/
			ToTransPlan toTransPlanTr = new ToTransPlan();
			toTransPlanTr.setEstPartTime(transPlanVO.getEstPartTimeTr()); 
			toTransPlanTr.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidTr() != null) {
				toTransPlanTr.setPkid(transPlanVO.getPkidTr());
				toTransPlanTr.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanTr);
			} else {
				toTransPlanTr.setPartCode("TaxReview");
				if(toTransPlanMapper.findTransPlan(toTransPlanTr) == null) {
					toTransPlanMapper.insertSelective(toTransPlanTr);
				}
			}
			/**过户*/
			ToTransPlan toTransPlanGh = new ToTransPlan();
			toTransPlanGh.setEstPartTime(transPlanVO.getEstPartTimeGh()); 
			toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidGh() != null) {
				toTransPlanGh.setPkid(transPlanVO.getPkidGh());
				toTransPlanGh.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanGh);
			} else {
				toTransPlanGh.setPartCode("Guohu");
				if(toTransPlanMapper.findTransPlan(toTransPlanGh) == null) {
					toTransPlanMapper.insertSelective(toTransPlanGh);
				}
			}
			/**领证*/
			ToTransPlan toTransPlanLz = new ToTransPlan();
			toTransPlanLz.setEstPartTime(transPlanVO.getEstPartTimeLz()); 
			toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
			if(transPlanVO.getPkidLz() != null) {
				toTransPlanLz.setPkid(transPlanVO.getPkidLz());
				toTransPlanLz.setCaseCode(transPlanVO.getCaseCode());
				toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanLz);
			} else {
				toTransPlanLz.setPartCode("HouseBookGet");
				if(toTransPlanMapper.findTransPlan(toTransPlanLz) == null) {
					toTransPlanMapper.insertSelective(toTransPlanLz);
				}
			}
			
			/**放款*/
			ToTransPlan toTransPlanFk = new ToTransPlan();
			if(transPlanVO.getEstPartTimeFk() != null) {
				toTransPlanFk.setEstPartTime(transPlanVO.getEstPartTimeFk()); 
				toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
				if(transPlanVO.getPkidFk() != null) {
					toTransPlanFk.setPkid(transPlanVO.getPkidFk());
					toTransPlanFk.setCaseCode(transPlanVO.getCaseCode());
					toTransPlanMapper.updateByPrimaryKeySelective(toTransPlanFk);
				} else {
					toTransPlanFk.setPartCode("LoanRelease");
					if(toTransPlanMapper.findTransPlan(toTransPlanFk) == null) {
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


}
