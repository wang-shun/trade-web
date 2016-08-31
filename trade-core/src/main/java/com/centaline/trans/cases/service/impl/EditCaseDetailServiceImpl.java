package com.centaline.trans.cases.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.entity.ToCaseInfo;
import com.centaline.trans.cases.entity.ToClose;
import com.centaline.trans.cases.repository.ToCaseInfoMapper;
import com.centaline.trans.cases.repository.ToCaseMapper;
import com.centaline.trans.cases.repository.ToCloseMapper;
import com.centaline.trans.cases.service.EditCaseDetailService;
import com.centaline.trans.cases.vo.EditCaseDetailVO;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.loan.entity.ToCloseLoan;
import com.centaline.trans.loan.repository.ToCloseLoanMapper;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.repository.TsFinOrgMapper;
import com.centaline.trans.mortgage.entity.ToEvaReport;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToEvaReportMapper;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.entity.ToPayment;
import com.centaline.trans.task.entity.ToPurchaseLimitSearch;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.entity.ToTax;
import com.centaline.trans.task.repository.ToGetPropertyBookMapper;
import com.centaline.trans.task.repository.ToHouseTransferMapper;
import com.centaline.trans.task.repository.ToPaymentMapper;
import com.centaline.trans.task.repository.ToPurchaseLimitSearchMapper;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.repository.ToTaxMapper;
import com.centaline.trans.utils.DateUtil;

@Service
public class EditCaseDetailServiceImpl implements EditCaseDetailService {
	
	@Autowired
	private ToCaseMapper toCaseMapper;
	@Autowired
	private ToCaseInfoMapper toCaseInfoMapper;
	@Autowired
	private ToCloseMapper toCloseMapper;
	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper;
	@Autowired
	private ToPaymentMapper toPaymentMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToSignMapper toSignMapper;
	@Autowired
	private ToCloseLoanMapper toCloseLoanMapper;
	@Autowired
	private ToHouseTransferMapper toHouseTransferMapper;
	@Autowired
	private ToTaxMapper toTaxMapper;
	@Autowired
	private ToPurchaseLimitSearchMapper toPurchaseLimitSearchMapper;
	@Autowired
	private ToGetPropertyBookMapper toGetPropertyBookMapper;
	@Autowired
	private ToMortgageMapper toMortgageMapper;
	@Autowired
	private ToEvaReportMapper toEvaReportMapper;
	@Autowired
	private TsFinOrgMapper tsFinOrgMapper;

	@Autowired
	private ToMortgageService toMortgageService;
	
