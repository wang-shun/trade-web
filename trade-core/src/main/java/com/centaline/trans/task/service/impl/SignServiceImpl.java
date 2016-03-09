package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.common.repository.ToPropertyInfoMapper;
import com.centaline.trans.task.entity.ToFirstFollow;
import com.centaline.trans.task.entity.ToHouseTransfer;
import com.centaline.trans.task.entity.ToPayment;
import com.centaline.trans.task.entity.ToSign;
import com.centaline.trans.task.repository.ToFirstFollowMapper;
import com.centaline.trans.task.repository.ToHouseTransferMapper;
import com.centaline.trans.task.repository.ToPaymentMapper;
import com.centaline.trans.task.repository.ToSignMapper;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.vo.TransSignVO;
import com.centaline.trans.utils.DateUtil;


@Service
public class SignServiceImpl implements SignService {

	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper;
	@Autowired
	private ToPaymentMapper toPaymentMapper;
	@Autowired
	private ToPropertyInfoMapper toPropertyInfoMapper;
	@Autowired
	private ToSignMapper toSignMapper;
	@Autowired
	private ToHouseTransferMapper toHouseTransferMapper;
	
	@Autowired
	private ToFirstFollowMapper tofirstFollowMapper;
	
	@Override
	public Boolean insertGuestInfo(TransSignVO transSignVO) {
		if(transSignVO.getCaseCode() == null || transSignVO.getCaseCode().isEmpty()) {
			return false;
		}
		if(transSignVO.getGuestPkid() != null && transSignVO.getGuestPkid().size() > 0) {
			for(Long pkid:transSignVO.getGuestPkid()) {
				tgGuestInfoMapper.deleteByPrimaryKey(pkid);
			}
		}
		/**上家*/
		TgGuestInfo tgGuestInfoUP = new TgGuestInfo();
		tgGuestInfoUP.setCaseCode(transSignVO.getCaseCode());
		tgGuestInfoUP.setTransPosition("30006001");
		for(int i=0; i<transSignVO.getPkidUp().size(); i++) {
			tgGuestInfoUP.setPkid(null);
			tgGuestInfoUP.setGuestName(transSignVO.getGuestNameUp().get(i));
			tgGuestInfoUP.setGuestPhone(transSignVO.getGuestPhoneUp().get(i));
			if(transSignVO.getPkidUp().get(i) != null && transSignVO.getPkidUp().get(i) != 0) {
				tgGuestInfoUP.setPkid(transSignVO.getPkidUp().get(i));
				tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoUP);
			} else {
				if(tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoUP).size() == 0) { 
					tgGuestInfoMapper.insertSelective(tgGuestInfoUP);
				}
			}
		}
		
		/**下家*/
		TgGuestInfo tgGuestInfoDown = new TgGuestInfo();
		tgGuestInfoDown.setCaseCode(transSignVO.getCaseCode());
		tgGuestInfoDown.setTransPosition("30006002");
		for(int i=0; i<transSignVO.getPkidDown().size(); i++) {
			tgGuestInfoDown.setPkid(null);
			tgGuestInfoDown.setGuestName(transSignVO.getGuestNameDown().get(i));
			tgGuestInfoDown.setGuestPhone(transSignVO.getGuestPhoneDown().get(i));
			if(transSignVO.getPkidDown().get(i) != null && transSignVO.getPkidDown().get(i) != 0) {
				tgGuestInfoDown.setPkid(transSignVO.getPkidDown().get(i));
				tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfoDown);
			} else {
				if(tgGuestInfoMapper.findTgGuestInfosByCaseCode(tgGuestInfoDown).size() == 0) {
					tgGuestInfoMapper.insertSelective(tgGuestInfoDown);
				}
			}
		}
		
		/**首付款*/
		ToPayment toPaymentInit = new ToPayment();
		toPaymentInit.setAmount(transSignVO.getInitAmount()!=null?transSignVO.getInitAmount().multiply(new BigDecimal(10000)):null);
		toPaymentInit.setCaseCode(transSignVO.getCaseCode());
		toPaymentInit.setPayName(transSignVO.getInitPayName());
		toPaymentInit.setPayTime(transSignVO.getInitPayTime());
		toPaymentInit.setPayType(transSignVO.getInitPayType());
		if(transSignVO.getInitPayPkid() != null) {
			toPaymentInit.setPkid(transSignVO.getInitPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentInit);
		} else {
			if(toPaymentMapper.findToPaymentByPayment(toPaymentInit) == null) {
				toPaymentMapper.insertSelective(toPaymentInit);
			}
		}
		/**二次付款*/
		ToPayment toPaymentSec = new ToPayment();
		toPaymentSec.setAmount(transSignVO.getSecAmount()!=null?transSignVO.getSecAmount().multiply(new BigDecimal(10000)):null);
		toPaymentSec.setCaseCode(transSignVO.getCaseCode());
		toPaymentSec.setPayName(transSignVO.getSecPayName());
		toPaymentSec.setPayTime(transSignVO.getSecPayTime());
		toPaymentSec.setPayType(transSignVO.getSecPayType());
		if(transSignVO.getSecPayPkid() != null) {
			toPaymentSec.setPkid(transSignVO.getSecPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentSec);
		} else {
			if(toPaymentMapper.findToPaymentByPayment(toPaymentSec) == null) {
				toPaymentMapper.insertSelective(toPaymentSec);
			}
		}
		/**尾款付款*/
		ToPayment toPaymentLast = new ToPayment();
		toPaymentLast.setAmount(transSignVO.getLastAmount()!=null?transSignVO.getLastAmount().multiply(new BigDecimal(10000)):null);
		toPaymentLast.setCaseCode(transSignVO.getCaseCode());
		toPaymentLast.setPayName(transSignVO.getLastPayName());
		toPaymentLast.setPayTime(transSignVO.getLastPayTime());
		toPaymentLast.setPayType(transSignVO.getLastPayType());
		if(transSignVO.getLastPayPkid() != null) {
			toPaymentLast.setPkid(transSignVO.getLastPayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentLast);
		} else {
			if(toPaymentMapper.findToPaymentByPayment(toPaymentLast) == null) {
				toPaymentMapper.insertSelective(toPaymentLast);
			}
		}
		/**装修补偿款*/
		ToPayment toPaymentCompensate = new ToPayment();
		toPaymentCompensate.setAmount(transSignVO.getCompensateAmount()!=null?transSignVO.getCompensateAmount().multiply(new BigDecimal(10000)):null);
		toPaymentCompensate.setCaseCode(transSignVO.getCaseCode());
		toPaymentCompensate.setPayName(transSignVO.getCompensatePayName());
		toPaymentCompensate.setPayTime(transSignVO.getCompensatePayTime());
		toPaymentCompensate.setPayType(transSignVO.getCompensatePayType());
		if(transSignVO.getCompensatePayPkid() != null) {
			toPaymentCompensate.setPkid(transSignVO.getCompensatePayPkid());
			toPaymentMapper.updateByPrimaryKeySelective(toPaymentCompensate);
		} else {
			if(toPaymentMapper.findToPaymentByPayment(toPaymentCompensate) == null) {
				toPaymentMapper.insertSelective(toPaymentCompensate);
			}
		}
		
		/**物业信息*/
		ToPropertyInfo toPropertyInfo = new ToPropertyInfo();
		toPropertyInfo.setCaseCode(transSignVO.getCaseCode());
		toPropertyInfo.setPropertyAddr(transSignVO.getPropertyAddr());
		toPropertyInfo.setTotalFloor(transSignVO.getTotalFloor());
		toPropertyInfo.setLocateFloor(transSignVO.getLocateFloor());
		toPropertyInfo.setSquare(transSignVO.getSquare());
		toPropertyInfo.setFinishYear(DateUtil.strToFullDate(transSignVO.getFinishYear()+"-01-01"));
		toPropertyInfo.setPropertyType(transSignVO.getPropertyType());
		if(transSignVO.getPropertyPkid() != null) {
			toPropertyInfo.setPkid(transSignVO.getPropertyPkid());
			toPropertyInfoMapper.updateByPrimaryKeySelective(toPropertyInfo);
		} else {
			if(toPropertyInfoMapper.findToPropertyInfoByCaseCode(transSignVO.getCaseCode()) == null) {
				toPropertyInfoMapper.insertSelective(toPropertyInfo);
			}
		}
		
		/**签约信息*/
		ToSign toSign = new ToSign();
		toSign.setCaseCode(transSignVO.getCaseCode());
		toSign.setPartCode(transSignVO.getPartCode());
		toSign.setIsConCert(transSignVO.getIsConCert());
		toSign.setIsHukou(transSignVO.getIsHukou());
		toSign.setComment(transSignVO.getComment());
		toSign.setRealConTime(transSignVO.getRealConTime());
		toSign.setConPrice(transSignVO.getConPrice().multiply(new BigDecimal(10000)));  // 合同价
		toSign.setRealPrice(transSignVO.getRealPrice().multiply(new BigDecimal(10000)));  // 成交价
		
		if(transSignVO.getSignPkid() != null) {
			toSign.setPkid(transSignVO.getSignPkid());
			toSignMapper.updateByPrimaryKeySelective(toSign);
		} else {
			if(toSignMapper.findToSignByCaseCode(transSignVO.getCaseCode()) == null) {
				toSignMapper.insertSelective(toSign);
			}
		}
		
		/*预估税费*/
		ToHouseTransfer toHouseTransfer = new ToHouseTransfer();
		toHouseTransfer.setCaseCode(transSignVO.getCaseCode());
		toHouseTransfer.setBusinessTax(transSignVO.getBusinessTax()!=null?transSignVO.getBusinessTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setContractTax(transSignVO.getContractTax()!=null?transSignVO.getContractTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setHouseHodingTax(transSignVO.getHouseHodingTax()!=null?transSignVO.getHouseHodingTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setLandIncrementTax(transSignVO.getLandIncrementTax()!=null?transSignVO.getLandIncrementTax().multiply(new BigDecimal(10000)):null);
		toHouseTransfer.setPersonalIncomeTax(transSignVO.getPersonalIncomeTax()!=null?transSignVO.getPersonalIncomeTax().multiply(new BigDecimal(10000)):null);
		if(transSignVO.getHousePkid() != null) {
			toHouseTransfer.setPkid(transSignVO.getHousePkid());
			toHouseTransferMapper.updateByPrimaryKeySelective(toHouseTransfer);
		} else {
			toHouseTransfer.setPartCode("Guohu");
			if(toHouseTransferMapper.findToGuoHuByCaseCode(transSignVO.getCaseCode()) == null) {
				toHouseTransferMapper.insertSelective(toHouseTransfer);
			}
		}
		
		
		// 功能：根据 casecode 到T_TO_FIRST_FOLLOW表中去查询，如果存在则做update，否则做insert, 作者：zhangxb16 时间 2016-1-27
		int isExist=tofirstFollowMapper.isExistCasecode(transSignVO.getCaseCode());
		ToFirstFollow ff=new ToFirstFollow();
		if(isExist>0){
			if("true".equals(transSignVO.getIsLoanClose())){ // 有抵押
				ff.setIsLoanClose("1");
			}else{
				ff.setIsLoanClose("0");
			}
			
			if("true".equals(transSignVO.getIsPerchaseReserachNeed())){  // 是否需要查限购
				ff.setIsPerchaseReserachNeed("1");
			}else{
				ff.setIsPerchaseReserachNeed("0");
			}
			ff.setCaseCode(transSignVO.getCaseCode());
			tofirstFollowMapper.updateByCaseCode(ff);
		}else{
			if(ff.getIsLoanClose().equals(true)){ // 有抵押
				ff.setIsLoanClose("1");
			}else{
				ff.setIsLoanClose("0");
			}
			
			if(ff.getIsPerchaseReserachNeed().equals(true)){  // 是否需要查限购
				ff.setIsPerchaseReserachNeed("1");
			}else{
				ff.setIsPerchaseReserachNeed("0");
			}
			ff.setCaseCode(transSignVO.getCaseCode());
			tofirstFollowMapper.insertSelective(ff);
		}
		
		return true;
	}
	
	@Override
	public TransSignVO qureyGuestInfo(String caseCode) {
		TransSignVO transSignVO = new TransSignVO();
		
		/**读取打款数据*/
		List<ToPayment> toPaymentList = toPaymentMapper.findToPaymentByCaseCode(caseCode);
		for(int i=0; i<toPaymentList.size(); i++) {
			ToPayment toPayment = toPaymentList.get(i);
			if(toPayment.getPayName().equals("首付付款")){
				transSignVO.setInitPayPkid(toPayment.getPkid());
				transSignVO.setInitAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				transSignVO.setInitPayName(toPayment.getPayName());
				transSignVO.setInitPayTime(toPayment.getPayTime());
				transSignVO.setInitPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("二期付款")){
				transSignVO.setSecPayPkid(toPayment.getPkid());
				transSignVO.setSecAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				transSignVO.setSecPayName(toPayment.getPayName());
				transSignVO.setSecPayTime(toPayment.getPayTime());
				transSignVO.setSecPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("尾款付款")){
				transSignVO.setLastPayPkid(toPayment.getPkid());
				transSignVO.setLastAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				transSignVO.setLastPayName(toPayment.getPayName());
				transSignVO.setLastPayTime(toPayment.getPayTime());
				transSignVO.setLastPayType(toPayment.getPayType());
			} else if(toPayment.getPayName().equals("装修补偿款")){
				transSignVO.setCompensatePayPkid(toPayment.getPkid());
				transSignVO.setCompensateAmount(toPayment.getAmount()!=null?toPayment.getAmount().divide(new BigDecimal(10000)):null);
				transSignVO.setCompensatePayName(toPayment.getPayName());
				transSignVO.setCompensatePayTime(toPayment.getPayTime());
				transSignVO.setCompensatePayType(toPayment.getPayType());
			}
		}
		
		/**读取物业信息*/
		ToPropertyInfo toPropertyInfo = toPropertyInfoMapper.findToPropertyInfoByCaseCode(caseCode);
		if(toPropertyInfo!=null) {
			transSignVO.setPropertyPkid(toPropertyInfo.getPkid());
			transSignVO.setPropertyAddr(toPropertyInfo.getPropertyAddr());
			transSignVO.setTotalFloor(toPropertyInfo.getTotalFloor());
			transSignVO.setLocateFloor(toPropertyInfo.getLocateFloor());
			transSignVO.setSquare(toPropertyInfo.getSquare());
			if(toPropertyInfo.getFinishYear() != null) {
				transSignVO.setFinishYear(DateUtil.getFormatDate(toPropertyInfo.getFinishYear(), "yyyy"));
			}
			transSignVO.setPropertyType(toPropertyInfo.getPropertyType());
		}
		
		/**读取签约信息*/
		ToSign toSign = toSignMapper.findToSignByCaseCode(caseCode);
		if(toSign!=null) {
			transSignVO.setSignPkid(toSign.getPkid());
			transSignVO.setIsConCert(toSign.getIsConCert());
			transSignVO.setIsHukou(toSign.getIsHukou());
			transSignVO.setComment(toSign.getComment());
			transSignVO.setConPrice(toSign.getConPrice());  // 合同价 
			transSignVO.setRealPrice(toSign.getRealPrice());  // 成交价 
			transSignVO.setRealConTime(toSign.getRealConTime());
		}
		
		/* 读取首次跟进信息 作者：zhangxb16 时间：2016-1-27 */
		ToFirstFollow tofw=tofirstFollowMapper.selectByCaseCode(caseCode);
		if(null!=tofw){
			transSignVO.setIsLoanClose(tofw.getIsLoanClose()); // 抵押情况
			transSignVO.setIsPerchaseReserachNeed(tofw.getIsPerchaseReserachNeed()); // 是否需要查限购
		}
		
		return transSignVO;
	}

	
	@Override
	public ToSign findToSignByCaseCode(String caseCode) {
		ToSign sign=toSignMapper.findToSignByCaseCode(caseCode);
		sign.setConPrice(sign.getConPrice()!=null?sign.getConPrice().divide(new BigDecimal(10000)):null);
		sign.setRealPrice(sign.getRealPrice()!=null?sign.getRealPrice().divide(new BigDecimal(10000)):null);
		return sign;
	}
	
}
