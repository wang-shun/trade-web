package com.centaline.trans.ransom.service;

import java.util.List;

import com.centaline.trans.ransom.entity.AddRansomForm;

public interface AddRansomFormService {
	
	int insert(AddRansomForm ar);
	
	void addRansomForm(List<AddRansomForm> list);
}
