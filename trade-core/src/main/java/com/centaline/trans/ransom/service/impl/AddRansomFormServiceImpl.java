package com.centaline.trans.ransom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aist.common.exception.BusinessException;
<<<<<<< Upstream, based on origin/develop
import com.centaline.trans.ransom.entity.AddRansomForm;
import com.centaline.trans.ransom.repository.AddRansomFormMapper;
import com.centaline.trans.ransom.service.AddRansomFormService;

@Service
@Transactional
public class AddRansomFormServiceImpl implements AddRansomFormService {

	@Autowired
	private AddRansomFormMapper arm;
	
	@Override
	public int insert(AddRansomForm ar) {
		int status = -1; // -1：接收异常  0：接收正常 
		int res = arm.insert(ar);
		if(res > 0){
		}else{
			throw new BusinessException("抱歉,案件表没有新增成功,请刷新后再次尝试!");
		}
		status = 0;
		return status;
	}

	@Override
	public void addRansomForm(List<AddRansomForm> ar) {
=======
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
>>>>>>> 119371f 解决冲突
		arm.addRansomForm(ar);
	}

}
