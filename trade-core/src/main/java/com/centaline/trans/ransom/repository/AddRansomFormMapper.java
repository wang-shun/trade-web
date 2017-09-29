package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
<<<<<<< Upstream, based on origin/develop
import com.centaline.trans.ransom.entity.AddRansomForm;

@MyBatisRepository
public interface AddRansomFormMapper {

	int insert(AddRansomForm ar);

	void addRansomForm(List<AddRansomForm> list);
=======
import com.centaline.trans.ransom.entity.ToRansomFormVo;

@MyBatisRepository
public interface AddRansomFormMapper {

	void addRansomForm(List<ToRansomFormVo> list);
>>>>>>> 119371f 解决冲突
}
