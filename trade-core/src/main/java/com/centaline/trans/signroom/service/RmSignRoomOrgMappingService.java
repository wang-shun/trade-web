package com.centaline.trans.signroom.service;

import com.centaline.trans.signroom.entity.RmSignRoomOrgMapping;

/**
 * 签约室组别关系信息service接口
 * 
 * @author yinjf2
 *
 */
public interface RmSignRoomOrgMappingService {

	/**
	 * 根据交易中心标识获取对应的组别关系信息
	 * 
	 * @param tradeCenterId
	 *            交易中心标识
	 * @return 组别关系信息
	 */
	public RmSignRoomOrgMapping getOrgMappingInfoByTradeCenterId(
			Long tradeCenterId);
}
