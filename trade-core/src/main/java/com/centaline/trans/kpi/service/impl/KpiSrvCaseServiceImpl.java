package com.centaline.trans.kpi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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
import com.centaline.trans.utils.NumberUtil;

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
	public List<TsKpiSrvCase> importOne(KpiSrvCaseVo vo) {
		List<TsKpiSrvCase> kpiSrvCaseEntity = voToEntity(vo);
		return kpiSrvCaseEntity;
	}

	public int deleteKpiSrvCaseByBelongMonth(Date belongMoht) {
		return kpiSrvCaseMapper.deleteByBelongMonth(belongMoht);
	}

	@Transactional(readOnly = false)
	@Override
	public List<KpiSrvCaseVo> importBatch(List<KpiSrvCaseVo> listVOs, Boolean currentMonth) {
		deleteKpiSrvCaseByBelongMonth(getFirstDay(currentMonth));
		List<KpiSrvCaseVo> errList = checkVo(listVOs);
		if (errList != null) {
			return errList;
		}
		Set<String> caseCodes = kpiSrvCaseMapper.getCaseCodeByCaseCode(listVOs);
		errList = filterByCaseCodeSetMsg(listVOs, caseCodes, "该案件数据已经存在");
		if (errList != null) {
			return errList;
		}
		List<TsKpiSrvCase> vos = new ArrayList<>();
		if (listVOs != null && !listVOs.isEmpty()) {
			int i = 0;// 每条数据插入有19个参数 每导入一条数据会被拆分成五条 每导入一条数据大概100个参数
						// 2100个参数就是最多能导入21条数据左右
			int pSize = 20;// 插入一条数据有多少个参数
			int maxSize = 2100;// 数据库限制最多只有2100个参数
			for (KpiSrvCaseVo kpiSrvCaseVo : listVOs) {
				if (StringUtils.isBlank(kpiSrvCaseVo.getCaseCode())) {
					continue;
				}
				List<TsKpiSrvCase> t = importOne(kpiSrvCaseVo);
				if (i + t.size() * pSize > maxSize) {
					i = t.size() * pSize;
					kpiSrvCaseMapper.batchInsert(vos);
					vos.clear();
					vos.addAll(t);
				} else {
					vos.addAll(t);
					i += (t.size() * pSize);
				}
			}
			kpiSrvCaseMapper.batchInsert(vos);
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 校验Case在当前文件中是否重复
	 * 
	 * @param listVOs
	 * @return
	 */
	private List<KpiSrvCaseVo> checkVo(List<KpiSrvCaseVo> listVOs) {
		if (listVOs == null || listVOs.isEmpty())
			return null;
		Set<String> set = new HashSet<>();
		Set<String> tSet = new HashSet<>();

		for (KpiSrvCaseVo kpiSrvCaseVo : listVOs) {
			if (StringUtils.isBlank(kpiSrvCaseVo.getCaseCode())) {
				continue;
			}
			if (set.contains(kpiSrvCaseVo.getCaseCode())) {
				tSet.add(kpiSrvCaseVo.getCaseCode());
			}
			set.add(kpiSrvCaseVo.getCaseCode());
		}
		return filterByCaseCodeSetMsg(listVOs, tSet, "案件编号重复");
	}

	private List<KpiSrvCaseVo> filterByCaseCodeSetMsg(List<KpiSrvCaseVo> listVOs, Collection<String> colls,
			String msg) {
		if (colls == null || colls.isEmpty() || listVOs == null || listVOs.isEmpty()) {
			return null;
		} else {
			List<KpiSrvCaseVo> t = new ArrayList<>();
			listVOs.forEach(x -> {
				if (colls.contains(x.getCaseCode())) {
					x.setMsg(msg);
					t.add(x);
				}
			});
			if (!t.isEmpty())
				return t;
		}

		return null;
	}

	private List<TsKpiSrvCase> voToEntity(KpiSrvCaseVo vo) {
		List<TsKpiSrvCase> listEntity = new ArrayList<>();
		TsKpiSrvCase entity = new TsKpiSrvCase();
		entity.setBelongMonth(getFirstDay(vo.isCurrentMonth()));
		entity.setCreateBy(vo.getCreateBy());
		entity.setCaseCode(vo.getCaseCode());
		entity.setSrvPart(DoubleToBigDecimal(1d));
		Boolean salesCallBack = false;
		Boolean buyerCallBack = false;
		// 下家
		if ("通".equals(vo.getCallBack())) {
			entity.setBuyerCallback("1");
			entity.setBuyerComment(vo.getBuyerComment());
			buyerCallBack = true;
		} else {
			entity.setBuyerCallback("0");
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
				StrToBo(entity.getSalerCallback()) && StrToBo(entity.getBuyerCallback())));// 签约

		listEntity.add(generateNewEntity(entity, "LoanClose", salesCallBack ? vo.getAccompanyRepayLoanScore() : null,
				null, false, StrToBo(entity.getSalerCallback())));// 上家贷款结清

		listEntity.add(generateNewEntity(entity, "Guohu", salesCallBack ? vo.getSalesTransferScore() : null,
				buyerCallBack ? vo.getTransferScore() : null, true,
				StrToBo(entity.getSalerCallback()) && StrToBo(entity.getBuyerCallback())));// 过户
		listEntity.add(generateNewEntity(entity, "ComLoanProcess", null, buyerCallBack ? vo.getComLoanScore() : null,
				false, StrToBo(entity.getBuyerCallback())));// 下家贷款
		listEntity.add(generateNewEntity(entity, "PSFSign", null, buyerCallBack ? vo.getAccuFundScore() : null, false,
				StrToBo(entity.getBuyerCallback())));// 公积金贷款

		return listEntity;
	}

	private BigDecimal DoubleToBigDecimal(Double d) {
		return d == null ? null :  BigDecimal.valueOf(d).setScale(2);
	}

	private Boolean StrToBo(String str) {
		return new Boolean("1".equals(str));
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
			newEntity.setSatisfaction(NumberUtil.add(ssc, bsc).divide(DoubleToBigDecimal(bothHave ? 2d : 1d),2,RoundingMode.HALF_UP));
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

	@Override
	public void callKpiStastic(Boolean currentMonth) {
		kpiSrvCaseMapper.callKpiStastic(getFirstDay(currentMonth));
	}
}
