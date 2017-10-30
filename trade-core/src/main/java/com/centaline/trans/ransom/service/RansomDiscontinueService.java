package com.centaline.trans.ransom.service;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.centaline.trans.ransom.entity.ToRansomCaseVo;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

public interface RansomDiscontinueService {
	
	boolean submitDiscontinueApply(ToRansomCaseVo ransomCase, ProcessInstanceVO processInstanceVO) throws Exception;
	
	public boolean isCanSuspend(ServletRequest request, String ransomCode) throws Exception;
	
	boolean submitDiscontinue(ToRansomCaseVo ransomCase, HttpServletRequest request, ProcessInstanceVO processInstanceVO, String caseCode, String ransomCode) throws Exception;
	
	public Boolean aprroSubmit(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO, String examContent, String remark, String caseCode, String ransomCode)throws Exception;

	public Map<String, Object> getSingleRansomTaskInfo(HttpServletRequest request, Boolean isSuspend, Boolean isSuspended,
			Boolean isIgnoreAssignee, String caseCode);
}