	@Override
	public EditCaseDetailVO queryCaseDetai(String caseCode) {
		EditCaseDetailVO editCaseDetailVO = new EditCaseDetailVO();
		/*业务单基本信息*/
		ToCase toCase = toCaseMapper.findToCaseByCaseCode(caseCode);
		ToCaseInfo toCaseInfo = toCaseInfoMapper.findToCaseInfoByCaseCode(caseCode);
		ToClose toClose = toCloseMapper.findToCloseByCaseCode(caseCode);
		editCaseDetailVO.setCaseCode(caseCode);
		editCaseDetailVO.setLoanReq(toCase.getLoanReq());
		if(toCase != null) {
			editCaseDetailVO.setCreateTime(toCase.getCreateTime());
		}
		if(toCaseInfo != null) {
			editCaseDetailVO.setResDate(toCaseInfo.getResDate());
		}
		if(toClose != null) {
			editCaseDetailVO.setApproveTime(toClose.getApproveTime());
		}
		
//		/**读取上下家数据*/
//		List<TgGuestInfo> tgGuestInfoList = tgGuestInfoMapper.findTgGuestInfoByCaseCode(caseCode);
//		for(int i=0; i<tgGuestInfoList.size(); i++) {
//			TgGuestInfo tgGuestInfo = tgGuestInfoList.get(i);
//			if(tgGuestInfo.getTransPosition().equals("30006001")) {
//				editCaseDetailVO.setGuestPkidUp(tgGuestInfo.getPkid());
//				editCaseDetailVO.setGuestNameUp(tgGuestInfo.getGuestName());
//				editCaseDetailVO.setGuestPhoneUp(tgGuestInfo.getGuestPhone());
//			} else if(tgGuestInfo.getTransPosition().equals("30006002")){
//				editCaseDetailVO.setGuestPkidDown(tgGuestInfo.getPkid());
//				editCaseDetailVO.setGuestNameDown(tgGuestInfo.getGuestName());
//				editCaseDetailVO.setGuestPhoneDown(tgGuestInfo.getGuestPhone());
//			}
//		}
		
		/**读取打款数据*/
		List<ToPayment> toPaymentList = toPaymentMapper.findToPaymentByCaseCode(caseCode);
		for(int i=0; i<toPaymentList.size(); i++) {
			ToPayment toPayment = toPaymentList.get(i);
			if(toPayment.getPayName().equals("首付付款")){
				editCaseDetailVO.setInitPayPkid(toPayment.getPkid());
				editCaseDetailVO.setInitAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				editCaseDetailVO.setInitPayTime(toPayment.getPayTime());
				editCaseDetailVO.setInitPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("二期付款")){
				editCaseDetailVO.setSecPayPkid(toPayment.getPkid());
				editCaseDetailVO.setSecAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				editCaseDetailVO.setSecPayTime(toPayment.getPayTime());
				editCaseDetailVO.setSecPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("尾款付款")){
				editCaseDetailVO.setLastPayPkid(toPayment.getPkid());
				editCaseDetailVO.setLastAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				editCaseDetailVO.setLastPayTime(toPayment.getPayTime());
				editCaseDetailVO.setLastPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("装修补偿款")){
				editCaseDetailVO.setCompensatePayPkid(toPayment.getPkid());
				editCaseDetailVO.setCompensateAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				editCaseDetailVO.setCompensatePayTime(toPayment.getPayTime());
				editCaseDetailVO.setCompensatePayType(toPayment.getPayType());
			}
		}
		
		/**读取物业信息*/
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		if(toPropertyInfo!=null) {
			editCaseDetailVO.setPropertyPkid(toPropertyInfo.getPkid());
			editCaseDetailVO.setPropertyAddr(toPropertyInfo.getPropertyAddr());
			editCaseDetailVO.setTotalFloor(toPropertyInfo.getTotalFloor());
			editCaseDetailVO.setLocateFloor(toPropertyInfo.getLocateFloor());
			editCaseDetailVO.setSquare(toPropertyInfo.getSquare());
			if(toPropertyInfo.getFinishYear() != null) {
				editCaseDetailVO.setFinishYear(DateUtil.getFormatDate(toPropertyInfo.getFinishYear(), "yyyy"));
			}
			editCaseDetailVO.setPropertyType(toPropertyInfo.getPropertyType());
		}
		
		/**读取签约信息*/
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		if(toSign!=null) {
			editCaseDetailVO.setSignPkid(toSign.getPkid());
			editCaseDetailVO.setIsConCert(toSign.getIsConCert());
			editCaseDetailVO.setIsHukou(toSign.getIsHukou());
			editCaseDetailVO.setRealConTime(toSign.getRealConTime());
			editCaseDetailVO.setConPrice(toSign.getConPrice()!=null?toSign.getConPrice().divide(new BigDecimal(10000)):null);
			editCaseDetailVO.setRealPrice(toSign.getRealPrice()!=null?toSign.getRealPrice().divide(new BigDecimal(10000)):null);
		}
		
		/*贷款结清*/
		ToCloseLoan toCloseLoan = toCloseLoanMapper.findToCloseLoanByCaseCode(caseCode);
		if(toCloseLoan != null) {
			editCaseDetailVO.setLcid(toCloseLoan.getPkid());
			editCaseDetailVO.setLoanCloseCode(toCloseLoan.getLoanCloseCode());
			editCaseDetailVO.setCloseType(toCloseLoan.getCloseType());
			editCaseDetailVO.setUncloseMoney(toCloseLoan.getUncloseMoney()!=null?toCloseLoan.getUncloseMoney().divide(new BigDecimal(10000)):null);
		}
		
		/*过户*/
		ToHouseTransfer toHouseTransfer = toHouseTransferMapper.findToGuoHuByCaseCode(caseCode);
		if(toHouseTransfer != null) {
			editCaseDetailVO.setGhid(toHouseTransfer.getPkid());
			editCaseDetailVO.setRealHtTime(toHouseTransfer.getRealHtTime());
			editCaseDetailVO.setHouseHodingTax(toHouseTransfer.getHouseHodingTax()!=null?toHouseTransfer.getHouseHodingTax().divide(new BigDecimal(10000)):null);
			editCaseDetailVO.setPersonalIncomeTax(toHouseTransfer.getPersonalIncomeTax()!=null?toHouseTransfer.getPersonalIncomeTax().divide(new BigDecimal(10000)):null);
			editCaseDetailVO.setBusinessTax(toHouseTransfer.getBusinessTax()!=null?toHouseTransfer.getBusinessTax().divide(new BigDecimal(10000)):null);
			editCaseDetailVO.setContractTax(toHouseTransfer.getContractTax()!=null?toHouseTransfer.getContractTax().divide(new BigDecimal(10000)):null);
			editCaseDetailVO.setLandIncrementTax(toHouseTransfer.getLandIncrementTax()!=null?toHouseTransfer.getLandIncrementTax().divide(new BigDecimal(10000)):null);
		}
		
		/*审税*/
		ToTax toTax = toTaxMapper.findToTaxByCaseCode(caseCode);
		if(toTax != null) {
			editCaseDetailVO.setTaxid(toTax.getPkid());
			editCaseDetailVO.setTaxTime(toTax.getTaxTime());
			editCaseDetailVO.setIsUniqueHome(toTax.getIsUniqueHome());
			editCaseDetailVO.setHoldYear(toTax.getHoldYear());
		}
		
		/*查限购*/
		ToPurchaseLimitSearch tpls = toPurchaseLimitSearchMapper.findToPlsByCaseCode(caseCode);
		if(tpls != null) {
			editCaseDetailVO.setRealPlsTime(tpls.getRealPlsTime());
		}
		
		/*领证*/
		ToGetPropertyBook toGetPropertyBook = toGetPropertyBookMapper.findGetPropertyBookByCaseCode(caseCode);
		if(toGetPropertyBook != null) {
			editCaseDetailVO.setRealPropertyGetTime(toGetPropertyBook.getRealPropertyGetTime());
		}
		
		/*贷款信息*/
		ToMortgage toMortgage = toMortgageService.findToMortgageByCaseCode2(caseCode);
		if(toMortgage != null) {
			editCaseDetailVO.setMpkid(toMortgage.getPkid());
			
			editCaseDetailVO.setSignDate(toMortgage.getSignDate());
			editCaseDetailVO.setApprDate(toMortgage.getApprDate());
			editCaseDetailVO.setMortType(toMortgage.getMortType());
			editCaseDetailVO.setMortTotalAmount(toMortgage.getMortTotalAmount());
			
			editCaseDetailVO.setComAmount(toMortgage.getComAmount());
			editCaseDetailVO.setComYear(toMortgage.getComYear());
			editCaseDetailVO.setPrfAmount(toMortgage.getPrfAmount());
			editCaseDetailVO.setPrfYear(toMortgage.getPrfYear());
			
			editCaseDetailVO.setComDiscount(toMortgage.getComDiscount());
			editCaseDetailVO.setIsDelegateYucui(toMortgage.getIsDelegateYucui());
			editCaseDetailVO.setLendWay(toMortgage.getLendWay());
			editCaseDetailVO.setTazhengArrDate(toMortgage.getTazhengArrDate());
			
			editCaseDetailVO.setCustCode(toMortgage.getCustCode());
			editCaseDetailVO.setPrfApplyDate(toMortgage.getPrfApplyDate());
			editCaseDetailVO.setLendDate(toMortgage.getLendDate());
			editCaseDetailVO.setLoanerName(toMortgage.getLoanerName());
			
			editCaseDetailVO.setCustCompany(toMortgage.getCustCompany());
			editCaseDetailVO.setHouseNum(toMortgage.getHouseNum());
			editCaseDetailVO.setLoanerPhone(toMortgage.getLoanerPhone());
			
			editCaseDetailVO.setLastLoanBank(toMortgage.getLastLoanBank());
			editCaseDetailVO.setFinOrgCode(toMortgage.getFinOrgCode());
			editCaseDetailVO.setCustCode(toMortgage.getCustCode());
			/*主贷人*/
			if(!StringUtil.isBlank(toMortgage.getCustCode())) {
//				editCaseDetailVO.setCustName(editCaseDetailVO.getGuestNameDown());
			}
		}
		
		/*评估公司*/
		ToEvaReport evaReport = toEvaReportMapper.findFinalComByCaseCode(caseCode);
		if(evaReport!=null&& !StringUtils.isEmpty(evaReport.getFinOrgCode())){
			TsFinOrg reportCom = tsFinOrgMapper.findBankByFinOrg(evaReport.getFinOrgCode());
			editCaseDetailVO.setFinOrgName(reportCom.getFinOrgName());
		}
		
		return editCaseDetailVO;
	}

