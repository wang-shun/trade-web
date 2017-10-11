package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;

@MyBatisRepository
public interface AddRansomFormMapper {
	int insert(ToRansomTailinsVo ar);

	void addRansomForm(List<ToRansomFormVo> list);
	
	ToRansomFormVo selectByCaseCode(String caseCode);
}