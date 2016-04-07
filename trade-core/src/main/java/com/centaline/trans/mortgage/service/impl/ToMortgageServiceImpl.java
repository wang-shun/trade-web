package com.centaline.trans.mortgage.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.mgr.entity.ToSupDocu;
import com.centaline.trans.mgr.service.ToSupDocuService;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.mortgage.service.ToMortgageService;

@Service
public class ToMortgageServiceImpl implements ToMortgageService {

	@Autowired
	private ToMortgageMapper toMortgageMapper;

	@Autowired
	private ToSupDocuService toSupDocuService;

	@Override
	public ToMortgage saveToMortgage(ToMortgage toMortgage) {

		if (toMortgage.getPkid() != null) {
				toMortgageMapper.update(toMortgage);
		} else {
				toMortgageMapper.insertSelective(toMortgage);
		}
		return toMortgage;
	}

	@Override
	public void saveToMortgageAndSupDocu(ToMortgage toMortgage) {

		ToMortgage mortgage = this.findToMortgageByCaseCode(toMortgage);

		if (mortgage != null) {
				toMortgageMapper.update(toMortgage);
		} else {
			toMortgage.setIsDelegateYucui("1");
			toMortgageMapper.insertSelective(toMortgage);
		}
		ToSupDocu toSupDocu = toMortgage.getToSupDocu();
		ToSupDocu supDocu = toSupDocuService.findByCaseCode(toMortgage
				.getCaseCode());

		if (supDocu != null) {
			supDocu.setSupContent(toSupDocu.getSupContent());
			supDocu.setRemindTime(toSupDocu.getRemindTime());
			toSupDocuService.updateToSupDocu(supDocu);
		} else {
			if (StringUtils.isNotBlank(toSupDocu.getSupContent())) {
				toSupDocu.setCaseCode(toMortgage.getCaseCode());
				toSupDocu.setPartCode("ComLoanProcess");
				toSupDocuService.saveToSupDocu(toSupDocu);
			}
		}
	}

	@Override
	public ToMortgage findToMortgageById(Long id) {
		return toMortgageMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateToMortgage(ToMortgage toMortgage) {
		toMortgageMapper.update(toMortgage);

	}

	@Override
	public ToMortgage findToMortgageByCaseCode(String caseCode) {
		ToMortgage toMortgage = toMortgageMapper
				.findToMortgageByCaseCode(caseCode);
		if (toMortgage != null) {
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(caseCode);
			toMortgage.setToSupDocu(toSupDocu);
			toMortgage
					.setComAmount(toMortgage.getComAmount() != null ? toMortgage
							.getComAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setMortTotalAmount(toMortgage.getMortTotalAmount()!=null?toMortgage.getMortTotalAmount()
					.divide(new BigDecimal(10000)):null);
			toMortgage
					.setPrfAmount(toMortgage.getPrfAmount() != null ? toMortgage
							.getPrfAmount().divide(new BigDecimal(10000))
							: null);
			toMortgage.setToSupDocu(toSupDocu);
		}
		return toMortgage;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode(ToMortgage toMortgage) {
		List<ToMortgage> list = toMortgageMapper
				.findToMortgageByCaseCodeAndBankType(toMortgage);
		if (CollectionUtils.isNotEmpty(list)) {
			ToMortgage mort = null;
			ToSupDocu toSupDocu = toSupDocuService.findByCaseCode(toMortgage
					.getCaseCode());

			if (list.size() == 1) {
				mort = list.get(0);
			} else {
				for (ToMortgage mortgage : list) {
					if (StringUtils.isNotBlank(mortgage.getLastLoanBank())) {
						mort = mortgage;
						break;
					}
				}
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() != null ?mort.getMortTotalAmount().divide(
					new BigDecimal(10000)):null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setToSupDocu(toSupDocu);

			return mort;
		}
		return null;
	}

	@Override
	public ToMortgage findToMortgageByCaseCode2(String caseCode) {

		List<ToMortgage> toMortgageList = toMortgageMapper
				.findToMortgageByCaseCodeNoBlank(caseCode);
		if (CollectionUtils.isNotEmpty(toMortgageList)) {
			ToMortgage mort = null;
			if (toMortgageList.size() == 1) {
				mort = toMortgageList.get(0);
			} else {
				for (ToMortgage toMortgage : toMortgageList) {
					if (StringUtils.isNotEmpty(toMortgage.getLastLoanBank())) {
						mort = toMortgage;
					}
				}
			}
			if (mort == null) {
				return mort;
			}
			mort.setComAmount(mort.getComAmount() != null ? mort.getComAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setMortTotalAmount(mort.getMortTotalAmount() !=null ?mort.getMortTotalAmount()
					.divide(new BigDecimal(10000)) : null);
			mort.setPrfAmount(mort.getPrfAmount() != null ? mort.getPrfAmount()
					.divide(new BigDecimal(10000)) : null);
			return mort;
		}
		return null;
	}

	@Override
	public ToMortgage findToMortgageByMortTypeAndCaseCode(String caseCode, String mortType) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setMortType(mortType);
		toMortgage.setIsDelegateYucui("1");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public ToMortgage findToSelfLoanMortgage(String caseCode) {
		ToMortgage toMortgage=new ToMortgage();
		toMortgage.setCaseCode(caseCode);
		toMortgage.setIsDelegateYucui("0");
		toMortgage.setIsActive("1");
		List<ToMortgage> list=toMortgageMapper.findToMortgageByCondition(toMortgage);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
