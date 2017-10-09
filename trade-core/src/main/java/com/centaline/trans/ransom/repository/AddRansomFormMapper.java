package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.AddRansomForm;
import com.centaline.trans.ransom.entity.ToRansomFormVo;

@MyBatisRepository
public interface AddRansomFormMapper {
	int insert(AddRansomForm ar);

	void addRansomForm(List<ToRansomFormVo> list);
}