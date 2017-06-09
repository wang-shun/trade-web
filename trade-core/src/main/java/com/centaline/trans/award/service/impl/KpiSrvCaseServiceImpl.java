package com.centaline.trans.award.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
import com.centaline.trans.award.entity.AwardBaseEntity;
import com.centaline.trans.award.entity.TsKpiSrvCase;
import com.centaline.trans.award.repository.AwardBaseEntityMapper;
import com.centaline.trans.award.repository.TsKpiSrvCaseMapper;
import com.centaline.trans.award.service.KpiSrvCaseService;
import com.centaline.trans.award.vo.KpiSrvCaseVo;
import com.centaline.trans.utils.DateUtil;
import com.centaline.trans.utils.NumberUtil;

import sun.util.resources.nl.CalendarData_nl;

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

	private void removeBlankCaseCode(List<KpiSrvCaseVo> listVOs) {
		Iterator<KpiSrvCaseVo> it = listVOs.iterator();
		while (it.hasNext()) {
			KpiSrvCaseVo kpiSrvCaseVo = (KpiSrvCaseVo) it.next();
			if (StringUtils.isBlank(kpiSrvCaseVo.getCaseCode())) {
				it.remove();
			}
		}
	}
	/*
	 * 移除全部不通的
	 */
	private List<KpiSrvCaseVo>romveCanottCall(List<KpiSrvCaseVo> listVOs){
		List<KpiSrvCaseVo> returnList=new ArrayList<>();
		for (KpiSrvCaseVo x : listVOs) {
			if(x.getCallBack().equals("通") || x.getSalesCallBack().equals("通")){
				returnList.add(x);
			}
		}
		return returnList;
	}

	@Transactional(readOnly = false)
	@Override
	public List<KpiSrvCaseVo> importBatch(List<KpiSrvCaseVo> listVOs, Boolean currentMonth) {
		listVOs=romveCanottCall(listVOs);
		List<KpiSrvCaseVo> errList = null; //checkVo(listVOs);
		if (errList != null) {
			return errList;
		}
		Set<String> caseCodes = kpiSrvCaseMapper.getCaseCodeByCaseCode(listVOs);
		errList = filterByCaseCodeSetMsg(listVOs, caseCodes, "该案件数据已经存在并且已经发放奖金");
		if (errList != null) {
			return errList;
		}
		/*该案件数据不存在  */
		caseCodes = kpiSrvCaseMapper.getCaseCodeByCaseCodefromTToCase(listVOs);
		errList = filterNoCaseCodeSetMsg(listVOs,caseCodes,"该案件数据不存在");
		if (errList != null) {
			return errList;
		}
		/* 只有过户审批通过的案件才能导入   
		caseCodes = kpiSrvCaseMapper.getCaseCodeByCaseCodefromTToAwardBase(listVOs);
		errList = filterNoGuoHuCaseCodeSetMsg(listVOs,caseCodes,"该案件数据还未过户");
		if (errList != null) {
			return errList;
		}*/
		/* 满意度0-10的整数  上下家电话只能是通过、不通过两种*/
		errList = filterNoInteger(listVOs);
		if (errList != null) {
			return errList;
		}
		if(currentMonth){
			deleteKpiSrvCaseByBelongMonth(DateUtil.getFirstDayOfTheMonth());
		}else{
			deleteKpiSrvCaseByBelongMonth(DateUtil.getFirstDayOfTheMonth(DateUtil.plusMonth(new Date(), -1)));
		}
		
		removeBlankCaseCode(listVOs);
		
		List<TsKpiSrvCase> vos = new ArrayList<>();
		if (listVOs != null && !listVOs.isEmpty()) {
			int i = 0;// 每条数据插入有19个参数 每导入一条数据会被拆分成五条 每导入一条数据大概100个参数
						// 2100个参数就是最多能导入21条数据左右
			int pSize = 21;// 插入一条数据有多少个参数
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
			if (!vos.isEmpty()) {
			kpiSrvCaseMapper.batchInsert(vos);
			}
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
	@SuppressWarnings("unused")
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
		List<KpiSrvCaseVo> list = filterByCaseCodeSetMsg(listVOs, tSet, "案件编号重复");
		if (list != null) {
			return list;
		}

		tSet = kpiSrvCaseMapper.getNoneAwardCase(listVOs);
		return filterByCaseCodeSetMsg(listVOs, tSet, "案件基本奖金尚未生成");
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
	
	private List<KpiSrvCaseVo> filterNoCaseCodeSetMsg(List<KpiSrvCaseVo> listVOs,Collection<String> colls,String msg){
		if(listVOs!=null){
			if (colls == null || colls.isEmpty()){
				List<KpiSrvCaseVo> t = new ArrayList<>();
				listVOs.forEach(x -> {
						x.setMsg(msg);
						t.add(x);
				});
				if (!t.isEmpty())
					return t;
			}else{
				List<KpiSrvCaseVo> t = new ArrayList<>();
				listVOs.forEach(x -> {
					if (!colls.contains(x.getCaseCode())) {
						x.setMsg(msg);
						t.add(x);
					}
				});
				if (!t.isEmpty())
					return t;
			}
		}else{
			return null;
		}
		
		return null;
	}
	
	private List<KpiSrvCaseVo> filterNoInteger(List<KpiSrvCaseVo> listVOs){
		
			List<KpiSrvCaseVo> t = new ArrayList<>();
			
			for(KpiSrvCaseVo x:listVOs){
				if(StringUtils.isNotBlank(x.getSalesSignScore())){//上家签约
					try{
					if(Double.valueOf(x.getSalesSignScore())%1!=0){
						x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}else{
						if(Double.valueOf(x.getSalesSignScore())<0 || Double.valueOf(x.getSalesSignScore())>10){
							x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}
					}
					}catch(Exception e){
						x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getAccompanyRepayLoanScore())){//上家陪同还贷
					try{
						if(Double.valueOf(x.getAccompanyRepayLoanScore())%1!=0){
							x.setMsg("该案件数据上家陪同还贷满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getAccompanyRepayLoanScore())<0 || Double.valueOf(x.getAccompanyRepayLoanScore())>10){
								x.setMsg("该案件数据上家陪同还贷满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据上家陪同还贷满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getSalesTransferScore())){//上家过户
					try{
						if(Double.valueOf(x.getSalesTransferScore())%1!=0){
							x.setMsg("该案件数据上家过户满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getSalesTransferScore())<0 || Double.valueOf(x.getSalesTransferScore())>10){
								x.setMsg("该案件数据上家过户满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据上家过户满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getSignScore())){//下家签约
					try{
						if(Double.valueOf(x.getSignScore())%1!=0){
							x.setMsg("该案件数据下家签约满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getSignScore())<0 || Double.valueOf(x.getSignScore())>10){
								x.setMsg("该案件数据下家签约满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据下家签约满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getComLoanScore())){//下家贷款
					try{
						if(Double.valueOf(x.getComLoanScore())%1!=0){
							x.setMsg("该案件数据下家贷款满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getComLoanScore())<0 || Double.valueOf(x.getComLoanScore())>10){
								x.setMsg("该案件数据下家贷款满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据下家贷款满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getAccuFundScore())){//下家纯公积金
					try{
						if(Double.valueOf(x.getAccuFundScore())%1!=0){
							x.setMsg("该案件数据下家纯公积金满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getAccuFundScore())<0 || Double.valueOf(x.getAccuFundScore())>10){
								x.setMsg("该案件数据下家纯公积金满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据下家纯公积金满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				if(StringUtils.isNotBlank(x.getTransferScore())){//下家过户
					try{
						if(Double.valueOf(x.getTransferScore())%1!=0){
							x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
							t.add(x);
							continue;
						}else{
							if(Double.valueOf(x.getTransferScore())<0 || Double.valueOf(x.getTransferScore())>10){
								x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
								t.add(x);
								continue;
							}
						}
					}catch(Exception e){
						x.setMsg("该案件数据上家签约满意度不是[0-10]的整数");
						t.add(x);
						continue;
					}
					
				}
				
				if(StringUtils.isNotBlank(x.getSalesCallBack())){//上家电话接通
					if(x.getSalesCallBack().equals("通") || x.getSalesCallBack().equals("不通")){
					}else{
						x.setMsg("该案件数据上家电话接通须填写通或不通");
						t.add(x);
						continue;
					}
				}else{
					x.setMsg("该案件数据上家电话接通未填写");
					t.add(x);
					continue;
				}
				if(StringUtils.isNotBlank(x.getCallBack())){//下家电话接通
					if(x.getCallBack().equals("通") || x.getCallBack().equals("不通")){
					}else{
						x.setMsg("该案件数据下家电话接通须填写通或不通");
						t.add(x);
						continue;
					}
				}else{
					x.setMsg("该案件数据下家电话接通未填写");
					t.add(x);
					continue;
				}
				
			}
			
			if (!t.isEmpty())
				return t;
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private List<KpiSrvCaseVo> filterNoGuoHuCaseCodeSetMsg(List<KpiSrvCaseVo> listVOs,Collection<String> colls,String msg){
		
		if(listVOs!=null){
			if (colls == null || colls.isEmpty()){
				List<KpiSrvCaseVo> t = new ArrayList<>();
				listVOs.forEach(x -> {
						x.setMsg(msg);
						t.add(x);
				});
				if (!t.isEmpty())
					return t;
			}else{
				List<KpiSrvCaseVo> t = new ArrayList<>();
				listVOs.forEach(x -> {
					if (!colls.contains(x.getCaseCode())) {
						x.setMsg(msg);
						t.add(x);
					}
				});
				if (!t.isEmpty())
					return t;
			}
		}else{
			return null;
		}
		
		return null;
	}

	private List<TsKpiSrvCase> voToEntity(KpiSrvCaseVo vo) {
		List<TsKpiSrvCase> listEntity = new ArrayList<>();
		List<TsKpiSrvCase> emList = new ArrayList<>();
		TsKpiSrvCase entity = new TsKpiSrvCase();
		
		if(vo.isCurrentMonth()){
			entity.setBelongMonth(DateUtil.getFirstDayOfTheMonth());
		}else{
			entity.setBelongMonth(DateUtil.getFirstDayOfTheMonth(DateUtil.plusMonth(new Date(), -1)));
		}
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
		if (!buyerCallBack || !salesCallBack) {// 上下家有一家不通 不导入
			return emList;
		}
		
		entity.setType(KPI_SRV_CASE_TYPE_IMP);
		entity.setCreateTime(new Date());
		TsKpiSrvCase tscTemp = new TsKpiSrvCase();
		tscTemp=generateNewEntity(entity, "TransSign", 
				salesCallBack && StringUtils.isNotBlank(vo.getSalesSignScore()) ? Double.valueOf(vo.getSalesSignScore())
						: null,
				buyerCallBack && StringUtils.isNotBlank(vo.getSignScore()) ? Double.valueOf(vo.getSignScore()) : null,
				true, StrToBo(entity.getSalerCallback()) && StrToBo(entity.getBuyerCallback()));

		listEntity.add(tscTemp);// 签约
		
		tscTemp=generateNewEntity(entity, "LoanClose", 
				salesCallBack && StringUtils.isNotBlank(vo.getAccompanyRepayLoanScore())
						? Double.valueOf(vo.getAccompanyRepayLoanScore()) : null,
					null, false, StrToBo(entity.getSalerCallback()));
		
		listEntity.add(tscTemp);// 上家贷款结清
		
		tscTemp=generateNewEntity(entity, "Guohu", 
				salesCallBack && StringUtils.isNotBlank(vo.getSalesTransferScore())
						? Double.valueOf(vo.getSalesTransferScore()) : null,
				buyerCallBack && StringUtils.isNotBlank(vo.getTransferScore()) ? Double.valueOf(vo.getTransferScore())
						: null,
				true, StrToBo(entity.getSalerCallback()) && StrToBo(entity.getBuyerCallback()));// 过户

		listEntity.add(tscTemp);// 过户
		
		/* 下家贷款为空的话则不插入数据页面不显示   20160823*/
		//if(StringUtils.isNotBlank(vo.getComLoanScore())){
		tscTemp=generateNewEntity(entity, "ComLoanProcess", null, 
				buyerCallBack && StringUtils.isNotBlank(vo.getComLoanScore()) ? Double.valueOf(vo.getComLoanScore())
						: null,
					false, StrToBo(entity.getBuyerCallback()));// 下家贷款

		listEntity.add(tscTemp);// 下家贷款
		
		tscTemp=generateNewEntity(entity, "PSFSign", null, 
				buyerCallBack && StringUtils.isNotBlank(vo.getAccuFundScore()) ? Double.valueOf(vo.getAccuFundScore())
						: null,
				false, StrToBo(entity.getBuyerCallback()));// 公积金贷款

		listEntity.add(tscTemp);// 下家贷款
		return listEntity;
	}

	private BigDecimal DoubleToBigDecimal(Double d) {
		return d == null ? null : BigDecimal.valueOf(d);
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
		AwardBaseEntity awardBase =null;
		// boolean existsAwardBase=false;
		try {
			 awardBase = awardBaseEntityMapper.selectByCaseCodeAndSrvCode(entity.getCaseCode(), srvCode);
		} catch (Exception e) {
			System.err.println(entity.getCaseCode()+"----"+ srvCode);
			throw e;
		}
	
		
		if (awardBase != null) {
			newEntity.setTeamId(awardBase.getTeamId());
			newEntity.setOrgId(awardBase.getOrgId());
			newEntity.setDistrictId(awardBase.getDistrictId());
		}

		if (ssc != null && bsc != null) {
			newEntity.setSatisfaction(
					NumberUtil.add(ssc, bsc).divide(DoubleToBigDecimal(bothHave ? 2d : 1d), 2, RoundingMode.HALF_UP));
		} else if (!bothHave) {
			if (ssc != null) {
				newEntity.setSatisfaction(DoubleToBigDecimal(ssc));
			} else {
				newEntity.setSatisfaction(DoubleToBigDecimal(bsc));
			}
		}
		/*
		 * //没有拿到满意度但又AwardBase又存在返回NULL该案件满意度不导入 这个规则又改了
		 * if(newEntity.getSatisfaction()==null&&existsAwardBase){
		 * newEntity.setCheckFlag(false); }else{ newEntity.setCheckFlag(true); }
		 */
		
		newEntity.setCanCallback(canCallBack ? "1" : "0");
		return newEntity;
	}

	@Override
	public void callKpiStastic(Boolean currentMonth) {
		if(currentMonth){
			kpiSrvCaseMapper.callKpiStastic(DateUtil.getFirstDayOfTheMonth());
		}else{
			kpiSrvCaseMapper.callKpiStastic(DateUtil.getFirstDayOfTheMonth(DateUtil.plusMonth(new Date(), -1)));
		}
		
	}

	@Override
	public void callKpiSyncSatis(String belongMonth) {
		if(null == belongMonth || "".equals(belongMonth)){
			throw new BusinessException("同步计件奖金满意度请求参数有误！");
		}
		
		
		Map<String, Date> paramMap = new HashMap<String, Date>();
/*		//过户审批通过时间：默认上月月底
		Date today1 = DateUtil.getFirstDayOfTheMonth();
		Date guohuTime = DateUtil.plusDay(today1, -1);
		//计件月份：默认上月月初
		Date today2 = DateUtil.getFirstDayOfTheMonth();
		Date countTime = DateUtil.plusMonth(today2, -1);	*/	
		
		
		Date belongMon = DateUtil.strToFullDate(belongMonth);		
		Calendar calendar = Calendar.getInstance();	
		calendar.setTime(belongMon);	
		//calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天	
		calendar.add(Calendar.MONTH, 1);
		Date guohuTime = calendar.getTime();
		
		
	    paramMap.put("guohu_approve_time", guohuTime);
	    paramMap.put("belong_month", belongMon);
		kpiSrvCaseMapper.callKpiSyncSatis(paramMap);
	}
}
