package com.centaline.trans.award.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.centaline.trans.award.entity.TsKpiSrvCase;
import com.centaline.trans.award.vo.KpiSrvCaseVo;
import com.centaline.trans.common.MyBatisRepository;

@MyBatisRepository
public interface TsKpiSrvCaseMapper {
	int deleteByPrimaryKey(Long pkid);

	int insert(TsKpiSrvCase record);

	int insertSelective(TsKpiSrvCase record);

	TsKpiSrvCase selectByPrimaryKey(Long pkid);

	int updateByPrimaryKeySelective(TsKpiSrvCase record);

	int updateByPrimaryKey(TsKpiSrvCase record);

	int deleteByBelongMonth(Date belongMoht);

	int batchInsert(List<TsKpiSrvCase> list);

	void callKpiStastic(Date date);

	Set<String> getCaseCodeByCaseCode(List<KpiSrvCaseVo> list);
	Set<String> getNoneAwardCase(List<KpiSrvCaseVo> list);
	/* 从t_to_case表里面获取导入案件的所有案件编号 */
	Set<String> getCaseCodeByCaseCodefromTToCase(List<KpiSrvCaseVo> list);
	/* 计算过户案件的案件编号  */
	Set<String> getCaseCodeByCaseCodefromTToAwardBase(List<KpiSrvCaseVo> list);
}