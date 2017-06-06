package com.centaline.parportal.mobile.taskflow.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.cases.entity.ToCase;
import com.centaline.trans.cases.service.ToCaseService;
import com.centaline.trans.cases.vo.CaseBaseVO;
import com.centaline.trans.engine.bean.RestVariable;
import com.centaline.trans.engine.service.WorkFlowManager;
import com.centaline.trans.transplan.service.TransplanServiceFacade;
import com.centaline.trans.transplan.vo.TransPlanVO;

/**
 * 填写交易计划环节controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "/task/transPlan")
public class TransPlanController
{

    @Autowired
    private TransplanServiceFacade transplanServiceFacade;

    @Autowired(required = true)
    private ToCaseService toCaseService;
    @Autowired
    private WorkFlowManager workFlowManager;

    /**
     * 填写交易计划详情
     * 
     * @param caseCode
     *            案件编号
     * @param instCode
     *            流程实例id
     * @return 返回交易计划详情信息
     */
    @RequestMapping("process")
    @ResponseBody
    public Object toProcess(String caseCode, String taskId, String processInstanceId)
    {
        JSONObject jsonObject = new JSONObject();

        CaseBaseVO caseBaseVO = toCaseService.getCaseBaseVO(caseCode);
        int cou = toCaseService.findToLoanAgentByCaseCode(caseCode);
        if (cou > 0)
        {
            caseBaseVO.setLoanType("30004005");
        }

        RestVariable dy = workFlowManager.getVar(processInstanceId, "LoanCloseNeed");/* 抵押 */
        RestVariable psf = workFlowManager.getVar(processInstanceId, "PSFLoanNeed");/* 公积金 */
        RestVariable self = workFlowManager.getVar(processInstanceId, "SelfLoanNeed");/* 自办 */
        RestVariable com = workFlowManager.getVar(processInstanceId, "ComLoanNeed");/* 贷款 */
        boolean dk = ((boolean) (psf == null ? false : psf.getValue()) || (boolean) (self == null ? false : self.getValue()) || (boolean) (com == null ? false
                : com.getValue()));

        jsonObject.put("taskId", taskId);
        jsonObject.put("processInstanceId", processInstanceId);
        jsonObject.put("caseBaseVO", caseBaseVO);
        jsonObject.put("dy", dy == null ? false : dy.getValue());
        jsonObject.put("dk", dk);
        jsonObject.put("transPlan", transplanServiceFacade.findTransPlanByCaseCode(caseCode));

        return jsonObject;
    }

    /**
     * 保存交易计划信息
     * 
     * @param transPlanVO
     *            交易计划信息
     * @return 保存结果
     */
    @RequestMapping(value = "saveTransPlan")
    @ResponseBody
    public Object saveTransPlan(TransPlanVO transPlanVO)
    {
        AjaxResponse response = new AjaxResponse();

        try
        {
            transplanServiceFacade.saveToTransPlan(transPlanVO);

            response.setSuccess(true);
            response.setMessage("交易计划信息保存成功！");
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage("交易计划信息保存失败！");
            e.printStackTrace();
        }

        return response;
    }

    /**
     * 提交交易计划信息
     * 
     * @param transPlanVO
     *            交易计划信息
     * @return 提交结果
     */
    @RequestMapping(value = "submitTransPlan")
    @ResponseBody
    public Object submitTransPlan(TransPlanVO transPlanVO)
    {
        AjaxResponse response = new AjaxResponse();

        try
        {
            transplanServiceFacade.saveToTransPlan(transPlanVO);

            /* 流程引擎相关 */
            List<RestVariable> variables = new ArrayList<RestVariable>();

            ToCase toCase = toCaseService.findToCaseByCaseCode(transPlanVO.getCaseCode());

            workFlowManager.submitTask(variables, transPlanVO.getTaskId(), transPlanVO.getProcessInstanceId(), toCase.getLeadingProcessId(),
                    transPlanVO.getCaseCode());

            response.setSuccess(true);
            response.setMessage("交易计划信息提交成功！");
        }
        catch (Exception e)
        {
            response.setSuccess(false);
            response.setMessage("交易计划信息提交失败！");
            e.printStackTrace();
        }

        return response;
    }
}
