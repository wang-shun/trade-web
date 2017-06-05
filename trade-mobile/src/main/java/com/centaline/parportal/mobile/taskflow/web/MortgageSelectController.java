package com.centaline.parportal.mobile.taskflow.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.service.ToWorkFlowService;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.task.service.MortgageSelectService;
import com.centaline.trans.task.vo.MortgageSelecteVo;
import com.centaline.trans.transplan.entity.ToTransPlan;
import com.centaline.trans.transplan.service.TransplanServiceFacade;

@Controller
@RequestMapping(value = "/task/mortgageSelect")
public class MortgageSelectController
{
    @Autowired
    private UamSessionService uamSessionService;
    @Autowired
    private MortgageSelectService mortgageSelectService;
    @Autowired
    private TransplanServiceFacade transplanServiceFacade;
    @Autowired
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;
    @Autowired
    private ToWorkFlowService toWorkFlowService;

    @ResponseBody
    @RequestMapping(value = "submit")
    public Object submit(MortgageSelecteVo vo)
    {
        AjaxResponse response = new AjaxResponse();

        if (!"2".equals(vo.getMortageService()))
        {// 只有纯公积金才需要选择合作人否则都是取当前用户
            SessionUser u = uamSessionService.getSessionUser();
            vo.setPartner(u.getId());
        }

        boolean result = mortgageSelectService.submit2(vo);

        if (result)
        {
            response.setSuccess(true);
            response.setMessage("贷款需求选择提交成功");
        }
        else
        {
            response.setSuccess(false);
            response.setMessage("贷款需求选择提交失败");
        }

        return response;
    }

    @RequestMapping(value = "process")
    @ResponseBody
    public Object toProcess(String caseCode, String processInstanceId)
    {
        JSONObject jsonObject = new JSONObject();

        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        // 税费卡
        int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
        if (cou > 0)
        {
            caseBaseVO.setLoanType("30004005");
        }

        ToTransPlan plan = new ToTransPlan();
        plan.setCaseCode(caseCode);
        plan.setPartCode("LoanRelease");// 放款

        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("caseCode", caseCode);
        jsonObject.put("caseBaseVO", caseBaseVO);
        jsonObject.put("loanReleasePlan", transplanServiceFacade.findTransPlan(plan));

        return jsonObject;

    }
}
