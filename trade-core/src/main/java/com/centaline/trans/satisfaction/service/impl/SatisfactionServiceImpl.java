package com.centaline.trans.satisfaction.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.trans.common.enums.SatisfactionStatusEnum;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.impl.PropertyUtilsServiceImpl;
import com.centaline.trans.engine.entity.ToWorkFlow;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.satisfaction.entity.ToSatisfaction;
import com.centaline.trans.satisfaction.repository.ToSatisfactionMapper;
import com.centaline.trans.satisfaction.service.SatisfactionService;

@Service
public class SatisfactionServiceImpl implements SatisfactionService {
	
	@Autowired
	private ToSatisfactionMapper toSatisfactionMapper;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	PropertyUtilsServiceImpl propertyUtilsService;
	
	@Autowired
	SatisfactionService satisfactionService;
	
	@Autowired(required = true)
	ToWorkFlowService toWorkFlowService;
	
	@Autowired
	UamBasedataService uamBasedataService;

	@Override
	public int update(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKey(toSatisfaction);
	}
	
	@Override
	public int updateSelective(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.updateByPrimaryKeySelective(toSatisfaction);
	}

	@Override
	public int insert(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.insert(toSatisfaction);
	}
	
	@Override
	public int insertSelective(ToSatisfaction toSatisfaction) {
		return toSatisfactionMapper.insertSelective(toSatisfaction);
	}

	@Override
	public List<ToSatisfaction> queryToSatisfactionList() {
		return toSatisfactionMapper.selectAll();
	}

	@Override
	public ToSatisfaction queryToSatisfactionById(Long id) {
		return toSatisfactionMapper.selectByPrimaryKey(id);
	}

	@Override
	public ToSatisfaction queryToSatisfactionByCaseCode(String caseCode) {
		return toSatisfactionMapper.selectByCaseCode(caseCode);
	}

	@Override
	public void handleAfterSign(String caseCode, String signerId) {
		ToSatisfaction toSatisfaction = new ToSatisfaction();
		toSatisfaction.setCaseCode(caseCode);
		String castsatCode = uamBasedataService.nextSeqVal("CASTSAT_CODE", new SimpleDateFormat("yyyyMM").format(new Date()));
		toSatisfaction.setSignTime(new Date());
		toSatisfaction.setCastsatCode(castsatCode);
		toSatisfaction.setStatus(SatisfactionStatusEnum.DEFAULT.getCode());
		toSatisfaction.setCreateBy(signerId);
		toSatisfaction.setCreateTime(new Date());
		satisfactionService.insertSelective(toSatisfaction);
	}

	@Override
	public void handleAfterGuohuApprove(String caseCode, String guohuerId) {
		ToWorkFlow record = new ToWorkFlow();
		record.setCaseCode(caseCode);
		record.setBusinessKey(propertyUtilsService.getSatisProcessDfKey());
		ToWorkFlow wf = toWorkFlowService.queryActiveToWorkFlowByCaseCodeBusKey(record);
		if(wf != null){
			messageService.sendSatisFinishMsgByIntermi(wf.getInstCode());
			
			ToSatisfaction toSatisfaction = queryToSatisfactionByCaseCode(caseCode);
			toSatisfaction.setGuohuTime(new Date());
			toSatisfaction.setStatus(SatisfactionStatusEnum.GUOHU_SURVEY_ING.getCode());
			toSatisfaction.setUpdateBy(guohuerId);
			toSatisfaction.setUpdateTime(new Date());
			update(toSatisfaction);
		}else{
			//此处暂不做处理，兼容老案件
			//@TODO 
		}
	}

}
