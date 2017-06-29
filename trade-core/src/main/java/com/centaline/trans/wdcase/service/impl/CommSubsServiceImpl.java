package com.centaline.trans.wdcase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.wdcase.entity.TpdCommSubs;
import com.centaline.trans.wdcase.entity.TpdCommSubsDetals;
import com.centaline.trans.wdcase.repository.TpdCommSubsDetalsMapper;
import com.centaline.trans.wdcase.repository.TpdCommSubsMapper;
import com.centaline.trans.wdcase.service.CommSubsService;
import com.centaline.trans.wdcase.vo.CommSubsVo;

@Service
public class CommSubsServiceImpl implements CommSubsService {
	@Autowired
	private TpdCommSubsMapper commSubsMapper;
	@Autowired
	private TpdCommSubsDetalsMapper commSubsDetailsMapper;
	/**
	 * 生成应收费用
	 */
	@Override
	public void generatorCommSubs(CommSubsVo vo) {
		TpdCommSubs commSubs = new TpdCommSubs();
		commSubs.setCommCost(vo.getCommCost());
		commSubs.setCaseCode(vo.getCaseCode());
		commSubs.setBizCode(vo.getBizCode());
		commSubsMapper.insertSelective(commSubs);
		List<TpdCommSubsDetals> detals = vo.getCommSubsDetals();
		for (TpdCommSubsDetals tpdCommSubsDetals : detals) {
			tpdCommSubsDetals.setCommSubsId(commSubs.getPkid());
			tpdCommSubsDetals.setCaseCode(vo.getCaseCode());
			tpdCommSubsDetals.setBizCode(vo.getBizCode());
			commSubsDetailsMapper.insertSelective(tpdCommSubsDetals);
		}
	}
}
