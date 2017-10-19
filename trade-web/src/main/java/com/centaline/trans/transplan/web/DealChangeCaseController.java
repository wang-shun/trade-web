package com.centaline.trans.transplan.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.Org;
import com.centaline.trans.common.enums.DepTypeEnum;
import com.centaline.trans.common.enums.TransJobs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.centaline.trans.transplan.entity.TtsReturnVisitRegistration;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TsTransPlanHistoryVO;

/**
 * 交易计划变更控制器
 * @author zhoujp7
 *
 */
@Controller
@RequestMapping(value="/transplan")
public class DealChangeCaseController {
	
	@Resource
	TransplanServiceFacade toTransplanOperateService;
	@Autowired(required = true)
	private UamSessionService uamSessionService;
	@Autowired(required = true)
	private UamUserOrgService uamUserOrgService;
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	/**
	 * 交易计划变更案件列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value="dealChangeCaseList")
	public String dealChangeCaseList(Model model, HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//获取当前月第一天：
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		//获取当前月最后一天
		Calendar ca = Calendar.getInstance();    
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

		/**
		 * 查询用户所在组
		 */
		SessionUser user = uamSessionService.getSessionUser();
		String userJob=user.getServiceJobCode();
		boolean queryOrgFlag = false;
		boolean isAdminFlag = false;

		StringBuffer reBuffer = new StringBuffer();
		//如果登录用户不是交易顾问
		//TODO 如果非交易顾问(过户/贷款权证),查组织内案件
		if(!userJob.equals(TransJobs.TJYGW.getCode())){
			queryOrgFlag=true;
			String depString = user.getServiceDepHierarchy();
			String userOrgIdString = user.getServiceDepId();
			//组别
			if(depString.equals(DepTypeEnum.TYCTEAM.getCode())){
				reBuffer.append(userOrgIdString);
				//区域
			}else if(depString.equals(DepTypeEnum.TYCQY.getCode())){
				List<Org> orgList = uamUserOrgService.getOrgByDepHierarchy(userOrgIdString, DepTypeEnum.TYCTEAM.getCode());
				for(Org org:orgList){
					reBuffer.append(org.getId());
					reBuffer.append(",");
				}
				reBuffer.deleteCharAt(reBuffer.length()-1);

			}else{
				isAdminFlag=true;//总部flag
			}
		}
		request.setAttribute("queryOrgs", reBuffer.toString());

		request.setAttribute("queryOrgFlag", queryOrgFlag);
		request.setAttribute("isAdminFlag", isAdminFlag);
		request.setAttribute("userId", user.getId());
		request.setAttribute("serviceDepId", user.getServiceDepId());//登录用户的org_id
		request.setAttribute("serviceDepName", user.getServiceDepName());

		model.addAttribute("curMonthStart", sdf.format(c.getTime()));
		model.addAttribute("curMonthEnd", sdf.format(ca.getTime()));
		return "transplan/dealChangeList";
	}
	
	/**
	 * 新增案件回访
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="addReturnVisit")
	@ResponseBody
	public AjaxResponse<TtsReturnVisitRegistration> addReturnVisit(TtsReturnVisitRegistration ttsReturnVisitRegistration){
		AjaxResponse<TtsReturnVisitRegistration> response = new AjaxResponse<TtsReturnVisitRegistration>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = Calendar.getInstance().getTime();
			ttsReturnVisitRegistration.setCreateTime(sdf.format(date));
			ttsReturnVisitRegistration.setCrtTime(date);
			toTransplanOperateService.addReturnVisit(ttsReturnVisitRegistration);
			response.setContent(ttsReturnVisitRegistration);
			response.setCode("400");
			response.setMessage("案件回访处理成功！");
			response.setSuccess(true);
		}catch(Exception e){
			logger.error("案件回访处理失败:", e);
			response.setCode("500");
			response.setMessage("案件回访处理失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	/**
	 * 查询交易变更历史信息
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="queryTtsTransPlanHistorys")
	@ResponseBody
	public AjaxResponse<List<TsTransPlanHistoryVO>> queryTtsTransPlanHistorys(TsTransPlanHistoryVO tsTransPlanHistoryVO){
		AjaxResponse<List<TsTransPlanHistoryVO>> response = new AjaxResponse<List<TsTransPlanHistoryVO>>();
		List<TsTransPlanHistoryVO>  ttp = null;
		try{
			ttp =  toTransplanOperateService.queryTtsTransPlanHistorys(tsTransPlanHistoryVO);
			response.setCode("400");
			response.setMessage("查询交易变更历史成功！");
			response.setSuccess(true);
			response.setContent(ttp);
		}catch(Exception e){
			logger.error("查询交易变更历史失败:", e);
			response.setCode("500");
			response.setMessage("查询交易变更历史失败！");
			response.setSuccess(false);
		}
		return response;
	}
	/**
	 * 查询回访跟进历史信息
	 * @param ttsReturnVisitRegistration
	 * @return
	 */
	@RequestMapping(value="queryReturnVisitHistorys")
	@ResponseBody
	public AjaxResponse<List<TtsReturnVisitRegistration>> queryReturnVisitHistorys(Long batchId){
		AjaxResponse<List<TtsReturnVisitRegistration>> response = new AjaxResponse<List<TtsReturnVisitRegistration>>();
		List<TtsReturnVisitRegistration>  ttp = null;
		try{
			ttp =  toTransplanOperateService.queryReturnVisitRegistrations(batchId);
			response.setCode("400");
			response.setMessage("查询回访跟进历史成功！");
			response.setSuccess(true);
			response.setContent(ttp);
		}catch(Exception e){
			logger.error("查询回访跟进历史失败:", e);
			response.setCode("500");
			response.setMessage("查询回访跟进历史失败！");
			response.setSuccess(false);
		}
		return response;
	}
	
	
	
}
