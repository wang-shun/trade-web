package com.centaline.trans.signroom.repository;

import com.centaline.trans.signroom.entity.RmSignRoomOrgMapping;

public interface RmSignRoomOrgMappingMapper {
    int insert(RmSignRoomOrgMapping record);

    int insertSelective(RmSignRoomOrgMapping record);
}