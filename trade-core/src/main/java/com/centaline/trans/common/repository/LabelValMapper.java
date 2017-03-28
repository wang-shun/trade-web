package com.centaline.trans.common.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.common.vo.LabelVal;
@MyBatisRepository
public interface LabelValMapper {
	List<LabelVal> queryUserInfo(String keyword);
	List<LabelVal> queryOrgInfo(String keyword);
}
