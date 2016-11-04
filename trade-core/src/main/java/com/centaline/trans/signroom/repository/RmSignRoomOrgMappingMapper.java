package com.centaline.trans.signroom.repository;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.signroom.entity.RmSignRoomOrgMapping;

@MyBatisRepository
public interface RmSignRoomOrgMappingMapper {
	int insert(RmSignRoomOrgMapping record);

	int insertSelective(RmSignRoomOrgMapping record);

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