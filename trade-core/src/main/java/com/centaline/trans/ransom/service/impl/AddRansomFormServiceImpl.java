package com.centaline.trans.ransom.service.impl;

import java.util.List;

import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centaline.trans.ransom.entity.ToRansomFormVo;
import com.centaline.trans.ransom.repository.AddRansomFormMapper;
import com.centaline.trans.ransom.service.AddRansomFormService;

@Service
@Transactional
public class AddRansomFormServiceImpl implements AddRansomFormService {

	@Autowired
	private AddRansomFormMapper addRansomFormMapper;
	
	@Override
	public void addRansomForm(List<ToRansomFormVo> ar) {
		addRansomFormMapper.addRansomForm(ar);
	}

	@Override
	public int insert(ToRansomTailinsVo ar) {
		return addRansomFormMapper.insert(ar);
	}

}
