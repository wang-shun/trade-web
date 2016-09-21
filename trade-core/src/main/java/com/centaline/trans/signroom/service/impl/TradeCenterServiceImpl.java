package com.centaline.trans.signroom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.TradeCenter;
import com.centaline.trans.signroom.repository.TradeCenterMapper;
import com.centaline.trans.signroom.service.TradeCenterService;

@Service
public class TradeCenterServiceImpl implements TradeCenterService {

	@Autowired
	private TradeCenterMapper tradeCenterMapper;

	@Override
	public List<TradeCenter> getTradeCenterList() {
		return tradeCenterMapper.getTradeCenterList();
	}

}
