package com.centaline.trans.signroom.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.repository.TradeCenterMapper;
import com.centaline.trans.signroom.service.TradeCenterService;
import com.centaline.trans.signroom.vo.Consultant;

@Service
public class TradeCenterServiceImpl implements TradeCenterService {

	@Autowired
	private TradeCenterMapper tradeCenterMapper;

	@Override
	public List<TradeCenter> getTradeCenterList() {
		return tradeCenterMapper.getTradeCenterList();
	}

	@Override
	public TradeCenter getTradeCenter(Map map) {
		return tradeCenterMapper.getTradeCenter(map);
	}

	@Override
	public List<Consultant> getConsultantListByTradecentId(Long tradeCenterId) {
		return tradeCenterMapper.getConsultantListByTradecentId(tradeCenterId);
	}

}
