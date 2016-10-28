package com.centaline.trans.kpi.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.centaline.trans.kpi.entity.TsKpiPsnMonth;
import com.centaline.trans.kpi.repository.TsKpiPsnMonthMapper;
import com.centaline.trans.kpi.service.TsKpiPsnMonthService;
import com.centaline.trans.kpi.vo.KpiMonthVO;

@Service
public class TsKpiPsnMonthServiceImpl implements TsKpiPsnMonthService {
	
	@Autowired
	private TsKpiPsnMonthMapper tsKpiPsnMonthMapper;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired(required = true)
	UamSessionService uamSessionService;
	
	@Override
	public List<TsKpiPsnMonth> getTsKpiPsnMonthListByPro(TsKpiPsnMonth record) {
		return tsKpiPsnMonthMapper.getTsKpiPsnMonthListByPro(record);
	}

	@Override
	public int insertTsKpiPsnMonthList(List<TsKpiPsnMonth> list) {
		return tsKpiPsnMonthMapper.insertTsKpiPsnMonthList(list);
	}
	
	@Override
	public int importExcelTsKpiPsnMonthList(Date belongM, String createBy, List<KpiMonthVO> list) {
		List<TsKpiPsnMonth> record = new ArrayList<TsKpiPsnMonth>();
		for(KpiMonthVO kpiMonthVO : list) {
			String employeeCode = kpiMonthVO.getEmployeeCode();
			if(StringUtils.isBlank(employeeCode)) {
				continue;
			}
			//User user = uamUserOrgService.getUserByEmployeeCode(employeeCode);
		    //List<UserOrgJob> uoj  = uamUserOrgService.getUserOrgJobByUserId(user.getId());

			TsKpiPsnMonth monthKpi = new TsKpiPsnMonth();
			monthKpi.setCreateTime(new Date());
			monthKpi.setCreateBy(createBy);
			monthKpi.setBelongMonth(belongM);
			monthKpi.setParticipantName(employeeCode);//这里直接放Code后台统一更新
			monthKpi.setType("JRDBL");
			monthKpi.setComments("IMP");
			//monthKpi.setParticipant(user.getId());
			monthKpi.setFinOrder(Integer.parseInt(String.valueOf(kpiMonthVO.getFinOrder())));
			// 上月滚存数
			// 过户案件数
			// 金融达标率
			record.add(monthKpi);
		}
		int count = insertTsKpiPsnMonthList(record);
		return count ;
	}

	@Override
	public void getPMonthKpiStastic(Date belongMonth) {
		tsKpiPsnMonthMapper.getPMonthKpiStastic(belongMonth);
	}

	@Override
	public int deleteTsKpiPsnMonthByProperty(TsKpiPsnMonth record) {
		return tsKpiPsnMonthMapper.deleteTsKpiPsnMonthByProperty(record);
	}
	

}
