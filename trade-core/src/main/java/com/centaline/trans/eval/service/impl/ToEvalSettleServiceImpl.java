package com.centaline.trans.eval.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.cases.service.ToCaseParticipantService;
import com.centaline.trans.common.enums.EvalWaitAccountEnum;
import com.centaline.trans.common.enums.FeeChangeTypeEnum;
import com.centaline.trans.eval.entity.ToEvalRebate;
import com.centaline.trans.eval.entity.ToEvalReportProcess;
import com.centaline.trans.eval.entity.ToEvalSettle;
import com.centaline.trans.eval.repository.ToEvalSettleMapper;
import com.centaline.trans.eval.service.ToEvalRebateService;
import com.centaline.trans.eval.service.ToEvalReportProcessService;
import com.centaline.trans.eval.service.ToEvalSettleService;
import com.centaline.trans.eval.vo.EvalAccountShowVO;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;

@Service
public class ToEvalSettleServiceImpl implements ToEvalSettleService {
	
	
	@Autowired
	private ToEvalSettleMapper toEvalSettleMapper; 
	
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper; 
	
	@Autowired(required = true)
	ToEvalSettleService toEvalSettleService;
	
	@Autowired(required = true)
	ToEvalRebateService toEvalRebateService;
	
	@Autowired(required = true)
	ToEvalReportProcessService toEvalReportProcessService;
	
	@Autowired(required = true)
	ToCaseParticipantService toCaseParticipantService;
	
	
	@Override
	public int deleteByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ToEvalSettle record) {
		return toEvalSettleMapper.insertSelective(record);
	}

	@Override
	public ToEvalSettle selectByPrimaryKey(Long pkid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ToEvalSettle toEvalSettle) {
		return toEvalSettleMapper.updateByPrimaryKeySelective(toEvalSettle);
	}

	@Override
	public int updateByPrimaryKey(ToEvalSettle record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int updateByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateByCaseCode(record);
	}
	
	@Override
	public int updateSettleTimeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateSettleTimeByCaseCode(record);
	}
	
	@Override
	public ToEvalSettle findToCaseByCaseCode(String caseCode) {
		return toEvalSettleMapper.findToCaseByCaseCode(caseCode);
	}

	@Override
	public int updateSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.updateSettleFeeByCaseCode(record);
	}
	
	@Override
	public int newSettleFeeByCaseCode(ToEvalSettle record) {
		return toEvalSettleMapper.newSettleFeeByCaseCode(record);
	}

	@Override
	public List<ToEvalSettle> findCaseCodesByStauts() {
		// TODO Auto-generated method stub
		return toEvalSettleMapper.findCaseCodesByStauts();
	}

	@Override
	public List<String> waitApproCaseCodes() {
		List<String> recordList = new ArrayList<>();
		List<ToEvalSettle> settleList =  toEvalSettleService.findCaseCodesByStauts();
		for (ToEvalSettle toEvalSettle : settleList) {
			recordList.add(toEvalSettle.getCaseCode());
		}
		return recordList;
	}
	
	//根据评估公司编号查询评估公司
	@Override
	public TsFinOrg findTsFinOrgByfinOrgCode(String finOrgCode) {
		// TODO Auto-generated method stub
		return tsFinOrgMapper.findBankByFinOrg(finOrgCode);
	}

	@Override
	public int insertWaitAccount(String caseCode,String evaCode, String feeChangeReason) {
		ToEvalSettle settle = toEvalSettleService.findToCaseByCaseCode(caseCode);
		if(settle==null) {
			//查询无插入
			ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
			if(feeChangeReason.equals(FeeChangeTypeEnum.FPSD.getCode())) {//发票税点
					ToEvalSettle record = new ToEvalSettle();
					record.setCaseCode(caseCode);
					record.setEvaCode(evaCode);
					record.setFeeChangeReason(FeeChangeTypeEnum.getName(feeChangeReason));
					record.setStatus(EvalWaitAccountEnum.WTJ.getCode());//未提交
					//初始化结算费用
					if(toEvalRebate.getEvaComAmount()!= null) {
						record.setSettleFee(toEvalRebate.getEvaComAmount());
					}
					toEvalSettleMapper.insertSelective(record);
			}else {
					ToEvalSettle record = new ToEvalSettle();
					record.setCaseCode(caseCode);
					record.setEvaCode(evaCode);
					record.setFeeChangeReason(FeeChangeTypeEnum.getName(feeChangeReason));
					record.setStatus(EvalWaitAccountEnum.WXJS.getCode());//无需结算
					//初始化结算费用
					if(toEvalRebate.getEvaComAmount()!= null) {
						record.setSettleFee(toEvalRebate.getEvaComAmount());
					}
					if(feeChangeReason.equals(FeeChangeTypeEnum.WD.getCode())) {
						record.setSettleNotReason("爆单");
					}else {
						record.setSettleNotReason(FeeChangeTypeEnum.getName(feeChangeReason));
					}
					toEvalSettleMapper.insertSelective(record);
			}
		}else {
			//查询有不插入
			return 0;
		}
		
		return 0;
	}

	@Override
	public EvalAccountShowVO selectAssociatedCaseInfo(String caseCode) {
		EvalAccountShowVO evalAccountShowVO  = new EvalAccountShowVO();
		ToEvalReportProcess toEvalReportProcess = toEvalReportProcessService.findToEvalReportProcessByCaseCode(caseCode);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		evalAccountShowVO.setApplyDate(format.format(toEvalReportProcess.getApplyDate()));
		//查询评估公司名称
		TsFinOrg tsFinOrg = toEvalSettleService.findTsFinOrgByfinOrgCode(toEvalReportProcess.getFinOrgId());
		evalAccountShowVO.setEvalCompany(tsFinOrg.getFinOrgName());
		if(toEvalReportProcess.getIssueDate() != null) {
			evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
		}else {
			evalAccountShowVO.setIssueDate(String.valueOf(' '));
		}
		//evalAccountShowVO.setIssueDate(format.format(toEvalReportProcess.getIssueDate()));
		evalAccountShowVO.setCaseCode(toEvalReportProcess.getCaseCode());
		if(toEvalReportProcess.getEvaCode() != null) {
			evalAccountShowVO.setEvaCode(toEvalReportProcess.getEvaCode());
		}else {
			evalAccountShowVO.setEvaCode(String.valueOf(' '));
		}
		
		//评估值
		if(toEvalReportProcess.getEvaPrice()!= null) {
			evalAccountShowVO.setEvaPrice(toEvalReportProcess.getEvaPrice());
		}else {
			evalAccountShowVO.setEvaPrice(new BigDecimal("0.00"));
		}
		
		//贷款权证
		User user = toCaseParticipantService.findCaseOwner(caseCode);
		evalAccountShowVO.setLoaner(user.getRealName());
		//评估费实收金额
		ToEvalRebate toEvalRebate = toEvalRebateService.findToEvalRebateByCaseCode(caseCode);
		if(toEvalRebate!=null) {
			//结算费用
			if(toEvalRebate.getEvaComAmount()!= null) {
				evalAccountShowVO.setEvalComAmount(toEvalRebate.getEvaComAmount());
			}else {
				evalAccountShowVO.setEvalComAmount(new BigDecimal("0.00"));
			}
			evalAccountShowVO.setEvalRealCharges(toEvalRebate.getEvalRealCharges());
		}
		return evalAccountShowVO;
	}
	
	

}
