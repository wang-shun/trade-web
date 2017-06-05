package com.centaline.trans.task.service;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.task.vo.PSFSignVO;

public interface PSFSignService {

	public Boolean savePSFSign(PSFSignVO psfSignVO);
	
	public PSFSignVO queryPSFSign(String caseCode);

	public PSFSignVO queryPSFSignNoBlank(String caseCode);

	AjaxResponse saveAndSubmitPSFSign(PSFSignVO psfSignVO);


}
