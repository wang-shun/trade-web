package com.centaline.trans.ransom.service.impl;

import java.util.List;

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
	private AddRansomFormMapper arm;
	
	@Override
	public void addRansomForm(List<ToRansomFormVo> ar) {
		arm.addRansomForm(ar);
	}

}
