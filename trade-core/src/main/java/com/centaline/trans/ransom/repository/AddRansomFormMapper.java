package com.centaline.trans.ransom.repository;

import java.util.List;

import com.centaline.trans.common.MyBatisRepository;
import com.centaline.trans.ransom.entity.ToRansomTailinsVo;
import com.centaline.trans.ransom.entity.ToRansomFormVo;

@MyBatisRepository
public interface AddRansomFormMapper {
	int insert(ToRansomTailinsVo ar);

	int insertRansomForm(List<ToRansomFormVo> list);
	
	ToRansomFormVo selectByCaseCode(String caseCode);
	
	/**
	 * 根据案件编号获取尾款信息
	 * @author wbcaiyx
	 * @param caseCode
	 * @return
	 */
	List<ToRansomFormVo> findTaiLinsInfoByCaseCode(String caseCode);
}