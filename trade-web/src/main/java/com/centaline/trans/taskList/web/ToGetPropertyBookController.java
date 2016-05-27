package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.exception.BusinessException;
import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.basedata.remote.vo.Dict;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.entity.ToPropertyInfo;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToPropertyInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.entity.ToGetPropertyBook;
import com.centaline.trans.task.entity.TsMsgSendHistory;
import com.centaline.trans.task.service.ToGetPropertyBookService;
import com.centaline.trans.task.service.TsMsgSendHistoryService;

@Controller
@RequestMapping(value="/task/tgpb")
public class ToGetPropertyBookController {

	@Autowired
	private ToGetPropertyBookService toGetPropertyBookService;
	
	@Autowired(required = true)
	private ToCaseService toCaseService;
	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Autowired
	private ToPropertyInfoService topropertyInfoService;
	
	@Autowired
    private UamBasedataService   uambasedataService;
	
	@Autowired
	private UamSessionService uamSessionService;
	
	@Autowired
	private TsMsgSendHistoryService tsmsgSendHistoryService;
	
	
	@RequestMapping(value="saveToGetPropertyBook")
	@ResponseBody
	public AjaxResponse<Boolean> saveToGetPropertyBook(HttpServletRequest request, ToGetPropertyBook toGetPropertyBook) {
		boolean isSuccess = toGetPropertyBookService.saveToGetPropertyBook(toGetPropertyBook);
		
		return new AjaxResponse<Boolean>(isSuccess,"保存出错");
	}
	

	@RequestMapping(value="submitToGetPropertyBook")
	@ResponseBody
	public Result submitPricing(HttpServletRequest request, ToGetPropertyBook toGetPropertyBook,
			String taskId, String processInstanceId) {
		try {
			toGetPropertyBookService.saveToGetPropertyBook(toGetPropertyBook);
			
			/*流程引擎相关*/
			List<RestVariable> variables = new ArrayList<RestVariable>();
			ToCase toCase = toCaseService.findToCaseByCaseCode(toGetPropertyBook.getCaseCode());	
			workFlowManager.submitTask(variables, taskId, processInstanceId, 
					toCase.getLeadingProcessId(), toGetPropertyBook.getCaseCode());

			/*修改案件状态*/
			toCase.setStatus("30001005");
			toCaseService.updateByCaseCodeSelective(toCase);
		} catch(Exception e) {
			e.printStackTrace();
			// return false;
		}
		

		/**
		 * 功能: 给客户发送短信
		 * 作者：zhangxb16
		 */
		Result rs=new Result();
		try{
			int result=tgGuestInfoService.sendMsgHistory(toGetPropertyBook.getCaseCode(), toGetPropertyBook.getPartCode());
			if(result>0){
			}else{
				rs.setMessage("短信发送失败, 请您线下手工再次发送！");
			}
		}catch(BusinessException ex){
			ex.getMessage();
		}
		
		return rs;
	}
	
}
