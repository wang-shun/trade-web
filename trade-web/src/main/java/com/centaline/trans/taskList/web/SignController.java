package com.centaline.trans.taskList.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.aist.common.exception.BusinessException;
import com.aist.message.core.remote.UamMessageService;
import com.aist.uam.permission.remote.UamPermissionService;
import com.aist.uam.permission.remote.vo.App;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.cases.web.Result;
import com.centaline.trans.common.entity.TgGuestInfo;
import com.centaline.trans.common.enums.AppTypeEnum;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.common.service.ToAccesoryListService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.task.service.SignService;
import com.centaline.trans.task.service.ToHouseTransferService;
import com.centaline.trans.task.vo.TransSignVO;

@Controller
@RequestMapping(value="/task/sign")
public class SignController {
	
	@Autowired
	private SignService signService;
	
	@Autowired
	private ToMortgageService toMortgageService;

	@Autowired(required = true)
	private ToCaseService toCaseService;

	@Autowired
	private WorkFlowManager workFlowManager;
	
	@Autowired
	private TgGuestInfoService tgGuestInfoService;
	
	@Qualifier("uamMessageServiceClient")
    @Autowired
    private UamMessageService uamMessageService;
	@Autowired
	private ToAccesoryListService toAccesoryListService;
	
	@Autowired
	private ToHouseTransferService toHouseTransferService;
	@Autowired
	private UamPermissionService uamPermissionService;
	@RequestMapping("process")
	public String toLoanLostApproveManagerProcess(HttpServletRequest request, HttpServletResponse response,
			String caseCode, String source, String taskitem, String processInstanceId) {
		CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
		request.setAttribute("source", source);
		request.setAttribute("caseBaseVO", caseBaseVO);
		
		toAccesoryListService.getAccesoryList(request, taskitem);
		request.setAttribute("transSign", signService.qureyGuestInfo(caseCode));
		request.setAttribute("houseTransfer", toHouseTransferService.findToGuoHuByCaseCode(caseCode));
		
	    App app = uamPermissionService.getAppByAppName(AppTypeEnum.APP_FILESVR.getCode());
	    request.setAttribute("imgweb", app.genAbsoluteUrl());
		return "task/taskTransSign";
	}
	@RequestMapping(value="/saveSign")
	public String saveSign(HttpServletRequest request, TransSignVO transSignVO) {
		signService.insertGuestInfo(transSignVO);
		
		//同时需要修改贷款表里面的 主贷人信息	
		ToMortgage toMortgage=new ToMortgage();		
		List<Long>  pkidDownList=new ArrayList<Long>();
		if(null!=transSignVO){
			toMortgage.setCaseCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
			pkidDownList = transSignVO.getPkidDown();
			for(int i=0;i<pkidDownList.size();i++){
				toMortgage.setCustCode(String.valueOf(pkidDownList.get(i)));
				ToMortgage getMortgageByCode = toMortgageService.findToMortgageByCaseCodeAndCustcode(toMortgage);
				if(null != getMortgageByCode){
					//不为空 说明更新了主贷人信息
					ToMortgage toMortgageForUpdate=new ToMortgage();
					toMortgageForUpdate.setCaseCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
					toMortgageForUpdate.setCustCode(String.valueOf(pkidDownList.get(i)));
					TgGuestInfo tgGuestInfo=tgGuestInfoService.findTgGuestInfoById(pkidDownList.get(i));
					if(tgGuestInfo!=null){
						toMortgageForUpdate.setCustName(tgGuestInfo.getGuestName()==null?"":tgGuestInfo.getGuestName());
					}					
					toMortgageService.updateToMortgageBySign(toMortgageForUpdate);
				}else{
					//为空 说明已选的主贷人已被删除、清空主贷表对应casecode的主贷人信息
					toMortgageService.updateToMortgageByCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
				}
			}
			
		}
		
		return "task/task"+transSignVO.getPartCode();
	}
	
	
	@RequestMapping(value="submitSign")
	@ResponseBody
	public Result submitSign(HttpServletRequest request, TransSignVO transSignVO) {
		//签约保存信息先更新 客户信息表 
		signService.insertGuestInfo(transSignVO);
		
		//同时需要修改贷款表里面的 主贷人信息	
		ToMortgage toMortgage=new ToMortgage();		
		List<Long>  pkidDownList=new ArrayList<Long>();
		if(null!=transSignVO){
			toMortgage.setCaseCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
			pkidDownList = transSignVO.getPkidDown();
			for(int i=0;i<pkidDownList.size();i++){
				toMortgage.setCustCode(String.valueOf(pkidDownList.get(i)));
				ToMortgage getMortgageByCode = toMortgageService.findToMortgageByCaseCodeAndCustcode(toMortgage);
				if(null != getMortgageByCode){
					//不为空 说明更新了主贷人信息
					ToMortgage toMortgageForUpdate=new ToMortgage();
					toMortgageForUpdate.setCaseCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
					toMortgageForUpdate.setCustCode(String.valueOf(pkidDownList.get(i)));
					TgGuestInfo tgGuestInfo=tgGuestInfoService.findTgGuestInfoById(pkidDownList.get(i));
					if(tgGuestInfo!=null){
						toMortgageForUpdate.setCustName(tgGuestInfo.getGuestName()==null?"":tgGuestInfo.getGuestName());
					}					
					toMortgageService.updateToMortgageBySign(toMortgageForUpdate);
				}else{
					//为空 说明已选的主贷人已被删除、清空主贷表对应casecode的主贷人信息
					toMortgageService.updateToMortgageByCode(transSignVO.getCaseCode()==null?"":transSignVO.getCaseCode());
				}
			}
			
		}
		
		//toMortgageService.updateToMortgage(toMortgage);
		
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
