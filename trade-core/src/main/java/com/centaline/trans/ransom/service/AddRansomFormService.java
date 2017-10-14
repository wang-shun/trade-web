package com.centaline.trans.ransom.service;

import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;

public interface AddRansomFormService {
	
	void addRansomForm(List<ToRansomFormVo> list);
	int insert(ToRansomTailinsVo ar);
}