	@Override
	public void saveCaseDetai(EditCaseDetailVO editCaseDetailVO) {
		/*执行上下家删除操作*/
		if(editCaseDetailVO.getGuestPkid() != null && editCaseDetailVO.getGuestPkid().size() > 0) {
			for(Long pkid:editCaseDetailVO.getGuestPkid()) {
				tgGuestInfoMapper.deleteByPrimaryKey(pkid);
			}
		}
		
		/**上家*/
		TgGuestInfo tgGuestInfoUP = new TgGuestInfo();
		tgGuestInfoUP.setCaseCode(editCaseDetailVO.getCaseCode());
		tgGuestInfoUP.setTransPosition("30006001");
		for(int i=0; i<editCaseDetailVO.getPkidUp().size(); i++) {
			tgGuestInfoUP.setPkid(null);
			tgGuestInfoUP.setGuestName(editCaseDetailVO.getGuestNameUp().get(i));
			tgGuestInfoUP.setGuestPhone(editCaseDetailVO.getGuestPhoneUp().get(i));
			if(editCaseDetailVO.getPkidUp().get(i)!=null && editCaseDetailVO.getPkidUp().get(i)!=0) {
				tgGuestInfoUP.setPkid(editCaseDetailVO.getPkidUp().get(i));
				tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoUP);
			} else {
				if(tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoUP).size() == 0) { 
					tgGuestInfoMapper.insertSelective(tgGuestInfoUP);
				}
			}
		}
		
		/**下家*/
		TgGuestInfo tgGuestInfoDown = new TgGuestInfo();
		tgGuestInfoDown.setTransPosition("30006002");
		tgGuestInfoDown.setCaseCode(editCaseDetailVO.getCaseCode());
		for(int i=0; i<editCaseDetailVO.getPkidDown().size(); i++) {
			tgGuestInfoDown.setPkid(null);
			tgGuestInfoDown.setGuestName(editCaseDetailVO.getGuestNameDown().get(i));
			tgGuestInfoDown.setGuestPhone(editCaseDetailVO.getGuestPhoneDown().get(i));
			if(editCaseDetailVO.getPkidDown().get(i)!=null&&editCaseDetailVO.getPkidDown().get(i)!=0) {
				tgGuestInfoDown.setPkid(editCaseDetailVO.getPkidDown().get(i));
				tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoDown);
			} else {
				if(tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoDown).size() == 0) { 
					tgGuestInfoMapper.insertSelective(tgGuestInfoDown);
				}
			}
		}
		
		/**首付款*/
		ToPayment toPaymentInit = new ToPayment();
		toPaymentInit.setAmount(editCaseDetailVO.getInitAmount()!=null?editCaseDetailVO.getInitAmount().multiply(new BigDecimal(10000)):null);
		toPaymentInit.setPayTime(editCaseDetailVO.getInitPayTime());
		toPaymentInit.setPayType(editCaseDetailVO.getInitPayType());
		if(editCaseDetailVO.getInitPayPkid()!=null) {
			toPaymentInit.setPkid(editCaseDetailVO.getInitPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentInit);
		} else {
			toPaymentInit.setPayName("首付付款");
			toPaymentInit.setCaseCode(editCaseDetailVO.getCaseCode());
			if(toPaymentMapper.findToPaymentByPayment(toPaymentInit) == null){
				toPaymentMapper.insertSelective(toPaymentInit);
			}
		}
		
		/**二次付款*/
		ToPayment toPaymentSec = new ToPayment();
		toPaymentSec.setAmount(editCaseDetailVO.getSecAmount()!=null?editCaseDetailVO.getSecAmount().multiply(new BigDecimal(10000)):null);
		toPaymentSec.setPayTime(editCaseDetailVO.getSecPayTime());
		toPaymentSec.setPayType(editCaseDetailVO.getSecPayType());
		if(editCaseDetailVO.getSecPayPkid()!=null) {
			toPaymentSec.setPkid(editCaseDetailVO.getSecPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentSec);
		} else {
			toPaymentSec.setPayName("二期付款");
			toPaymentSec.setCaseCode(editCaseDetailVO.getCaseCode());
			if(toPaymentMapper.findToPaymentByPayment(toPaymentSec) == null) {
				toPaymentMapper.insertSelective(toPaymentSec);
			}
		}
		
		/**尾款付款*/
		ToPayment toPaymentLast = new ToPayment();
		toPaymentLast.setAmount(editCaseDetailVO.getLastAmount()!=null?editCaseDetailVO.getLastAmount().multiply(new BigDecimal(10000)):null);
		toPaymentLast.setPayTime(editCaseDetailVO.getLastPayTime());
		toPaymentLast.setPayType(editCaseDetailVO.getLastPayType());
		if(editCaseDetailVO.getLastPayPkid()!=null) {
			toPaymentLast.setPkid(editCaseDetailVO.getLastPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentLast);
		} else {
			toPaymentLast.setPayName("尾款付款");
			toPaymentLast.setCaseCode(editCaseDetailVO.getCaseCode());
			if(toPaymentMapper.findToPaymentByPayment(toPaymentLast) == null) {
				toPaymentMapper.insertSelective(toPaymentLast);
			}
		}
		
		/**装修补偿款*/
		ToPayment toPaymentCompensate = new ToPayment();
		toPaymentCompensate.setAmount(editCaseDetailVO.getCompensateAmount()!=null?editCaseDetailVO.getCompensateAmount().multiply(new BigDecimal(10000)):null);
		toPaymentCompensate.setPayTime(editCaseDetailVO.getCompensatePayTime());
		toPaymentCompensate.setPayType(editCaseDetailVO.getCompensatePayType());
		if(editCaseDetailVO.getCompensatePayPkid()!=null) {
			toPaymentCompensate.setPkid(editCaseDetailVO.getCompensatePayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentCompensate);
		} else {
			toPaymentCompensate.setPayName("装修补偿款");
			toPaymentCompensate.setCaseCode(editCaseDetailVO.getCaseCode());
			if(toPaymentMapper.findToPaymentByPayment(toPaymentCompensate) == null) {
				toPaymentMapper.insertSelective(toPaymentCompensate);
			}
		}
		
		/**物业信息*/
		ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
		toPropertyInfo.setPropertyAddr(editCaseDetailVO.getPropertyAddr());
		toPropertyInfo.setTotalFloor(editCaseDetailVO.getTotalFloor());
		toPropertyInfo.setLocateFloor(editCaseDetailVO.getLocateFloor());
		toPropertyInfo.setSquare(editCaseDetailVO.getSquare());
		toPropertyInfo.setFinishYear(DateUtil.strToFullDate(editCaseDetailVO.getFinishYear() + "-01-01"));
		toPropertyInfo.setPropertyType(editCaseDetailVO.getPropertyType());
		if(editCaseDetailVO.getPropertyPkid()!=null) {
			toPropertyInfo.setPkid(editCaseDetailVO.getPropertyPkid());
			toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
		} else {
			toPropertyInfo.setCaseCode(editCaseDetailVO.getCaseCode());
			toPropertyInfoMapper.insertSelective(toPropertyInfo);
		}
		
		/**签约信息*/
		ToSign toSign = new ToSign();
		toSign.setIsConCert(editCaseDetailVO.getIsConCert());
		toSign.setIsHukou(editCaseDetailVO.getIsHukou());
		toSign.setConPrice(editCaseDetailVO.getConPrice()!=null?editCaseDetailVO.getConPrice().multiply(new BigDecimal(10000)):null);
		toSign.setRealPrice(editCaseDetailVO.getRealPrice()!=null?editCaseDetailVO.getRealPrice().multiply(new BigDecimal(10000)):null);
