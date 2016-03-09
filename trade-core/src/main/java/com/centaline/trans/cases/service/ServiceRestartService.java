package com.centaline.trans.cases.service;

import com.centaline.trans.cases.vo.ServiceRestartVo;
import com.centaline.trans.engine.vo.StartProcessInstanceVo;

public interface ServiceRestartService {
	StartProcessInstanceVo restart(ServiceRestartVo vo);
	boolean apply(ServiceRestartVo vo);
	boolean approve(ServiceRestartVo vo);
	
}
