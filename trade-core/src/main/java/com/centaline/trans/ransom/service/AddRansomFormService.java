package com.centaline.trans.ransom.service;

import java.util.List;

<<<<<<< Upstream, based on origin/develop
import com.centaline.trans.ransom.entity.AddRansomForm;

public interface AddRansomFormService {
	
	int insert(AddRansomForm ar);
	
	void addRansomForm(List<AddRansomForm> list);
=======
import com.centaline.trans.ransom.entity.ToRansomFormVo;

public interface AddRansomFormService {
	
	void addRansomForm(List<ToRansomFormVo> list);
>>>>>>> 119371f 解决冲突
}
