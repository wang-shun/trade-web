package com.centaline.trans.ransom.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.common.enums.LampEnum;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.ransom.entity.ToRansomDetailVo;
import com.centaline.trans.ransom.service.RansomService;

/**
 * 赎楼单控制器
 * @author wbwumf
 *
 */
@Controller
@RequestMapping(value = "task/ransom")
public class RansomController {
	
	@Value("${process.df.key.Ransom_Process}")
	private String PROCESS_DEFINITION_ID;
	
	@Autowired(required=true)
	UamSessionService uamSessionService;
	
	@Autowired(required=true)
	private RansomService ransomService;
	
	@Autowired(required=true)
	private WorkFlowManager workFlowManager;
	
	/**
	 * 赎楼待办任务列表
	 * by wbshume
	 * @return
	 */
	@RequestMapping(value="/taskList")
	public String taskList(ServletRequest request){
		SessionUser user = uamSessionService.getSessionUser();
		String[] lamps = LampEnum.getCodes();
		request.setAttribute("userId", user.getId());
		request.setAttribute("Lamp1", lamps[0]);
		request.setAttribute("Lamp2", lamps[1]);
		request.setAttribute("Lamp3", lamps[2]);
		request.setAttribute("PROCESS_DEFINITION_ID", PROCESS_DEFINITION_ID);
		return "ransom/ransomTaskList";
	}
	
	/**
	 * 赎楼详情画面 
	 * @param ransomCode 赎楼编号
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomDetail")
	public String ransomDetail(String ransomCode, ServletRequest request){
		
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		
		request.setAttribute("detailVo", detailVo);

		return "ransom/ransomDetail";
	}
	
	/**
	 * 注销抵押
	 * @param taskId     任务id
	 * @param ransomCode 赎楼编号
	 * @param tailPkid   尾款表pkid(区分一抵/二抵)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomCancel")
	public String ransomCancel(String ransomCode,Integer diyaType,ServletRequest request){
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);		
		request.setAttribute("detailVo", detailVo);
		
		request.setAttribute("diyaType", diyaType);

		return "ransom/ransomCancel";
	}
	
	/**
	 * 注销抵押提交
	 * @param taskId             任务id
	 * @param processInstanceId  流程实例id
	 * @param ransomCode         赎楼编号
	 * @param tailPkid           尾款表pkid(区分一抵/二抵)
	 * @param cancelDiyaTime     注销抵押时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value="submitCancelDiya")
	public boolean submitCancelDiya(String taskId, String processInstanceId, 
											String ransomCode, Integer diyaType, 
											Date cancelDiyaTime, ServletRequest request){
		//插入注销抵押数据
		ransomService.updateRansomCancel(ransomCode, diyaType, cancelDiyaTime);
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		boolean result = workFlowManager.submitTask(variables, taskId, processInstanceId, null, null);		
		return result;
	}
	
	
	/**
	 * 领取产证
	 * @param taskId     任务id
	 * @param ransomCode 赎楼编号
	 * @param diyaType           抵押类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomPermit")
	public String ransomPermit(String ransomCode,Integer diyaType,ServletRequest request){

		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		request.setAttribute("detailVo", detailVo);
		
		request.setAttribute("diyaType", diyaType);
		return "ransom/ransomPermit";
	}
	
	/**
	 * 领取产证提交
	 * @param taskId             任务id
	 * @param processInstanceId  流程实例id
	 * @param ransomCode         赎楼编号
	 * @param diyaType           抵押类型
	 * @param permitTime         领取产证时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value="submitPermit")
	public boolean submitPermit(String taskId, String processInstanceId, 
											String ransomCode, Integer diyaType, 
											Date permitTime, ServletRequest request){
		//插入领取产证数据
		ransomService.updateRansomPermit(ransomCode, diyaType, permitTime);
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		boolean result = workFlowManager.submitTask(variables, taskId, processInstanceId, null, null);		
		return result;
	}
	
	
	/**
	 * 回款结清
	 * @param taskId      任务id
	 * @param ransomCode  赎楼编号
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ransomPayment")
	public String ransomPayment(String ransomCode, ServletRequest request){
	
		//公共基本信息
		ToRansomDetailVo detailVo = ransomService.getRansomDetail(ransomCode);
		request.setAttribute("detailVo", detailVo);
	
		return "ransom/ransomPayment";
	}
	
	/**
	 * 回款结清提交
	 * @param taskId             任务id
	 * @param processInstanceId  流程实例id
	 * @param ransomCode         赎楼编号
	 * @param paymentTime        回款结清时间
	 * @param request
	 * @return
	 */
	@RequestMapping(value="submitPayment")
	public boolean submitPayment(String taskId, String processInstanceId, String ransomCode, Date paymentTime, ServletRequest request){
		//插入回款结清数据
		ransomService.updateRansomPayment(ransomCode, paymentTime);
		
		//完成任务流程
		List<RestVariable> variables = new ArrayList<RestVariable>();
		
		boolean result = workFlowManager.submitTask(variables, taskId, processInstanceId, null, null);		
		return result;
	}
}
