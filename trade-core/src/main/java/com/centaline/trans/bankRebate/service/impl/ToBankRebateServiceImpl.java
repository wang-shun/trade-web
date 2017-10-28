package com.centaline.trans.bankRebate.service.impl;

import com.aist.common.exception.BusinessException;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.api.service.EvalApiService;
import com.centaline.trans.api.service.FlowApiService;
import com.centaline.trans.api.vo.ApiResultData;
import com.centaline.trans.api.vo.CcaiFlowApiResultData;
import com.centaline.trans.api.vo.FlowFeedBack;
import com.centaline.trans.bankRebate.entity.ToBankRebate;
import com.centaline.trans.bankRebate.entity.ToBankRebateInfo;
import com.centaline.trans.bankRebate.repository.ToBankRebateInfoMapper;
import com.centaline.trans.bankRebate.repository.ToBankRebateMapper;
import com.centaline.trans.bankRebate.service.ToBankRebateService;
import com.centaline.trans.bankRebate.vo.ToBankRebateInfoVO;
import com.centaline.trans.common.enums.BankRebateStatusEnum;
import com.centaline.trans.common.enums.CcaiFlowResultEnum;
import com.centaline.trans.common.enums.CcaiTaskEnum;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.ToApproveRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ToBankRebateServiceImpl implements ToBankRebateService {
	
	@Autowired
	ToBankRebateMapper toBankRebateMapper;
	@Autowired
	private ToBankRebateInfoMapper toBankRebateInfoMapper;
	@Autowired
	private EvalApiService evalApiService;
	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private ToApproveRecordService toApproveRecordService;
	@Autowired
	private FlowApiService flowApiService;
	@Autowired
	private WorkFlowManager workFlowManager;
	//银行返利 审批类型
	private static String BANK_REBATE_APPROVE_TYPE = "22";

	@Override
	public void insertBankRebate(ToBankRebateInfoVO info) {
		toBankRebateMapper.insertSelective(info.getToBankRebate());
		List<ToBankRebateInfo> toBankRebateInfoList = info.getToBankRebateInfoList();
		for (ToBankRebateInfo toBankRebateInfo : toBankRebateInfoList) {
			toBankRebateInfo.setGuaranteeCompId(info.getToBankRebate().getGuaranteeCompId());
			toBankRebateInfoMapper.insertSelective(toBankRebateInfo);
		}
	}

	@Override
	public void deleteByGuaranteeCompId(String[] guaCompIds) {
		for(String compid : guaCompIds){
			toBankRebateInfoMapper.deleteRebateInfoByGuaranteeCompId(compid);
			toBankRebateMapper.deleteByGuaranteeCompId(compid);
		}
	}

	@Override
	public ToBankRebateInfoVO selectRebateByGuaranteeCompId(String guaCompId) {
		ToBankRebateInfoVO vo = new ToBankRebateInfoVO();
		vo.setToBankRebate(toBankRebateMapper.selectRebateByGuaranteeCompId(guaCompId));
		vo.setToBankRebateInfoList(toBankRebateInfoMapper.selectRebateInfoByGuaranteeCompId(guaCompId));
		return vo;
	}

	@Override
	public void deleteTobankRebateInfo(Long pkid) {
		toBankRebateInfoMapper.deleteByPrimaryKey(pkid);
	}

	@Override
	public void updateBankRebate(ToBankRebateInfoVO info) {
		toBankRebateMapper.updateByPrimaryKeySelective(info.getToBankRebate());
		for(ToBankRebateInfo bankRebateInfo : info.getToBankRebateInfoList()){
			if(bankRebateInfo!=null && bankRebateInfo.getPkid()!=null){
				toBankRebateInfoMapper.updateByPrimaryKeySelective(bankRebateInfo);
			}else if(bankRebateInfo!=null && StringUtils.isNotBlank(bankRebateInfo.getCcaiCode())){
				bankRebateInfo.setGuaranteeCompId(info.getToBankRebate().getGuaranteeCompId());
				toBankRebateInfoMapper.insertSelective(bankRebateInfo);
			}
		}
	}

	@Override
	public void exportMatrixLeaderSheet(OutputStream out) {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 声明一个工作薄
		HSSFSheet sheet = workbook.createSheet("Sheet1");  // 生成一个表格
		sheet.setDefaultColumnWidth(20);// 设置表格默认列宽度为30个字节
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short)(15.625*40));

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("案件编号");

		cell = row.createCell(1);
		cell.setCellValue("银行");

		cell = row.createCell(2);
		cell.setCellValue("返利金额");

		cell = row.createCell(3);
		cell.setCellValue("权证返利金额");

		cell = row.createCell(4);
		cell.setCellValue("业务返利金额");

		HSSFCellStyle style = workbook.createCellStyle();
		style.setWrapText(true);

		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String checkBankRebate(ToBankRebateInfoVO info) {
		BigDecimal total = info.getToBankRebate().getRebateTotal();
		StringBuilder msg = new StringBuilder();
		for(ToBankRebateInfo rebate : info.getToBankRebateInfoList()){
			BigDecimal money = rebate.getRebateMoney();//返利总额
			BigDecimal warrant = rebate.getRebateWarrant();//权证返利
			BigDecimal business = rebate.getRebateBusiness();//业务返利
			//验证 返利金额 = 权证返利金额 + 业务返利金额
			if(money.compareTo(warrant.add(business))!=0){
				msg.append("案件编号["+rebate.getCaseCode()+"]的返利金额与权证返利金额 + 业务返利金额不符<br/>");
			}else{
				if(money.multiply(new BigDecimal("0.3")).compareTo(warrant)!=0){
					msg.append("案件编号["+rebate.getCaseCode()+"]的权证返利金额不为返利金额的30%.应该为:"+money.multiply(new BigDecimal("0.3"))+"<br/>");
				}
				if(money.multiply(new BigDecimal("0.7")).compareTo(business)!=0){
					msg.append("案件编号["+rebate.getCaseCode()+"]的业务返利金额不为返利金额的70%.应该为:"+money.multiply(new BigDecimal("0.7"))+"<br/>");
				}
			}
			total = total.subtract(money);
		}
		if(total.compareTo(BigDecimal.ZERO)!=0){
			msg.append("返利申请的总金额，与所有案件合计不符!");
		}
		return msg.length()>0?msg.toString():"success";
	}

	@Override
	public void submitCcai(ToBankRebateInfoVO info) {
		SessionUser user = uamSessionService.getSessionUser();
		ApiResultData result = null;
		if(BankRebateStatusEnum.NOT_SUBMIT.getCode().equals(info.getToBankRebate().getStatus())){
			//CCAI首次同步
			result = evalApiService.evalBankRebateSync(info,user.getId());
		}else if(BankRebateStatusEnum.BACK.getCode().equals(info.getToBankRebate().getStatus())){
			//CCAI 财务驳回修改
			result = evalApiService.evalBankRebateModify(info,user.getId());
		}else{
			result = new CcaiFlowApiResultData();
			result.setSuccess(false);
			result.setMessage("错误的案件状态!");
		}
		if(result.isSuccess()){
			ToBankRebate rebate = info.getToBankRebate();
			rebate.setStatus(BankRebateStatusEnum.SUBMIT.getCode());
			rebate.setSubmitTime(new Date());
			toBankRebateMapper.updateByPrimaryKeySelective(rebate);
			ToApproveRecord record = new ToApproveRecord();
			record.setOperator(user.getId());
			record.setPartCode("BankRebate");
			record.setApproveType(BANK_REBATE_APPROVE_TYPE);
			record.setContent("提交审批");
			record.setOperatorTime(new Date());
			record.setCaseCode(info.getToBankRebate().getGuaranteeCompId());//以批次号作为查询条件
			toApproveRecordService.saveToApproveRecord(record);
		}else{
			throw new BusinessException(result.getMessage());
		}
	}

	@Override
	public ToBankRebate selectById(String id) {
		return toBankRebateMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateToBankRebate(ToBankRebate bankRebate) {
		toBankRebateMapper.updateByPrimaryKeySelective(bankRebate);
	}

	@Override
	public void updateToBankRebateInfo(ToBankRebateInfo info) {
		toBankRebateInfoMapper.updateByPrimaryKeySelective(info);
	}

	@Override
	public ToBankRebateInfo selectToRebateInfoByCaseCodeAndCompId(String caseCode, String compId) {
		return toBankRebateInfoMapper.selectToRebateInfoByCaseCodeAndCompId(caseCode,compId);
	}

	@Override
	public void submitRebateReport(ToBankRebateInfo info, ToApproveRecord record) {
		SessionUser user = uamSessionService.getSessionUser();
		FlowFeedBack feedBack = new FlowFeedBack(user,CcaiFlowResultEnum.SUCCESS,record.getContent());
		//与CCAI交互
		ApiResultData result = flowApiService.feedBackCcai(info.getApplyId(), CcaiTaskEnum.BANK_REBATE_MANAGER,feedBack);
		if(result.isSuccess()){
			//TODO 交互成功 流程处理
			toBankRebateInfoMapper.updateByPrimaryKeySelective(info);
			record.setOperatorTime(new Date());
			record.setOperator(user.getId());
			record.setApproveType(BANK_REBATE_APPROVE_TYPE);
			record.setCaseCode(info.getReportCode());//以返利报告编号为主键存审批记录，便于返利报告查看
			toApproveRecordService.saveToApproveRecord(record);
			workFlowManager.submitTask(new ArrayList<>(),record.getTaskId(),record.getProcessInstance(),record.getOperator(),info.getCaseCode());
		}else{
			throw new BusinessException(result.getMessage());
		}
	}
}
