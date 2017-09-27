package com.centaline.trans.eloan.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.eloan.entity.ToAppRecordInfo;

/**
 * 
 * @author wblujian
 *
 */
@MyBatisRepository
public interface ToAppRecordInfoMapper {

	void insertAppRecordInfo(List<ToAppRecordInfo> list);
}
