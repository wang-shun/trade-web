package com.centaline.parportal.mobile.taskflow.web;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.attachment.service.ToAccesoryListService;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.common.service.MessageService;
import com.centaline.trans.common.service.TgGuestInfoService;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.mortgage.entity.ToMortgage;
import com.centaline.trans.mortgage.service.MortStepService;
import com.centaline.trans.mortgage.service.ToEguPricingService;
import com.centaline.trans.mortgage.service.ToMortgageService;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caoyuan7 on 2017/4/20.
 */
@Controller
@RequestMapping(value="/task")
public class MortgageController {

    private Logger logger = Logger.getLogger(MortgageController.class);

    @Autowired
    private ToMortgageService toMortgageService;
    @Autowired
    private TransplanServiceFacade transplanServiceFacade;

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;

    @Autowired
    private MortStepService mortStepService;

    @Autowired
    private TgGuestInfoService tgGuestInfoService;
    @Autowired
    private ToEguPricingService toEguPricingService;
    @Autowired
    private ToAccesoryListService toAccesoryListService;
    @Autowired
    MessageService messageService;
    @Autowired
    private ToWorkFlowService toWorkFlowService;
    @Autowired
    private UamSessionService uamSessionService;

    @RequestMapping(value="mortgage/submitPsfApply")
    @ResponseBody
    public  Object submitPsfApply(String caseCode,Date estPartTime,String ifRequireReconsider,Date prfApplyDate,String taskId, String processInstanceId) {
        AjaxResponse<?> response = new AjaxResponse<>();
        try {
            if(caseCode==null||"".equals(caseCode)){response.setSuccess(false);response.setMessage("案件编号为空");return response;}
            if(taskId==null||"".equals(taskId)||processInstanceId==null||"".equals(processInstanceId)){response.setSuccess(false);response.setMessage("环节编码为空");return response;}
            if(ifRequireReconsider==null||"".equals(ifRequireReconsider)){response.setSuccess(false);response.setMessage("是否需要复议为空");return response;}
            if(prfApplyDate==null){response.setSuccess(false);response.setMessage("公积金申请时间为空");return response;}

            ToTransPlan toTransPlan = new ToTransPlan();
            toTransPlan.setCaseCode(caseCode);
            toTransPlan.setPartCode("PSFApply");
            toTransPlan.setEstPartTime(estPartTime);
            transplanServiceFacade.updateTransPlan(toTransPlan);

            ToMortgage mortgage =  toMortgageService.findToMortgageByCaseCodeNoBlank(caseCode).get(0);
            mortgage.setIsDelegateYucui("1");
            mortgage.setIsMainLoanBank("1");
            mortgage.setIfRequireReconsider(ifRequireReconsider);
            mortgage.setPrfApplyDate(prfApplyDate);
            SessionUser user = uamSessionService.getSessionUser();
            mortgage.setLoanAgent(user.getId());
            mortgage.setLoanAgentTeam(user.getServiceDepId());
            toMortgageService.saveToMortgage(mortgage);

		/*流程引擎相关*/
            List<RestVariable> variables = new ArrayList<RestVariable>();
            ToCase toCase = toCaseService.findToCaseByCaseCode(caseCode);
            workFlowManager.submitTask(variables, taskId, processInstanceId,toCase.getLeadingProcessId(), caseCode);
            response.setSuccess(true);
            response.setMessage("公积金申请提交成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setMessage("公积金申请提交失败");
        }
        return response;
    }


    @RequestMapping(value="mortgage/submitSelfLoanApprove")
    @ResponseBody
    public Object submitSelfLoanApprove(HttpServletRequest request,ToMortgage toMortgage,String taskId, String processInstanceId) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        try{
            toMortgageService.submitSelfLoanApprove(request, toMortgage, taskId, processInstanceId);
            ajaxResponse.setSuccess(true);
            ajaxResponse.setMessage("自办贷款补充提交成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            ajaxResponse.setSuccess(false);
            ajaxResponse.setMessage("自办贷款补充提交失败");
        }
        return ajaxResponse;
    }
}
