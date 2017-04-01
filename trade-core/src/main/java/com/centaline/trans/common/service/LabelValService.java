package com.centaline.trans.common.service;

import java.util.List;

import com.centaline.trans.common.vo.LabelVal;

public interface LabelValService {
	List<LabelVal> queryUserInfo(String keyword);
	List<LabelVal> queryOrgInfo(String keyword);
}
