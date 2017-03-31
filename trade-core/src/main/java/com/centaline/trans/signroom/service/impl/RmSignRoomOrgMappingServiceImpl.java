package com.centaline.trans.signroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.signroom.entity.RmSignRoomOrgMapping;
import com.centaline.trans.signroom.repository.RmSignRoomOrgMappingMapper;
import com.centaline.trans.signroom.service.RmSignRoomOrgMappingService;

@Service
public class RmSignRoomOrgMappingServiceImpl implements
		RmSignRoomOrgMappingService {

	@Autowired
	private RmSignRoomOrgMappingMapper rmSignRoomOrgMappingMapper;

	@Override
	public RmSignRoomOrgMapping getOrgMappingInfoByTradeCenterId(
			Long tradeCenterId) {
		return rmSignRoomOrgMappingMapper
				.getOrgMappingInfoByTradeCenterId(tradeCenterId);
	}

}
