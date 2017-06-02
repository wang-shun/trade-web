package com.centaline.trans.task.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.repository.TgGuestInfoMapper;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.repository.ToMortgageMapper;
import com.centaline.trans.task.service.PSFSignService;
import com.centaline.trans.task.vo.PSFSignVO;
import com.centaline.trans.utils.NumberUtil;

@Service
public class PSFSignServiceImpl implements PSFSignService {

	@Autowired
	private ToMortgageMapper toMortgageMapper;
	@Autowired
	private TgGuestInfoMapper tgGuestInfoMapper;
	@Autowired
	private PSFSignService psfSignService;
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;

	
	@Override
	public Boolean savePSFSign(PSFSignVO psfSignVO) {
		if(StringUtils.isBlank(psfSignVO.getCaseCode())) {
			return false;
		}
		/*按揭贷款表*/
		ToMortgage toMortgage = new ToMortgage();
		toMortgage.setMortType(psfSignVO.getMortType()); /*字典字段*/
		if(psfSignVO.getMortTotalAmount()!=null){
			toMortgage.setMortTotalAmount(psfSignVO.getMortTotalAmount().multiply(new BigDecimal(10000)));
			toMortgage.setPrfAmount(psfSignVO.getMortTotalAmount().multiply(new BigDecimal(10000)));  // add zhangxiaobo 2016-2-16
		}
		toMortgage.setFinOrgCode(psfSignVO.getFinOrgCode());
		toMortgage.setCustCode(psfSignVO.getCustCode());
		toMortgage.setPrfYear(psfSignVO.getPrfYear());
		toMortgage.setSignDate(psfSignVO.getSignDate());
		toMortgage.setRemark(psfSignVO.getRemark());
		
		// add zhangxb16 2016-2-16
		if(null !=psfSignVO.getCustCode()){
			TgGuestInfo tg=tgGuestInfoMapper.selectByPrimaryKey(Long.parseLong(psfSignVO.getCustCode()));
			if(null!=tg){
				toMortgage.setCustName(tg.getGuestName());
			}
		}
		
		// 修改人：zhangxb16    时间：2015-11-12    功能: 去掉主贷人单位这个字段
		// toMortgage.setCustCompany(psfSignVO.getWorkUnit());
		toMortgage.setLastLoanBank(psfSignVO.getFinOrgCode());
//		toMortgage.setIsMainLoanBank("1");
		if(psfSignVO.getPkid() != null) {
			toMortgage.setPkid(psfSignVO.getPkid());
			toMortgageMapper.update(toMortgage);
		} else {
			toMortgage.setCaseCode(psfSignVO.getCaseCode());
			if(toMortgageMapper.findToMortgageByCaseCodeNoBlank(toMortgage.getCaseCode()) == null) {
				toMortgageMapper.insertSelective(toMortgage);
			}
		}
		
		/*客户表*/
		// 修改人：zhangxb16    时间：2015-11-12   功能: 去掉主贷人单位这个字段
		/*if(psfSignVO.getWorkUnit() != null && psfSignVO.getWorkUnit().intern().length() > 0) {
			TgGuestInfo tgGuestInfo = new TgGuestInfo();
			// tgGuestInfo.setWorkUnit(psfSignVO.getWorkUnit());
			if(psfSignVO.getCustCode() != null) {
				tgGuestInfo.setPkid(Long.parseLong(psfSignVO.getCustCode()));
				tgGuestInfoMapper.updateByPrimaryKeySelective(tgGuestInfo);
			} 
		}*/
		return true;
	}

	@Override
	public PSFSignVO queryPSFSign(String caseCode) {
		
		ToMortgage toMortgage = toMortgageMapper.findToMortgageByCaseCode(caseCode);
		PSFSignVO psfSignVO = new PSFSignVO();
		if(toMortgage != null) {
			/*按揭贷款表*/
			psfSignVO.setMortType(toMortgage.getMortType());
			if(toMortgage.getMortTotalAmount()!=null){
				psfSignVO.setMortTotalAmount(toMortgage.getMortTotalAmount().divide(new BigDecimal(10000)));
			}
			psfSignVO.setFinOrgCode(toMortgage.getFinOrgCode());
			psfSignVO.setPrfYear(toMortgage.getPrfYear());
			psfSignVO.setCustCode(toMortgage.getCustCode());
			psfSignVO.setSignDate(toMortgage.getSignDate());
			psfSignVO.setPkid(toMortgage.getPkid());
			psfSignVO.setRemark(toMortgage.getRemark());
			/*客户表*/
			if(toMortgage.getCustCode() != null && toMortgage.getCustCode().intern().length() > 0 && !toMortgage.getCustCode().equals("null")) {
				TgGuestInfo tgGuestInfo = tgGuestInfoMapper.findTgGuestInfoByGuestCode(toMortgage.getCustCode());
				if(tgGuestInfo != null) {
					psfSignVO.setWorkUnit(tgGuestInfo.getWorkUnit());
				}
			}
		}
		return psfSignVO;
	}

	@Override
	public PSFSignVO queryPSFSignNoBlank(String caseCode) {
		List<ToMortgage> toMortgageList = toMortgageMapper.findToMortgageByCaseCodeNoBlank(caseCode);
		PSFSignVO psfSignVO = new PSFSignVO();
		if(CollectionUtils.isNotEmpty(toMortgageList)) {
			ToMortgage toMortgage = toMortgageList.get(0);
			/*按揭贷款表*/
			psfSignVO.setMortType(toMortgage.getMortType());
			psfSignVO.setMortTotalAmount(NumberUtil.divideNumber(toMortgage.getMortTotalAmount(), 10000));
			psfSignVO.setFinOrgCode(toMortgage.getFinOrgCode());
			psfSignVO.setPrfYear(toMortgage.getPrfYear());
			psfSignVO.setCustCode(toMortgage.getCustCode());
			psfSignVO.setSignDate(toMortgage.getSignDate());
			psfSignVO.setPkid(toMortgage.getPkid());
			psfSignVO.setRemark(toMortgage.getRemark());
			/*客户表*/
			if(toMortgage.getCustCode() != null && toMortgage.getCustCode().intern().length() > 0 && !toMortgage.getCustCode().equals("null")) {
				TgGuestInfo tgGuestInfo = tgGuestInfoMapper.findTgGuestInfoByGuestCode(toMortgage.getCustCode());
				if(tgGuestInfo != null) {
					psfSignVO.setWorkUnit(tgGuestInfo.getWorkUnit());
				}
			}
		}
		return psfSignVO;
	}

	@Override
	public AjaxResponse saveAndSubmitPSFSign(PSFSignVO psfSignVO) {
		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean saveFlag = psfSignService.savePSFSign(psfSignVO);
		if(saveFlag){
			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService.findToCaseByCaseCode(psfSignVO.getCaseCode());
			workFlowManager.submitTask(variables, psfSignVO.getTaskId(), psfSignVO.getProcessInstanceId(),toCase.getLeadingProcessId(), psfSignVO.getCaseCode());
			ajaxResponse.setSuccess(true);
		} else {
			ajaxResponse.setSuccess(false);
			ajaxResponse.setMessage("公积金贷款签约出错");
		}
		return ajaxResponse;
	}

}
