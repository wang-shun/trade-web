package com.centaline.trans.ransom.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.service.RansomService;
import com.centaline.trans.task.entity.ToApproveRecord;
import com.centaline.trans.task.service.LoanlostApproveService;
import com.centaline.trans.task.vo.LoanlostApproveVO;
import com.centaline.trans.task.vo.ProcessInstanceVO;

/**
 * <font color=red>流程引擎 任务处理</font>
 * @author wbwumf
 */
@Controller
@RequestMapping("/ransomDiscontinue")
public class RansomDiscontinueController {
	
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired(required=true)
	private RansomService ransomService;
	
	@Autowired
	private LoanlostApproveService loanlostApproveService;

	@RequestMapping("/process")
	public String process(String ransomCode, String taskId, String source, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes attr){
		
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		request.setAttribute("detailVo", detailVo);
		
		return null;
	}
	
	@RequestMapping("/submitDiscontinue")
	@ResponseBody
	public boolean submitDiscontinue(HttpServletRequest request, ProcessInstanceVO processInstanceVO,
			LoanlostApproveVO loanlostApproveVO){
		ToApproveRecord toApproveRecord = saveToApproveRecord(processInstanceVO, loanlostApproveVO, "", "中止赎楼提交。");
		
		List<RestVariable> variables = new ArrayList<RestVariable>();
		return workFlowManager.submitTask(variables, processInstanceVO.getTaskId(), processInstanceVO.getProcessInstanceId(), 
				"toCase.getLeadingProcessId()", processInstanceVO.getCaseCode());
	}
	
	/**
	 * 保存审批记录
	 * @param processInstanceVO
	 * @param loanlostApproveVO
	 * @param loanLost
	 * @param loanLost_response
	 * @return
	 */
	private ToApproveRecord saveToApproveRecord(ProcessInstanceVO processInstanceVO, LoanlostApproveVO loanlostApproveVO,
			String loanLost, String loanLost_response) {
		ToApproveRecord toApproveRecord = new ToApproveRecord();
		toApproveRecord.setProcessInstance(processInstanceVO.getProcessInstanceId());
		toApproveRecord.setPartCode(processInstanceVO.getPartCode());
		toApproveRecord.setOperatorTime(new Date());
		toApproveRecord.setApproveType(loanlostApproveVO.getApproveType());
		toApproveRecord.setCaseCode(processInstanceVO.getCaseCode());
		boolean b = loanLost.equals("true");
		toApproveRecord.setContent((b?"通过":"不通过") + (",审批意见为"+loanLost_response));
		toApproveRecord.setOperator(loanlostApproveVO.getOperator());
		loanlostApproveService.saveLoanlostApprove(toApproveRecord);
		return toApproveRecord;
	}
}
