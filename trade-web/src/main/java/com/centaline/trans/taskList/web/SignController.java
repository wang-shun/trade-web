package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.template.remote.UamTemplateService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.TsMsgSendHistoryService;
import com.centaline.trans.task.vo.TransSignVO;

@Controller
@RequestMapping(value="/task/sign")
public class SignController {
	
	@Autowired
	private SignService signService;

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired(required=true)
    private UamTemplateService uamTemplateService;
	
	@Autowired
	private UamUserOrgService uamUserOrgService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;

	@Autowired
    private UamBasedataService   uambasedataService;
	
	@Autowired
	private TsMsgSendHistoryService tsmsgSendHistoryService;
	
	@RequestMapping(value="/saveSign")
	public String saveSign(HttpServletRequest request, TransSignVO transSignVO) {
		signService.insertGuestInfo(transSignVO);
		return "task/task"+transSignVO.getPartCode();
	}
	
	
	@RequestMapping(value="submitSign")
	@ResponseBody
	public Result submitSign(HttpServletRequest request, TransSignVO transSignVO) {
		signService.insertGuestInfo(transSignVO);
		
		try{
		/*流程引擎相关*/
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		/* start 查限购和有抵押工作流   作者：zhangxb16 时间：2016-1-27 */
		RestVariable restVariable3 = new RestVariable();/* 限购 */
		restVariable3.setName("PurLimitCheckNeed");
		RestVariable restVariable4 = new RestVariable();/* 抵押 */
		restVariable4.setName("LoanCloseNeed");
		restVariable3.setValue(transSignVO.getIsPerchaseReserachNeed().equals("true"));
		restVariable4.setValue(transSignVO.getIsLoanClose().equals("true"));
		variables.add(restVariable3);
		variables.add(restVariable4);
		/* end 查限购和有抵押工作流   作者：zhangxb16 时间：2016-1-27 */
		
		ToCase toCase=toCaseService.findToCaseByCaseCode(transSignVO.getCaseCode());	
		workFlowManager.submitTask(variables, transSignVO.getTaskId(), transSignVO.getProcessInstanceId(), 
				toCase.getLeadingProcessId(), transSignVO.getCaseCode());
		} catch(Exception e) {
			e.printStackTrace();
			// return false;
		} 

		/*修改案件状态*/
		ToCase toCase = new ToCase();
		toCase.setCaseCode(transSignVO.getCaseCode());
		toCase.setStatus("30001003");
		toCaseService.updateByCaseCodeSelective(toCase);
		
		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(transSignVO.getCaseCode(), transSignVO.getPartCode());
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}
		
		return rs;
	}
	
	@RequestMapping(value="/removeGuest")
	@ResponseBody
	public Boolean removeGuest(HttpServletRequest request, Long pkid) {
		tgGuestInfoService.removeGuestInfo(pkid);
		return true;
	}
	
	@RequestMapping(value="queryGuestInfo")
	@ResponseBody
	public List<TgGuestInfo> queryGuestInfo(String caseCode, String transPosition) {
		TgGuestInfo tgGuestInfo = new TgGuestInfo();
		tgGuestInfo.setCaseCode(caseCode);
		tgGuestInfo.setTransPosition(transPosition);
		List<TgGuestInfo> list = tgGuestInfoService.findTgGuestInfosByCaseCode(tgGuestInfo);
		return list;
	}
}