//		toSign.setRealConTime(editCaseDetailVO.getRealConTime());
		if(editCaseDetailVO.getSignPkid()!=null) {
			toSign.setPkid(editCaseDetailVO.getSignPkid());
			toSignMapper.updateByPrimaryKeySelective(toSign);
		} else {
			toSign.setCaseCode(editCaseDetailVO.getCaseCode());
			toSignMapper.insertSelective(toSign);
		}
		
		/*贷款结清*/
		ToCloseLoan toCloseLoan = new ToCloseLoan();
//		toCloseLoan.setLoanCloseCode(editCaseDetailVO.getLoanCloseCode());
		toCloseLoan.setCloseType(editCaseDetailVO.getCloseType());
		toCloseLoan.setUncloseMoney(editCaseDetailVO.getUncloseMoney()!=null?editCaseDetailVO.getUncloseMoney().multiply(new BigDecimal(10000)):null);
		if(editCaseDetailVO.getLcid()!=null) {
			toCloseLoan.setPkid(editCaseDetailVO.getLcid());
			toCloseLoanMapper.updateByPrimaryKeySelective(toCloseLoan);
		}
		
		/*过户*/
		ToHouseTransfer toHouseTransfer = new ToHouseTransfer();
//		toHouseTransfer.setRealHtTime(editCaseDetailVO.getRealHtTime());
		toHouseTransfer.setHouseHodingTax(editCaseDetailVO.getHouseHodingTax()!=null?editCaseDetailVO.getHouseHodingTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setPersonalIncomeTax(editCaseDetailVO.getPersonalIncomeTax()!=null?editCaseDetailVO.getPersonalIncomeTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setBusinessTax(editCaseDetailVO.getBusinessTax()!=null?editCaseDetailVO.getBusinessTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setContractTax(editCaseDetailVO.getContractTax()!=null?editCaseDetailVO.getContractTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setLandIncrementTax(editCaseDetailVO.getLandIncrementTax()!=null?editCaseDetailVO.getLandIncrementTax().multiply(new BigDecimal(10000)):null);
		if(editCaseDetailVO.getGhid()!=null) {
			toHouseTransfer.setPkid(editCaseDetailVO.getGhid());
			toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer);
		} else {
			toHouseTransfer.setCaseCode(editCaseDetailVO.getCaseCode());
			toHouseTransferMapper.insertSelective(toHouseTransfer);
		}
		
		/*审税*/
		ToTax toTax = new ToTax();
//		toTax.setTaxTime(editCaseDetailVO.getTaxTime());
		toTax.setIsUniqueHome(editCaseDetailVO.getIsUniqueHome());
		toTax.setHoldYear(editCaseDetailVO.getHoldYear());
		if(editCaseDetailVO.getTaxid()!=null) {
			toTax.setPkid(editCaseDetailVO.getTaxid());
			toTaxMapper.updateByPrimaryKeySelective(toTax);
		} else {
			toTax.setCaseCode(editCaseDetailVO.getCaseCode());
			toTaxMapper.insertSelective(toTax);
		}
		
//		/*查限购*/
//		ToPurchaseLimitSearch tpls = new ToPurchaseLimitSearch();
//		tpls.setRealPlsTime(editCaseDetailVO.getRealPlsTime());
//		
//		/*领证*/
//		ToGetPropertyBook toGetPropertyBook = new ToGetPropertyBook();
//		toGetPropertyBook.setRealPropertyGetTime(editCaseDetailVO.getRealPropertyGetTime());
		
		/*贷款信息 mortgage*/
		ToMortgage toMortgage = new ToMortgage();
//		toMortgage.setSignDate(editCaseDetailVO.getSignDate());
//		toMortgage.setApprDate(editCaseDetailVO.getApprDate());
		toMortgage.setMortType(editCaseDetailVO.getMortType());
		toMortgage.setMortTotalAmount(editCaseDetailVO.getMortTotalAmount()!=null?editCaseDetailVO.getMortTotalAmount().multiply(new BigDecimal(10000)):null);
		
		toMortgage.setComAmount(editCaseDetailVO.getComAmount()!=null?editCaseDetailVO.getComAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setComYear(editCaseDetailVO.getComYear());
		toMortgage.setPrfAmount(editCaseDetailVO.getPrfAmount()!=null?editCaseDetailVO.getPrfAmount().multiply(new BigDecimal(10000)):null);
		toMortgage.setPrfYear(editCaseDetailVO.getPrfYear());
		
		toMortgage.setComDiscount(editCaseDetailVO.getComDiscount());
		toMortgage.setIsDelegateYucui(editCaseDetailVO.getIsDelegateYucui());
		toMortgage.setLendWay(editCaseDetailVO.getLendWay());
//		toMortgage.setTazhengArrDate(editCaseDetailVO.getTazhengArrDate());
		
		toMortgage.setCustCode(editCaseDetailVO.getCustCode());
//		toMortgage.setPrfApplyDate(editCaseDetailVO.getPrfApplyDate());
		toMortgage.setLendDate(editCaseDetailVO.getLendDate());
		toMortgage.setLoanerName(editCaseDetailVO.getLoanerName());
		
		toMortgage.setCustCompany(editCaseDetailVO.getCustCompany());
		toMortgage.setHouseNum(editCaseDetailVO.getHouseNum());
		toMortgage.setLoanerPhone(editCaseDetailVO.getLoanerPhone());
		
		toMortgage.setLastLoanBank(editCaseDetailVO.getLastLoanBank());
		toMortgage.setFinOrgCode(editCaseDetailVO.getFinOrgCode());
		
		if(editCaseDetailVO.getMpkid()!=null) {
			toMortgage.setPkid(editCaseDetailVO.getMpkid());
			toMortgageMapper.update(toMortgage);
		} else {
			/*结案不应该对这个表作插入操作 
			 * 
			 * toMortgage.setCaseCode(editCaseDetailVO.getCaseCode());
			toMortgageMapper.insertSelective(toMortgage);*/
		}
		
	}

}
