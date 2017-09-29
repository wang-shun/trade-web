package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.AddRansomForm;

@MyBatisRepository
public interface AddRansomFormMapper {

	int insert(AddRansomForm ar);

	void addRansomForm(List<AddRansomForm> list);
}
