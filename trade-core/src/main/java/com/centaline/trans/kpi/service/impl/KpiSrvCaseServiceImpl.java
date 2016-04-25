package com.centaline.trans.kpi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BaseException;
import com.centaline.trans.award.entity.AwardBaseEntity;
import com.centaline.trans.award.repository.AwardBaseEntityMapper;
import com.centaline.trans.kpi.entity.TsKpiSrvCase;
import com.centaline.trans.kpi.repository.TsKpiSrvCaseMapper;
import com.centaline.trans.kpi.service.KpiSrvCaseService;
import com.centaline.trans.kpi.vo.KpiSrvCaseVo;

@Service(value = "kpiSrvCaseService")
@Transactional(readOnly = true)
public class KpiSrvCaseServiceImpl implements KpiSrvCaseService {
	public static final String KPI_SRV_CASE_TYPE_IMP = "IMP";
	public static final String KPI_SRV_CASE_TYPE_GEN = "GEN";
	@Autowired
	private TsKpiSrvCaseMapper kpiSrvCaseMapper;
	@Autowired
	private AwardBaseEntityMapper awardBaseEntityMapper;

	/**
	 * maybe propagation set to REQUIRED_NEW will be better
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public boolean importOne(KpiSrvCaseVo vo) {
		List<TsKpiSrvCase> kpiSrvCaseEntity = voToEntity(vo);
		for (TsKpiSrvCase tsKpiSrvCase : kpiSrvCaseEntity) {
			if (kpiSrvCaseMapper.insertSelective(tsKpiSrvCase) <= 0) {
				throw new BaseException("导入失败");
			}
		}
		return true;
	}

	public int deleteKpiSrvCaseByBelongMonth(Date belongMoht) {
		return kpiSrvCaseMapper.deleteByBelongMonth(belongMoht);
	}

	@Transactional(readOnly = false)
	@Override
	public boolean importBatch(List<KpiSrvCaseVo> listVOs, Boolean currentMonth) {
		deleteKpiSrvCaseByBelongMonth(getFirstDay(currentMonth));
		if (listVOs != null && !listVOs.isEmpty()) {
			for (KpiSrvCaseVo kpiSrvCaseVo : listVOs) {
				if (!importOne(kpiSrvCaseVo)) {
					throw new BaseException("导入失败");
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private List<TsKpiSrvCase> voToEntity(KpiSrvCaseVo vo) {
		List<TsKpiSrvCase> listEntity = new ArrayList<>();
		TsKpiSrvCase entity = new TsKpiSrvCase();
		entity.setBelongMonth(getFirstDay(vo.isCurrentMonth()));
		entity.setCreateBy(vo.getCreateBy());
		Boolean salesCallBack = false;
		Boolean buyerCallBack = false;
		// 下家
		if ("通".equals(vo.getCallBack())) {
			entity.setCanCallback("1");
			entity.setBuyerComment(vo.getBuyerComment());
			buyerCallBack = true;
		} else {
			entity.setCanCallback("0");
		}

		// 上家字段
		if ("通".equals(vo.getSalesCallBack())) {
			entity.setSalerCallback("1");
			entity.setSalerComment(vo.getSalerComment());
			salesCallBack = true;
		} else {
			entity.setSalerCallback("0");
		}
		entity.setType(KPI_SRV_CASE_TYPE_IMP);
		entity.setCreateTime(new Date());

		listEntity.add(generateNewEntity(entity, "TransSign", salesCallBack ? vo.getSalesSignScore() : null,
				buyerCallBack ? vo.getSignScore() : null, true,
				StrToBo(entity.getSalerCallback()) && StrToBo(entity.getSalerCallback())));// 签约

		listEntity.add(generateNewEntity(entity, "LoanClose", salesCallBack ? vo.getAccompanyRepayLoanScore() : null,
				null, false, StrToBo(entity.getSalerCallback())));// 上家贷款结清

		listEntity.add(generateNewEntity(entity, "Guohu", salesCallBack ? vo.getSalesTransferScore() : null,
				buyerCallBack ? vo.getTransferScore() : null, true,
				StrToBo(entity.getSalerCallback()) && StrToBo(entity.getSalerCallback())));// 过户
		listEntity.add(generateNewEntity(entity, "ComLoanProcess", null, buyerCallBack ? vo.getComLoanScore() : null,
				false, StrToBo(entity.getBuyerCallback())));// 下家贷款
		listEntity.add(generateNewEntity(entity, "PSFSign", null, buyerCallBack ? vo.getAccuFundScore() : null, false,
				StrToBo(entity.getBuyerCallback())));// 公积金贷款

		return listEntity;
	}

	private BigDecimal DoubleToBigDecimal(Double d) {
		return d == null ? null : new BigDecimal(d);
	}

	private Boolean StrToBo(String str) {
		return new Boolean(str);
	}

	/**
	 * 
	 * @param entity
	 * @param srvCode
	 * @param ssc
	 *            上家评分
	 * @param bsc
	 *            下家评分
	 * @return
	 */
	private TsKpiSrvCase generateNewEntity(TsKpiSrvCase entity, String srvCode, Double ssc, Double bsc,
			Boolean bothHave, Boolean canCallBack) {

		TsKpiSrvCase newEntity = new TsKpiSrvCase();
		BeanUtils.copyProperties(entity, newEntity);
		newEntity.setSrvCode(srvCode);
		newEntity.setSalerSatis(DoubleToBigDecimal(ssc));
		newEntity.setBuyerSatis(DoubleToBigDecimal(bsc));
		AwardBaseEntity awardBase = awardBaseEntityMapper.selectByCaseCodeAndSrvCode(entity.getCaseCode(), srvCode);
		if (awardBase != null) {
			newEntity.setTeamId(awardBase.getTeamId());
			newEntity.setDistrictId(awardBase.getDistrictId());
		}

		if (ssc != null && bsc != null) {
			newEntity.setSatisfaction(new BigDecimal((ssc + bsc) / (bothHave ? 2 : 1)));
		} else if (!bothHave) {
			if (ssc != null) {
				newEntity.setSatisfaction(DoubleToBigDecimal(ssc));
			} else {
				newEntity.setSatisfaction(DoubleToBigDecimal(bsc));
			}
		}
		newEntity.setCanCallback(canCallBack ? "1" : "0");
		return newEntity;
	}

	private Date getFirstDay(Boolean currentMonth) {
		Calendar cal = Calendar.getInstance();
		if (!currentMonth) {// 非当月就是上个月
			cal.add(Calendar.MONTH, -1);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}
