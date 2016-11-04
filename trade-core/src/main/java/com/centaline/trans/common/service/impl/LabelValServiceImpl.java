package com.centaline.trans.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centaline.trans.common.repository.LabelValMapper;
import com.centaline.trans.common.service.LabelValService;
import com.centaline.trans.common.vo.LabelVal;
@Service
public class LabelValServiceImpl implements LabelValService {
	@Autowired
	private LabelValMapper labelValMapper;
	@Override
	public List<LabelVal> queryUserInfo(String keyword) {
		return labelValMapper.queryUserInfo(keyword);
	}
	@Override
	public List<LabelVal> queryOrgInfo (String keyword) {
		return labelValMapper.queryOrgInfo(keyword);
	}

}
